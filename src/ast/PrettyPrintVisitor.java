package ast;

import ast.classs.Class;
import ast.method.Method;
import ast.stm.InvokeVirtual;
import ast.stm.SgetObject;

public class PrettyPrintVisitor implements Visitor {
	// program
	@Override
	public void visit(ast.program.Program p) {
	}

	@Override
	public void visit(Class clazz) {
	}

	@Override
	public void visit(Method method) {
	}

	@Override
	public void visit(SgetObject sgetObject) {
	}

	@Override
	public void visit(InvokeVirtual invokeVirtual) {
	}
}