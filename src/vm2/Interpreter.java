package vm2;

import ast.Visitor;
import ast.annotation.Annotation;
import ast.classs.*;
import ast.method.Method;
import ast.program.Program;
import ast.stm.Instruction;

public class Interpreter implements Visitor {
    VM vm;

    // invoke-*:
    //  1. pc++
    //  2. save frame
    //  3. set code/pc/reg

    // return-*:
    //  1. set result
    //  2. pop frame to code/pc/reg


    @Override
    public void visit(MethodItem item) {

    }

    @Override
    public void visit(FieldItem item) {

    }

    @Override
    public void visit(Method method) {

    }

    @Override
    public void visit(Method.MethodPrototype prototype) {

    }

    @Override
    public void visit(Program program) {

    }

    @Override
    public void visit(ast.classs.Class clazz) {

    }

    @Override
    public void visit(Instruction instruction) {

    }

    @Override
    public void visit(Instruction.Nop inst) {

    }

    @Override
    public void visit(Instruction.Move inst) {

    }

    @Override
    public void visit(Instruction.MoveFrom16 inst) {

    }

    @Override
    public void visit(Instruction.Move16 inst) {

    }

    @Override
    public void visit(Instruction.MoveWide inst) {

    }

    @Override
    public void visit(Instruction.MoveWideFrom16 inst) {

    }

    @Override
    public void visit(Instruction.MoveWide16 inst) {

    }

    @Override
    public void visit(Instruction.MoveObject inst) {

    }

    @Override
    public void visit(Instruction.MoveObjectFrom16 inst) {

    }

    @Override
    public void visit(Instruction.MoveObject16 inst) {

    }

    @Override
    public void visit(Instruction.MoveResult inst) {

    }

    @Override
    public void visit(Instruction.MoveResultWide inst) {

    }

    @Override
    public void visit(Instruction.MoveResultObject inst) {

    }

    @Override
    public void visit(Instruction.MoveException inst) {

    }

    @Override
    public void visit(Instruction.ReturnVoid inst) {

    }

    @Override
    public void visit(Instruction.Return inst) {

    }

    @Override
    public void visit(Instruction.ReturnWide inst) {

    }

    @Override
    public void visit(Instruction.ReturnObject inst) {

    }

    @Override
    public void visit(Instruction.Const4 inst) {

    }

    @Override
    public void visit(Instruction.Const16 inst) {

    }

    @Override
    public void visit(Instruction.Const inst) {

    }

    @Override
    public void visit(Instruction.ConstHigh16 inst) {

    }

    @Override
    public void visit(Instruction.ConstWide16 inst) {

    }

    @Override
    public void visit(Instruction.ConstWide32 inst) {

    }

    @Override
    public void visit(Instruction.ConstWide inst) {

    }

    @Override
    public void visit(Instruction.ConstWideHigh16 inst) {

    }

    @Override
    public void visit(Instruction.ConstString inst) {

    }

    @Override
    public void visit(Instruction.ConstStringJumbo inst) {

    }

    @Override
    public void visit(Instruction.ConstClass inst) {

    }

    @Override
    public void visit(Instruction.MonitorEnter inst) {

    }

    @Override
    public void visit(Instruction.MonitorExit inst) {

    }

    @Override
    public void visit(Instruction.CheckCast inst) {

    }

    @Override
    public void visit(Instruction.InstanceOf inst) {

    }

    @Override
    public void visit(Instruction.arrayLength inst) {

    }

    @Override
    public void visit(Instruction.NewInstance inst) {

    }

    @Override
    public void visit(Instruction.NewArray inst) {

    }

    @Override
    public void visit(Instruction.FilledNewArray inst) {

    }

    @Override
    public void visit(Instruction.FilledNewArrayRange inst) {

    }

    @Override
    public void visit(Instruction.FillArrayData inst) {

    }

    @Override
    public void visit(Instruction.Throw inst) {

    }

    @Override
    public void visit(Instruction.Goto inst) {

    }

    @Override
    public void visit(Instruction.Goto16 inst) {

    }

    @Override
    public void visit(Instruction.Goto32 inst) {

    }

    @Override
    public void visit(Instruction.PackedSwitch inst) {

    }

    @Override
    public void visit(Instruction.SparseSwitch inst) {

    }

    @Override
    public void visit(Instruction.CmplFloat inst) {

    }

    @Override
    public void visit(Instruction.CmpgFloat inst) {

    }

    @Override
    public void visit(Instruction.CmplDouble inst) {

    }

