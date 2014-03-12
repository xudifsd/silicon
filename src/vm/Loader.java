package vm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import util.MultiThreadUtils.TranslateWorker;

public class Loader {
	public static void printErr(Object obj) {
		System.err.println(obj);
		System.exit(1);
	}

	/*
	 * update the InterpreterVisitor.classMap when load a user class
	 * format of fullMethodName : Lhong/example/Person;-><init>(Ljava/lang/String；I)V
	 */
	public static VmClass updateClassPool(String className,
			ast.classs.Class newClass) {
		VmClass vmClass = null;
		String fullMethodName = null;
		Map<String, VmField> fieldMap = new HashMap<String, VmField>();
		Map<String, VmField> staticFieldMap = new HashMap<String, VmField>();
		Map<String, ast.method.Method> methodMap = new HashMap<String, ast.method.Method>();
		for (ast.classs.Class.Field field : newClass.fieldList) {
			boolean isStatic = false;
			for (String access : field.accessList) {
				if (access.equals("static")) {
					staticFieldMap.put(field.name, new VmField(field.name,
							field.type));
					isStatic = true;
					break;
				}
			}
			if (!isStatic) {
				fieldMap.put(field.name, new VmField(field.name, field.type));
			}
		}
		for (ast.method.Method method : newClass.methods) {
			fullMethodName = Util.getFullMethodName(
					newClass.FullyQualifiedName, method);
			methodMap.put(fullMethodName, method);
		}
		vmClass = new VmClass(className, newClass.superName, fieldMap,
				methodMap, staticFieldMap);
		InterpreterVisitor.classMap.put(className, vmClass);
		return vmClass;
	}

