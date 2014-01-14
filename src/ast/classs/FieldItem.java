package ast.classs;

import ast.Visitor;

public class FieldItem extends T {
	public String classType;
	public String fieldName;
	public String fieldType;

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return this.classType + "->" + this.fieldName + ":" + this.fieldType;
	}

}
