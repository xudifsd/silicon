package ast.stm;

import java.util.List;

import ast.Visitor;
import ast.method.Method.MethodPrototype;

public class InvokeVirtual extends T {
	public List<String> registers;
	public String type;
	public MethodPrototype methodPrototype;

	public InvokeVirtual(List<String> registers, String type,
			MethodPrototype methodPrototype) {
		this.registers = registers;
		this.type = type;
		this.methodPrototype = methodPrototype;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}