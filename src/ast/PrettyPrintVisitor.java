package ast;

public class PrettyPrintVisitor implements Visitor {
	private final static int TAB = 4; 
	private int indents = 0;
	
	private void indent(){
		indents += TAB;
	}
	private void unIndent(){
		if(indents > 0)
			indents -= TAB;
	}
	
	private void printSpace(){
		for (int i = 0; i < indents; i++){
			System.err.print(" ");
		}
	}
	
	private void say(String s){
		System.err.print(s);
	}
	private void sayln(String s){
		System.err.println(s);
	}
	
	
	// program
	@Override
	public void visit(ast.program.Program p) {
	}

	@Override
	public void visit(ast.classs.Class clazz) {
		ast.classs.Class classs = (ast.classs.Class)clazz;
		this.say(".class ");
		for(String access : clazz.accessList){
			this.say(access + " ");
		}
		this.sayln(clazz.FullyQualifiedName);
		
		this.say(".super ");
		this.sayln(clazz.superName);

		this.say(".source ");
		this.sayln("\"" + clazz.source + "\"");
		
		for(ast.method.Method method : classs.methods){
			method.accept(this);
		}
	}

	@Override
	public void visit(ast.method.Method method) {
		this.say(".method ");
		for(String access : method.accessList){
			this.say(access + " ");
		}
		this.say(method.name);
		method.prototype.accept(this);
		this.indent();
		this.printSpace();
		this.say(method.registers_directive + " ");
		this.sayln(method.registers_directive_count + "");
		for(int i = 0; i < method.prototype.argsType.size(); i++){
			this.printSpace();
			this.sayln(".parameter");
		}
		this.sayln("");
		this.printSpace();
		this.sayln(".prologue");
		
		for(ast.stm.T stmt : method.statements){
			stmt.accept(this);
		}
		
		this.unIndent();
		this.sayln(".end method");
	}

	@Override
	public void visit(ast.method.Method.MethodPrototype prototype) {
		this.say("(");
		for(String type : prototype.argsType){
			this.say(type);
		}
		this.say(")");
		this.sayln(prototype.returnType);
	}
	
	
	public void visit(ast.stm.Instruction.F35c_method insn){
		this.printSpace();
		say(insn.name + " ");
		
		say("{");
		if(insn.regList.size() > 0)
			say(insn.regList.get(0));
		for(int i = 1; i < insn.regList.size(); i++){
			say(", " + insn.regList.get(i));
		}
		say("}, ");
		
		insn.methodItem.accept(this);
		sayln("");
	}
	@Override
	public void visit(ast.stm.Instruction.F10x insn) {
		this.printSpace();
		sayln(insn.name);
	}
	@Override
	public void visit(ast.stm.Instruction.F21c_field insn) {
		this.printSpace();
		say(insn.name + " ");
		say(insn.reg + ", ");
		insn.fieldItem.accept(this);
		sayln("");
	}
	@Override
	public void visit(ast.stm.Instruction.F21c_string insn) {
		this.printSpace();
		say(insn.name + " ");
		say(insn.reg + ", ");
		sayln("\"" + insn.str + "\"");
	}
	@Override
	public void visit(ast.classs.MethodItem item) {
		say(item.classType + "->");
		say(item.methodName);
		item.prototype.accept(this);
	}
	@Override
	public void visit(ast.classs.FieldItem item) {
		say(item.classType + "->");
		say(item.fieldName + ":");
		say(item.fieldType);
	}
}