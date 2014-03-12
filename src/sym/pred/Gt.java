package sym.pred;

import sym.op.IOp;

public class Gt implements IPrediction {
	public final IOp left;
	public final IOp right;

	public Gt(IOp left, IOp right) {
		this.left = left;
		this.right = right;
	}
}