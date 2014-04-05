package sym;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import clojure.lang.IPersistentMap;
import sim.classs.Class;
import sim.method.Method;

public class StackItem {
	public final sim.classs.Class clazz;
	public final sim.method.Method method;
	public final int pc;
	public final IPersistentMap mapToSym;
	public final HashMap<String, AtomicInteger> labelCount;

	public StackItem(Class clazz, Method method, int pc,
			IPersistentMap mapToSym, HashMap<String, AtomicInteger> labelCount) {
		this.clazz = clazz;
		this.method = method;
		this.pc = pc;
		this.mapToSym = mapToSym;
		this.labelCount = labelCount;
	}
}