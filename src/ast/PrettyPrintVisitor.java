package ast;

import ast.stm.*;
import ast.stm.Instruction.AddDouble;
import ast.stm.Instruction.AddDouble2Addr;
import ast.stm.Instruction.AddFloat;
import ast.stm.Instruction.AddFloat2Addr;
import ast.stm.Instruction.AddInt;
import ast.stm.Instruction.AddInt2Addr;
import ast.stm.Instruction.AddIntLit16;
import ast.stm.Instruction.AddIntLit8;
import ast.stm.Instruction.AddLong;
import ast.stm.Instruction.AddLong2Addr;
import ast.stm.Instruction.Aget;
import ast.stm.Instruction.AgetBoolean;
import ast.stm.Instruction.AgetByte;
import ast.stm.Instruction.AgetChar;
import ast.stm.Instruction.AgetObject;
import ast.stm.Instruction.AgetShort;
import ast.stm.Instruction.AgetWide;
import ast.stm.Instruction.AndInt;
import ast.stm.Instruction.AndInt2Addr;
import ast.stm.Instruction.AndIntLit16;
import ast.stm.Instruction.AndIntLit8;
import ast.stm.Instruction.AndLong;
import ast.stm.Instruction.AndLong2Addr;
import ast.stm.Instruction.Aput;
import ast.stm.Instruction.AputBoolean;
import ast.stm.Instruction.AputByte;
import ast.stm.Instruction.AputChar;
import ast.stm.Instruction.AputObject;
import ast.stm.Instruction.AputShort;
import ast.stm.Instruction.AputWide;
import ast.stm.Instruction.CheckCast;
import ast.stm.Instruction.CmpLong;
import ast.stm.Instruction.CmpgFloat;
import ast.stm.Instruction.Cmpgdouble;
import ast.stm.Instruction.CmplDouble;
import ast.stm.Instruction.CmplFloat;
import ast.stm.Instruction.Const;
import ast.stm.Instruction.Const16;
import ast.stm.Instruction.Const4;
import ast.stm.Instruction.ConstClass;
import ast.stm.Instruction.ConstHigh16;
import ast.stm.Instruction.ConstString;
import ast.stm.Instruction.ConstStringJumbo;
import ast.stm.Instruction.ConstWide;
import ast.stm.Instruction.ConstWide16;
import ast.stm.Instruction.ConstWide32;
import ast.stm.Instruction.ConstWideHigh16;
import ast.stm.Instruction.DivDouble;
import ast.stm.Instruction.DivDouble2Addr;
import ast.stm.Instruction.DivFloat;
import ast.stm.Instruction.DivFloat2Addr;
import ast.stm.Instruction.DivInt;
import ast.stm.Instruction.DivInt2Addr;
import ast.stm.Instruction.DivIntLit16;
import ast.stm.Instruction.DivIntLit8;
import ast.stm.Instruction.DivLong;
import ast.stm.Instruction.DivLong2Addr;
import ast.stm.Instruction.DoubleToFloat;
import ast.stm.Instruction.DoubleToInt;
import ast.stm.Instruction.DoubleToLong;
import ast.stm.Instruction.FillArrayData;
import ast.stm.Instruction.FilledNewArray;
import ast.stm.Instruction.FilledNewArrayRange;
import ast.stm.Instruction.FloatToDouble;
import ast.stm.Instruction.FloatToInt;
import ast.stm.Instruction.FloatToLong;
import ast.stm.Instruction.Goto;
import ast.stm.Instruction.Goto16;
import ast.stm.Instruction.Goto32;
import ast.stm.Instruction.IfEq;
import ast.stm.Instruction.IfEqz;
import ast.stm.Instruction.IfGe;
import ast.stm.Instruction.IfGez;
import ast.stm.Instruction.IfGt;
import ast.stm.Instruction.IfGtz;
import ast.stm.Instruction.IfLe;
import ast.stm.Instruction.IfLez;
import ast.stm.Instruction.IfLt;
import ast.stm.Instruction.IfLtz;
import ast.stm.Instruction.IfNe;
import ast.stm.Instruction.IfNez;
import ast.stm.Instruction.Iget;
import ast.stm.Instruction.IgetBoolean;
import ast.stm.Instruction.IgetByte;
import ast.stm.Instruction.IgetChar;
import ast.stm.Instruction.IgetOjbect;
import ast.stm.Instruction.IgetShort;
import ast.stm.Instruction.IgetWide;
import ast.stm.Instruction.InstanceOf;
import ast.stm.Instruction.IntToByte;
import ast.stm.Instruction.IntToChar;
import ast.stm.Instruction.IntToDouble;
import ast.stm.Instruction.IntToFloat;
import ast.stm.Instruction.IntToLong;
import ast.stm.Instruction.IntToShort;
import ast.stm.Instruction.InvokeDirect;
import ast.stm.Instruction.InvokeDirectRange;
import ast.stm.Instruction.InvokeInterface;
import ast.stm.Instruction.InvokeInterfaceRange;
import ast.stm.Instruction.InvokeStatic;
import ast.stm.Instruction.InvokeStaticRange;
import ast.stm.Instruction.InvokeSuper;
import ast.stm.Instruction.InvokeSuperRange;
import ast.stm.Instruction.InvokeVirtual;
import ast.stm.Instruction.InvokeVirtualRange;
import ast.stm.Instruction.Iput;
import ast.stm.Instruction.IputBoolean;
import ast.stm.Instruction.IputByte;
import ast.stm.Instruction.IputChar;
import ast.stm.Instruction.IputObject;
import ast.stm.Instruction.IputShort;
import ast.stm.Instruction.IputWide;
import ast.stm.Instruction.LongToDouble;
import ast.stm.Instruction.LongToFloat;
import ast.stm.Instruction.LongToInt;
import ast.stm.Instruction.MonitorEnter;
import ast.stm.Instruction.MonitorExit;
import ast.stm.Instruction.Move;
import ast.stm.Instruction.Move16;
import ast.stm.Instruction.MoveException;
import ast.stm.Instruction.MoveFrom16;
import ast.stm.Instruction.MoveObject;
import ast.stm.Instruction.MoveObject16;
import ast.stm.Instruction.MoveOjbectFrom16;
import ast.stm.Instruction.MoveResult;
import ast.stm.Instruction.MoveResultObject;
import ast.stm.Instruction.MoveResultWide;
import ast.stm.Instruction.MoveWide;
import ast.stm.Instruction.MoveWide16;
import ast.stm.Instruction.MoveWideFrom16;
import ast.stm.Instruction.MulDouble;
import ast.stm.Instruction.MulDouble2Addr;
import ast.stm.Instruction.MulFloat;
import ast.stm.Instruction.MulFloat2Addr;
import ast.stm.Instruction.MulInt;
import ast.stm.Instruction.MulInt2Addr;
import ast.stm.Instruction.MulIntLit16;
import ast.stm.Instruction.MulIntLit8;
import ast.stm.Instruction.MulLong;
import ast.stm.Instruction.MulLong2Addr;
import ast.stm.Instruction.NegDouble;
import ast.stm.Instruction.NegFloat;
import ast.stm.Instruction.NegInt;
import ast.stm.Instruction.NegLong;
import ast.stm.Instruction.NewArray;
import ast.stm.Instruction.NewInstance;
import ast.stm.Instruction.Nop;
import ast.stm.Instruction.NotInt;
import ast.stm.Instruction.NotLong;
import ast.stm.Instruction.OrInt;
import ast.stm.Instruction.OrInt2Addr;
import ast.stm.Instruction.OrIntLit16;
import ast.stm.Instruction.OrIntLit8;
import ast.stm.Instruction.OrLong;
import ast.stm.Instruction.OrLong2Addr;
import ast.stm.Instruction.PackedSwitch;
import ast.stm.Instruction.RemDouble;
import ast.stm.Instruction.RemDouble2Addr;
import ast.stm.Instruction.RemFloat;
import ast.stm.Instruction.RemFloat2Addr;
import ast.stm.Instruction.RemInt;
import ast.stm.Instruction.RemInt2Addr;
import ast.stm.Instruction.RemIntLit16;
import ast.stm.Instruction.RemIntLit8;
import ast.stm.Instruction.RemLong;
import ast.stm.Instruction.RemLong2Addr;
import ast.stm.Instruction.Return;
import ast.stm.Instruction.ReturnObject;
import ast.stm.Instruction.ReturnVoid;
import ast.stm.Instruction.ReturnWide;
import ast.stm.Instruction.RsubInt;
import ast.stm.Instruction.RsubIntLit8;
import ast.stm.Instruction.Sget;
import ast.stm.Instruction.SgetBoolean;
import ast.stm.Instruction.SgetByte;
import ast.stm.Instruction.SgetChar;
import ast.stm.Instruction.SgetObject;
import ast.stm.Instruction.SgetShort;
import ast.stm.Instruction.SgetWide;
import ast.stm.Instruction.ShlInt;
import ast.stm.Instruction.ShlInt2Addr;
import ast.stm.Instruction.ShlIntLit8;
import ast.stm.Instruction.ShlLong;
import ast.stm.Instruction.ShlLong2Addr;
import ast.stm.Instruction.ShrInt;
import ast.stm.Instruction.ShrInt2Addr;
import ast.stm.Instruction.ShrIntLit8;
import ast.stm.Instruction.ShrLong;
import ast.stm.Instruction.ShrLong2Addr;
import ast.stm.Instruction.SparseSwitch;
import ast.stm.Instruction.Sput;
import ast.stm.Instruction.SputBoolean;
import ast.stm.Instruction.SputByte;
import ast.stm.Instruction.SputChar;
import ast.stm.Instruction.SputObject;
import ast.stm.Instruction.SputShort;
import ast.stm.Instruction.SputWide;
import ast.stm.Instruction.SubDouble;
import ast.stm.Instruction.SubDouble2Addr;
import ast.stm.Instruction.SubFloat;
import ast.stm.Instruction.SubFloat2Addr;
import ast.stm.Instruction.SubInt;
import ast.stm.Instruction.SubInt2Addr;
import ast.stm.Instruction.SubLong;
import ast.stm.Instruction.SubLong2Addr;
import ast.stm.Instruction.Throw;
import ast.stm.Instruction.UshrInt;
import ast.stm.Instruction.UshrInt2Addr;
import ast.stm.Instruction.UshrIntLit8;
import ast.stm.Instruction.UshrLong;
import ast.stm.Instruction.UshrLong2Addr;
import ast.stm.Instruction.XorInt;
import ast.stm.Instruction.XorInt2Addr;
import ast.stm.Instruction.XorIntLit16;
import ast.stm.Instruction.XorIntLit8;
import ast.stm.Instruction.XorLong;
import ast.stm.Instruction.XorLong2Addr;
import ast.stm.Instruction.arrayLength;

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
	@Override
	public void visit(Instruction instruction)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AputChar aputChar)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Goto goto1)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Nop nop)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ReturnVoid returnVoid)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Const4 const4)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MoveResult moveResult)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MoveResultWide moveResultWide)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MoveResultObject moveResultObject)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MoveException moveException)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Return return1)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ReturnWide returnWide)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ReturnObject returnObject)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MonitorEnter monitorEnter)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MonitorExit monitorExit)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Throw throw1)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Move move)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MoveWide moveWide)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MoveObject moveObject)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(arrayLength arrayLength)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(NegInt negInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(NotInt notInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(NegLong negLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(NotLong notLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(NegFloat negFloat)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(NegDouble negDouble)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IntToLong intToLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IntToFloat intToFloat)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IntToDouble intToDouble)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(LongToInt longToInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(LongToFloat longToFloat)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(LongToDouble longToDouble)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(FloatToInt floatToInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(FloatToLong floatToLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(FloatToDouble floatToDouble)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(DoubleToInt doubleToInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(DoubleToLong doubleToLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(DoubleToFloat doubleToFloat)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IntToByte intToByte)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IntToChar intToChar)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IntToShort intToShort)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AddInt2Addr addInt2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SubInt2Addr subInt2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MulInt2Addr mulInt2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(DivInt2Addr divInt2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(RemInt2Addr remInt2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AndInt2Addr andInt2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(OrInt2Addr orInt2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(XorInt2Addr xorInt2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ShlInt2Addr shlInt2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ShrInt2Addr shrInt2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(UshrInt2Addr ushrInt2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AddLong2Addr addLong2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SubLong2Addr subLong2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MulLong2Addr mulLong2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(DivLong2Addr divLong2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(RemLong2Addr remLong2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AndLong2Addr andLong2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(OrLong2Addr orLong2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(XorLong2Addr xorLong2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ShlLong2Addr shlLong2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ShrLong2Addr shrLong2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(UshrLong2Addr ushrLong2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AddFloat2Addr addFloat2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SubFloat2Addr subFloat2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MulFloat2Addr mulFloat2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(DivFloat2Addr divFloat2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(RemFloat2Addr remFloat2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AddDouble2Addr addDouble2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SubDouble2Addr subDouble2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MulDouble2Addr mulDouble2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(DivDouble2Addr divDouble2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(RemDouble2Addr remDouble2Addr)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Goto16 goto16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ConstString constString)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ConstClass constClass)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(CheckCast checkCast)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(NewInstance newInstance)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Sget sget)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SgetWide sgetWide)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SgetObject sgetObject)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SgetBoolean sgetBoolean)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SgetByte sgetByte)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SgetChar sgetChar)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SgetShort sgetShort)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Sput sput)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SputWide sputWide)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SputObject sputObject)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SputBoolean sputBoolean)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SputByte sputByte)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SputChar sputChar)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SputShort sputShort)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ConstHigh16 constHigh16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ConstWideHigh16 constWideHigh16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Const16 const16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ConstWide16 constWide16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IfEqz ifEqz)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IfNez ifNez)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IfLtz ifLtz)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IfGez ifGez)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IfGtz ifGtz)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IfLez ifLez)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AddIntLit8 addIntLit8)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(RsubIntLit8 rsubIntLit8)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MulIntLit8 mulIntLit8)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(DivIntLit8 divIntLit8)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(RemIntLit8 remIntLit8)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AndIntLit8 andIntLit8)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(OrIntLit8 orIntLit8)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(XorIntLit8 xorIntLit8)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ShlIntLit8 shlIntLit8)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ShrIntLit8 shrIntLit8)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(UshrIntLit8 ushrIntLit8)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(InstanceOf instanceOf)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(NewArray newArray)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Iget iget)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IgetWide igetWide)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IgetOjbect igetOjbect)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IgetBoolean igetBoolean)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IgetByte igetByte)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IgetChar igetChar)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IgetShort igetShort)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Iput iput)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IputWide iputWide)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IputObject iputObject)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IputBoolean iputBoolean)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IputByte iputByte)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IputChar iputChar)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IputShort iputShort)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AddIntLit16 addIntLit16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(RsubInt rsubInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MulIntLit16 mulIntLit16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(DivIntLit16 divIntLit16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(RemIntLit16 remIntLit16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AndIntLit16 andIntLit16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(OrIntLit16 orIntLit16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(XorIntLit16 xorIntLit16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IfEq ifEq)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IfNe ifNe)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IfLt ifLt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IfGe ifGe)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IfGt ifGt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(IfLe ifLe)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MoveFrom16 moveFrom16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MoveWideFrom16 moveWideFrom16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MoveOjbectFrom16 moveOjbectFrom16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(CmplFloat cmplFloat)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(CmpgFloat cmpgFloat)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(CmplDouble cmplDouble)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Cmpgdouble cmpgdouble)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(CmpLong cmpLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Aget aget)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AgetWide agetWide)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AgetObject agetObject)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AgetBoolean agetBoolean)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AgetByte agetByte)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AgetChar agetChar)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AgetShort agetShort)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Aput aput)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AputWide aputWide)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AputObject aputObject)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AputBoolean aputBoolean)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AputByte aputByte)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AputShort aputShort)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AddInt addInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SubInt subInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MulInt mulInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(DivInt divInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(RemInt remInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AndInt andInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(OrInt orInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(XorInt xorInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ShlInt shlInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ShrInt shrInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(UshrInt ushrInt)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AddLong addLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SubLong subLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MulLong mulLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(DivLong divLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(RemLong remLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AndLong andLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(OrLong orLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(XorLong xorLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ShlLong shlLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ShrLong shrLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(UshrLong ushrLong)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AddFloat addFloat)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SubFloat subFloat)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MulFloat mulFloat)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(DivFloat divFloat)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(RemFloat remFloat)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(AddDouble addDouble)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SubDouble subDouble)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MulDouble mulDouble)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(DivDouble divDouble)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(RemDouble remDouble)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Goto32 goto32)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ConstStringJumbo constStringJumbo)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Const const1)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ConstWide32 constWide32)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(FillArrayData fillArrayData)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(PackedSwitch packedSwitch)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(SparseSwitch sparseSwitch)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(Move16 move16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MoveWide16 moveWide16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(MoveObject16 moveObject16)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(FilledNewArray filledNewArray)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(InvokeVirtual invokeVirtual)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(InvokeSuper invokeSuper)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(InvokeDirect invokeDirect)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(InvokeStatic invokeStatic)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(InvokeInterface invokeInterface)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(InvokeVirtualRange invokeVirtualRange)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(InvokeSuperRange invokeSuperRange)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(InvokeDirectRange invokeDirectRange)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(InvokeStaticRange invokeStaticRange)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(InvokeInterfaceRange invokeInterfaceRange)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(FilledNewArrayRange filledNewArrayRange)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void visit(ConstWide constWide)
	{
		// TODO Auto-generated method stub
		
	}
}