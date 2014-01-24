package ast.classs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ast.Visitor;
import ast.annotation.Annotation;

public class Class extends T {
	public String FullyQualifiedName;
	public List<String> accessList;
	public String superName;
	public String source;
	public List<String> implementsList;
	// methods
	public List<ast.method.Method> methods;
	// fields
	public List<Field> fieldList;
	// annotations
	public List<ast.annotation.Annotation> annotationList;

	public static class Field {
		public String name;
		public List<String> accessList;
		public String type;
		//public String initValue;
		public ast.annotation.Annotation.ElementLiteral elementLiteral;
		public List<ast.annotation.Annotation> annotationList;

		public Field(String name, List<String> accessList, String type,
				ast.annotation.Annotation.ElementLiteral elementLiteral,
				List<Annotation> annotationList) {
			super();
			this.name = name;
			this.accessList = accessList;
			this.type = type;
			this.elementLiteral = elementLiteral;
			this.annotationList = annotationList;
		}
	}

	public Class() {
		methods = new LinkedList<ast.method.Method>();
		implementsList = new ArrayList<String>();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}