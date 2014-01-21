package sim.method;

import java.util.ArrayList;
import java.util.List;

import sim.Visitor;
import sim.annotation.Annotation;
import sim.method.T;

public class Method extends T {
	// 1.method_name_and_prototype
	public String name;
	public MethodPrototype prototype;
	
	// 2.access_list
	public List<String> accessList;
	
	// 3.registers_directive
	public String registers_directive;
	
	public String registers_directive_count;

	// 7.statements
	public List<sim.stm.T> statements;
	
	// 8.catches
	public List<sim.method.Method.Catch> catchList;

	// 9.parameters
	public List<sim.method.Method.Parameter> parameterList;

	// 10.annotations
	public List<sim.annotation.Annotation> annotationList;

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
		public List<sim.annotation.Annotation> annotationList;

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

		public Catch(String add, boolean isAll, String type, String startLab, String endLab,
				String catchLab) {
			super();
			this.add = add;
			this.isAll = isAll;
			this.type = type;
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
		statements = new ArrayList<sim.stm.T>();
		catchList = new ArrayList<sim.method.Method.Catch>();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}