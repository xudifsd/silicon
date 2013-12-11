package ast;

public interface Visitor {
	// program
	public void visit(ast.stm.SgetObject sgetObject);

	public void visit(ast.classs.Class class1);

	public void visit(ast.method.Method method);

	public void visit(ast.program.Program program);

	public void visit(ast.stm.InvokeVirtual invokeVirtual);
}