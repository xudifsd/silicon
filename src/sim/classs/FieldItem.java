package sim.classs;

import sim.Visitor;

public class FieldItem extends T {
	public String classType;
	public String fieldName;
	public String fieldType;

	public FieldItem(String classType, String fieldName, String fieldType) {
		super();
		this.classType = classType;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return this.classType + "->" + this.fieldName + ":" + this.fieldType;
	}

}
