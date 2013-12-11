package ast.program;

import ast.Visitor;

public class Program extends T {
	public Program() {
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
		return;
	}
}