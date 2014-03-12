package sym.op;

public class Const implements IOp {
	public final long value;

	public Const(long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Long.toString(value);
	}
}