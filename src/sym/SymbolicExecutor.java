package sym;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

	// two fields below can only accessed via getClass(name), which is synchronized
	private HashMap<String, SimplifyWorker> unaccessedClass;
	private HashMap<String, sim.classs.Class> allClass;

	public final String symResultPath;
	public final String apkOutputPath;
	public final String mainClass;

	public SymbolicExecutor(List<SimplifyWorker> workers, File output,
			String apkOutputPath, String mainClass) throws IOException {
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
		this.mainClass = mainClass;
	}

	public synchronized void write(String msg) {
		try {
			this.writer.write(msg);
			this.writer.flush();
		} catch (IOException e) {
			System.err.format("exception %s when writing to %s", e,
					symResultPath);
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

	public synchronized sym.op.IOp sget(String className, String fieldName) {
		HashMap<String, sym.op.IOp> obj = staticObjs.get(className);
		if (obj == null)
			return null; //error

		return obj.get(fieldName);
	}

	public synchronized void sput(String className, String fieldName, sym.op.IOp value) {
		HashMap<String, sym.op.IOp> obj = staticObjs.get(className);
		if (obj == null) {
			staticObjs.put(className, new HashMap<String, sym.op.IOp>());
		}
		obj.put(fieldName, value);
	}

	// className should be something like java/lang/Object, return null
	// on 404
	public synchronized sim.classs.Class getClass(String className) {
		sim.classs.Class result = allClass.get(className);
		if (result == null) {
			SimplifyWorker worker = unaccessedClass.get(className);
			if (worker == null) {
				result = null;
			} else {
				try {
					result = worker.call();
				} catch (Exception e) {
					System.err.format("error while processing %s\n",
							worker.translateWorker.parserWorker.path);
					e.printStackTrace();
				}
				// TODO invoke result.<clinit>
				if (result != null)
					allClass.put(className, result);
			}
		}
		return result;
	}

	// wrapper for System.out.println, for debug usage
	public synchronized void println(String msg) {
		System.out.println(msg);
	}

	public void execute() {
		for (SimplifyWorker worker : workers) {
			String name = worker.translateWorker.parserWorker.path;
			String key = name.substring(apkOutputPath.length() + 7,
					name.length() - 6);
			unaccessedClass.put(key, worker);
		}

		sim.classs.Class mainClass = getClass(this.mainClass);
		sim.method.Method main = null;

		for (sim.method.Method method : mainClass.methods) {
			// TODO add HashMap to sim.class.Class
			if (!method.name.equals("main"))
				continue;
			else {
				main = method;
				break;
			}
		}

		if (main == null) {
			System.err.println("no main method found for main class "
					+ mainClass);
			System.exit(3);
		}

		HashMap<String, AtomicInteger> labelCount = new HashMap<String, AtomicInteger>();
		for (sim.method.Method.Label label : main.labelList) {
			labelCount.put(label.lab, new AtomicInteger(0));
		}

		IPersistentMap pReg = PersistentHashMap.EMPTY;
		SymGenerator symGen = new SymGenerator();

		int index = 0;
		// arguments is stored at p{0..} register
		for (; index < main.prototype.argsType.size(); index++) {
			String t = main.prototype.argsType.get(index);
			String reg = "p" + index;
			if (t.equals("I")) {
				pReg = pReg.assoc(reg, symGen.genSym(t));
			} else if (t.startsWith("[")) {
				pReg = pReg.assoc(reg, new sym.op.Array(t, new sym.op.Const(0)));
			} else if (t.startsWith("L")) {
				continue; // assume object argument is null
			} else {
				System.err.format(
						"before symbolic executing %s, encount type %s, don't add to mapToSym\n",
						main, t);
			}
		}

		executor.submit(new Kagebunsin(this, labelCount, mainClass, main, 0,
				pReg, PersistentVector.EMPTY, symGen, PersistentVector.EMPTY));

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