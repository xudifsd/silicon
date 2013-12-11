package ast.program;

import ast.Visitor;

import java.util.Map;
import java.util.HashMap;

public class Program extends T {
	public Map<String, ast.classs.Class> classs;
	public String mainClassName;

	public Program() {
		classs = new HashMap<String, ast.classs.Class>();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
		return;
	}
}