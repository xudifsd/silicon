package ast.classs;

import ast.Visitor;

public class Class extends T {
	public String id;
	public String extendss;

	public Class() {
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}