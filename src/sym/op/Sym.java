package sym.op;

public class Sym implements IOp {
	public final String value;

	public Sym(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}