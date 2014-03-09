package vm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
public class VmMethod {
	public String name;
	public ast.method.Method astMethod;
	public boolean isSystem;
	public boolean isSystemConstructor;
	public Method systemMethod;
	public Constructor<?> systemConstructor;

	public VmMethod(String name, ast.method.Method astMethod) {
		this.name = name;
		this.astMethod = astMethod;
		this.isSystem = false;
	}

	public VmMethod(String name, java.lang.reflect.Method systemMethod) {
		super();
		this.name = name;
		this.systemMethod = systemMethod;
		this.isSystem = true;
	}

	public VmMethod(String name, Constructor<?> systemConstructor) {
		super();
		this.name = name;
		this.systemConstructor = systemConstructor;
		this.isSystem = true;
		this.isSystemConstructor = true;
	}
}
