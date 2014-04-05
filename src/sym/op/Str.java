package sym.op;

// Constant String
public class Str implements IOp {
	public final String value;

	public Str(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}