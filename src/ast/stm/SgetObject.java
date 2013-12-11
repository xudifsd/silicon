package ast.stm;

import ast.Visitor;
import ast.stm.T;

public class SgetObject extends T {
	public String register;
	public String className;
	public String field;
	public String type;

	public SgetObject(String register, String className, String field, String type) {
		this.register = register;
		this.className = className;
		this.field = field;
		this.type = type;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}