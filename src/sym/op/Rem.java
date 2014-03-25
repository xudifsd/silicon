package sym.op;

public class Rem implements IOp {
	final public IOp left;
	final public IOp right;

	public Rem(IOp left, IOp right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return String.format("(mod %s %s)", left.toString(), right.toString());
	}
}
