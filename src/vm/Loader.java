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

	public static Object getSystemInstance(String className) {
		Object systemInstance = null;
		try {
			systemInstance = Class.forName(Util.getFormatClassName(className))
					.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		return systemInstance;
	}

	/*
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
	 *  system class : return null;
	 *  ast class : return vmClass
	 */
	public static VmClass loadUserClass(String className) {
		TranslateWorker worker = Source.classMap.get(className);
		VmClass vmClass = null;
		if (worker != null) {
			try {
				vmClass = Loader.updateClassPool(className, worker.call());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vmClass;
	}

	public static VmInstance loadInstance(String className) {
		VmInstance newInstance;
		VmClass vmClass = null;
		if (Source.classMap.get(className) == null) {
			//system class
			newInstance = new VmInstance(className);
		} else {
			//ast class
			vmClass = InterpreterVisitor.classMap.get(className);
			if (vmClass == null) {
				try {
					vmClass = Loader.loadUserClass(className);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			Map<String, VmField> newFieldMap = new HashMap<String, VmField>();
			Map<String, VmField> oldFieldMap = vmClass.fieldMap;
			Map.Entry<String, VmField> entry;
			Iterator<Map.Entry<String, VmField>> iter = oldFieldMap.entrySet()
					.iterator();
			while (iter.hasNext()) {
				entry = iter.next();
				String key = entry.getKey();
				VmField vmfield = entry.getValue();
				newFieldMap.put(key, new VmField(vmfield.name, vmfield.type));
			}
			newInstance = new VmInstance(className, newFieldMap);
		}
		return newInstance;
	}

	public static Object getStaticField(ast.classs.FieldItem fieldItem) {
		String fullFieldName = Util.getFullFieldName(fieldItem);
		Object content = InterpreterVisitor.staticFieldMap.get(fullFieldName);
		if (content != null)
			return content;
		VmClass vmClass = Loader.loadUserClass(fieldItem.classType);
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

	//--------------------------------the new line--------------------------------

	public static ast.method.Method getUserMethod(VmClass vmClass,
			String fullMethodName) {
		return vmClass.methodMap.get(fullMethodName);
	}

	@SuppressWarnings("rawtypes")
	public static Method getSystemMethod(String formateClassName,
			String methodName, Class[] parameterTypes) {
		Method systemMethod = null;
		try {
			systemMethod = Class.forName(formateClassName).getMethod(
					methodName, parameterTypes);
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
		if (Source.classMap.containsKey(fullClassName)) {
			// user method
			VmClass vmClass = InterpreterVisitor.classMap.get(fullClassName);
			if (vmClass == null)
				vmClass = loadUserClass(fullClassName);
			if (vmClass == null)
				printErr("cannot find the staticMethod : " + fullMethodName);
			vmMethod = new VmMethod(methodItem.methodName, getUserMethod(
					vmClass, fullMethodName));
		} else {
			//system method
			String formateClassName = Util.getFormatClassName(fullClassName);
			Class[] parameterTypes = Util
					.getParameterTypes(methodItem.prototype.argsType);
			Method systemMethod = getSystemMethod(formateClassName,
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
		if (Source.classMap.containsKey(fullClassName)) {
			// user method
			VmClass vmClass = InterpreterVisitor.classMap.get(fullClassName);
			if (vmClass == null)
				vmClass = loadUserClass(fullClassName);
			if (vmClass == null)
				printErr("cannot find the directMethod : " + fullMethodName);
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Method systemMethod = getSystemMethod(formateClassName,
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
		String formateClassName = Util.getFormatClassName(fullClassName);
		Class[] parameterTypes = Util
				.getParameterTypes(methodItem.prototype.argsType);
		Method systemMethod = null;
		VmMethod vmMethod = InterpreterVisitor.methodMap.get(fullMethodName);
		if (vmMethod != null)
			return vmMethod;
		if (isSystemRef == true
				|| Source.classMap.containsKey(fullClassName) == false) {
			// system class
			systemMethod = getSystemMethod(formateClassName,
					methodItem.methodName, parameterTypes);
			vmMethod = new VmMethod(methodItem.methodName, systemMethod);
			return new VmMethod(methodItem.methodName, systemMethod);
		}
		// user class
		VmClass currentClass = null;
		String currentName = fullClassName;
		while (!currentName.equals("Ljava/lang/Object;")) {

			if (Source.classMap.containsKey(currentName) == false) {
				//virtual method is system method
				formateClassName = Util.getFormatClassName(currentName);
				systemMethod = getSystemMethod(formateClassName,
						methodItem.methodName, parameterTypes);
				vmMethod = new VmMethod(methodItem.methodName, systemMethod);
				break;
			}

			currentClass = InterpreterVisitor.classMap.get(currentName);
			if (currentClass == null) {
				currentClass = loadUserClass(fullClassName);
				currentName = currentClass.name;
			}
			if (currentClass.methodMap.containsKey(fullMethodName)) {
				vmMethod = new VmMethod(methodItem.methodName,
						currentClass.methodMap.get(fullMethodName));
				break;
			}
		}
		if (vmMethod == null)
			printErr("can't find virtual method: + " + fullMethodName);
		return vmMethod;
	}
}
