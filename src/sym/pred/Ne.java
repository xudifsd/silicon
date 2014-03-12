package sym.pred;

import sym.op.IOp;

public class Ne implements IPrediction {
	public final IOp left;
	public final IOp right;

	public Ne(IOp left, IOp right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return "(" + left.toString() + ") != (" + right.toString() + ")";
	}
}