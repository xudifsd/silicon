package sym.pred;

import sym.op.IOp;

public class Ge implements IPrediction {
	public final IOp left;
	public final IOp right;

	public Ge(IOp left, IOp right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return String.format("(>= %s %s)", left.toString(), right.toString());
	}
}