package sim;

public interface Visitor {
	public void visit(sim.program.Program program);

	public void visit(sim.classs.Class item);

	public void visit(sim.annotation.Annotation annotation);

	public void visit(sim.annotation.Annotation.SubAnnotation subAnnotation);

	public void visit(sim.method.Method method);

	public void visit(sim.annotation.Annotation.ElementLiteral elementLiteral);

	public void visit(sim.stm.Instruction.Nop nop);

	public void visit(sim.stm.Instruction.Move move);

	public void visit(sim.stm.Instruction.MoveResult moveResult);

	public void visit(sim.stm.Instruction.ReturnVoid returnVoid);

	public void visit(sim.stm.Instruction.Return return1);

	public void visit(sim.stm.Instruction.Const const1);

	public void visit(sim.stm.Instruction.Monitor monitor);

	public void visit(sim.stm.Instruction.CheckCast checkCast);

	public void visit(sim.stm.Instruction.InstanceOf instanceOf);

	public void visit(sim.stm.Instruction.ArrayLength arrayLength);

	public void visit(sim.stm.Instruction.NewInstance newInstance);

	public void visit(sim.stm.Instruction.NewArray newArray);

	public void visit(sim.stm.Instruction.FilledNewArray filledNewArray);

	public void visit(sim.stm.Instruction.FillArrayData fillArrayData);

	public void visit(sim.stm.Instruction.Throw throw1);

	public void visit(sim.stm.Instruction.Goto goto1);

	public void visit(sim.stm.Instruction.Switch switch1);

	public void visit(sim.stm.Instruction.Cmp cmp);

	public void visit(sim.stm.Instruction.IfTest ifTest);

	public void visit(sim.stm.Instruction.IfTestz ifTestz);

	public void visit(sim.stm.Instruction.Aget aget);

	public void visit(sim.stm.Instruction.Aput aput);

	public void visit(sim.stm.Instruction.Iget iget);

	public void visit(sim.stm.Instruction.Iput iput);

	public void visit(sim.stm.Instruction.Sget sget);

	public void visit(sim.stm.Instruction.Sput sput);

	public void visit(sim.stm.Instruction.Invoke invoke);

	public void visit(sim.stm.Instruction.UnOp unOp);

	public void visit(sim.stm.Instruction.BinOp binOp);

	public void visit(sim.stm.Instruction.BinOpLit binOpLit);

	public void visit(sim.method.Method.MethodPrototype methodPrototype);

	public void visit(sim.stm.Instruction.ArrayDataDirective arrayDataDirective);

	public void visit(sim.stm.Instruction.PackedSwitchDirective packed);

	public void visit(sim.stm.Instruction.SparseSwitchDirective sparse);
}