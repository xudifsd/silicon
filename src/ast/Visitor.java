package ast;


public interface Visitor {
	// program
	public void visit(ast.classs.MethodItem item);
	public void visit(ast.classs.FieldItem item);
	public void visit(ast.method.Method method);
	public void visit(ast.method.Method.MethodPrototype prototype);
	public void visit(ast.program.Program program);
	public void visit(ast.classs.Class clazz);
	public void visit(ast.stm.Instruction instruction);

    // 0x00
    public void visit(ast.stm.Instruction.Nop inst);
    public void visit(ast.stm.Instruction.Move inst);
    public void visit(ast.stm.Instruction.MoveFrom16 inst);
    public void visit(ast.stm.Instruction.Move16 inst);
    public void visit(ast.stm.Instruction.MoveWide inst);
    public void visit(ast.stm.Instruction.MoveWideFrom16 inst);
    public void visit(ast.stm.Instruction.MoveWide16 inst);
    public void visit(ast.stm.Instruction.MoveObject inst);
    public void visit(ast.stm.Instruction.MoveObjectFrom16 inst);
    public void visit(ast.stm.Instruction.MoveObject16 inst);
    public void visit(ast.stm.Instruction.MoveResult inst);
    public void visit(ast.stm.Instruction.MoveResultWide inst);
    public void visit(ast.stm.Instruction.MoveResultObject inst);
    public void visit(ast.stm.Instruction.MoveException inst);
    public void visit(ast.stm.Instruction.ReturnVoid inst);
    public void visit(ast.stm.Instruction.Return inst);

    //0x10
    public void visit(ast.stm.Instruction.ReturnWide inst);
    public void visit(ast.stm.Instruction.ReturnObject inst);
    public void visit(ast.stm.Instruction.Const4 inst);
    public void visit(ast.stm.Instruction.Const16 inst);
    public void visit(ast.stm.Instruction.Const inst);
    public void visit(ast.stm.Instruction.ConstHigh16 inst);
    public void visit(ast.stm.Instruction.ConstWide16 inst);
    public void visit(ast.stm.Instruction.ConstWide32 inst);
    public void visit(ast.stm.Instruction.ConstWide inst);
    public void visit(ast.stm.Instruction.ConstWideHigh16 inst);
    public void visit(ast.stm.Instruction.ConstString inst);
    public void visit(ast.stm.Instruction.ConstStringJumbo inst);
    public void visit(ast.stm.Instruction.ConstClass inst);
    public void visit(ast.stm.Instruction.MonitorEnter inst);
    public void visit(ast.stm.Instruction.MonitorExit inst);
    public void visit(ast.stm.Instruction.CheckCast inst);

    //0x20
    public void visit(ast.stm.Instruction.InstanceOf inst);
    public void visit(ast.stm.Instruction.arrayLength inst);
    public void visit(ast.stm.Instruction.NewInstance inst);
    public void visit(ast.stm.Instruction.NewArray inst);
    public void visit(ast.stm.Instruction.FilledNewArray inst);
    public void visit(ast.stm.Instruction.FilledNewArrayRange inst);
    public void visit(ast.stm.Instruction.FillArrayData inst);
    public void visit(ast.stm.Instruction.Throw inst);
    public void visit(ast.stm.Instruction.Goto inst);
    public void visit(ast.stm.Instruction.Goto16 inst);
    public void visit(ast.stm.Instruction.Goto32 inst);

    //0x2b
    public void visit(ast.stm.Instruction.PackedSwitch inst);
    public void visit(ast.stm.Instruction.SparseSwitch inst);

    //0x2d
    public void visit(ast.stm.Instruction.CmplFloat inst);
    public void visit(ast.stm.Instruction.CmpgFloat inst);
    public void visit(ast.stm.Instruction.CmplDouble inst);
    public void visit(ast.stm.Instruction.Cmpgdouble inst);
    public void visit(ast.stm.Instruction.CmpLong inst);

