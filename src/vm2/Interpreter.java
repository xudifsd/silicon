package vm2;

import ast.Visitor;
import ast.annotation.Annotation;
import ast.classs.FieldItem;
import ast.classs.MethodItem;
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
    public void visit(Instruction.Goto goto1) {

    }

    @Override
    public void visit(Instruction.Nop nop) {

    }

    @Override
    public void visit(Instruction.ReturnVoid returnVoid) {

    }

    @Override
    public void visit(Instruction.Const4 const4) {

    }

    @Override
    public void visit(Instruction.MoveResult moveResult) {

    }

    @Override
    public void visit(Instruction.MoveResultWide moveResultWide) {

    }

    @Override
    public void visit(Instruction.MoveResultObject moveResultObject) {

    }

    @Override
    public void visit(Instruction.MoveException moveException) {

    }

    @Override
    public void visit(Instruction.Return return1) {

    }

    @Override
    public void visit(Instruction.ReturnWide returnWide) {

    }

    @Override
    public void visit(Instruction.ReturnObject returnObject) {

    }

    @Override
    public void visit(Instruction.MonitorEnter monitorEnter) {

    }

    @Override
    public void visit(Instruction.MonitorExit monitorExit) {

    }

    @Override
    public void visit(Instruction.Throw throw1) {

    }

    @Override
    public void visit(Instruction.Move move) {

    }

    @Override
    public void visit(Instruction.MoveWide moveWide) {

    }

    @Override
    public void visit(Instruction.MoveObject moveObject) {

    }

    @Override
    public void visit(Instruction.arrayLength arrayLength) {

    }

    @Override
    public void visit(Instruction.NegInt negInt) {

    }

    @Override
    public void visit(Instruction.NotInt notInt) {

    }

    @Override
    public void visit(Instruction.NegLong negLong) {

    }

    @Override
    public void visit(Instruction.NotLong notLong) {

    }

    @Override
    public void visit(Instruction.NegFloat negFloat) {

    }

    @Override
    public void visit(Instruction.NegDouble negDouble) {

    }

    @Override
    public void visit(Instruction.IntToLong intToLong) {

    }

    @Override
    public void visit(Instruction.IntToFloat intToFloat) {

    }

    @Override
    public void visit(Instruction.IntToDouble intToDouble) {

    }

    @Override
    public void visit(Instruction.LongToInt longToInt) {

    }

    @Override
    public void visit(Instruction.LongToFloat longToFloat) {

    }

    @Override
    public void visit(Instruction.LongToDouble longToDouble) {

    }

    @Override
    public void visit(Instruction.FloatToInt floatToInt) {

    }

    @Override
    public void visit(Instruction.FloatToLong floatToLong) {

    }

    @Override
    public void visit(Instruction.FloatToDouble floatToDouble) {

    }

    @Override
    public void visit(Instruction.DoubleToInt doubleToInt) {

    }

    @Override
    public void visit(Instruction.DoubleToLong doubleToLong) {

    }

    @Override
    public void visit(Instruction.DoubleToFloat doubleToFloat) {

    }

    @Override
    public void visit(Instruction.IntToByte intToByte) {

    }

    @Override
    public void visit(Instruction.IntToChar intToChar) {

    }

    @Override
    public void visit(Instruction.IntToShort intToShort) {

    }

    @Override
    public void visit(Instruction.AddInt2Addr addInt2Addr) {

    }

    @Override
    public void visit(Instruction.SubInt2Addr subInt2Addr) {

    }

    @Override
    public void visit(Instruction.MulInt2Addr mulInt2Addr) {

    }

    @Override
    public void visit(Instruction.DivInt2Addr divInt2Addr) {

    }

    @Override
    public void visit(Instruction.RemInt2Addr remInt2Addr) {

    }

    @Override
    public void visit(Instruction.AndInt2Addr andInt2Addr) {

    }

    @Override
    public void visit(Instruction.OrInt2Addr orInt2Addr) {

    }

    @Override
    public void visit(Instruction.XorInt2Addr xorInt2Addr) {

    }

    @Override
    public void visit(Instruction.ShlInt2Addr shlInt2Addr) {

    }

    @Override
    public void visit(Instruction.ShrInt2Addr shrInt2Addr) {

    }

    @Override
    public void visit(Instruction.UshrInt2Addr ushrInt2Addr) {

    }

    @Override
    public void visit(Instruction.AddLong2Addr addLong2Addr) {

    }

    @Override
    public void visit(Instruction.SubLong2Addr subLong2Addr) {

    }

    @Override
    public void visit(Instruction.MulLong2Addr mulLong2Addr) {

    }

    @Override
    public void visit(Instruction.DivLong2Addr divLong2Addr) {

    }

    @Override
    public void visit(Instruction.RemLong2Addr remLong2Addr) {

    }

    @Override
    public void visit(Instruction.AndLong2Addr andLong2Addr) {

    }

    @Override
    public void visit(Instruction.OrLong2Addr orLong2Addr) {

    }

    @Override
    public void visit(Instruction.XorLong2Addr xorLong2Addr) {

    }

    @Override
    public void visit(Instruction.ShlLong2Addr shlLong2Addr) {

    }

    @Override
    public void visit(Instruction.ShrLong2Addr shrLong2Addr) {

    }

    @Override
    public void visit(Instruction.UshrLong2Addr ushrLong2Addr) {

    }

    @Override
    public void visit(Instruction.AddFloat2Addr addFloat2Addr) {

    }

    @Override
    public void visit(Instruction.SubFloat2Addr subFloat2Addr) {

    }

    @Override
    public void visit(Instruction.MulFloat2Addr mulFloat2Addr) {

    }

    @Override
    public void visit(Instruction.DivFloat2Addr divFloat2Addr) {

    }

    @Override
    public void visit(Instruction.RemFloat2Addr remFloat2Addr) {

    }

    @Override
    public void visit(Instruction.AddDouble2Addr addDouble2Addr) {

    }

    @Override
    public void visit(Instruction.SubDouble2Addr subDouble2Addr) {

    }

    @Override
    public void visit(Instruction.MulDouble2Addr mulDouble2Addr) {

    }

    @Override
    public void visit(Instruction.DivDouble2Addr divDouble2Addr) {

    }

    @Override
    public void visit(Instruction.RemDouble2Addr remDouble2Addr) {

    }

    @Override
    public void visit(Instruction.Goto16 goto16) {

    }

    @Override
    public void visit(Instruction.ConstString constString) {

    }

    @Override
    public void visit(Instruction.ConstClass constClass) {

    }

    @Override
    public void visit(Instruction.CheckCast checkCast) {

    }

    @Override
    public void visit(Instruction.NewInstance newInstance) {

    }

    @Override
    public void visit(Instruction.Sget sget) {

    }

    @Override
    public void visit(Instruction.SgetWide sgetWide) {

    }

    @Override
    public void visit(Instruction.SgetObject sgetObject) {

    }

    @Override
    public void visit(Instruction.SgetBoolean sgetBoolean) {

    }

    @Override
    public void visit(Instruction.SgetByte sgetByte) {

    }

    @Override
    public void visit(Instruction.SgetChar sgetChar) {

    }

    @Override
    public void visit(Instruction.SgetShort sgetShort) {

    }

    @Override
    public void visit(Instruction.Sput sput) {

    }

    @Override
    public void visit(Instruction.SputWide sputWide) {

    }

    @Override
    public void visit(Instruction.SputObject sputObject) {

    }

    @Override
    public void visit(Instruction.SputBoolean sputBoolean) {

    }

    @Override
    public void visit(Instruction.SputByte sputByte) {

    }

    @Override
    public void visit(Instruction.SputChar sputChar) {

    }

    @Override
    public void visit(Instruction.SputShort sputShort) {

    }

    @Override
    public void visit(Instruction.ConstHigh16 constHigh16) {

    }

    @Override
    public void visit(Instruction.ConstWideHigh16 constWideHigh16) {

    }

    @Override
    public void visit(Instruction.Const16 const16) {

    }

    @Override
    public void visit(Instruction.ConstWide16 constWide16) {

    }

    @Override
    public void visit(Instruction.IfEqz ifEqz) {

    }

    @Override
    public void visit(Instruction.IfNez ifNez) {

    }

    @Override
    public void visit(Instruction.IfLtz ifLtz) {

    }

    @Override
    public void visit(Instruction.IfGez ifGez) {

    }

    @Override
    public void visit(Instruction.IfGtz ifGtz) {

    }

    @Override
    public void visit(Instruction.IfLez ifLez) {

    }

    @Override
    public void visit(Instruction.AddIntLit8 addIntLit8) {

    }

    @Override
    public void visit(Instruction.RsubIntLit8 rsubIntLit8) {

    }

    @Override
    public void visit(Instruction.MulIntLit8 mulIntLit8) {

    }

    @Override
    public void visit(Instruction.DivIntLit8 divIntLit8) {

    }

    @Override
    public void visit(Instruction.RemIntLit8 remIntLit8) {

    }

    @Override
    public void visit(Instruction.AndIntLit8 andIntLit8) {

    }

    @Override
    public void visit(Instruction.OrIntLit8 orIntLit8) {

    }

    @Override
    public void visit(Instruction.XorIntLit8 xorIntLit8) {

    }

    @Override
    public void visit(Instruction.ShlIntLit8 shlIntLit8) {

    }

    @Override
    public void visit(Instruction.ShrIntLit8 shrIntLit8) {

    }

    @Override
    public void visit(Instruction.UshrIntLit8 ushrIntLit8) {

    }

    @Override
    public void visit(Instruction.InstanceOf instanceOf) {

    }

    @Override
    public void visit(Instruction.NewArray newArray) {

    }

    @Override
    public void visit(Instruction.Iget iget) {

    }

    @Override
    public void visit(Instruction.IgetWide igetWide) {

    }

    @Override
    public void visit(Instruction.IgetOjbect igetOjbect) {

    }

    @Override
    public void visit(Instruction.IgetBoolean igetBoolean) {

    }

    @Override
    public void visit(Instruction.IgetByte igetByte) {

    }

    @Override
    public void visit(Instruction.IgetChar igetChar) {

    }

    @Override
    public void visit(Instruction.IgetShort igetShort) {

    }

    @Override
    public void visit(Instruction.Iput iput) {

    }

    @Override
    public void visit(Instruction.IputWide iputWide) {

    }

    @Override
    public void visit(Instruction.IputObject iputObject) {

    }

    @Override
    public void visit(Instruction.IputBoolean iputBoolean) {

    }

    @Override
    public void visit(Instruction.IputByte iputByte) {

    }

    @Override
    public void visit(Instruction.IputChar iputChar) {

    }

    @Override
    public void visit(Instruction.IputShort iputShort) {

    }

    @Override
    public void visit(Instruction.AddIntLit16 addIntLit16) {

    }

    @Override
    public void visit(Instruction.RsubInt rsubInt) {

    }

    @Override
    public void visit(Instruction.MulIntLit16 mulIntLit16) {

    }

    @Override
    public void visit(Instruction.DivIntLit16 divIntLit16) {

    }

    @Override
    public void visit(Instruction.RemIntLit16 remIntLit16) {

    }

    @Override
    public void visit(Instruction.AndIntLit16 andIntLit16) {

    }

    @Override
    public void visit(Instruction.OrIntLit16 orIntLit16) {

    }

    @Override
    public void visit(Instruction.XorIntLit16 xorIntLit16) {

    }

    @Override
    public void visit(Instruction.IfEq ifEq) {

    }

    @Override
    public void visit(Instruction.IfNe ifNe) {

    }

    @Override
    public void visit(Instruction.IfLt ifLt) {

    }

    @Override
    public void visit(Instruction.IfGe ifGe) {

    }

    @Override
    public void visit(Instruction.IfGt ifGt) {

    }

    @Override
    public void visit(Instruction.IfLe ifLe) {

    }

    @Override
    public void visit(Instruction.MoveFrom16 moveFrom16) {

    }

    @Override
    public void visit(Instruction.MoveWideFrom16 moveWideFrom16) {

    }

    @Override
    public void visit(Instruction.MoveObjectFrom16 moveOjbectFrom16) {

    }

    @Override
    public void visit(Instruction.CmplFloat cmplFloat) {

    }

    @Override
    public void visit(Instruction.CmpgFloat cmpgFloat) {

    }

    @Override
    public void visit(Instruction.CmplDouble cmplDouble) {

    }

    @Override
    public void visit(Instruction.Cmpgdouble cmpgdouble) {

    }

    @Override
    public void visit(Instruction.CmpLong cmpLong) {

    }

    @Override
    public void visit(Instruction.Aget aget) {

    }

    @Override
    public void visit(Instruction.AgetWide agetWide) {

    }

    @Override
    public void visit(Instruction.AgetObject agetObject) {

    }

    @Override
    public void visit(Instruction.AgetBoolean agetBoolean) {

    }

    @Override
    public void visit(Instruction.AgetByte agetByte) {

    }

    @Override
    public void visit(Instruction.AgetChar agetChar) {

    }

    @Override
    public void visit(Instruction.AgetShort agetShort) {

    }

    @Override
    public void visit(Instruction.Aput aput) {

    }

    @Override
    public void visit(Instruction.AputWide aputWide) {

    }

    @Override
    public void visit(Instruction.AputObject aputObject) {

    }

    @Override
    public void visit(Instruction.AputBoolean aputBoolean) {

    }

    @Override
    public void visit(Instruction.AputByte aputByte) {

    }

    @Override
    public void visit(Instruction.AputChar aputChar) {

    }

    @Override
    public void visit(Instruction.AputShort aputShort) {

    }

    @Override
    public void visit(Instruction.AddInt addInt) {

    }

    @Override
    public void visit(Instruction.SubInt subInt) {

    }

    @Override
    public void visit(Instruction.MulInt mulInt) {

    }

    @Override
    public void visit(Instruction.DivInt divInt) {

    }

    @Override
    public void visit(Instruction.RemInt remInt) {

    }

    @Override
    public void visit(Instruction.AndInt andInt) {

    }

    @Override
    public void visit(Instruction.OrInt orInt) {

    }

    @Override
    public void visit(Instruction.XorInt xorInt) {

    }

    @Override
    public void visit(Instruction.ShlInt shlInt) {

    }

    @Override
    public void visit(Instruction.ShrInt shrInt) {

    }

    @Override
    public void visit(Instruction.UshrInt ushrInt) {

    }

    @Override
    public void visit(Instruction.AddLong addLong) {

    }

    @Override
    public void visit(Instruction.SubLong subLong) {

    }

    @Override
    public void visit(Instruction.MulLong mulLong) {

    }

    @Override
    public void visit(Instruction.DivLong divLong) {

    }

    @Override
    public void visit(Instruction.RemLong remLong) {

    }

    @Override
    public void visit(Instruction.AndLong andLong) {

    }

    @Override
    public void visit(Instruction.OrLong orLong) {

    }

    @Override
    public void visit(Instruction.XorLong xorLong) {

    }

    @Override
    public void visit(Instruction.ShlLong shlLong) {

    }

    @Override
    public void visit(Instruction.ShrLong shrLong) {

    }

    @Override
    public void visit(Instruction.UshrLong ushrLong) {

    }

    @Override
    public void visit(Instruction.AddFloat addFloat) {

    }

    @Override
    public void visit(Instruction.SubFloat subFloat) {

    }

    @Override
    public void visit(Instruction.MulFloat mulFloat) {

    }

    @Override
    public void visit(Instruction.DivFloat divFloat) {

    }

    @Override
    public void visit(Instruction.RemFloat remFloat) {

    }

    @Override
    public void visit(Instruction.AddDouble addDouble) {

    }

    @Override
    public void visit(Instruction.SubDouble subDouble) {

    }

    @Override
    public void visit(Instruction.MulDouble mulDouble) {

    }

    @Override
    public void visit(Instruction.DivDouble divDouble) {

    }

    @Override
    public void visit(Instruction.RemDouble remDouble) {

    }

    @Override
    public void visit(Instruction.Goto32 goto32) {

    }

    @Override
    public void visit(Instruction.ConstStringJumbo constStringJumbo) {

    }

    @Override
    public void visit(Instruction.Const const1) {

    }

    @Override
    public void visit(Instruction.ConstWide32 constWide32) {

    }

    @Override
    public void visit(Instruction.FillArrayData fillArrayData) {

    }

    @Override
    public void visit(Instruction.PackedSwitch packedSwitch) {

    }

    @Override
    public void visit(Instruction.SparseSwitch sparseSwitch) {

    }

    @Override
    public void visit(Instruction.Move16 move16) {

    }

    @Override
    public void visit(Instruction.MoveWide16 moveWide16) {

    }

    @Override
    public void visit(Instruction.MoveObject16 moveObject16) {

    }

    @Override
    public void visit(Instruction.FilledNewArray filledNewArray) {

    }

    @Override
    public void visit(Instruction.InvokeVirtual invokeVirtual) {

    }

    @Override
    public void visit(Instruction.InvokeSuper invokeSuper) {

    }

    @Override
    public void visit(Instruction.InvokeDirect invokeDirect) {

    }

    @Override
    public void visit(Instruction.InvokeStatic invokeStatic) {

    }

    @Override
    public void visit(Instruction.InvokeInterface invokeInterface) {

    }

    @Override
    public void visit(Instruction.InvokeVirtualRange invokeVirtualRange) {

    }

    @Override
    public void visit(Instruction.InvokeSuperRange invokeSuperRange) {

    }

    @Override
    public void visit(Instruction.InvokeDirectRange invokeDirectRange) {

    }

    @Override
    public void visit(Instruction.InvokeStaticRange invokeStaticRange) {

    }

    @Override
    public void visit(Instruction.InvokeInterfaceRange invokeInterfaceRange) {

    }

    @Override
    public void visit(Instruction.FilledNewArrayRange filledNewArrayRange) {

    }

    @Override
    public void visit(Instruction.ConstWide constWide) {

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