	/*
	 * always return a UserClass
	 */
	public static VmClass loadUserClass(String fullClassName) {
		TranslateWorker worker = Source.classMap.get(fullClassName);
		VmClass vmClass = null;
		try {
			vmClass = Loader.updateClassPool(fullClassName, worker.call());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vmClass;
	}

	/*
	 * return a user Class or return null if the class is system class
	 */
	public static VmClass getUserClass(String fullClassName) {
		VmClass vmClass = InterpreterVisitor.classMap.get(fullClassName);
		if (vmClass != null)
			return vmClass;
		if (Source.classMap.containsKey(fullClassName))
			return loadUserClass(fullClassName);
		return null;
	}

	/*
	 * get a system or user static field
	 */
	public static Object getStaticField(ast.classs.FieldItem fieldItem) {
		String fullFieldName = Util.getFullFieldName(fieldItem);
		Object content = InterpreterVisitor.staticFieldMap.get(fullFieldName);
		if (content != null)
			return content;
		VmClass vmClass = Loader.getUserClass(fieldItem.classType);
		if (vmClass == null) {
			//system class
			String formatClassName = Util
					.getFormatClassName(fieldItem.classType);
			try {
				content = Class.forName(formatClassName)
						.getField(fieldItem.fieldName).get(null);
			} catch (IllegalArgumentException | IllegalAccessException
					| NoSuchFieldException | SecurityException
					| ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			content = vmClass.staticFieldMap.get(fieldItem.fieldName);
			if (content == null)
				printErr("can't find static field : " + fullFieldName);
		}
		Util.updateStaticFieldMap(fullFieldName, content);
		return content;
	}

	/*
	 * get user method in a  specific user class
	 */
	public static ast.method.Method getUserMethod(VmClass vmClass,
			String fullMethodName) {
		if (!vmClass.methodMap.containsKey(fullMethodName))
			printErr("can't find user method : + " + fullMethodName);

		return vmClass.methodMap.get(fullMethodName);
	}

	/*
	 * get system method
	 */
	@SuppressWarnings("rawtypes")
	public static Method getSystemMethod(String fullClassName,
			String methodName, Class[] parameterTypes) {
		Method systemMethod = null;
		String formatClassName = Util.getFormatClassName(fullClassName);
		try {
			systemMethod = Class.forName(formatClassName).getMethod(methodName,
					parameterTypes);
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		return systemMethod;
	}

	@SuppressWarnings("rawtypes")
	public static VmMethod getStaticMethod(ast.classs.MethodItem methodItem) {
		String fullClassName = methodItem.classType;
		String fullMethodName = Util.getFullMethodName(methodItem);
		VmMethod vmMethod = InterpreterVisitor.methodMap.get(fullMethodName);
		if (vmMethod != null)
			return vmMethod;
		VmClass vmClass = getUserClass(fullClassName);
		if (vmClass != null) {
			// user method
			vmMethod = new VmMethod(methodItem.methodName, getUserMethod(
					vmClass, fullMethodName));
		} else {
			//system method
			Class[] parameterTypes = Util
					.getParameterTypes(methodItem.prototype.argsType);
			Method systemMethod = getSystemMethod(fullClassName,
					methodItem.methodName, parameterTypes);
			vmMethod = new VmMethod(methodItem.methodName, systemMethod);
		}
		InterpreterVisitor.methodMap.put(fullMethodName, vmMethod);
		return vmMethod;
	}

	@SuppressWarnings("rawtypes")
	public static VmMethod getDirectMethod(ast.classs.MethodItem methodItem) {
		String fullClassName = methodItem.classType;
		String fullMethodName = Util.getFullMethodName(methodItem);
		VmMethod vmMethod = InterpreterVisitor.methodMap.get(fullMethodName);
		if (vmMethod != null)
			return vmMethod;
		VmClass vmClass = getUserClass(fullClassName);
		if (vmClass != null) {
			// user method
			vmMethod = new VmMethod(methodItem.methodName, getUserMethod(
					vmClass, fullMethodName));
		} else {
			// system method
			String formateClassName = Util.getFormatClassName(fullClassName);
			Class[] parameterTypes = Util
					.getParameterTypes(methodItem.prototype.argsType);
			if (methodItem.methodName.equals("<init>")) {
				try {
					Constructor<?> systemConstructor = Class.forName(
							formateClassName).getConstructor(parameterTypes);
					vmMethod = new VmMethod(methodItem.methodName,
							systemConstructor);
				} catch (NoSuchMethodException | SecurityException
						| ClassNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				Method systemMethod = getSystemMethod(fullClassName,
						methodItem.methodName, parameterTypes);
				vmMethod = new VmMethod(methodItem.methodName, systemMethod);
			}

		}
		InterpreterVisitor.methodMap.put(fullMethodName, vmMethod);
		return vmMethod;
	}

	@SuppressWarnings("rawtypes")
	public static VmMethod getVirtualMethod(boolean isSystemRef,
			ast.classs.MethodItem methodItem) {
		String fullClassName = methodItem.classType;
		String fullMethodName = Util.getFullMethodName(methodItem);
		Class[] parameterTypes = Util
				.getParameterTypes(methodItem.prototype.argsType);
		Method systemMethod = null;
		VmMethod vmMethod = InterpreterVisitor.methodMap.get(fullMethodName);
		if (vmMethod != null)
			return vmMethod;
		if (isSystemRef == true
				|| Source.classMap.containsKey(fullClassName) == false) {
			// system method
			systemMethod = getSystemMethod(fullClassName,
					methodItem.methodName, parameterTypes);
			vmMethod = new VmMethod(methodItem.methodName, systemMethod);
			return new VmMethod(methodItem.methodName, systemMethod);
		}

		// user class
		VmClass currentClass = getUserClass(fullClassName);
		String superName = currentClass.superClass;
		while (true) {
			if (currentClass != null) {
				// user class
				if (currentClass.methodMap.containsKey(fullMethodName)) {
					vmMethod = new VmMethod(methodItem.methodName,
							currentClass.methodMap.get(fullMethodName));
					break;
				} else {
					superName = currentClass.superClass;
					currentClass = getUserClass(superName);
				}
			} else {
				//virtual method is system method
				systemMethod = getSystemMethod(superName,
						methodItem.methodName, parameterTypes);
				vmMethod = new VmMethod(methodItem.methodName, systemMethod);
				break;
			}
		}
		if (vmMethod == null)
			printErr("can't find virtual method: + " + fullMethodName);
		return vmMethod;
	}
	
	public VmMethod getSuperMethod() {
		//TODO
		return null;
	}
	public VmMethod getInterfaceMethod() {
		//TODO
		return null;
	}
}
