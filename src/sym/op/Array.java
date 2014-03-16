package sym.op;

public class Array implements IOp {
	final public String type;
	final public IOp length;

	public Array(String type, IOp length) {
		this.type = type;
		this.length = length;
	}

	@Override
	public String toString() {
		return type + " with length " + length;
	}
}