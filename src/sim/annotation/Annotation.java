package sim.annotation;

import java.util.ArrayList;
import java.util.List;

import sim.Visitor;

public class Annotation {
	public static class ElementLiteral extends T {
		public List<Object> element;
		public String type;
		public List<String> arrayLiteralType;

		public ElementLiteral() {
			this.element = new ArrayList<Object>();
			this.arrayLiteralType = new ArrayList<String>();
		}

		public ElementLiteral(List<Object> element, String type,
				List<String> arrayLiteralType) {
			this.element = element;
			this.type = type;
			this.arrayLiteralType = arrayLiteralType;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}
}