    //0x32
    public void visit(ast.stm.Instruction.IfEq inst);
    public void visit(ast.stm.Instruction.IfNe inst);
    public void visit(ast.stm.Instruction.IfLt inst);
    public void visit(ast.stm.Instruction.IfGe inst);
    public void visit(ast.stm.Instruction.IfGt inst);
    public void visit(ast.stm.Instruction.IfLe inst);

    //0x38
    public void visit(ast.stm.Instruction.IfEqz inst);
    public void visit(ast.stm.Instruction.IfNez inst);
    public void visit(ast.stm.Instruction.IfLtz inst);
    public void visit(ast.stm.Instruction.IfGez inst);
    public void visit(ast.stm.Instruction.IfGtz inst);
    public void visit(ast.stm.Instruction.IfLez inst);

    // 0x44
    public void visit(ast.stm.Instruction.Aget inst);
    public void visit(ast.stm.Instruction.AgetWide inst);
    public void visit(ast.stm.Instruction.AgetObject inst);
    public void visit(ast.stm.Instruction.AgetBoolean inst);
    public void visit(ast.stm.Instruction.AgetByte inst);
    public void visit(ast.stm.Instruction.AgetChar inst);
    public void visit(ast.stm.Instruction.AgetShort inst);
    public void visit(ast.stm.Instruction.Aput inst);
    public void visit(ast.stm.Instruction.AputWide inst);
    public void visit(ast.stm.Instruction.AputObject inst);
    public void visit(ast.stm.Instruction.AputBoolean inst);
    public void visit(ast.stm.Instruction.AputByte inst);
    public void visit(ast.stm.Instruction.AputChar inst);
    public void visit(ast.stm.Instruction.AputShort inst);

    //0x52
    public void visit(ast.stm.Instruction.Iget inst);
    public void visit(ast.stm.Instruction.IgetWide inst);
    public void visit(ast.stm.Instruction.IgetOjbect inst);
    public void visit(ast.stm.Instruction.IgetBoolean inst);
    public void visit(ast.stm.Instruction.IgetByte inst);
    public void visit(ast.stm.Instruction.IgetChar inst);
    public void visit(ast.stm.Instruction.IgetShort inst);
    public void visit(ast.stm.Instruction.Iput inst);
    public void visit(ast.stm.Instruction.IputWide inst);
    public void visit(ast.stm.Instruction.IputObject inst);
    public void visit(ast.stm.Instruction.IputBoolean inst);
    public void visit(ast.stm.Instruction.IputByte inst);
    public void visit(ast.stm.Instruction.IputChar inst);
    public void visit(ast.stm.Instruction.IputShort inst);

    //0x60
    public void visit(ast.stm.Instruction.Sget inst);
    public void visit(ast.stm.Instruction.SgetWide inst);
    public void visit(ast.stm.Instruction.SgetObject inst);
    public void visit(ast.stm.Instruction.SgetBoolean inst);
    public void visit(ast.stm.Instruction.SgetByte inst);
    public void visit(ast.stm.Instruction.SgetChar inst);
    public void visit(ast.stm.Instruction.SgetShort inst);
    public void visit(ast.stm.Instruction.Sput inst);
    public void visit(ast.stm.Instruction.SputWide inst);
    public void visit(ast.stm.Instruction.SputObject inst);
    public void visit(ast.stm.Instruction.SputBoolean inst);
    public void visit(ast.stm.Instruction.SputByte inst);
    public void visit(ast.stm.Instruction.SputChar inst);
    public void visit(ast.stm.Instruction.SputShort inst);

    //0x6e
    public void visit(ast.stm.Instruction.InvokeVirtual inst);
    public void visit(ast.stm.Instruction.InvokeSuper inst);
    public void visit(ast.stm.Instruction.InvokeDirect inst);
    public void visit(ast.stm.Instruction.InvokeStatic inst);
    public void visit(ast.stm.Instruction.InvokeInterface inst);

