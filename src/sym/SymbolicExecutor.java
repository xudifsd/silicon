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
	public final String path;

	public SymbolicExecutor(List<SimplifyWorker> workers, File output)
			throws IOException {
		this.staticObjs = new HashMap<String, HashMap<String, sym.op.IOp>>();
		this.workers = workers;
		this.latch = new CountDownLatch(1);
		this.executor = new ThreadPoolExecutor(threadPoolSize,
				2 * threadPoolSize, 5, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		this.writer = new FileWriter(output);
		this.path = output.getAbsolutePath();
		this.z3 = new Z3Stub();
	}

	public synchronized void write(String msg) {
		try {
			this.writer.write(msg);
			this.writer.flush();
		} catch (IOException e) {
			System.err.format("exception %s when writing to %s", e, path);
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

	public void execute() {
		for (SimplifyWorker worker : workers) {
			try {
				sim.classs.Class clazz = worker.call();
				for (sim.method.Method method : clazz.methods) {
					HashMap<String, AtomicInteger> labelCount = new HashMap<String, AtomicInteger>();
					for (sim.method.Method.Label label : method.labelList) {
						labelCount.put(label.lab, new AtomicInteger(0));
					}

					IPersistentMap pReg = PersistentHashMap.EMPTY;
					SymGenerator symGen = new SymGenerator();

					int index = 0;
					// arguments is stored at p{0..} register
					for (; index < method.prototype.argsType.size(); index++) {
						String t = method.prototype.argsType.get(index);
						String reg = "p" + index;
						switch (t) {
						case "I":
							pReg = pReg.assoc(reg, symGen.genSym(t));
							break;
						default:
							System.err.format(
									"before symbolic executing %s, encount type %s\n",
									method, t);
							continue;
						}
					}

					executor.submit(new Kagebunsin(this, labelCount, clazz,
							method, 0, pReg, PersistentVector.EMPTY, symGen));
				}
			} catch (Exception e) {
				System.err.format("error while processing %s\n",
						worker.translateWorker.parserWorker.path);
				e.printStackTrace();
			}
		}

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
				path);
	}
}