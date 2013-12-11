package ast.method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ast.Visitor;

public class Method extends T {
	public String name;
	public int localNum;
	public List<ast.stm.T> statements;
	public MethodPrototype prototype;
	public Map<String, Integer> labels; // name -> offset
	public List<String> registerList;

	public class MethodPrototype {
		public String returnType;
		public List<String> argsType;

		public MethodPrototype(String returnType, List<String> argsType) {
			this.returnType = returnType;
			this.argsType = argsType;
		}
	}

	public Method() {
		statements = new LinkedList<ast.stm.T>();
		labels = new HashMap<String, Integer>();
		registerList = new ArrayList<String>();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}