    @Override
    public void visit(Instruction.Cmpgdouble inst) {

    }

    @Override
    public void visit(Instruction.CmpLong inst) {

    }

    @Override
    public void visit(Instruction.IfEq inst) {

    }

    @Override
    public void visit(Instruction.IfNe inst) {

    }

    @Override
    public void visit(Instruction.IfLt inst) {

    }

    @Override
    public void visit(Instruction.IfGe inst) {

    }

    @Override
    public void visit(Instruction.IfGt inst) {

    }

    @Override
    public void visit(Instruction.IfLe inst) {

    }

    @Override
    public void visit(Instruction.IfEqz inst) {

    }

    @Override
    public void visit(Instruction.IfNez inst) {

    }

    @Override
    public void visit(Instruction.IfLtz inst) {

    }

    @Override
    public void visit(Instruction.IfGez inst) {

    }

    @Override
    public void visit(Instruction.IfGtz inst) {

    }

    @Override
    public void visit(Instruction.IfLez inst) {

    }

    @Override
    public void visit(Instruction.Aget inst) {

    }

    @Override
    public void visit(Instruction.AgetWide inst) {

    }

    @Override
    public void visit(Instruction.AgetObject inst) {

    }

    @Override
    public void visit(Instruction.AgetBoolean inst) {

    }

    @Override
    public void visit(Instruction.AgetByte inst) {

    }

    @Override
    public void visit(Instruction.AgetChar inst) {

    }

    @Override
    public void visit(Instruction.AgetShort inst) {

    }

    @Override
    public void visit(Instruction.Aput inst) {

    }

    @Override
    public void visit(Instruction.AputWide inst) {

    }

    @Override
    public void visit(Instruction.AputObject inst) {

    }

    @Override
    public void visit(Instruction.AputBoolean inst) {

    }

    @Override
    public void visit(Instruction.AputByte inst) {

    }

    @Override
    public void visit(Instruction.AputChar inst) {

    }

    @Override
    public void visit(Instruction.AputShort inst) {

    }

    @Override
    public void visit(Instruction.Iget inst) {

    }

    @Override
    public void visit(Instruction.IgetWide inst) {

    }

    @Override
    public void visit(Instruction.IgetOjbect inst) {

    }

    @Override
    public void visit(Instruction.IgetBoolean inst) {

    }

    @Override
    public void visit(Instruction.IgetByte inst) {

    }

    @Override
    public void visit(Instruction.IgetChar inst) {

    }

    @Override
    public void visit(Instruction.IgetShort inst) {

    }

    @Override
    public void visit(Instruction.Iput inst) {

    }

    @Override
    public void visit(Instruction.IputWide inst) {

    }

    @Override
    public void visit(Instruction.IputObject inst) {

    }

    @Override
    public void visit(Instruction.IputBoolean inst) {

    }

    @Override
    public void visit(Instruction.IputByte inst) {

    }

    @Override
    public void visit(Instruction.IputChar inst) {

    }

    @Override
    public void visit(Instruction.IputShort inst) {

    }

    @Override
    public void visit(Instruction.Sget inst) {

    }

    @Override
    public void visit(Instruction.SgetWide inst) {

    }

    @Override
    public void visit(Instruction.SgetObject inst) {

    }

    @Override
    public void visit(Instruction.SgetBoolean inst) {

    }

    @Override
    public void visit(Instruction.SgetByte inst) {

    }

    @Override
    public void visit(Instruction.SgetChar inst) {

    }

    @Override
    public void visit(Instruction.SgetShort inst) {

    }

    @Override
    public void visit(Instruction.Sput inst) {

    }

    @Override
    public void visit(Instruction.SputWide inst) {

    }

    @Override
    public void visit(Instruction.SputObject inst) {

    }

    @Override
    public void visit(Instruction.SputBoolean inst) {

    }

    @Override
    public void visit(Instruction.SputByte inst) {

    }

    @Override
    public void visit(Instruction.SputChar inst) {

    }

    @Override
    public void visit(Instruction.SputShort inst) {

    }

    @Override
    public void visit(Instruction.InvokeVirtual inst) {

    }

    @Override
    public void visit(Instruction.InvokeSuper inst) {

    }

    @Override
    public void visit(Instruction.InvokeDirect inst) {

    }

    @Override
    public void visit(Instruction.InvokeStatic inst) {

    }

    @Override
    public void visit(Instruction.InvokeInterface inst) {

    }

