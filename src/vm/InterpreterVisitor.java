package vm;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import ast.Visitor;
import ast.annotation.Annotation;
import ast.annotation.Annotation.ElementLiteral;
import ast.annotation.Annotation.SubAnnotation;
import ast.classs.FieldItem;
import ast.classs.MethodItem;
import ast.method.Method;
import ast.method.Method.MethodPrototype;
import ast.program.Program;
import ast.stm.*;
import ast.stm.Instruction.AddDouble;
import ast.stm.Instruction.AddDouble2Addr;
import ast.stm.Instruction.AddFloat;
import ast.stm.Instruction.AddFloat2Addr;
import ast.stm.Instruction.AddInt;
import ast.stm.Instruction.AddInt2Addr;
import ast.stm.Instruction.AddIntLit16;
import ast.stm.Instruction.AddIntLit8;
import ast.stm.Instruction.AddLong;
import ast.stm.Instruction.AddLong2Addr;
import ast.stm.Instruction.Aget;
import ast.stm.Instruction.AgetBoolean;
import ast.stm.Instruction.AgetByte;
import ast.stm.Instruction.AgetChar;
import ast.stm.Instruction.AgetObject;
import ast.stm.Instruction.AgetShort;
import ast.stm.Instruction.AgetWide;
import ast.stm.Instruction.AndInt;
import ast.stm.Instruction.AndInt2Addr;
import ast.stm.Instruction.AndIntLit16;
import ast.stm.Instruction.AndIntLit8;
import ast.stm.Instruction.AndLong;
import ast.stm.Instruction.AndLong2Addr;
import ast.stm.Instruction.Aput;
import ast.stm.Instruction.AputBoolean;
import ast.stm.Instruction.AputByte;
import ast.stm.Instruction.AputChar;
import ast.stm.Instruction.AputObject;
import ast.stm.Instruction.AputShort;
import ast.stm.Instruction.AputWide;
import ast.stm.Instruction.ArrayDataDirective;
import ast.stm.Instruction.CheckCast;
import ast.stm.Instruction.CmpLong;
import ast.stm.Instruction.CmpgFloat;
import ast.stm.Instruction.Cmpgdouble;
import ast.stm.Instruction.CmplDouble;
import ast.stm.Instruction.CmplFloat;
import ast.stm.Instruction.Const;
import ast.stm.Instruction.Const16;
import ast.stm.Instruction.Const4;
import ast.stm.Instruction.ConstClass;
import ast.stm.Instruction.ConstHigh16;
import ast.stm.Instruction.ConstString;
import ast.stm.Instruction.ConstStringJumbo;
import ast.stm.Instruction.ConstWide;
import ast.stm.Instruction.ConstWide16;
import ast.stm.Instruction.ConstWide32;
import ast.stm.Instruction.ConstWideHigh16;
import ast.stm.Instruction.DivDouble;
import ast.stm.Instruction.DivDouble2Addr;
import ast.stm.Instruction.DivFloat;
import ast.stm.Instruction.DivFloat2Addr;
import ast.stm.Instruction.DivInt;
import ast.stm.Instruction.DivInt2Addr;
import ast.stm.Instruction.DivIntLit16;
import ast.stm.Instruction.DivIntLit8;
import ast.stm.Instruction.DivLong;
import ast.stm.Instruction.DivLong2Addr;
import ast.stm.Instruction.DoubleToFloat;
import ast.stm.Instruction.DoubleToInt;
import ast.stm.Instruction.DoubleToLong;
import ast.stm.Instruction.FillArrayData;
import ast.stm.Instruction.FilledNewArray;
import ast.stm.Instruction.FilledNewArrayRange;
import ast.stm.Instruction.FloatToDouble;
import ast.stm.Instruction.FloatToInt;
import ast.stm.Instruction.FloatToLong;
import ast.stm.Instruction.Goto;
import ast.stm.Instruction.Goto16;
import ast.stm.Instruction.Goto32;
import ast.stm.Instruction.IfEq;
import ast.stm.Instruction.IfEqz;
import ast.stm.Instruction.IfGe;
import ast.stm.Instruction.IfGez;
import ast.stm.Instruction.IfGt;
import ast.stm.Instruction.IfGtz;
import ast.stm.Instruction.IfLe;
import ast.stm.Instruction.IfLez;
import ast.stm.Instruction.IfLt;
import ast.stm.Instruction.IfLtz;
import ast.stm.Instruction.IfNe;
import ast.stm.Instruction.IfNez;
import ast.stm.Instruction.Iget;
import ast.stm.Instruction.IgetBoolean;
import ast.stm.Instruction.IgetByte;
import ast.stm.Instruction.IgetChar;
import ast.stm.Instruction.IgetOjbect;
import ast.stm.Instruction.IgetShort;
import ast.stm.Instruction.IgetWide;
import ast.stm.Instruction.InstanceOf;
import ast.stm.Instruction.IntToByte;
import ast.stm.Instruction.IntToChar;
import ast.stm.Instruction.IntToDouble;
import ast.stm.Instruction.IntToFloat;
import ast.stm.Instruction.IntToLong;
import ast.stm.Instruction.IntToShort;
import ast.stm.Instruction.InvokeDirect;
import ast.stm.Instruction.InvokeDirectRange;
import ast.stm.Instruction.InvokeInterface;
import ast.stm.Instruction.InvokeInterfaceRange;
import ast.stm.Instruction.InvokeStatic;
import ast.stm.Instruction.InvokeStaticRange;
import ast.stm.Instruction.InvokeSuper;
import ast.stm.Instruction.InvokeSuperRange;
import ast.stm.Instruction.InvokeVirtual;
import ast.stm.Instruction.InvokeVirtualRange;
import ast.stm.Instruction.Iput;
import ast.stm.Instruction.IputBoolean;
import ast.stm.Instruction.IputByte;
import ast.stm.Instruction.IputChar;
import ast.stm.Instruction.IputObject;
import ast.stm.Instruction.IputShort;
import ast.stm.Instruction.IputWide;
import ast.stm.Instruction.LongToDouble;
import ast.stm.Instruction.LongToFloat;
import ast.stm.Instruction.LongToInt;
import ast.stm.Instruction.MonitorEnter;
import ast.stm.Instruction.MonitorExit;
import ast.stm.Instruction.Move;
import ast.stm.Instruction.Move16;
import ast.stm.Instruction.MoveException;
import ast.stm.Instruction.MoveFrom16;
import ast.stm.Instruction.MoveObject;
import ast.stm.Instruction.MoveObject16;
import ast.stm.Instruction.MoveOjbectFrom16;
import ast.stm.Instruction.MoveResult;
import ast.stm.Instruction.MoveResultObject;
import ast.stm.Instruction.MoveResultWide;
import ast.stm.Instruction.MoveWide;
import ast.stm.Instruction.MoveWide16;
import ast.stm.Instruction.MoveWideFrom16;
import ast.stm.Instruction.MulDouble;
import ast.stm.Instruction.MulDouble2Addr;
import ast.stm.Instruction.MulFloat;
import ast.stm.Instruction.MulFloat2Addr;
import ast.stm.Instruction.MulInt;
import ast.stm.Instruction.MulInt2Addr;
import ast.stm.Instruction.MulIntLit16;
import ast.stm.Instruction.MulIntLit8;
import ast.stm.Instruction.MulLong;
import ast.stm.Instruction.MulLong2Addr;
import ast.stm.Instruction.NegDouble;
import ast.stm.Instruction.NegFloat;
import ast.stm.Instruction.NegInt;
import ast.stm.Instruction.NegLong;
import ast.stm.Instruction.NewArray;
import ast.stm.Instruction.NewInstance;
import ast.stm.Instruction.Nop;
import ast.stm.Instruction.NotInt;
import ast.stm.Instruction.NotLong;
import ast.stm.Instruction.OrInt;
import ast.stm.Instruction.OrInt2Addr;
import ast.stm.Instruction.OrIntLit16;
import ast.stm.Instruction.OrIntLit8;
import ast.stm.Instruction.OrLong;
import ast.stm.Instruction.OrLong2Addr;
import ast.stm.Instruction.PackedSwitch;
import ast.stm.Instruction.PackedSwitchDirective;
import ast.stm.Instruction.RemDouble;
import ast.stm.Instruction.RemDouble2Addr;
import ast.stm.Instruction.RemFloat;
import ast.stm.Instruction.RemFloat2Addr;
import ast.stm.Instruction.RemInt;
import ast.stm.Instruction.RemInt2Addr;
import ast.stm.Instruction.RemIntLit16;
import ast.stm.Instruction.RemIntLit8;
import ast.stm.Instruction.RemLong;
import ast.stm.Instruction.RemLong2Addr;
import ast.stm.Instruction.Return;
import ast.stm.Instruction.ReturnObject;
import ast.stm.Instruction.ReturnVoid;
import ast.stm.Instruction.ReturnWide;
import ast.stm.Instruction.RsubInt;
import ast.stm.Instruction.RsubIntLit8;
import ast.stm.Instruction.Sget;
import ast.stm.Instruction.SgetBoolean;
import ast.stm.Instruction.SgetByte;
import ast.stm.Instruction.SgetChar;
import ast.stm.Instruction.SgetObject;
import ast.stm.Instruction.SgetShort;
import ast.stm.Instruction.SgetWide;
import ast.stm.Instruction.ShlInt;
import ast.stm.Instruction.ShlInt2Addr;
import ast.stm.Instruction.ShlIntLit8;
import ast.stm.Instruction.ShlLong;
import ast.stm.Instruction.ShlLong2Addr;
import ast.stm.Instruction.ShrInt;
import ast.stm.Instruction.ShrInt2Addr;
import ast.stm.Instruction.ShrIntLit8;
import ast.stm.Instruction.ShrLong;
import ast.stm.Instruction.ShrLong2Addr;
import ast.stm.Instruction.SparseSwitch;
import ast.stm.Instruction.SparseSwitchDirective;
import ast.stm.Instruction.Sput;
import ast.stm.Instruction.SputBoolean;
import ast.stm.Instruction.SputByte;
import ast.stm.Instruction.SputChar;
import ast.stm.Instruction.SputObject;
import ast.stm.Instruction.SputShort;
import ast.stm.Instruction.SputWide;
import ast.stm.Instruction.SubDouble;
import ast.stm.Instruction.SubDouble2Addr;
import ast.stm.Instruction.SubFloat;
import ast.stm.Instruction.SubFloat2Addr;
import ast.stm.Instruction.SubInt;
import ast.stm.Instruction.SubInt2Addr;
import ast.stm.Instruction.SubLong;
import ast.stm.Instruction.SubLong2Addr;
import ast.stm.Instruction.Throw;
import ast.stm.Instruction.UshrInt;
import ast.stm.Instruction.UshrInt2Addr;
import ast.stm.Instruction.UshrIntLit8;
import ast.stm.Instruction.UshrLong;
import ast.stm.Instruction.UshrLong2Addr;
import ast.stm.Instruction.XorInt;
import ast.stm.Instruction.XorInt2Addr;
import ast.stm.Instruction.XorIntLit16;
import ast.stm.Instruction.XorIntLit8;
import ast.stm.Instruction.XorLong;
import ast.stm.Instruction.XorLong2Addr;
import ast.stm.Instruction.arrayLength;