    //0x74
    public void visit(ast.stm.Instruction.InvokeVirtualRange inst);
    public void visit(ast.stm.Instruction.InvokeSuperRange inst);
    public void visit(ast.stm.Instruction.InvokeDirectRange inst);
    public void visit(ast.stm.Instruction.InvokeStaticRange inst);
    public void visit(ast.stm.Instruction.InvokeInterfaceRange inst);

    //0x7b
    public void visit(ast.stm.Instruction.NegInt inst);
    public void visit(ast.stm.Instruction.NotInt inst);
    public void visit(ast.stm.Instruction.NegLong inst);
    public void visit(ast.stm.Instruction.NotLong inst);
    public void visit(ast.stm.Instruction.NegFloat inst);
    public void visit(ast.stm.Instruction.NegDouble inst);
    public void visit(ast.stm.Instruction.IntToLong inst);
    public void visit(ast.stm.Instruction.IntToFloat inst);
    public void visit(ast.stm.Instruction.IntToDouble inst);
    public void visit(ast.stm.Instruction.LongToInt inst);
    public void visit(ast.stm.Instruction.LongToFloat inst);
    public void visit(ast.stm.Instruction.LongToDouble inst);
    public void visit(ast.stm.Instruction.FloatToInt inst);
    public void visit(ast.stm.Instruction.FloatToLong inst);
    public void visit(ast.stm.Instruction.FloatToDouble inst);
    public void visit(ast.stm.Instruction.DoubleToInt inst);
    public void visit(ast.stm.Instruction.DoubleToLong inst);
    public void visit(ast.stm.Instruction.DoubleToFloat inst);
    public void visit(ast.stm.Instruction.IntToByte inst);
    public void visit(ast.stm.Instruction.IntToChar inst);
    public void visit(ast.stm.Instruction.IntToShort inst);

    //0x90
    public void visit(ast.stm.Instruction.AddInt inst);
    public void visit(ast.stm.Instruction.SubInt inst);
    public void visit(ast.stm.Instruction.MulInt inst);
    public void visit(ast.stm.Instruction.DivInt inst);
    public void visit(ast.stm.Instruction.RemInt inst);
    public void visit(ast.stm.Instruction.AndInt inst);
    public void visit(ast.stm.Instruction.OrInt inst);
    public void visit(ast.stm.Instruction.XorInt inst);
    public void visit(ast.stm.Instruction.ShlInt inst);
    public void visit(ast.stm.Instruction.ShrInt inst);
    public void visit(ast.stm.Instruction.UshrInt inst);
    public void visit(ast.stm.Instruction.AddLong inst);
    public void visit(ast.stm.Instruction.SubLong inst);
    public void visit(ast.stm.Instruction.MulLong inst);
    public void visit(ast.stm.Instruction.DivLong inst);
    public void visit(ast.stm.Instruction.RemLong inst);
    public void visit(ast.stm.Instruction.AndLong inst);
    public void visit(ast.stm.Instruction.OrLong inst);
    public void visit(ast.stm.Instruction.XorLong inst);
    public void visit(ast.stm.Instruction.ShlLong inst);
    public void visit(ast.stm.Instruction.ShrLong inst);
    public void visit(ast.stm.Instruction.UshrLong inst);
    public void visit(ast.stm.Instruction.AddFloat inst);
    public void visit(ast.stm.Instruction.SubFloat inst);
    public void visit(ast.stm.Instruction.MulFloat inst);
    public void visit(ast.stm.Instruction.DivFloat inst);
    public void visit(ast.stm.Instruction.RemFloat inst);
    public void visit(ast.stm.Instruction.AddDouble inst);
    public void visit(ast.stm.Instruction.SubDouble inst);
    public void visit(ast.stm.Instruction.MulDouble inst);
    public void visit(ast.stm.Instruction.DivDouble inst);
    public void visit(ast.stm.Instruction.RemDouble inst);

