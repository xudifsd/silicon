package ast.classs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ast.Visitor;

public class Class extends T {
	public String FullyQualifiedName;
	public boolean isMain;
	public String source;
	public String superName;
	public String accessList;
	public List<ast.method.Method> methods;
	public Map<String, String> fields;// name -> type

	public Class() {
		isMain = false;
		fields = new HashMap<String, String>();
		methods = new LinkedList<ast.method.Method>();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}