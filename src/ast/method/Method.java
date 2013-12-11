package ast.method;

import ast.Visitor;

public class Method extends T {

	public Method() {
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}