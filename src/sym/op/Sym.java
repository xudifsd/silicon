package sym.op;

public class Sym implements IOp {
	private static class Id {
		private static int id = 0;

		public synchronized static int genId() {
			return id++;
		}
	}

	public final String value;

	public Sym() {
		this.value = "p" + Id.genId();
	}

	@Override
	public String toString() {
		return value;
	}
}