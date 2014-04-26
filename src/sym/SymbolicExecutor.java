package sym;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import clojure.lang.IPersistentMap;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentVector;
import control.Control;
import sym.Z3Stub.Z3Result;
import util.MultiThreadUtils.SimplifyWorker;

public class SymbolicExecutor {
	public static final int threadPoolSize = 10;
	private CountDownLatch latch;
	private ThreadPoolExecutor executor;
	private List<SimplifyWorker> workers;
	private FileWriter writer;
	private Z3Stub z3;
	private HashMap<String, HashMap<String, sym.op.IOp>> staticObjs;

	// we synchronized on unaccessedClass to avoid init unaccessedClass twice
	private HashMap<String, SimplifyWorker> unaccessedClass;
	private HashMap<String, sim.classs.Class> allClass;
	// contains classes that has clinit called, but not scaned by Kagebunsin
	private ConcurrentLinkedQueue<sim.classs.Class> unScanedClass;

	public final String symResultPath;
	public final String apkOutputPath;

	public SymbolicExecutor(List<SimplifyWorker> workers, File output,
			String apkOutputPath) throws IOException {
		this.staticObjs = new HashMap<String, HashMap<String, sym.op.IOp>>();
		this.workers = workers;
		this.latch = new CountDownLatch(1);
		this.executor = new ThreadPoolExecutor(threadPoolSize,
				2 * threadPoolSize, 5, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		this.writer = new FileWriter(output);
		this.symResultPath = output.getAbsolutePath();
		this.z3 = new Z3Stub();
		this.unaccessedClass = new HashMap<String, SimplifyWorker>();
		this.allClass = new HashMap<String, sim.classs.Class>();
		this.apkOutputPath = apkOutputPath;
		this.unScanedClass = new ConcurrentLinkedQueue<sim.classs.Class>();
	}

	public synchronized void write(String msg) {
		try {
			this.writer.write(msg);
			this.writer.flush();
		} catch (IOException e) {
			printlnErr(String.format("exception %s when writing to %s", e,
					symResultPath));
		}
	}

	public synchronized void writeln(String msg) {
		write(msg + "\n");
	}

	public synchronized Future<?> submit(Runnable task) {
		return executor.submit(task);
	}

	public synchronized Z3Result calculate(PersistentVector types, PersistentVector conditions) {
		return this.z3.calculate(types, conditions);
	}

	public sym.op.IOp sget(String className, String fieldName) {
		getClass(className); // to init the class
		synchronized (staticObjs) {
			HashMap<String, sym.op.IOp> obj = staticObjs.get(className);
			if (obj == null)
				return null; //error

			return obj.get(fieldName);
		}
	}

	public void sput(String className, String fieldName, sym.op.IOp value) {
		getClass(className); // to init the class
		synchronized (staticObjs) {
			HashMap<String, sym.op.IOp> obj = staticObjs.get(className);
			if (obj == null) {
				obj = new HashMap<String, sym.op.IOp>();
				staticObjs.put(className, obj);
			}
			obj.put(fieldName, value);
		}
	}

	// call clazz.<clinit>
	private void initClass(String className, sim.classs.Class clazz) {
		sim.method.Method.MethodPrototype clinitPrototype;
		ArrayList<String> parameterForClinit = new ArrayList<String>();
		clinitPrototype = new sim.method.Method.MethodPrototype("V",
				parameterForClinit);
		sim.classs.MethodItem clinitSpec = new sim.classs.MethodItem(className,
				"<clinit>", clinitPrototype);

		sim.method.Method clinit = getMethod(clazz, clinitSpec);

		if (clinit != null) {
			HashMap<String, AtomicInteger> labelCount = new HashMap<String, AtomicInteger>();
			for (sim.method.Method.Label label : clinit.labelList)
				labelCount.put(label.lab, new AtomicInteger(0));

			SymGenerator symGen = new SymGenerator();
			Kagebunsin kagebunsin = new Kagebunsin(this, labelCount, clazz,
					clinit, 0, PersistentHashMap.EMPTY, PersistentVector.EMPTY,
					symGen, PersistentVector.EMPTY);
			try {
				kagebunsin.run();
			} catch (Exception ex) {
				printlnErr(String.format(
						"in class %s encount %s while invoke its <clinit>",
						clazz.name, ex.toString()));
				printSt(ex);
			}
		}
	}

	// this method is not synchronized, it should called under protection of
	// synchronized (unaccessedClass)
	// push result to unScanedClass
	private sim.classs.Class getClass(String className, SimplifyWorker worker) {
		sim.classs.Class result;
		try {
			result = worker.call();
		} catch (Exception e) {
			printlnErr(String.format("error while processing %s\n",
					worker.translateWorker.parserWorker.path));
			printSt(e);
			return null;
		}

		// NOTE!! we should put result into allClass before we call its
		// <clinit>, because if not, we may recursively call ourselves
		allClass.put(className, result);
		unScanedClass.add(result);

		initClass(className, result);
		return result;
	}

	// className should be something like java/lang/Object, return null on 404
	public sim.classs.Class getClass(String className) {
		sim.classs.Class result = allClass.get(className);
		if (result == null) {
			synchronized (unaccessedClass) {
				SimplifyWorker worker = unaccessedClass.remove(className);
				if (worker == null)
					return null;
				else
					return getClass(className, worker);
			}
		}
		return result;
	}

	// get right method, including argtype
	private sim.method.Method findMatchedMethod(sim.classs.Class clazz, sim.classs.MethodItem method) {
		// FIXME make it more efficient
		out: for (sim.method.Method target : clazz.methods) {
			if (target.name.equals(method.methodName)
					&& target.prototype.argsType.size() == method.prototype.argsType.size()) {
				for (int i = 0; i < target.prototype.argsType.size(); i++) {
					String parameter = target.prototype.argsType.get(i);
					String argument = method.prototype.argsType.get(i);
					if (!parameter.equals(argument))
						continue out;
				}
				return target;
			} else
				continue;
		}
		return null;
	}

	// we'll find the class's super class
	public sim.method.Method getMethod(sim.classs.Class clazz, sim.classs.MethodItem method) {
		sim.method.Method result = findMatchedMethod(clazz, method);

		if (result != null)
			return result;

		// if we're trying to get <clinit> then we don't search its super
		if (method.methodName.equals("<clinit>"))
			return null;

		// we failed to find method in current class, so we find its super class
		sim.classs.Class superClass = clazz;
		do {
			String superName = superClass.superr;
			superName = superName.substring(1, superName.length() - 1);
			superClass = getClass(superName);
			if (superClass == null)
				break;
			result = findMatchedMethod(superClass, method);
			if (result != null)
				return result;
		} while (true);

		printlnErr(String.format(
				"failed to find method %s with arg %s in class %s\n",
				method.methodName, method.prototype.argsType, clazz.name));
		return null;
	}

	// wrapper for System.out.println, for debug usage
	public synchronized void println(String msg) {
		System.out.println(msg);
	}

	// wrapper for System.err.println, for debug usage
	public synchronized void printlnErr(String msg) {
		System.err.println(msg);
	}

	public void printSt(Throwable t) {
		synchronized (System.err) {
			t.printStackTrace();
		}
	}

	public void execute() {
		for (SimplifyWorker worker : workers) {
			String name = worker.translateWorker.parserWorker.path;
			String key = name.substring(apkOutputPath.length() + 7,
					name.length() - 6);
			unaccessedClass.put(key, worker);
		}

		while (unaccessedClass.size() != 0 || unScanedClass.size() != 0) {
			sim.classs.Class clazz = unScanedClass.poll();

			if (clazz == null) {
				synchronized (unaccessedClass) {
					// avoid race condition
					if (unaccessedClass.size() == 0)
						break;

					Iterator<Entry<String, SimplifyWorker>> it;
					it = unaccessedClass.entrySet().iterator();

					Entry<String, SimplifyWorker> entry = it.next();
					getClass(entry.getKey(), entry.getValue()); // this will push an item to unScanedClass
					unaccessedClass.remove(entry.getKey());
					continue;
				}
			}
			assert clazz != null;

			out: for (sim.method.Method method : clazz.methods) {
				HashMap<String, AtomicInteger> labelCount = new HashMap<String, AtomicInteger>();
				for (sim.method.Method.Label label : method.labelList)
					labelCount.put(label.lab, new AtomicInteger(0));

				IPersistentMap pReg = PersistentHashMap.EMPTY;
				SymGenerator symGen = new SymGenerator();

				int index = 0;

				if (!method.accessList.contains("static")) {
					// unstatic method has this as p0
					pReg = pReg.assoc("p0", new sym.op.Obj(clazz.name));
					index++;
				}

				// arguments is stored at p{0..} or p{1..} register
				for (; index < method.prototype.argsType.size(); index++) {
					String t = method.prototype.argsType.get(index);
					String reg = "p" + index;
					if (t.equals("I")) {
						pReg = pReg.assoc(reg, symGen.genSym(t));
					} else if (t.startsWith("[")) {
						pReg = pReg.assoc(reg, new sym.op.Array(t,
								new sym.op.Const(0)));
					} else if (t.startsWith("L")) {
						continue; // assume object argument is null
					} else {
						printlnErr(String.format(
								"before symbolic executing %s, encount type %s, don't add to mapToSym\n",
								method, t));
						continue out;
					}
				}

				executor.submit(new Kagebunsin(this, labelCount, clazz, method,
						0, pReg, PersistentVector.EMPTY, symGen,
						PersistentVector.EMPTY));
			}
		}

		// scaned all the class

		ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(2);

		// execute at most Control.symExeSec seconds
		stpe.scheduleAtFixedRate((new Runnable() {
			@Override
			public void run() {
				latch.countDown();
			}
		}), Control.symExeSec, 1, TimeUnit.SECONDS);

		// when the executor is idle (ie. all Kagebunsin exits), we exit.
		stpe.scheduleAtFixedRate((new Runnable() {
			@Override
			public void run() {
				if (executor.getActiveCount() == 0)
					latch.countDown();
			}
		}), 1, 1, TimeUnit.SECONDS);

		while (true) {
			try {
				latch.await();
				break;
			} catch (InterruptedException e) {
				continue;
			}
		}

		System.out.println("all Kagebunsins terminate");

		stpe.shutdown();
		executor.shutdown();

		while (true) {
			if (stpe.isTerminated() && executor.isTerminated())
				break;
			else {
				try {
					stpe.awaitTermination(1, TimeUnit.SECONDS);
					executor.awaitTermination(1, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.format(
				"symbolic executing terminate normally, result is at %s\n",
				symResultPath);
	}
}