package sym.pred;

/* *
 * We may not need this, but to avoid bug.
 * */
public class Not implements IPrediction {
	public final IPrediction op;

	public Not(IPrediction op) {
		this.op = op;
	}

	@Override
	public String toString() {
		return String.format("(not %s)", op.toString());
	}
}