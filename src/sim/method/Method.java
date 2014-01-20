package sim.method;

import sim.Visitor;

public class Method extends T {

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}