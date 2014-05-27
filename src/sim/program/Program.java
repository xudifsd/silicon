package sim.program;

import java.util.Map;

public class Program extends T {
	private static final long serialVersionUID = 1L;
	public Map<String, ast.classs.Class> classs;

	@Override
	public void accept(sim.Visitor v) {
		v.visit(this);
	}
}
