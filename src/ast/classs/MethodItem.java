package ast.classs;

import ast.Visitor;

public class MethodItem extends T{
	public String classType;
	public String methodName;
	public ast.method.Method.MethodPrototype prototype;
	
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
