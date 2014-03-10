package vm;

import java.util.List;

public class Util {
	
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
	
	public static void updateStaticFieldMap(String fullFieldName,
			Object content) {
		InterpreterVisitor.staticFieldMap.put(fullFieldName, content);
	}

	public static void updateMethodPool(String fullMethodName, VmMethod vmMethod) {
		InterpreterVisitor.methodMap.put(fullMethodName, vmMethod);
	}
	
	/*
	 *  haven't handle the [] !!!!!!!!!!!!
	 */
	@SuppressWarnings("rawtypes")
	public static Class[] getParameterTypes(List<String> parameters) {
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
						parameterTypes[i] = Class.forName(getFormatClassName(str));
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

}
