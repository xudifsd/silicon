package sym;

/* *
 * Interface to talk to Z3
 * */
public class Z3Stub {
	/* *
	 * `calculate` must be synchronized, because this method will call by
	 * multiple thread
	 * */
	public synchronized String calculate(String prediction) {
		return "";
	}
}