    @Override
    public void visit(Instruction.InvokeVirtualRange inst) {

    }

    @Override
    public void visit(Instruction.InvokeSuperRange inst) {

    }

    @Override
    public void visit(Instruction.InvokeDirectRange inst) {

    }

    @Override
    public void visit(Instruction.InvokeStaticRange inst) {

    }

    @Override
    public void visit(Instruction.InvokeInterfaceRange inst) {

    }

    @Override
    public void visit(Instruction.NegInt inst) {

    }

    @Override
    public void visit(Instruction.NotInt inst) {

    }

    @Override
    public void visit(Instruction.NegLong inst) {

    }

    @Override
    public void visit(Instruction.NotLong inst) {

    }

    @Override
    public void visit(Instruction.NegFloat inst) {

    }

    @Override
    public void visit(Instruction.NegDouble inst) {

    }

    @Override
    public void visit(Instruction.IntToLong inst) {

    }

    @Override
    public void visit(Instruction.IntToFloat inst) {

    }

    @Override
    public void visit(Instruction.IntToDouble inst) {

    }

    @Override
    public void visit(Instruction.LongToInt inst) {

    }

    @Override
    public void visit(Instruction.LongToFloat inst) {

    }

    @Override
    public void visit(Instruction.LongToDouble inst) {

    }

    @Override
    public void visit(Instruction.FloatToInt inst) {

    }

    @Override
    public void visit(Instruction.FloatToLong inst) {

    }

    @Override
    public void visit(Instruction.FloatToDouble inst) {

    }

    @Override
    public void visit(Instruction.DoubleToInt inst) {

    }

    @Override
    public void visit(Instruction.DoubleToLong inst) {

    }

    @Override
    public void visit(Instruction.DoubleToFloat inst) {

    }

    @Override
    public void visit(Instruction.IntToByte inst) {

    }

    @Override
    public void visit(Instruction.IntToChar inst) {

    }

    @Override
    public void visit(Instruction.IntToShort inst) {

    }

    @Override
    public void visit(Instruction.AddInt inst) {

    }

    @Override
    public void visit(Instruction.SubInt inst) {

    }

    @Override
    public void visit(Instruction.MulInt inst) {

    }

    @Override
    public void visit(Instruction.DivInt inst) {

    }

    @Override
    public void visit(Instruction.RemInt inst) {

    }

    @Override
    public void visit(Instruction.AndInt inst) {

    }

    @Override
    public void visit(Instruction.OrInt inst) {

    }

    @Override
    public void visit(Instruction.XorInt inst) {

    }

    @Override
    public void visit(Instruction.ShlInt inst) {

    }

    @Override
    public void visit(Instruction.ShrInt inst) {

    }

    @Override
    public void visit(Instruction.UshrInt inst) {

    }

    @Override
    public void visit(Instruction.AddLong inst) {

    }

    @Override
    public void visit(Instruction.SubLong inst) {

    }

    @Override
    public void visit(Instruction.MulLong inst) {

    }

    @Override
    public void visit(Instruction.DivLong inst) {

    }

    @Override
    public void visit(Instruction.RemLong inst) {

    }

    @Override
    public void visit(Instruction.AndLong inst) {

    }

    @Override
    public void visit(Instruction.OrLong inst) {

    }

    @Override
    public void visit(Instruction.XorLong inst) {

    }

    @Override
    public void visit(Instruction.ShlLong inst) {

    }

    @Override
    public void visit(Instruction.ShrLong inst) {

    }

    @Override
    public void visit(Instruction.UshrLong inst) {

    }

    @Override
    public void visit(Instruction.AddFloat inst) {

    }

    @Override
    public void visit(Instruction.SubFloat inst) {

    }

    @Override
    public void visit(Instruction.MulFloat inst) {

    }

    @Override
    public void visit(Instruction.DivFloat inst) {

    }

    @Override
    public void visit(Instruction.RemFloat inst) {

    }

    @Override
    public void visit(Instruction.AddDouble inst) {

    }

    @Override
    public void visit(Instruction.SubDouble inst) {

    }

    @Override
    public void visit(Instruction.MulDouble inst) {

    }

    @Override
    public void visit(Instruction.DivDouble inst) {

    }

    @Override
    public void visit(Instruction.RemDouble inst) {

    }

    @Override
    public void visit(Instruction.AddInt2Addr inst) {

    }

    @Override
    public void visit(Instruction.SubInt2Addr inst) {

    }

    @Override
    public void visit(Instruction.MulInt2Addr inst) {

    }