    //0xb0
    public void visit(ast.stm.Instruction.AddInt2Addr inst);
    public void visit(ast.stm.Instruction.SubInt2Addr inst);
    public void visit(ast.stm.Instruction.MulInt2Addr inst);
    public void visit(ast.stm.Instruction.DivInt2Addr inst);
    public void visit(ast.stm.Instruction.RemInt2Addr inst);
    public void visit(ast.stm.Instruction.AndInt2Addr inst);
    public void visit(ast.stm.Instruction.OrInt2Addr inst);
    public void visit(ast.stm.Instruction.XorInt2Addr inst);
    public void visit(ast.stm.Instruction.ShlInt2Addr inst);
    public void visit(ast.stm.Instruction.ShrInt2Addr inst);
    public void visit(ast.stm.Instruction.UshrInt2Addr inst);
    public void visit(ast.stm.Instruction.AddLong2Addr inst);
    public void visit(ast.stm.Instruction.SubLong2Addr inst);
    public void visit(ast.stm.Instruction.MulLong2Addr inst);
    public void visit(ast.stm.Instruction.DivLong2Addr inst);
    public void visit(ast.stm.Instruction.RemLong2Addr inst);
    public void visit(ast.stm.Instruction.AndLong2Addr inst);
    public void visit(ast.stm.Instruction.OrLong2Addr inst);
    public void visit(ast.stm.Instruction.XorLong2Addr inst);
    public void visit(ast.stm.Instruction.ShlLong2Addr inst);
    public void visit(ast.stm.Instruction.ShrLong2Addr inst);
    public void visit(ast.stm.Instruction.UshrLong2Addr inst);
    public void visit(ast.stm.Instruction.AddFloat2Addr inst);
    public void visit(ast.stm.Instruction.SubFloat2Addr inst);
    public void visit(ast.stm.Instruction.MulFloat2Addr inst);
    public void visit(ast.stm.Instruction.DivFloat2Addr inst);
    public void visit(ast.stm.Instruction.RemFloat2Addr inst);
    public void visit(ast.stm.Instruction.AddDouble2Addr inst);
    public void visit(ast.stm.Instruction.SubDouble2Addr inst);
    public void visit(ast.stm.Instruction.MulDouble2Addr inst);
    public void visit(ast.stm.Instruction.DivDouble2Addr inst);
    public void visit(ast.stm.Instruction.RemDouble2Addr inst);

    //0xd0
    public void visit(ast.stm.Instruction.AddIntLit16 inst);
    public void visit(ast.stm.Instruction.RsubInt inst);
    public void visit(ast.stm.Instruction.MulIntLit16 inst);
    public void visit(ast.stm.Instruction.DivIntLit16 inst);
    public void visit(ast.stm.Instruction.RemIntLit16 inst);
    public void visit(ast.stm.Instruction.AndIntLit16 inst);
    public void visit(ast.stm.Instruction.OrIntLit16 inst);
    public void visit(ast.stm.Instruction.XorIntLit16 inst);

    //0xd8
    public void visit(ast.stm.Instruction.AddIntLit8 inst);
    public void visit(ast.stm.Instruction.RsubIntLit8 inst);
    public void visit(ast.stm.Instruction.MulIntLit8 inst);
    public void visit(ast.stm.Instruction.DivIntLit8 inst);
    public void visit(ast.stm.Instruction.RemIntLit8 inst);
    public void visit(ast.stm.Instruction.AndIntLit8 inst);
    public void visit(ast.stm.Instruction.OrIntLit8 inst);
    public void visit(ast.stm.Instruction.XorIntLit8 inst);
    public void visit(ast.stm.Instruction.ShlIntLit8 inst);
    public void visit(ast.stm.Instruction.ShrIntLit8 inst);
    public void visit(ast.stm.Instruction.UshrIntLit8 inst);

	public void visit(ast.stm.Instruction.PackedSwitchDirective packedSwitchDirective);
	public void visit(ast.stm.Instruction.SparseSwitchDirective sparseSwitchDirective);
	public void visit(ast.stm.Instruction.ArrayDataDirective arrayDataDirective);
	
	public void visit(ast.annotation.Annotation annotation);
	public void visit(ast.annotation.Annotation.SubAnnotation subAnnotation);
	public void visit(ast.annotation.Annotation.ElementLiteral elementLiteral);
}