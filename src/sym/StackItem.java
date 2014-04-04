package sym;

import sim.method.Method;

public class StackItem {
	public final sim.classs.Class clazz;
	public final sim.method.Method method;
	public final int pc;

	// TODO add mapToSym

	public StackItem(sim.classs.Class clazz, Method method, int pc) {
		this.clazz = clazz;
		this.method = method;
		this.pc = pc;
	}
}