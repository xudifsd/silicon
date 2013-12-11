package ast;

public interface Visitor {
	// program
	public void visit(ast.program.Program p);

	public void visit(ast.classs.Class class1);

	public void visit(ast.method.Method method);
}