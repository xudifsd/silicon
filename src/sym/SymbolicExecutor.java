package sym;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.MultiThreadUtils.SimplifyWorker;

public class SymbolicExecutor {
	public static final int threadPoolSize = 10;
	private ExecutorService executor;
	private List<SimplifyWorker> workers;
	private File output;

	public SymbolicExecutor(List<SimplifyWorker> workers, File output) {
		this.workers = workers;
		this.output = output;
		executor = Executors.newFixedThreadPool(threadPoolSize);
	}

	public void execute() {
		// visitor all methods with Kagebunsin
	}
}