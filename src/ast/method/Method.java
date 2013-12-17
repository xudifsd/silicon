package ast.method;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ast.Visitor;

public class Method extends T {
	public String name;
	public String localNum;
	public List<ast.stm.T> statements;
	public MethodPrototype prototype;
	public Map<String, Integer> labels; // name -> offset
	public List<String> accessList;
	public String registers_directive;
	public String registers_directive_count;

	public static class MethodPrototype extends T{
		public String returnType;
		public List<String> argsType;

		public MethodPrototype() {
		}
		public MethodPrototype(String returnType, List<String> argsType) {
			this.returnType = returnType;
			this.argsType = argsType;
		}
		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public Method() {
		statements = new LinkedList<ast.stm.T>();
		labels = new HashMap<String, Integer>();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}