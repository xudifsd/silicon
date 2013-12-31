package ast.annotation;

import java.util.List;

public class Annotation {
	public String visibility;
	public ast.annotation.Annotation.SubAnnotation subAnnotation;

	public Annotation(String visibility, SubAnnotation subAnnotation) {
		super();
		this.visibility = visibility;
		this.subAnnotation = subAnnotation;
	}

	@Override
	public String toString() {
		String str = this.visibility;
		if (str == null)
			str = " ";
		return ".annotation " + str + " " + this.subAnnotation.toString()
				+ ".end annotation\n";
	}

	public static class SubAnnotation {
		public String classType;
		public List<ast.annotation.Annotation.AnnotationElement> elementList;

		public SubAnnotation(String classType,
				List<AnnotationElement> elementList) {
			super();
			this.classType = classType;
			this.elementList = elementList;
		}

		@Override
		public String toString() {
			String str = new String();
			for (ast.annotation.Annotation.AnnotationElement elem : this.elementList) {
				str = str + elem.toString() + "\n";
			}
			return this.classType + "\n" + str;
		}

	}

	public static class AnnotationElement {
		public String name;
		public String value;

		public AnnotationElement(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}

		@Override
		public String toString() {
			return this.name + " = " + this.value;
		}
	}
}
