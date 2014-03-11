package vm;

public class VmField {
	public String name;
	public Object content;
	public String type;
	public boolean isSystem;

	public VmField(String name, String type) {
		this.name = name;
		this.type = type;
		this.isSystem = false;
	}

	public VmField(String name, Object content, String type) {
		this.name = name;
		this.content = content;
		this.type = type;
		this.isSystem = true;
	}
}
