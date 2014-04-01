package sym.op;

import clojure.lang.IPersistentMap;

// TODO currently we ignore type check for field
public class Obj implements IOp {
	public final String className;
	private IPersistentMap fields; // field name -> IOp

	public Obj(String className) {
		this.className = className;
	}

	public IOp iget(String fieldName) {
		return (IOp) fields.valAt(fieldName);
	}

	public void iput(String fieldName, IOp value) {
		fields = fields.assoc(fieldName, value);
	}
}