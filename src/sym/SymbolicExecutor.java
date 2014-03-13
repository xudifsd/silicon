package sym;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentVector;
import control.Control;
import util.MultiThreadUtils.SimplifyWorker;

public class SymbolicExecutor {
	public static class ThreadSafeFileWriter {
		private FileWriter writer;
		public String path;

		public ThreadSafeFileWriter(File output) throws IOException {
			this.writer = new FileWriter(output);
			this.path = output.getAbsolutePath();
		}

		public synchronized void write(String msg) throws IOException {
			this.writer.write(msg);
		}
	}

	public static final int threadPoolSize = 10;
	private CountDownLatch latch;
	private ExecutorService executor;
	private List<SimplifyWorker> workers;
	private ThreadSafeFileWriter writer;
	private Z3Stub z3;

	public SymbolicExecutor(List<SimplifyWorker> workers, File output)
			throws IOException {
		this.workers = workers;
		this.latch = new CountDownLatch(1);
		this.executor = Executors.newFixedThreadPool(threadPoolSize);
		this.writer = new ThreadSafeFileWriter(output);
		this.z3 = new Z3Stub();
	}

	public void execute() {
		for (SimplifyWorker worker : workers) {
			try {
				sim.classs.Class clazz = worker.call();
				for (sim.method.Method method : clazz.methods) {
					ConcurrentHashMap<String, AtomicInteger> labelCount = new ConcurrentHashMap<String, AtomicInteger>();
					for (sim.method.Method.Label label : method.labelList) {
						labelCount.put(label.lab, new AtomicInteger(0));
					}
					executor.submit(new Kagebunsin(z3, executor, labelCount,
							method, 0, PersistentHashMap.EMPTY,
							PersistentVector.EMPTY, writer));
				}
			} catch (Exception e) {
				System.err.format("error while processing %s\n",
						worker.translateWorker.parserWorker.path);
				e.printStackTrace();
			}
		}

		ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(2);
		stpe.scheduleAtFixedRate((new Runnable() {
			@Override
			public void run() {
				latch.countDown();
			}
		}), Control.symExeSec, 1, TimeUnit.SECONDS);

		while (true) {
			try {
				latch.await();
				break;
			} catch (InterruptedException e) {
				continue;
			}
		}

		stpe.shutdown();
		executor.shutdown();

		try {
			stpe.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			executor.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.format(
				"symbolic executing terminate normally, result is at %s\n",
				writer.path);
	}
}