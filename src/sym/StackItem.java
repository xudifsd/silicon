package sym;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import clojure.lang.IPersistentMap;
import sim.method.Method;

public class StackItem {
	public final String clazzName; // this help us have no reference to big obj
	public final sim.method.Method method;
	public final int pc;
	public final IPersistentMap mapToSym;
	public final HashMap<String, AtomicInteger> labelCount;

	public StackItem(String clazzName, Method method, int pc,
			IPersistentMap mapToSym, HashMap<String, AtomicInteger> labelCount) {
		this.clazzName = clazzName;
		this.method = method;
		this.pc = pc;
		this.mapToSym = mapToSym;
		this.labelCount = labelCount;
	}
}