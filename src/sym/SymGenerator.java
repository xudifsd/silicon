package sym;

import clojure.lang.PersistentVector;

/* *
 * This class is used to generate unique id for symbolic value.
 * since every Kagebunsin has it's own namespace for id, so this
 * class don't need to be synchronized.
 * */
public class SymGenerator {
	public long id;
	public PersistentVector types; // types[0] is the type of 'sym0'

	public SymGenerator() {
		this.id = 0;
		types = PersistentVector.EMPTY;
	}

	public SymGenerator(long id, PersistentVector types) {
		this.id = id;
		this.types = types;
	}

	public sym.op.Sym genSym(String type) {
		types = types.cons(type);
		return new sym.op.Sym("sym" + id++);
	}

	public SymGenerator clone() {
		return new SymGenerator(id, types);
	}
}