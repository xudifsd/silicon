package sim;

import java.io.Serializable;

public interface Acceptable extends Serializable {
	public void accept(Visitor v);
}