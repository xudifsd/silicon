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

	@Override
	public String toString()
	{
		String tmp = new String();
		for(String type : this.prototype.argsType)
		{
			tmp = tmp+ type;
		}
		return this.classType+"->"+this.methodName+"("+tmp +")"+this.prototype.returnType;
	}
	
}
