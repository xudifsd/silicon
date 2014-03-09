package vm;

import java.util.Map;

import ast.method.Method;

public class VmClass {
	public String name;
	public String superClass;
	public Map<String, VmField> fieldMap;
	public Map<String, ast.method.Method> methodMap;
	public Map<String, VmField> staticFieldMap;

	public VmClass(String name, String superClass,
			Map<String, VmField> fieldMap, Map<String, Method> methodMap,
			Map<String, VmField> staticFieldMap) {
		super();
		this.name = name;
		this.superClass = superClass;
		this.fieldMap = fieldMap;
		this.methodMap = methodMap;
		this.staticFieldMap = staticFieldMap;
	}
}
