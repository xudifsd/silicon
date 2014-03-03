package vm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import util.MultiThreadUtils.TranslateWorker;
import vm.InterpreterVisitor.VmClass;
import vm.InterpreterVisitor.VmField;
import vm.InterpreterVisitor.VmInstance;
import vm.InterpreterVisitor.VmMethod;

public class Loader {

	public static void print(Object obj) {
		System.out.println(obj);
	}

	public static String getFullMethodName(String className,
			ast.method.Method method) {
		String fullMethodName = className + "->" + method.name + "(";
		for (String str : method.prototype.argsType)
			fullMethodName = fullMethodName + str;
		fullMethodName = fullMethodName + ")" + method.prototype.returnType;
		return fullMethodName;
	}

	public static String getFullMethodName(ast.classs.MethodItem methodItem) {
		String fullMethodName = methodItem.classType + "->"
				+ methodItem.methodName + "(";
		for (String str : methodItem.prototype.argsType)
			fullMethodName = fullMethodName + str;
		fullMethodName = fullMethodName + ")" + methodItem.prototype.returnType;
		return fullMethodName;
	}

	public static String getFullFieldName(ast.classs.FieldItem fieldItem) {
		return fieldItem.classType + "->" + fieldItem.fieldName + ":"
				+ fieldItem.fieldType;
	}

	public static void updateStaticFieldPool(String fullFieldName,
			VmField vmField) {
		InterpreterVisitor.staticFieldMap.put(fullFieldName, vmField);
	}

	public static void updateMethodPool(String fullMethodName, VmMethod vmMethod) {
		InterpreterVisitor.methodMap.put(fullMethodName, vmMethod);
	}

	public static void updateInstancePool(VmInstance instance) {
		InterpreterVisitor.instanceMap.put(instance.name, instance);
	}

