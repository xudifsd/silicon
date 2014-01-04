package ast.annotation;

import java.util.ArrayList;
import java.util.List;

import ast.Visitor;


public class Annotation extends T{
	public String visibility;
	public ast.annotation.Annotation.SubAnnotation subAnnotation;
	public Annotation(String visibility, SubAnnotation subAnnotation) {
		super();
		this.visibility = visibility;
		this.subAnnotation = subAnnotation;
	}
	public static class SubAnnotation  extends T{
		public String classType;
		public List<ast.annotation.Annotation.AnnotationElement> elementList;
		public SubAnnotation() {
			super();
		}
		public SubAnnotation(String classType,
				List<AnnotationElement> elementList) {
			super();
			this.classType = classType;
			this.elementList = elementList;
		}
		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
		
	}
	public static class AnnotationElement {
		public String name;
		public ast.annotation.Annotation.ElementLiteral elementLiteral;
		public AnnotationElement(String name, ElementLiteral elementLiteral) {
			super();
			this.name = name;
			this.elementLiteral = elementLiteral;
		}
	}
	public static class ElementLiteral extends T{
		public List<Object> element;
		// subannotation,array,other
		public String type;
		// subannotation,other
		public List<String> arrayLiteralType;
		public ElementLiteral() {
			this.element = new ArrayList<Object>();
			this.arrayLiteralType = new ArrayList<String>();
		}
		public ElementLiteral(List<Object> element, String type,
				List<String> arrayLiteralType) {
			super();
			this.element = element;
			this.type = type;
			this.arrayLiteralType = arrayLiteralType;
		}
		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
	
}
