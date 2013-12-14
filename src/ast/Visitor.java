package ast;

public interface Visitor {
	// program

	public void visit(ast.classs.Class class1);
	public void visit(ast.classs.MethodItem item);
	public void visit(ast.classs.FieldItem item);
	public void visit(ast.method.Method method);
	public void visit(ast.method.Method.MethodPrototype prototype);
	public void visit(ast.program.Program program);
	public void visit(ast.stm.Instruction.F35c_method insn);
	public void visit(ast.stm.Instruction.F10x insn);
	public void visit(ast.stm.Instruction.F21c_field insn);
	public void visit(ast.stm.Instruction.F21c_string insn);
}