	public static Object getSystemInstance(String className) {
		Object systemInstance = null;
		try {
			systemInstance = Class
					.forName(Loader.getFormatClassName(className))
					.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		return systemInstance;
	}

	/*
	 *  haven't handle the [] !!!!!!!!!!!!
	 */
	@SuppressWarnings("rawtypes")
	public static Class[] getArgsTypes(List<String> parameters) {
		Class[] parameterTypes = new Class[parameters.size()];
		String str = null;
		for (int i = 0; i < parameters.size(); i++) {
			str = parameters.get(i);
			switch (str.substring(0, 1)) {
			//			case "V" : break;
			case "Z":
				parameterTypes[i] = Boolean.TYPE;
				break;
			case "B":
				parameterTypes[i] = byte.class;
				break;
			case "S":
				parameterTypes[i] = short.class;
				break;
			case "C":
				parameterTypes[i] = char.class;
				break;
			case "I":
				parameterTypes[i] = int.class;
				break;
			case "J":
				parameterTypes[i] = long.class;
				break;
			case "F":
				parameterTypes[i] = float.class;
				break;
			case "D":
				parameterTypes[i] = double.class;
				break;
			case "L":
				// make a distinction between ast class and system class
				if (Source.classMap.get(str) == null)
					try {
						parameterTypes[i] = Class.forName(Loader
								.getFormatClassName(str));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				else
					parameterTypes[i] = VmInstance.class;
				break;
			case "[":
				break;
			}
		}
		return parameterTypes;
	}

	/*
	 * format of fullMethodName : Lhong/example/Person;-><init>(Ljava/lang/String；I)V
	 */
	public static VmClass updateClassPool(String className,
			ast.classs.Class newClass) {
		//		print("in updateClassPool");
		VmClass vmClass = null;
		String fullMethodName = null;
		Map<String, VmField> fieldMap = new HashMap<String, VmField>();
		Map<String, VmField> staticFieldMap = new HashMap<String, VmField>();
		Map<String, VmMethod> methodMap = new HashMap<String, VmMethod>();
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
			fullMethodName = Loader.getFullMethodName(
					newClass.FullyQualifiedName, method);
			methodMap.put(fullMethodName, new VmMethod(method.name, method));
		}
		//		print("------new class--------");
		//		print(methodMap);
		vmClass = new VmClass(className, fieldMap, methodMap, staticFieldMap);
		InterpreterVisitor.classMap.put(className, vmClass);
		return vmClass;
	}

	/*
	 * in : Ljava/lang/String;
	 * out : java.lang.String
	 */
	public static String getFormatClassName(String className) {
		String[] splits = className.substring(1, className.length() - 1).split(
				"/");
		String result = new String();
		for (int i = 0; i < splits.length - 1; i++)
			result = result + splits[i] + ".";
		return result + splits[splits.length - 1];
	}

	/*
	 * return null case system class 
	 * return vmClass case ast class
	 */
	public static VmClass loadClass(String className) {
		//		print("in loadClass");
		//		print("class name " + className);
		TranslateWorker worker = Source.classMap.get(className);
		VmClass vmClass = null;
		if (worker != null) {
			try {
				vmClass = Loader.updateClassPool(className, worker.call());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//		print("VmClass :" + vmClass);
		return vmClass;
	}

	public static VmInstance loadInstance(String className) {
		//		print("in loadInstance");
		//		print("class name : " + className);
		VmInstance newInstance;
		VmClass vmClass = null;
		if (Source.classMap.get(className) == null) {
			//system class
			newInstance = new VmInstance(className);

		} else {
			//ast class
			//			print("in ast class");
			vmClass = InterpreterVisitor.classMap.get(className);
			if (vmClass == null) {
				try {
					vmClass = Loader.loadClass(className);
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
			Loader.updateInstancePool(newInstance);
			//			System.err.println("newinstance complete");
		}
		return newInstance;
	}

	/*
	 * format of methodName : Lhong/example/Person;->getName()Ljava/lang/String;
	 */
	@SuppressWarnings("rawtypes")
	public static VmMethod loadMethod(ast.classs.MethodItem methodItem) {
		//		print("I in loadMethod");
		String fullMethodName = getFullMethodName(methodItem);
		String className = methodItem.classType;
		VmClass vmClass = InterpreterVisitor.classMap.get(className);
		VmMethod vmMethod = InterpreterVisitor.methodMap.get(fullMethodName);
		if (vmMethod != null)
			return vmMethod;
		if (vmClass == null)
			vmClass = Loader.loadClass(className);
		if (vmClass == null) {
			//system class
			//			print("I in system class");
			String formatClassName = Loader.getFormatClassName(className);
			Class<?> systemClass = null;
			try {
				systemClass = Class.forName(formatClassName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Class[] argsTypes = Loader
					.getArgsTypes(methodItem.prototype.argsType);
			//			for (Class cla : argsTypes)
			////				print("args class[] : " + cla.getName());
			Method systemMethod = null;
			Constructor systemConstructor = null;
			if (methodItem.methodName.equals("<init>")) {
				//				print("is a constructor");
				//system constructor
				try {
					systemConstructor = systemClass.getConstructor(argsTypes);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				vmMethod = new VmMethod(className, systemConstructor);
			} else {
				try {
					systemMethod = systemClass.getMethod(methodItem.methodName,
							argsTypes);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				vmMethod = new VmMethod(className, systemMethod);
			}
		} else {
			//ast class
			//			print("I in ast class");
			vmMethod = vmClass.methodMap.get(fullMethodName);
		}
		Loader.updateMethodPool(fullMethodName, vmMethod);
		//		print("I loadmethod complete : " + fullMethodName);
		return vmMethod;
	}

	public static VmField loadStaticField(ast.classs.FieldItem fieldItem) {
		String fullFieldName = Loader.getFullFieldName(fieldItem);
		//		print("in loadStaticField : " + fullFieldName);
		VmField vmField = InterpreterVisitor.staticFieldMap.get(fullFieldName);
		if (vmField == null) {
			VmClass vmClass = Loader.loadClass(fieldItem.classType);
			if (vmClass == null) {
				//				print("in system class");
				//system class
				//				print("field name : " + fieldItem.fieldName);
				String formatClassName = Loader
						.getFormatClassName(fieldItem.classType);
				try {
					vmField = new VmField(fieldItem.fieldName, Class
							.forName(formatClassName)
							.getField(fieldItem.fieldName).get(null),
							fieldItem.fieldType);
				} catch (IllegalArgumentException | IllegalAccessException
						| NoSuchFieldException | SecurityException
						| ClassNotFoundException e) {
					e.printStackTrace();
				}
				Loader.updateStaticFieldPool(fullFieldName, vmField);
				//				print("vmField : " + vmField.content);
			} else {
				//ast class
				//				print("in ast class");
				vmField = vmClass.staticFieldMap.get(fieldItem.fieldName);
				Loader.updateStaticFieldPool(fullFieldName, vmField);
			}
		}
		//		print("loadStaticField complete");
		return vmField;
	}

	/*
	 * haven't find invoke-interface in ast method
	 * the method is just for system method
	 * interfaceMethod haven't update in methodPool
	 */
	@SuppressWarnings("rawtypes")
	public static VmMethod loadInterfaceMethod(Object instance,
			Class[] argsTypes, String methodName) {
		Method systemMethod = null;
		try {
			//			print("in loadInterfaceMethod : methodName : " + methodName);
			systemMethod = ((VmInstance) instance).systemInstance.getClass()
					.getMethod(methodName, argsTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return new VmMethod(methodName, systemMethod);
	}
}
