package sim.classs;

import ast.method.Method.MethodPrototype;
import sim.Visitor;

public class MethodItem extends T {
	public String classType;
	public String methodName;
	public ast.method.Method.MethodPrototype prototype;

	public MethodItem(String classType, String methodName,
			MethodPrototype prototype) {
		super();
		this.classType = classType;
		this.methodName = methodName;
		this.prototype = prototype;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		String tmp = new String();
		for (String type : this.prototype.argsType) {
			tmp = tmp + type;
		}
		return this.classType + "->" + this.methodName + "(" + tmp + ")"
				+ this.prototype.returnType;
	}

}
