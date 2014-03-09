package vm;

import java.util.Map;

public class VmInstance {
	public String name;
	public Map<String, VmField> fieldMap;
	public boolean isSystem;
	public Object systemInstance;
	public Object parentInstance;

	public VmInstance(String name, Map<String, VmField> fieldMap) {
		this.name = name;
		this.fieldMap = fieldMap;
		this.isSystem = false;
	}

	public VmInstance(String name) {
		this.name = name;
		this.isSystem = true;
	}
}
