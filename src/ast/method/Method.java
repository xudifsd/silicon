package ast.method;

import java.util.ArrayList;
import java.util.List;

import ast.Visitor;
import ast.annotation.Annotation;

public class Method extends T {
	// 1.method_name_and_prototype
	public String name;
	public MethodPrototype prototype;
	// 2.access_list
	public List<String> accessList;
	// 3.registers_directive
	public String registers_directive;
	public String registers_directive_count;
	// 4.labels--
	public List<ast.method.Method.Label> labelList;
	// 7.statements
	public List<ast.stm.T> statements;

	// 5.packed_switch_declarations
	public List<ast.method.Method.PSwitchDec> pSwitchDecList;

	// 6.sparse_switch_declarations
	public List<ast.method.Method.SSwitchDec> sSwitchDecList;
	// 8.catches
	public List<ast.method.Method.Catch> catchList;

	// 9.parameters
	public List<ast.method.Method.Parameter> parameterList;
	// 10.ordered_debug_directives
	public List<ast.method.Method.Debug> debugList;
	// 11.annotations
	public List<ast.annotation.Annotation> annotationList;

	public static class Debug {
		public Type type;
		public Local local;
		public EndLocal endLocal;
		public RestartLocal restartLocal;
		public String addr;

		@Override
		public String toString() {
			if (this.type == Type.LOCAL) {
				if (this.local.literal != null)
					return ".local " + this.local.reg + ", " + this.local.name
							+ ":" + this.local.type + "," + this.local.literal;
				else
					return ".local " + this.local.reg + ", " + this.local.name
							+ ":" + this.local.type;
			} else if (this.type == Type.ENDLOCAL) {
				return ".end local " + this.endLocal.reg;
			} else {
				return ".restart local " + this.restartLocal.reg;
			}

		}

		public Debug(Type type, Local local, EndLocal endLocal,
				RestartLocal restartLocal, String addr) {
			super();
			this.type = type;
			this.local = local;
			this.endLocal = endLocal;
			this.restartLocal = restartLocal;
			this.addr = addr;
		}

		public static class Local {
			public String reg;
			public String name;
			public String type;
			public String literal;

			public Local(String reg, String name, String type, String literal) {
				super();
				this.reg = reg;
				this.name = name;
				this.type = type;
				this.literal = literal;
			}

		}

		public static class EndLocal {
			public String reg;

			public EndLocal(String reg) {
				super();
				this.reg = reg;
			}

		}

		public static class RestartLocal {
			public String reg;

			public RestartLocal(String reg) {
				super();
				this.reg = reg;
			}

		}

		public static enum Type {
			LOCAL, ENDLOCAL, RESTARTLOCAL;
		}

	}

	public static class Label implements Comparable<Label> {
		public String lab;
		public String add;

		public Label(String lab, String add) {
			super();
			this.lab = lab;
			this.add = add;

		}

		public int compareTo(Label la) {
			return Integer.parseInt(this.add) - Integer.parseInt(la.add);
		}

		@Override
		public String toString() {
			return ":" + this.lab;
		}

	}

	public static class Parameter {
		public String value;
		public List<ast.annotation.Annotation> annotationList;

		public Parameter(String value, List<Annotation> annotationList) {
			super();
			this.value = value;
			this.annotationList = annotationList;
		}

	}

	public static class Catch implements Comparable<Catch> {
		// catch catchall
		public String add;
		public boolean isAll;
		public String type;
		public String startLab;
		public String endLab;
		public String catchLab;

		public Catch(String add, String type, String startLab, String endLab,
				String catchLab) {
			super();
			this.add = add;
			this.isAll = false;
			this.type = type;
			this.startLab = startLab;
			this.endLab = endLab;
			this.catchLab = catchLab;
		}

		public Catch(String add, String startLab, String endLab, String catchLab) {
			super();
			this.add = add;
			this.isAll = true;
			this.startLab = startLab;
			this.endLab = endLab;
			this.catchLab = catchLab;
		}

		@Override
		public String toString() {
			if (this.isAll == false)
				return ".catch " + this.type + " {:" + this.startLab + " .. :"
						+ this.endLab + "} :" + this.catchLab;
			else
				return ".catchall " + "{:" + this.startLab + " .. :"
						+ this.endLab + "} :" + this.catchLab;
		}

		public int compareTo(Catch cat) {
			return Integer.parseInt(this.add) - (Integer.parseInt(cat.add));
		}
	}

	public static class PSwitchDec {
		public String add;
		public String dest;

		public PSwitchDec(String add, String dest) {
			super();
			this.add = add;
			this.dest = dest;
		}

	}

	public static class SSwitchDec {
		public String add;
		public String dest;

		public SSwitchDec(String add, String dest) {
			super();
			this.add = add;
			this.dest = dest;
		}

	}

	public static class MethodPrototype extends T {
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
		accessList = new ArrayList<String>();
		statements = new ArrayList<ast.stm.T>();
		labelList = new ArrayList<ast.method.Method.Label>();
		pSwitchDecList = new ArrayList<ast.method.Method.PSwitchDec>();
		sSwitchDecList = new ArrayList<ast.method.Method.SSwitchDec>();
		catchList = new ArrayList<ast.method.Method.Catch>();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}