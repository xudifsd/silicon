package vm;

public class VmInstance {
	public String name;
    private java.util.Map<String, Object> fieldMap;
	public boolean isSystem;
	public Object systemInstance;
//	public Object parentInstance;

	public VmInstance(String name) {
		this.name = name;
        this.fieldMap = new java.util.HashMap<String, Object>();
		this.isSystem = !Source.classMap.containsKey(name);
	}

    public void setField(String fieldName, Object value){
        this.fieldMap.put(fieldName, value);
    }

    public Object getField(String fieldName){
        return this.fieldMap.get(fieldName);
    }
}
