package sim.classs;

import java.util.List;

import sim.Visitor;

public class Class extends T {
	public String name; // class name
	public List<String> accessList;
	public String superr; // super class
	public String source;
	public List<String> implementsList;
	public List<sim.annotation.Annotation> annotationList;
	public List<Field> fieldList;
	public List<sim.method.Method> methods;

	public static class Field {
		public String name;
		public List<String> accessList;
		public String type;
		public sim.annotation.Annotation.ElementLiteral elementLiteral;
		public List<sim.annotation.Annotation> annotationList;

		public Field(String name, List<String> accessList, String type,
				sim.annotation.Annotation.ElementLiteral elementLiteral,
				List<sim.annotation.Annotation> annotationList) {
			this.name = name;
			this.accessList = accessList;
			this.type = type;
			this.elementLiteral = elementLiteral;
			this.annotationList = annotationList;
		}
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}