    @Override
    public void visit(Instruction.DivInt2Addr inst) {

    }

    @Override
    public void visit(Instruction.RemInt2Addr inst) {

    }

    @Override
    public void visit(Instruction.AndInt2Addr inst) {

    }

    @Override
    public void visit(Instruction.OrInt2Addr inst) {

    }

    @Override
    public void visit(Instruction.XorInt2Addr inst) {

    }

    @Override
    public void visit(Instruction.ShlInt2Addr inst) {

    }

    @Override
    public void visit(Instruction.ShrInt2Addr inst) {

    }

    @Override
    public void visit(Instruction.UshrInt2Addr inst) {

    }

    @Override
    public void visit(Instruction.AddLong2Addr inst) {

    }

    @Override
    public void visit(Instruction.SubLong2Addr inst) {

    }

    @Override
    public void visit(Instruction.MulLong2Addr inst) {

    }

    @Override
    public void visit(Instruction.DivLong2Addr inst) {

    }

    @Override
    public void visit(Instruction.RemLong2Addr inst) {

    }

    @Override
    public void visit(Instruction.AndLong2Addr inst) {

    }

    @Override
    public void visit(Instruction.OrLong2Addr inst) {

    }

    @Override
    public void visit(Instruction.XorLong2Addr inst) {

    }

    @Override
    public void visit(Instruction.ShlLong2Addr inst) {

    }

    @Override
    public void visit(Instruction.ShrLong2Addr inst) {

    }

    @Override
    public void visit(Instruction.UshrLong2Addr inst) {

    }

    @Override
    public void visit(Instruction.AddFloat2Addr inst) {

    }

    @Override
    public void visit(Instruction.SubFloat2Addr inst) {

    }

    @Override
    public void visit(Instruction.MulFloat2Addr inst) {

    }

    @Override
    public void visit(Instruction.DivFloat2Addr inst) {

    }

    @Override
    public void visit(Instruction.RemFloat2Addr inst) {

    }

    @Override
    public void visit(Instruction.AddDouble2Addr inst) {

    }

    @Override
    public void visit(Instruction.SubDouble2Addr inst) {

    }

    @Override
    public void visit(Instruction.MulDouble2Addr inst) {

    }

    @Override
    public void visit(Instruction.DivDouble2Addr inst) {

    }

    @Override
    public void visit(Instruction.RemDouble2Addr inst) {

    }

    @Override
    public void visit(Instruction.AddIntLit16 inst) {

    }

    @Override
    public void visit(Instruction.RsubInt inst) {

    }

    @Override
    public void visit(Instruction.MulIntLit16 inst) {

    }

    @Override
    public void visit(Instruction.DivIntLit16 inst) {

    }

    @Override
    public void visit(Instruction.RemIntLit16 inst) {

    }

    @Override
    public void visit(Instruction.AndIntLit16 inst) {

    }

    @Override
    public void visit(Instruction.OrIntLit16 inst) {

    }

    @Override
    public void visit(Instruction.XorIntLit16 inst) {

    }

    @Override
    public void visit(Instruction.AddIntLit8 inst) {

    }

    @Override
    public void visit(Instruction.RsubIntLit8 inst) {

    }

    @Override
    public void visit(Instruction.MulIntLit8 inst) {

    }

    @Override
    public void visit(Instruction.DivIntLit8 inst) {

    }

    @Override
    public void visit(Instruction.RemIntLit8 inst) {

    }

    @Override
    public void visit(Instruction.AndIntLit8 inst) {

    }

    @Override
    public void visit(Instruction.OrIntLit8 inst) {

    }

    @Override
    public void visit(Instruction.XorIntLit8 inst) {

    }

    @Override
    public void visit(Instruction.ShlIntLit8 inst) {

    }

    @Override
    public void visit(Instruction.ShrIntLit8 inst) {

    }

    @Override
    public void visit(Instruction.UshrIntLit8 inst) {

    }

    @Override
    public void visit(Instruction.PackedSwitchDirective packedSwitchDirective) {

    }

    @Override
    public void visit(Instruction.SparseSwitchDirective sparseSwitchDirective) {

    }

    @Override
    public void visit(Instruction.ArrayDataDirective arrayDataDirective) {

    }

    @Override
    public void visit(Annotation annotation) {

    }

    @Override
    public void visit(Annotation.SubAnnotation subAnnotation) {

    }

    @Override
    public void visit(Annotation.ElementLiteral elementLiteral) {

    }

}