public class InterpreterVisitor implements Visitor {

	public static HashMap<String, VmClass> classMap;
	public static HashMap<String, VmField> staticFieldMap;
	public static HashMap<String, VmMethod> methodMap;
	public static HashMap<String, VmMethod> staticMethodMap;
	public static HashMap<String, VmMethod> directMethodMap;

	public Stack<Frame> runStack;
	public int ip;
	public boolean methodEnd;
	public Object returnValue;

	public static void print(Object obj) {
		System.out.println(obj);
	}

	public static void printErr(Object obj) {
		System.err.println(obj);
	}

	public static class Frame {
		//who invoke the method
		public Object ownerObject;
		public Object[] variables;
		public Object[] parameters;
		public int returnAddress;
	}

	public InterpreterVisitor() {
		classMap = new HashMap<String, VmClass>();
		staticFieldMap = new HashMap<String, VmField>();
		methodMap = new HashMap<String, VmMethod>();
		staticMethodMap = new HashMap<String, VmMethod>();
		directMethodMap = new HashMap<String, VmMethod>();
		runStack = new Stack<Frame>();
		this.ip = 0;
		this.methodEnd = false;
	}

	/*
	 * start from public static void main(String[] args);
	 */
	public void Init(MethodItem methodItem) throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Loader.loadUserClass(methodItem.classType);
		//		print(InterpreterVisitor.classMap);
		InvokeStatic mainMethod = new InvokeStatic("invoke-static",
				new ArrayList<String>(), methodItem);
		mainMethod.accept(this);
	}

	public Object[] initParameters(List<String> argsType) {
		Object[] parameterList = new Object[argsType.size()];
		for (int i = 0; i < argsType.size(); i++) {
			String arg = argsType.get(i);
			parameterList[i] = this.getObjectFromRegister(arg);
		}
		return parameterList;
	}

	public static Object[] getObjectParameters(boolean isStatic, Object[] objs) {
		int i = isStatic == true ? 0 : 1;
		Object[] result = new Object[objs.length - i];
		for (int j = 0; i < objs.length; i++, j++) {
			if (objs[i] instanceof VmInstance) {
				if (((VmInstance) objs[i]).isSystem) {
					result[j] = ((VmInstance) objs[i]).systemInstance;
				} else {
					//TODO : how to handle the user Instance
					printErr("appear user Instance in getObjectParameters function");
				}
			} else if (objs[i] instanceof VmField) {
				result[j] = ((VmField) objs[i]).content;
			} else {
				result[j] = objs[i];
			}
		}
		return result;
	}

	public void storeObjectInRegister(String register, Object content) {
		int index = Integer.parseInt(register.substring(1));
		if (register.startsWith("v"))
			this.runStack.peek().variables[index] = content;
		else
			this.runStack.peek().parameters[index] = content;
	}

	public Object getObjectFromRegister(String register) {
		int index = Integer.parseInt(register.substring(1));
		if (register.startsWith("v"))
			return this.runStack.peek().variables[index];
		return this.runStack.peek().parameters[index];
	}

	public void handleVirtualMethod(InvokeVirtual inst) {
		String fullMethodName = Util.getFullMethodName(inst.type);
		Object[] parameters= initParameters(inst.argList);
		VmInstance refer = (VmInstance)parameters[0];
		if(refer.isSystem == true) {
			//system method
		} else {
			
		}
		
	}

	public void handleSuperMethod(InvokeSuper inst) {
		
	}

	// invoke-direct is used to invoke a non-static direct method 
	//(that is, an instance method that is by its nature non-overridable, 
	//   namely either a private instance method or a constructor)
	public void handleDircetMethod(InvokeDirect inst) {
		String fullMethodName = Util.getFullMethodName(inst.type);
		Object[] parameters= initParameters(inst.argList);
		Object refer = parameters[0];
		VmMethod directMethod = directMethodMap.get(fullMethodName);
		if(directMethod == null)
			directMethod = Loader.getDirectMethod(inst.type);
		if(directMethod.isSystem == true) {
			Object[] objectParameters = getObjectParameters(false,parameters);
			if(directMethod.isSystemConstructor == true) {
				Object newInstance = null;
				try {
					 newInstance = directMethod.systemConstructor.newInstance(objectParameters);
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException
						| InvocationTargetException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(((VmInstance)refer).isSystem == true)
					((VmInstance)refer).systemInstance = newInstance;
				else
					((VmInstance)refer).parentInstance = newInstance;
			} else {
				try {
					this.returnValue = directMethod.systemMethod.invoke(refer, objectParameters);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			Frame frame = new Frame();
			frame.parameters = parameters;
			frame.returnAddress = ip;
			frame.ownerObject = frame.parameters[0];
			this.runStack.push(frame);
			directMethod.astMethod.accept(this);
		}
	}

	public void handleStaticMethod(InvokeStatic inst) {
		String fullMethodName = Util.getFullMethodName(inst.type);
		Object[] parameters = initParameters(inst.argList);
		VmMethod staticMethod = staticMethodMap.get(fullMethodName);
		if (staticMethod == null)
			staticMethod = Loader.getStaticMethod(inst.type);
		if (staticMethod.isSystem == true) {
			try {
				this.returnValue = staticMethod.systemMethod.invoke(null,
						getObjectParameters(true, parameters));
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Frame frame = new Frame();
			frame.parameters = parameters;
			frame.returnAddress = ip;
			frame.ownerObject = null;
			this.runStack.push(frame);
			staticMethod.astMethod.accept(this);
		}
	}

	public void handleInterfaceMethod(InvokeInterface inst) {

	}

	/*
	 * if(the method is static ) stackFrame.ownerObject = null
	 * else stackFrame.ownerObject = stackFrame.parameters[0];
	 */
	public void enterVmMethod(VmMethod vmMethod, List<String> argList,
			boolean isStatic) {
		Frame frame = new Frame();
		frame.parameters = this.initParameters(argList);
		frame.returnAddress = ip;
		frame.ownerObject = null;
		Object ownerObject = null;
		if (!isStatic) {
			frame.ownerObject = frame.parameters[0];
			ownerObject = frame.parameters[0];
		}

		if (vmMethod.isSystem == true) {
			// system method
			//			print("is a system method");
			int i = 0;
			if (frame.ownerObject != null) {
				i = 1;
				if (ownerObject instanceof VmInstance)
					ownerObject = ((VmInstance) ownerObject).systemInstance;
				else if (ownerObject instanceof VmField)
					ownerObject = ((VmField) ownerObject).content;
				else {
				}
			}
			Object[] systemParameters = new Object[frame.parameters.length - i];
			//get the args
			for (int j = 0; i < frame.parameters.length; i++) {
				if (frame.parameters[i] instanceof VmInstance)
					systemParameters[j++] = ((VmInstance) frame.parameters[i]).systemInstance;
				else if (frame.parameters[i] instanceof VmField)
					systemParameters[j++] = ((VmField) frame.parameters[i]).content;
				else {
					systemParameters[j++] = frame.parameters[i];
				}
			}

			//needn't push the new stackframe
			if (vmMethod.isSystemConstructor == true) {
				//can't invoke construct method for ast object
				//but init for a systemInstance
				//				print("is a system constructor");
				if (((VmInstance) frame.ownerObject).isSystem == true)
					try {
						((VmInstance) frame.ownerObject).systemInstance = vmMethod.systemConstructor
								.newInstance(systemParameters);
					} catch (InstantiationException | IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} else {
				try {
					this.returnValue = vmMethod.systemMethod.invoke(
							ownerObject, systemParameters);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			this.runStack.push(frame);
			vmMethod.astMethod.accept(this);
		}
	}

	/*
	 * step 1 : update ip and methodEnd
	 * step 2 : remove current stack frame
	 */

	public void leaveVmMethod() {
		this.methodEnd = true;
		this.ip = this.runStack.peek().returnAddress;
		this.runStack.pop();
	}

	@Override
	public void visit(MethodItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FieldItem item) {
		// TODO Auto-generated method stub

	}

	public void printStack(String methodName) {
		print(methodName + "----start-------");
		if (this.runStack.size() != 0) {
			print("para:");
			int i = 0;
			for (Object obj : this.runStack.peek().parameters) {
				if (obj == null)
					print("p" + i + " " + null);
				else
					print("p" + i + " " + obj);
				i++;
			}
			i = 0;
			print("var:");
			for (Object obj : this.runStack.peek().variables) {
				if (obj == null)
					print("v" + i + " " + null);
				else
					print("v" + i + " " + obj);
				i++;
			}
		} else
			print("runStack is empty");
		print(methodName + "----end--------");
	}

	/*
	 * init variableList of top stackframe
	 */
	@Override
	public void visit(Method method) {
		// TODO Auto-generated method stub
		int variableLength;
		if (method.registers_directive.equals(".registers"))
			variableLength = Integer.parseInt(method.registers_directive_count)
					- this.runStack.peek().parameters.length;
		else
			variableLength = Integer.parseInt(method.registers_directive_count);
		this.runStack.peek().variables = new Object[variableLength];
		//		print("in method " + Loader.getFullMethodName("someclass", method));
		//		print("size of variables " + this.runStack.peek().variables.length);
		this.ip = 0;
		while (!this.methodEnd) {
			//			print(">>>> ip " + this.ip);
			//			print("inst " + method.statements.get(this.ip).getClass().getName());
			method.statements.get(this.ip).accept(this);
			//			this.printStack(method.name);
		}
		this.methodEnd = false;
	}

	@Override
	public void visit(MethodPrototype prototype) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Program program) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ast.classs.Class clazz) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Instruction instruction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Goto inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);
	}

	@Override
	public void visit(Nop inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * return-void
	 * update this.ip and this.methoEnd
	 */
	@Override
	public void visit(ReturnVoid inst) {
		// TODO Auto-generated method stub
		this.leaveVmMethod();
	}

	/*
	 * const/4 v6 0x2
	 */
	@Override
	public void visit(Const4 inst) {
		// TODO Auto-generated method stub
		int value;
		if (inst.value.startsWith("-"))
			value = -(Integer.parseInt(inst.value.substring(3), 16));
		else
			value = Integer.parseInt(inst.value.substring(2), 16);
		this.storeObjectInRegister(inst.dest, value);
		this.ip++;

	}

	@Override
	public void visit(MoveResult inst) {
		// TODO Auto-generated method stub
		this.storeObjectInRegister(inst.dest, this.returnValue);
		//		print("return value : " +this.returnValue);
		this.ip++;
	}

	@Override
	public void visit(MoveResultWide inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * move-result-object v0
	 */
	@Override
	public void visit(MoveResultObject inst) {
		// TODO Auto-generated method stub
		this.storeObjectInRegister(inst.dest, this.returnValue);
		//		print("return value : " + this.returnValue);
		this.ip++;
	}

	@Override
	public void visit(MoveException inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * return v0
	 */
	@Override
	public void visit(Return inst) {
		// TODO Auto-generated method stub
		int index = Integer.parseInt(inst.ret.substring(1));
		if (inst.ret.startsWith("v"))
			this.returnValue = this.runStack.peek().variables[index];
		else
			this.returnValue = this.runStack.peek().parameters[index];
		//		print("return value : " + this.returnValue);
		this.leaveVmMethod();
	}

	@Override
	public void visit(ReturnWide inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * return-object v0
	 */
	@Override
	public void visit(ReturnObject inst) {
		// TODO Auto-generated method stub
		int index = Integer.parseInt(inst.ret.substring(1));
		if (inst.ret.startsWith("v"))
			this.returnValue = this.runStack.peek().variables[index];
		else
			this.returnValue = this.runStack.peek().parameters[index];
		//		print("return value : " + this.returnValue);
		this.leaveVmMethod();
	}

	@Override
	public void visit(MonitorEnter inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MonitorExit inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(Throw inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(Move inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MoveWide inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MoveObject inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(arrayLength inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(NegInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(NotInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(NegLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(NotLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(NegFloat inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(NegDouble inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IntToLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IntToFloat inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IntToDouble inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(LongToInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(LongToFloat inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(LongToDouble inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(FloatToInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(FloatToLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(FloatToDouble inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(DoubleToInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(DoubleToLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(DoubleToFloat inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IntToByte inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IntToChar inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IntToShort inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AddInt2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SubInt2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MulInt2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(DivInt2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(RemInt2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AndInt2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(OrInt2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(XorInt2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ShlInt2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ShrInt2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(UshrInt2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AddLong2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SubLong2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MulLong2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(DivLong2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(RemLong2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AndLong2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(OrLong2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(XorLong2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ShlLong2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ShrLong2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(UshrLong2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AddFloat2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SubFloat2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MulFloat2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(DivFloat2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(RemFloat2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AddDouble2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SubDouble2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MulDouble2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(DivDouble2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(RemDouble2Addr inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(Goto16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * const-string v1, "xiaoming"
	 * "xiaoming" --> xiaoming
	 */
	@Override
	public void visit(ConstString inst) {
		// TODO Auto-generated method stub
		this.storeObjectInRegister(inst.dest,
				inst.str.substring(1, inst.str.length() - 1));
		this.ip++;
	}

	@Override
	public void visit(ConstClass inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(CheckCast inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * new-instance v0, Lhong/example/Person;
	 */
	@Override
	public void visit(NewInstance inst) {
		// TODO Auto-generated method stub
		this.storeObjectInRegister(inst.dest, Loader.loadInstance(inst.type));
		this.ip++;
	}

	@Override
	public void visit(Sget inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SgetWide inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * sget-object v4, Ljava/lang/System;->out:Ljava/io/PrintStream;
	 */
	@Override
	public void visit(SgetObject inst) {
		// TODO Auto-generated method stub
		this.storeObjectInRegister(inst.dest, Loader.loadStaticField(inst.type));
		this.ip++;
	}

	@Override
	public void visit(SgetBoolean inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SgetByte inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SgetChar inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SgetShort inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(Sput inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SputWide inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SputObject inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SputBoolean inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SputByte inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SputChar inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SputShort inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ConstHigh16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ConstWideHigh16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * const/16 vAA, #+BBBB
	 * const/16 v2,-0xb  (-0xb==-11)
	 * Move the given literal value (sign-extended to 32 bits) into the specified register.
	 * A: destination register (8 bits)
	   B: signed int (16 bits)
	 */
	@Override
	public void visit(Const16 inst) {
		// TODO Auto-generated method stub
		int value;
		if (inst.value.startsWith("-"))
			value = -(Integer.parseInt(inst.value.substring(3), 16));
		else
			value = Integer.parseInt(inst.value.substring(2), 16);
		this.storeObjectInRegister(inst.dest, value);
		this.ip++;
	}

	@Override
	public void visit(ConstWide16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IfEqz inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IfNez inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IfLtz inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IfGez inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IfGtz inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IfLez inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AddIntLit8 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(RsubIntLit8 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MulIntLit8 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(DivIntLit8 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(RemIntLit8 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AndIntLit8 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(OrIntLit8 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(XorIntLit8 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ShlIntLit8 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ShrIntLit8 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(UshrIntLit8 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(InstanceOf inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(NewArray inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	public void instanceGet(String srcReg, String dstReg, String fieldName) {
		Object srcObj, dstObj = null;
		if (srcReg.startsWith("v"))
			srcObj = this.runStack.peek().variables[Integer.parseInt(srcReg
					.substring(1))];
		else
			srcObj = this.runStack.peek().parameters[Integer.parseInt(srcReg
					.substring(1))];
		VmInstance vmInstance = (VmInstance) srcObj;
		if (vmInstance.isSystem == true) {
			//system instance
			try {
				dstObj = vmInstance.systemInstance.getClass()
						.getField(fieldName).get(vmInstance.systemInstance);
			} catch (IllegalArgumentException | IllegalAccessException
					| NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			dstObj = vmInstance.fieldMap.get(fieldName);
		}
		if (dstReg.startsWith("v"))
			this.runStack.peek().variables[Integer
					.parseInt(dstReg.substring(1))] = dstObj;
		else
			this.runStack.peek().parameters[Integer.parseInt(dstReg
					.substring(1))] = dstObj;

		//		if( dstObj instanceof VmField)
		//			print("after get the "
		//				+ dstReg
		//				+ " : "
		//				+ ((VmField)dstObj).content);
		//		else
		//			print("after get the "
		//					+ dstReg
		//					+ " : "
		//					+ dstObj);
	}

	public void instancePut(String srcReg, String dstReg, String fieldName) {
		Object srcObj, dstObj;
		if (srcReg.startsWith("v"))
			srcObj = this.runStack.peek().variables[Integer.parseInt(srcReg
					.substring(1))];
		else
			srcObj = this.runStack.peek().parameters[Integer.parseInt(srcReg
					.substring(1))];
		if (dstReg.startsWith("v"))
			dstObj = this.runStack.peek().variables[Integer.parseInt(dstReg
					.substring(1))];
		else
			dstObj = this.runStack.peek().parameters[Integer.parseInt(dstReg
					.substring(1))];
		VmInstance vmInstance = (VmInstance) dstObj;
		if (vmInstance.isSystem == true) {
			//system instance
			try {
				vmInstance.systemInstance.getClass().getField(fieldName)
						.set(dstObj, srcObj);
				//				print("after put the system field : "
				//						+ fieldName
				//						+ " : "
				//						+ vmInstance.systemInstance.getClass()
				//								.getField(fieldName)
				//								.get(vmInstance.systemInstance));
			} catch (IllegalArgumentException | IllegalAccessException
					| NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Object field = vmInstance.fieldMap.get(fieldName);
			((VmField) field).content = srcObj;
			//			print("after put the ast field : " + fieldName + " : "
			//					+ ((VmField) field).content);
		}

	}

	@Override
	public void visit(Iget inst) {
		// TODO Auto-generated method stub
		this.instanceGet(inst.field, inst.dest, inst.type.fieldName);
		this.ip++;
	}

	@Override
	public void visit(IgetWide inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * iget-object v0, p0 Lhong/example/Person;->name:Ljava/lang/String;
	 */
	@Override
	public void visit(IgetOjbect inst) {
		// TODO Auto-generated method stub
		this.instanceGet(inst.field, inst.dest, inst.type.fieldName);
		this.ip++;
	}

	@Override
	public void visit(IgetBoolean inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IgetByte inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IgetChar inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IgetShort inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * iput p2, p0 Lhong/example/Person;->age:I
	 */
	@Override
	public void visit(Iput inst) {
		// TODO Auto-generated method stub
		this.instancePut(inst.src, inst.field, inst.type.fieldName);
		this.ip++;
	}

	@Override
	public void visit(IputWide inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * iput-object p1,p0 Lhong/example/Person;->name;Ljava/lang/String;
	 * 
	 */
	@Override
	public void visit(IputObject inst) {
		// TODO Auto-generated method stub
		this.instancePut(inst.src, inst.field, inst.type.fieldName);
		this.ip++;
	}

	@Override
	public void visit(IputBoolean inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IputByte inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IputChar inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IputShort inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AddIntLit16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(RsubInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MulIntLit16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(DivIntLit16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(RemIntLit16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AndIntLit16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(OrIntLit16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(XorIntLit16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IfEq inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IfNe inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IfLt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IfGe inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IfGt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(IfLe inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MoveFrom16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MoveWideFrom16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MoveOjbectFrom16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(CmplFloat inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(CmpgFloat inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(CmplDouble inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(Cmpgdouble inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(CmpLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(Aget inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AgetWide inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AgetObject inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AgetBoolean inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AgetByte inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AgetChar inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AgetShort inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(Aput inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AputWide inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AputObject inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AputBoolean inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AputByte inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AputChar inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AputShort inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * add-int v0,p1,p2
	 */
	@Override
	public void visit(AddInt inst) {
		// TODO Auto-generated method stub
		Object obja = this.getObjectFromRegister(inst.first);
		Object objb = this.getObjectFromRegister(inst.second);
		//		print("obja " + obja);
		//		print("objb " + objb);
		int inta, intb;
		if (obja instanceof VmField)
			inta = (int) ((VmField) obja).content;
		else
			inta = (int) obja;
		if (objb instanceof VmField)
			intb = (int) ((VmField) objb).content;
		else
			intb = (int) objb;
		//		print("inta : " + inta);
		//		print("intb : " + intb);
		this.storeObjectInRegister(inst.dest, inta + intb);
		this.ip++;
	}

	@Override
	public void visit(SubInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MulInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(DivInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(RemInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AndInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(OrInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(XorInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ShlInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ShrInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(UshrInt inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AddLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SubLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MulLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(DivLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(RemLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AndLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(OrLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(XorLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ShlLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ShrLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(UshrLong inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AddFloat inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SubFloat inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MulFloat inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(DivFloat inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(RemFloat inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(AddDouble inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SubDouble inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MulDouble inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(DivDouble inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(RemDouble inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(Goto32 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ConstStringJumbo inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(Const inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ConstWide32 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(FillArrayData inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(PackedSwitch inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(SparseSwitch inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(Move16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MoveWide16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(MoveObject16 inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(FilledNewArray inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * invoke-virtual {v0} Lhong/example/Person;->getName():Ljava/lang/String;
	 */
	@Override
	public void visit(InvokeVirtual inst) {
		// TODO Auto-generated method stub
		this.enterVmMethod(Loader.loadMethod(inst.type), inst.argList, false);
		this.ip++;
	}

	@Override
	public void visit(InvokeSuper inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	/*
	 * invoke-direct{v0,v1,v2},Lhong/example/Person;-><init>(Ljava/lang/String;I)V
	 */
	@Override
	public void visit(InvokeDirect inst) {
		// TODO Auto-generated method stub
		this.enterVmMethod(Loader.loadMethod(inst.type), inst.argList, false);
		this.ip++;
	}

	/*
	 * for invoke instrucitons ,it's work is 
	 * init the parameterList for the new stackframe
	 * init the ownerObject for the new stackframe
	 * store the return address int the new stackframe
	 */
	@Override
	public void visit(InvokeStatic inst) {
		//		this.enterVmMethod(Loader.loadMethod(inst.type), inst.argList, true);
		handleStaticMethod(inst);
		this.ip++;
	}

	/*
	 * invoke-interface is used to invoke an interface method, 
	 * that is, on an object whose concrete class isn't known, 
	 * using a method_id that refers to an interface.
	 * invoke-interface{v2,v4,v5},Ljava/util/Map:
	 *         ->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
	 *         
	 * can't define constructor in an interface
	 * haven't find invoke-interface in ast method
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void visit(InvokeInterface inst) {
		// TODO Auto-generated method stub
		Class[] paraClass = Util.getArgsTypes(inst.type.prototype.argsType);
		String reg = inst.argList.get(0);
		int index = Integer.parseInt(reg.substring(1));
		Object instance;
		if (reg.startsWith("v"))
			instance = this.runStack.peek().variables[index];
		else
			instance = this.runStack.peek().parameters[index];
		this.enterVmMethod(Loader.loadInterfaceMethod(instance, paraClass,
				inst.type.methodName), inst.argList, false);
		this.ip++;
	}

	@Override
	public void visit(InvokeVirtualRange inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(InvokeSuperRange inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(InvokeDirectRange inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(InvokeStaticRange inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(InvokeInterfaceRange inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(FilledNewArrayRange inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(ConstWide inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + inst.op);

	}

	@Override
	public void visit(PackedSwitchDirective inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + "SparseSwitchDirective");

	}

	@Override
	public void visit(SparseSwitchDirective inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + "SparseSwitchDirective");

	}

	@Override
	public void visit(ArrayDataDirective inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + "ArrayDataDirective");

	}

	@Override
	public void visit(Annotation inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + "Annotation");

	}

	@Override
	public void visit(SubAnnotation inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + "SubAnnotation");

	}

	@Override
	public void visit(ElementLiteral inst) {
		// TODO Auto-generated method stub
		System.err.println("unknow inst : " + "ElementLiteral");

	}
}
