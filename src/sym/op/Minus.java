package sym.op;

public class Minus implements IOp {
	final public IOp left;
	final public IOp right;

	public Minus(IOp left, IOp right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return "(" + left.toString() + ") - (" + right.toString() + ")";
	}
}