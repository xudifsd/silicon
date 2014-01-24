package sim.program;

import java.util.Map;

public class Program extends T {
	public Map<String, ast.classs.Class> classs;

	@Override
	public void accept(sim.Visitor v) {
		v.visit(this);
	}
}
