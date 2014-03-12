package sym;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import sim.method.Method;
import clojure.lang.IPersistentMap;
import clojure.lang.PersistentVector;

/* *
 * This class is helper class for symbolic execution, execute the whole
 * basic block, create another Kagebunsin upon branch
 * */
public class Kagebunsin implements Runnable {
	public static final int countThreshold = 3;
	private Z3Stub z3;
	private ExecutorService executor;
	private ConcurrentHashMap<String, AtomicInteger> labelCount;
	private Method currentMethod;
	private Long pc;
	private IPersistentMap mapToSym; //register to their value(represented by sym.op.IOp)
	private PersistentVector conditions; //vector of IPrediction

	public Kagebunsin(Z3Stub z3, ExecutorService executor,
			ConcurrentHashMap<String, AtomicInteger> labelCount,
			Method currentMethod, Long pc, IPersistentMap mapToSym,
			PersistentVector conditions) {
		this.z3 = z3;
		this.executor = executor;
		this.labelCount = labelCount;
		this.currentMethod = currentMethod;
		this.pc = pc;
		this.mapToSym = mapToSym;
		this.conditions = conditions;
	}

	@Override
	public void run() {
	}
}