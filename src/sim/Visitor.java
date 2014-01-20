package sim;

public interface Visitor {
	public void visit(sim.program.Program program);

	public void visit(sim.classs.Class item);

	public void visit(sim.method.Method method);

	public void visit(sim.annotation.Annotation.ElementLiteral elementLiteral);
}