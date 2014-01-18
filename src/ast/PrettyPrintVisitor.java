package ast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import control.Control;
import ast.annotation.Annotation;
import ast.annotation.Annotation.ElementLiteral;
import ast.annotation.Annotation.SubAnnotation;
import ast.stm.Instruction;

/* *
 * This class should be thread safe
 * */
public class PrettyPrintVisitor implements Visitor {
	private final static int TAB = 4;
	private int indents = 0;
	public static final Map<String, Integer> instLen;
	// record the position for current method
	private int position;
	private String filePath;
	private String folderName;
	private FileWriter fileWrite;
	//store the .catch and .catchall
	private List<String> catchList;

	// create *.smali
	private void createFile(String fullyQualifiedName) throws IOException {
		this.filePath = Control.ppoutput
				+ "/"
				+ fullyQualifiedName.substring(1,
						fullyQualifiedName.length() - 1);

		String alterName = this.filePath;

		if (System.getProperty("os.name").equals("Mac OS X"))
			alterName += ".2.smali";
		else
			alterName += ".smali";

		this.filePath += ".smali";

		int index = fullyQualifiedName.lastIndexOf('/');

		if (index != -1)
			this.folderName = Control.ppoutput + "/"
					+ fullyQualifiedName.substring(1, index);
		else
			this.folderName = Control.ppoutput + "/";

		File file = new File(this.folderName);
		file.mkdirs();
		file = new File(this.filePath);
		if (file.createNewFile()) {
			this.fileWrite = new FileWriter(file, true);
		} else {
			file = new File(alterName);
			if (file.createNewFile()) {
				this.fileWrite = new FileWriter(file, true);
			} else {
				System.err.println("name conflicting " + fullyQualifiedName);
				System.exit(1);
			}
		}
	}

	/*
	 * the format I get from the method.catchList: catch,catch, ,catchall,catchall
	 * the output for diff in smalifile should be: catchall,catchall,...,catch,catch 
	 */
	private void printCatchList() {
		for (int i = 0; i < this.catchList.size(); i++) {
			if (this.catchList.get(i).startsWith(".catchall")) {
				this.printSpace();
				this.sayln(this.catchList.get(i));
			}
		}
		for (int i = 0; i < this.catchList.size(); i++) {
			if (this.catchList.get(i).startsWith(".catchall"))
				break;
			this.printSpace();
			this.sayln(this.catchList.get(i));
		}
	}

	/* *
	 * handle string literal escape, panic when meet escaped ASCII code eg.
	 * System.out.println("a'b\'c"); will output a'b'c
	 */
	private String processString(String str) {
		String ret = str.substring(1, str.length() - 1);
		ret = util.StringUtils.escapeString(ret);
		return "\"" + ret + "\"";
	}

	/* *
	 * handle char literal escape, panic when meet escaped ASCII code eg.
	 * char_literal: '\n'
	 */
	private String processChar(String str) {
		String ret = str.substring(1, str.length() - 1);
		ret = util.StringUtils.escapeString(ret);
		return "\'" + ret + "\'";
	}

	/*
	 * print literal except subannotation and array_literal
	 */
	private void printLiteral(String literal, String type) {
		if (literal.startsWith("\""))
			this.sayln(this.processString(literal));
		else if (literal.startsWith("\'"))
			this.sayln(this.processChar(literal));
		else {
			if (type.equals("enum"))
				this.sayln(".enum " + literal);
			else
				this.sayln(literal);
		}
	}

	private void indent() {
		indents += TAB;
	}

	private void unIndent() {
		if (indents > 0)
			indents -= TAB;
	}

	private void printSpace() {
		for (int i = 0; i < indents; i++) {
			// System.err.print(" ");
			// System.out.print(" ");
			try {
				this.fileWrite.write(" ");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void say(String s) {
		try {
			this.fileWrite.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sayln(String s) {
		try {
			this.fileWrite.write(s + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// program
	@Override
	public void visit(ast.program.Program p) {
	}

	@Override
	public void visit(ast.classs.Class clazz) {
		try {
			this.createFile(clazz.FullyQualifiedName);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		ast.classs.Class classs = (ast.classs.Class) clazz;
		this.say(".class ");
		for (String access : clazz.accessList) {
			this.say(access + " ");
		}
		this.sayln(clazz.FullyQualifiedName);

		this.say(".super ");
		this.sayln(clazz.superName);
		if (clazz.source != null) {
			this.say(".source ");
			this.sayln(clazz.source);
		}

		// implementsList
		for (String s : clazz.implementsList) {
			this.sayln(".implements " + s);
		}

		// annotations
		for (ast.annotation.Annotation annotation : clazz.annotationList)
			annotation.accept(this);

		// fileds
		for (ast.classs.Class.Field field : clazz.fieldList) {
			this.say(".field ");
			for (String str : field.accessList)
				this.say(str + " ");
			this.say(field.name + ":");
			this.say(field.type);
			// initialvalue
			if (field.initValue != null) {
				if (field.initValue.startsWith("\""))
					field.initValue = this.processString(field.initValue);
				this.sayln(" = " + field.initValue);
			}

			else
				this.sayln("");
			// annotations
			if (field.annotationList != null) {
				for (ast.annotation.Annotation annotation : field.annotationList) {
					annotation.accept(this);
				}
				if (field.annotationList.size() > 0)
					this.sayln(".end field");
			}
		}
		for (ast.method.Method method : classs.methods) {
			this.position = 0;
			method.accept(this);
		}
		try {
			this.fileWrite.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void visit(ast.method.Method method) {
		boolean ignorePrologue = false;
		Collections.sort(method.catchList);
		Collections.sort(method.labelList);
		this.say(".method ");
		for (String access : method.accessList) {
			if (access.equals("abstract") || access.equals("native"))
				ignorePrologue = true;
			this.say(access + " ");
		}
		this.say(method.name);
		method.prototype.accept(this);
		this.indent();
		this.printSpace();
		if (method.registers_directive != null) {
			this.say(method.registers_directive + " ");
			this.sayln(method.registers_directive_count + "");
		}
		// parameters
		for (ast.method.Method.Parameter parameter : method.parameterList) {
			this.printSpace();
			if (parameter.value != null)
				this.sayln(".parameter " + parameter.value);
			else
				this.sayln(".parameter");
			// annotations
			for (ast.annotation.Annotation annotation : parameter.annotationList) {
				annotation.accept(this);
				if (parameter.annotationList.size() > 0) {
					this.printSpace();
					this.sayln(".end parameter");
				}
			}
		}
		if (!ignorePrologue) {
			this.printSpace();
			this.sayln(".prologue");
		}
		// annotations
		if (method.annotationList != null)
			for (ast.annotation.Annotation annotation : method.annotationList) {
				annotation.accept(this);
			}
		this.sayln("");

		List<ast.method.Method.Label> labelList = method.labelList;
		List<ast.method.Method.Catch> catchList = method.catchList;
		List<ast.method.Method.Debug> debugList = method.debugList;
		int labelIndex = 0;
		int catchIndex = 0;
		int debugIndex = 0;
		int labelValue = -1;
		int catchValue = -1;
		int debugValue = -1;
		ast.method.Method.Label currentLab = null;
		ast.method.Method.Catch currentCatch = null;
		ast.method.Method.Debug currentDebug = null;
		if (method.labelList.size() != 0) {
			currentLab = labelList.get(labelIndex);
			labelValue = Integer.parseInt(currentLab.add);
		}
		if (method.catchList.size() != 0) {
			currentCatch = catchList.get(catchIndex);
			catchValue = Integer.parseInt(currentCatch.add);
		}
		if (method.debugList.size() != 0) {
			currentDebug = debugList.get(debugIndex);
			debugValue = Integer.parseInt(currentDebug.addr);
		}
		// int instIndex;
		// ast.stm.T stm;
		if (method.labelList.size() == 0) {
			for (ast.stm.T stm : method.statements) {
				while (debugIndex < method.debugList.size()
						&& this.position == debugValue) {
					this.printSpace();
					this.sayln(currentDebug.toString());
					debugIndex++;
					if (debugIndex < method.debugList.size()) {
						currentDebug = method.debugList.get(debugIndex);
						debugValue = Integer.parseInt(currentDebug.addr);
					}
				}
				this.printSpace();
				stm.accept(this);
				this.sayln("");
			}
		} else {
			for (ast.stm.T stm : method.statements) {
				if (labelIndex < method.labelList.size()
						&& (currentLab.lab.startsWith("sswitch_data")
								|| currentLab.lab.startsWith("pswitch_data") || currentLab.lab
									.startsWith("array_"))
						&& (stm instanceof ast.stm.Instruction.PackedSwitchDirective
								|| stm instanceof ast.stm.Instruction.SparseSwitchDirective || stm instanceof ast.stm.Instruction.ArrayDataDirective)) {
					this.printSpace();
					this.sayln(currentLab.toString());
					labelIndex++;
					if (labelIndex < method.labelList.size()) {
						currentLab = method.labelList.get(labelIndex);
						labelValue = Integer.parseInt(currentLab.add);
					}
				} else {
					while (debugIndex < method.debugList.size()
							&& this.position == debugValue) {
						this.printSpace();
						this.sayln(currentDebug.toString());
						debugIndex++;
						if (debugIndex < method.debugList.size()) {
							currentDebug = method.debugList.get(debugIndex);
							debugValue = Integer.parseInt(currentDebug.addr);
						}
					}
					while (labelIndex < method.labelList.size()
							&& this.position == labelValue) {
						this.printSpace();
						this.sayln(currentLab.toString());
						if (currentLab.lab.startsWith("try_end")) {
							this.catchList = new ArrayList<String>();
							while (catchIndex < method.catchList.size()
									&& this.position == catchValue) {
								//this.printSpace();
								//this.sayln(currentCatch.toString());
								this.catchList.add(currentCatch.toString());

								catchIndex++;
								if (catchIndex < method.catchList.size()) {
									currentCatch = method.catchList
											.get(catchIndex);
									catchValue = Integer
											.parseInt(currentCatch.add);
								}
							}
							this.printCatchList();
						}
						labelIndex++;
						if (labelIndex < method.labelList.size()) {
							currentLab = method.labelList.get(labelIndex);
							labelValue = Integer.parseInt(currentLab.add);
						}
					}

				}
				this.printSpace();
				stm.accept(this);
				this.sayln("");
			}
		}

		/* (source)
		 * current version     |         prev version(b25fa6c)
		 *---------------------------------------------------
		 * instruction         |         instruction
		 * :try_end_3          |         .end method
		 * .catch....          |
		 * .end method         |
		 */
		while (labelIndex < method.labelList.size()
				&& this.position == labelValue) {
			this.printSpace();
			this.sayln(currentLab.toString());
			if (currentLab.lab.startsWith("try_end")) {
				this.catchList = new ArrayList<String>();
				while (catchIndex < method.catchList.size()
						&& this.position == catchValue) {
					//this.printSpace();
					//this.sayln(currentCatch.toString());
					this.catchList.add(currentCatch.toString());
					catchIndex++;
					if (catchIndex < method.catchList.size()) {
						currentCatch = method.catchList.get(catchIndex);
						catchValue = Integer.parseInt(currentCatch.add);
					}
				}
				this.printCatchList();
			}
			labelIndex++;
			if (labelIndex < method.labelList.size()) {
				currentLab = method.labelList.get(labelIndex);
				labelValue = Integer.parseInt(currentLab.add);
			}
		}

		this.unIndent();
		this.sayln(".end method");
		this.sayln(""); // to make diff cleaner
	}

	@Override
	public void visit(ast.method.Method.MethodPrototype prototype) {
		this.say("(");
		for (String type : prototype.argsType) {
			this.say(type);
		}
		this.say(")");
		this.sayln(prototype.returnType);
	}

	@Override
	public void visit(ast.classs.MethodItem item) {
		say(item.classType + "->");
		say(item.methodName);
		item.prototype.accept(this);
	}

	@Override
	public void visit(ast.classs.FieldItem item) {
		say(item.classType + "->");
		say(item.fieldName + ":");
		say(item.fieldType);
	}

	@Override
	public void visit(Annotation annotation) {
		this.printSpace();
		this.say(".annotation ");
		this.say(annotation.visibility + " ");
		annotation.subAnnotation.accept(this);
		this.printSpace();
		this.sayln(".end annotation");
	}

	@Override
	public void visit(SubAnnotation subAnnotation) {
		this.sayln(subAnnotation.classType);
		this.indent();
		for (ast.annotation.Annotation.AnnotationElement element : subAnnotation.elementList) {
			this.printSpace();
			this.say(element.name + " = ");
			element.elementLiteral.accept(this);
		}
		this.unIndent();
	}

	@Override
	public void visit(ElementLiteral elementLiteral) {
		//subannotation
		if (elementLiteral.type.equals("subannotation")) {
			this.say(".subannotation ");
			((ast.annotation.Annotation.SubAnnotation) elementLiteral.element
					.get(0)).accept(this);
			this.printSpace();
			this.sayln(".end subannotation");
		}
		//array
		else if (elementLiteral.type.equals("array")) {
			if (elementLiteral.element.size() == 0)
				this.sayln("{}");
			else {
				this.sayln("{");
				if (elementLiteral.arrayLiteralType.equals("subannotation")) {
					System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					System.out.println("W:find subannotation in array_literal in PrettyPrint");
					System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				} else if (elementLiteral.arrayLiteralType.equals("subannotation")) {
					System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					System.out.println("W:find array in array_literal in PrettyPrint");
					System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				}
				this.indent();
				for (int i = 0; i < elementLiteral.element.size(); i++) {
					Object object = elementLiteral.element.get(i);
					String arrayLiteralTypet = elementLiteral.arrayLiteralType.get(i);
					this.printSpace();
					if (arrayLiteralTypet.equals("subannotation")) {
						this.say(".subannotation ");
						((ast.annotation.Annotation.SubAnnotation) object)
								.accept(this);
						this.printSpace();
						this.say(".end subannotation");
					} else {
						String str = new String();
						str = (String) (object);
						this.printLiteral(str, arrayLiteralTypet);
					}
					if (i < elementLiteral.element.size() - 1)
						this.sayln(",");
				}
				this.unIndent();
				this.printSpace();
				this.sayln("}");
				this.unIndent();
			}
		}
		//others
		else {
			this.printLiteral(((String) elementLiteral.element.get(0)),
					elementLiteral.type);
			this.sayln("");
		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 00 10x nop
	public void visit(ast.stm.Instruction.Nop inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op);
	}

	// 01 12x move vA, vB
	public void visit(ast.stm.Instruction.Move inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 02 22x move/from16 vAA, vBBBB
	public void visit(ast.stm.Instruction.MoveFrom16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 03 32x move/16 vAAAA, vBBBB ------
	public void visit(ast.stm.Instruction.Move16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 04 12x move-wide vA, vB
	public void visit(ast.stm.Instruction.MoveWide inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 05 22x move-wide/from16 vAA, vBBBB
	public void visit(ast.stm.Instruction.MoveWideFrom16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 06 32x move-wide/16 vAAAA, vBBBB -----
	public void visit(ast.stm.Instruction.MoveWide16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 07 12x move-object vA, vB
	public void visit(ast.stm.Instruction.MoveObject inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);

	}

	// 08 22x move-object/from16 vAA, vBBBB --
	public void visit(ast.stm.Instruction.MoveOjbectFrom16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);

	}

	// 09 32x move-object/16 vAAAA, vBBBB
	public void visit(ast.stm.Instruction.MoveObject16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);

	}

	// 0a 11x move-result vAA
	public void visit(ast.stm.Instruction.MoveResult inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest);

	}

	// 0b 11x move-result-wide vAA
	public void visit(ast.stm.Instruction.MoveResultWide inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest);

	}

	// 0c 11x move-result-object vAA
	public void visit(ast.stm.Instruction.MoveResultObject inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest);

	}

	// 0d 11x move-exception vAA
	public void visit(ast.stm.Instruction.MoveException inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest);

	}

	// 0e 10x return-void
	public void visit(ast.stm.Instruction.ReturnVoid inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op);

	}

	// 0f 11x return vAA
	public void visit(ast.stm.Instruction.Return inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.ret);

	}

	// 10 11x return-wide vAA
	public void visit(ast.stm.Instruction.ReturnWide inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.ret);
	}

	// 11 11x return-object vAA
	public void visit(ast.stm.Instruction.ReturnObject inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.ret);

	}

	// 12 11n const/4 vA, #+B
	public void visit(ast.stm.Instruction.Const4 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.value);
	}

	// 13 21s const/16 vAA, #+BBBB
	public void visit(ast.stm.Instruction.Const16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.value);
	}

	// 14 31i const vAA, #+BBBBBBBB
	public void visit(ast.stm.Instruction.Const inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.value);
	}

	// 15 21h const/high16 vAA, #+BBBB0000
	public void visit(ast.stm.Instruction.ConstHigh16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.value);
	}

	// 16 21s const-wide/16 vAA, #+BBBB
	public void visit(ast.stm.Instruction.ConstWide16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.value);
	}

	// 17 31i const-wide/32 vAA, #+BBBBBBBB
	public void visit(ast.stm.Instruction.ConstWide32 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.value);
	}

	// 18 51l const-wide vAA, #+BBBBBBBBBBBBBBBB
	public void visit(ast.stm.Instruction.ConstWide inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.value);
	}

	// 19 21h const-wide/high16 vAA, #+BBBB000000000000
	public void visit(ast.stm.Instruction.ConstWideHigh16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.value);
	}

	// 1a 21c const-string vAA, string@BBBB
	public void visit(ast.stm.Instruction.ConstString inst) {
		this.position += instLen.get(inst.op);
		inst.str = this.processString(inst.str);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.str);
	}

	// 1b 31c const-string/jumbo vAA, string@BBBBBBBB
	public void visit(ast.stm.Instruction.ConstStringJumbo inst) {
		this.position += instLen.get(inst.op);
		inst.str = this.processString(inst.str);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.str);
	}

	// 1c 21c const-class vAA, type@BBBB
	public void visit(ast.stm.Instruction.ConstClass inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.type);
	}

	// 1d 11x monitor-enter vAA
	public void visit(ast.stm.Instruction.MonitorEnter inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.ref);
	}

	// 1e 11x monitor-exit vAA
	public void visit(ast.stm.Instruction.MonitorExit inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.ref);
	}

	// 1f 21c check-cast vAA, type@BBBB
	public void visit(ast.stm.Instruction.CheckCast inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.ref + ", " + inst.type);
	}

	// 20 22c instance-of vA, vB, type@CCCC
	public void visit(ast.stm.Instruction.InstanceOf inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.ref + ", "
				+ inst.type);
	}

	// 21 12x array-length vA, vB
	public void visit(ast.stm.Instruction.arrayLength inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 22 21c new-instance vAA, type@BBBB
	public void visit(ast.stm.Instruction.NewInstance inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.type);
	}

	// 23 22c new-array vA, vB, type@CCCC
	// new-array v0,p1 [Landro......
	public void visit(ast.stm.Instruction.NewArray inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.size + ", "
				+ inst.type);
	}

	// 24 35c filled-new-array {vC, vD, vE, vF, vG}, type@BBBB
	// filled-new-array {v7, v9}, [I
	public void visit(ast.stm.Instruction.FilledNewArray inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op);
		this.say(" {");
		int cnt = 0;
		for (String s : inst.argList) {
			cnt++;
			if (cnt < inst.argList.size())
				this.say(s + ", ");
			else
				this.say(s);
		}
		this.say("}, ");
		this.sayln(inst.type);
	}

	// 25 3rc filled-new-array/range {vCCCC .. vNNNN}, type@BBBB ----
	public void visit(ast.stm.Instruction.FilledNewArrayRange inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op);
		this.say(" {");
		this.say(inst.start + " .. " + inst.end);
		this.say("}, ");
		this.sayln(inst.type);
	}

	// 26 31t fill-array-data vAA, +BBBBBBBB (with supplemental data as
	// specified below in "fill-array-data-payloadFormat") ------
	public void visit(ast.stm.Instruction.FillArrayData inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", :" + inst.src);
	}

	// 27 11x throw vAA
	public void visit(ast.stm.Instruction.Throw inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.kind);
	}

	// 28 10t goto +AA ??
	// !!!!! goto :goto_0
	public void visit(ast.stm.Instruction.Goto inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " :" + inst.dest);
	}

	// 29 20t goto/16 +AAAA ??
	public void visit(ast.stm.Instruction.Goto16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " :" + inst.dest);
	}

	// 2a 30t goto/32 +AAAAAAAA ??
	public void visit(ast.stm.Instruction.Goto32 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " :" + inst.dest);
	}

	// 2b 31t packed-switch vAA, +BBBBBBBB (with supplemental data as specified
	// below in "packed-switch- ??????????
	public void visit(ast.stm.Instruction.PackedSwitch inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.test + ", :" + inst.offset);
		// System.out.println(inst.op + " " + inst.test + ", :" + inst.offset);
	}

	// 2c 31t sparse-switch vAA, +BBBBBBBB (with supplemental data as specified
	// below in "sparse-switch-payloadFormat") ??????????
	public void visit(ast.stm.Instruction.SparseSwitch inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.test + ", :" + inst.offset);
		// System.out.println(inst.op + " " + inst.test + ", " + inst.offset);
	}

	//
	// 2d..31 23x cmpkind vAA, vBB, vCC
	// 2d: cmpl-float (lt bias)
	public void visit(ast.stm.Instruction.CmplFloat inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 2e: cmpg-float (gt bias)
	public void visit(ast.stm.Instruction.CmpgFloat inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 2f: cmpl-double (lt bias)
	public void visit(ast.stm.Instruction.CmplDouble inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 30: cmpg-double (gt bias)
	public void visit(ast.stm.Instruction.Cmpgdouble inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 31: cmp-long
	public void visit(ast.stm.Instruction.CmpLong inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	//
	// 32..37 22t if-test vA, vB, +CCCC
	// !!!!!!! if-eq v1, v0, :cond_1
	// 32: if-eq
	public void visit(ast.stm.Instruction.IfEq inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.first + ", " + inst.second + ", :"
				+ inst.dest);
	}

	// 33: if-ne
	public void visit(ast.stm.Instruction.IfNe inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.first + ", " + inst.second + ", :"
				+ inst.dest);
	}

	// 34: if-lt
	public void visit(ast.stm.Instruction.IfLt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.first + ", " + inst.second + ", :"
				+ inst.dest);
	}

	// 35: if-ge
	public void visit(ast.stm.Instruction.IfGe inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.first + ", " + inst.second + ", :"
				+ inst.dest);
	}

	// 36: if-gt
	public void visit(ast.stm.Instruction.IfGt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.first + ", " + inst.second + ", :"
				+ inst.dest);
	}

	// 37: if-le
	public void visit(ast.stm.Instruction.IfLe inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.first + ", " + inst.second + ", :"
				+ inst.dest);
	}

	//
	//
	// 38..3d 21t if-testz vAA, +BBBB
	// !!!! if-eqz v0, :cond_0
	// 38: if-eqz
	public void visit(ast.stm.Instruction.IfEqz inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.test + ", :" + inst.dest);
	}

	// 39: if-nez
	public void visit(ast.stm.Instruction.IfNez inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.test + ", :" + inst.dest);
	}

	// 3a: if-ltz
	public void visit(ast.stm.Instruction.IfLtz inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.test + ", :" + inst.dest);
	}

	// 3b: if-gez
	public void visit(ast.stm.Instruction.IfGez inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.test + ", :" + inst.dest);
	}

	// 3c: if-gtz
	public void visit(ast.stm.Instruction.IfGtz inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.test + ", :" + inst.dest);
	}

	// 3d: if-lez
	public void visit(ast.stm.Instruction.IfLez inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.test + ", :" + inst.dest);
	}

	//
	//
	// 3e..43 10x (unused)
	// 44..51 23x arrayop vAA, vBB, vCC

	// 44: aget
	public void visit(ast.stm.Instruction.Aget inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.array + ", "
				+ inst.index);
	}

	// 45: aget-wide
	public void visit(ast.stm.Instruction.AgetWide inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.array + ", "
				+ inst.index);
	}

	// 46: aget-object
	public void visit(ast.stm.Instruction.AgetObject inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.array + ", "
				+ inst.index);
	}

	// 47: aget-boolean
	public void visit(ast.stm.Instruction.AgetBoolean inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.array + ", "
				+ inst.index);
	}

	// 48: aget-byte
	public void visit(ast.stm.Instruction.AgetByte inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.array + ", "
				+ inst.index);
	}

	// 49: aget-char
	public void visit(ast.stm.Instruction.AgetChar inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.array + ", "
				+ inst.index);
	}

	// 4a: aget-short\
	public void visit(ast.stm.Instruction.AgetShort inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.array + ", "
				+ inst.index);
	}

	// 4b: aput
	public void visit(ast.stm.Instruction.Aput inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.array + ", "
				+ inst.index);
	}

	// 4c: aput-wide
	public void visit(ast.stm.Instruction.AputWide inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.array + ", "
				+ inst.index);
	}

	// 4d: aput-object
	public void visit(ast.stm.Instruction.AputObject inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.array + ", "
				+ inst.index);
	}

	// 4e: aput-boolean
	public void visit(ast.stm.Instruction.AputBoolean inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.array + ", "
				+ inst.index);
	}

	// 4f: aput-byte
	public void visit(ast.stm.Instruction.AputByte inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.array + ", "
				+ inst.index);
	}

	// 50: aput-char
	public void visit(ast.stm.Instruction.AputChar inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.array + ", "
				+ inst.index);
	}

	// 51: aput-short
	public void visit(ast.stm.Instruction.AputShort inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.array + ", "
				+ inst.index);
	}

	//
	// 52..5f 22c iinstanceop vA, vB, field@CCCC
	// 52: iget
	public void visit(ast.stm.Instruction.Iget inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	// 53: iget-wide
	public void visit(ast.stm.Instruction.IgetWide inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	// 54: iget-object
	public void visit(ast.stm.Instruction.IgetOjbect inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	// 55: iget-boolean
	public void visit(ast.stm.Instruction.IgetBoolean inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	// 56: iget-byte
	public void visit(ast.stm.Instruction.IgetByte inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	// 57: iget-char
	public void visit(ast.stm.Instruction.IgetChar inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	// 58: iget-short
	public void visit(ast.stm.Instruction.IgetShort inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	// 59: iput
	public void visit(ast.stm.Instruction.Iput inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	// 5a: iput-wide
	public void visit(ast.stm.Instruction.IputWide inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	// 5b: iput-object
	public void visit(ast.stm.Instruction.IputObject inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	// 5c: iput-boolean
	public void visit(ast.stm.Instruction.IputBoolean inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	// 5d: iput-byte
	public void visit(ast.stm.Instruction.IputByte inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	// 5e: iput-char
	public void visit(ast.stm.Instruction.IputChar inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	// 5f: iput-short
	public void visit(ast.stm.Instruction.IputShort inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.field + ", "
				+ inst.type.toString());
	}

	//
	// 60..6d 21c sstaticop vAA, field@BBBB
	// 60: sget
	public void visit(ast.stm.Instruction.Sget inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.type.toString());
	}

	// 61: sget-wide
	public void visit(ast.stm.Instruction.SgetWide inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.type.toString());
	}

	// 62: sget-object
	public void visit(ast.stm.Instruction.SgetObject inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.type.toString());
	}

	// 63: sget-boolean
	public void visit(ast.stm.Instruction.SgetBoolean inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.type.toString());
	}

	// 64: sget-byte
	public void visit(ast.stm.Instruction.SgetByte inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.type.toString());
	}

	// 65: sget-char
	public void visit(ast.stm.Instruction.SgetChar inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.type.toString());
	}

	// 66: sget-short
	public void visit(ast.stm.Instruction.SgetShort inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.type.toString());
	}

	// 67: sput
	public void visit(ast.stm.Instruction.Sput inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.type.toString());
	}

	// 68: sput-wide
	public void visit(ast.stm.Instruction.SputWide inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.type.toString());
	}

	// 69: sput-object
	public void visit(ast.stm.Instruction.SputObject inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.type.toString());
	}

	// 6a: sput-boolean
	public void visit(ast.stm.Instruction.SputBoolean inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.type.toString());
	}

	// 6b: sput-byte
	public void visit(ast.stm.Instruction.SputByte inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.type.toString());
	}

	// 6c: sput-char
	public void visit(ast.stm.Instruction.SputChar inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.type.toString());
	}

	// 6d: sput-short
	public void visit(ast.stm.Instruction.SputShort inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.src + ", " + inst.type.toString());
	}

	// 6e..72 35c invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB
	// 6e: invoke-virtual
	public void visit(ast.stm.Instruction.InvokeVirtual inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op);
		this.say(" {");
		int cnt = 0;
		for (String s : inst.argList) {
			cnt++;
			if (cnt < inst.argList.size())
				this.say(s + ", ");
			else
				this.say(s);
		}
		this.say("}, ");
		this.sayln(inst.type.toString());
	}

	// 6f: invoke-super
	public void visit(ast.stm.Instruction.InvokeSuper inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op);
		this.say(" {");
		int cnt = 0;
		for (String s : inst.argList) {
			cnt++;
			if (cnt < inst.argList.size())
				this.say(s + ", ");
			else
				this.say(s);
		}
		this.say("}, ");
		this.sayln(inst.type.toString());
	}

	// 70: invoke-direct
	public void visit(ast.stm.Instruction.InvokeDirect inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op);
		this.say(" {");
		int cnt = 0;
		for (String s : inst.argList) {
			cnt++;
			if (cnt < inst.argList.size())
				this.say(s + ", ");
			else
				this.say(s);
		}
		this.say("}, ");
		this.sayln(inst.type.toString());
	}

	// 71: invoke-static
	public void visit(ast.stm.Instruction.InvokeStatic inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op);
		this.say(" {");
		int cnt = 0;
		for (String s : inst.argList) {
			cnt++;
			if (cnt < inst.argList.size())
				this.say(s + ", ");
			else
				this.say(s);
		}
		this.say("}, ");
		this.sayln(inst.type.toString());
	}

	// 72: invoke-interface
	public void visit(ast.stm.Instruction.InvokeInterface inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op);
		this.say(" {");
		int cnt = 0;
		for (String s : inst.argList) {
			cnt++;
			if (cnt < inst.argList.size())
				this.say(s + ", ");
			else
				this.say(s);
		}
		this.say("}, ");
		this.sayln(inst.type.toString());
	}

	//
	//
	// 73 10x (unused)
	// 74..78 3rc invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB
	// 74: invoke-virtual/range
	public void visit(ast.stm.Instruction.InvokeVirtualRange inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op);
		this.say(" {");
		this.say(inst.start + " .. " + inst.end);
		this.say("}, ");
		this.sayln(inst.type.toString());
	}

	// 75: invoke-super/range
	public void visit(ast.stm.Instruction.InvokeSuperRange inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op);
		this.say(" {");
		this.say(inst.start + " .. " + inst.end);
		this.say("}, ");
		this.sayln(inst.type.toString());
	}

	// 76: invoke-direct/range
	public void visit(ast.stm.Instruction.InvokeDirectRange inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op);
		this.say(" {");
		this.say(inst.start + " .. " + inst.end);
		this.say("}, ");
		this.sayln(inst.type.toString());
	}

	// 77: invoke-static/range
	public void visit(ast.stm.Instruction.InvokeStaticRange inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op);
		this.say(" {");
		this.say(inst.start + " .. " + inst.end);
		this.say("}, ");
		this.sayln(inst.type.toString());
	}

	// 78: invoke-interface/range
	public void visit(ast.stm.Instruction.InvokeInterfaceRange inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op);
		this.say(" {");
		this.say(inst.start + " .. " + inst.end);
		this.say("}, ");
		this.sayln(inst.type.toString());
	}

	//
	// 79..7a 10x (unused)
	// 7b..8f 12x unop vA, vB
	// 7b: neg-int
	public void visit(ast.stm.Instruction.NegInt inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 7c: not-int
	public void visit(ast.stm.Instruction.NotInt inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 7d: neg-long
	public void visit(ast.stm.Instruction.NegLong inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 7e: not-long
	public void visit(ast.stm.Instruction.NotLong inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 7f: neg-float
	public void visit(ast.stm.Instruction.NegFloat inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 80: neg-double
	public void visit(ast.stm.Instruction.NegDouble inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 81: int-to-long
	public void visit(ast.stm.Instruction.IntToLong inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 82: int-to-float
	public void visit(ast.stm.Instruction.IntToFloat inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 83: int-to-double
	public void visit(ast.stm.Instruction.IntToDouble inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 84: long-to-int
	public void visit(ast.stm.Instruction.LongToInt inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 85: long-to-float
	public void visit(ast.stm.Instruction.LongToFloat inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 86: long-to-double
	public void visit(ast.stm.Instruction.LongToDouble inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 87: float-to-int
	public void visit(ast.stm.Instruction.FloatToInt inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 88: float-to-long
	public void visit(ast.stm.Instruction.FloatToLong inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 89: float-to-double
	public void visit(ast.stm.Instruction.FloatToDouble inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 8a: double-to-int
	public void visit(ast.stm.Instruction.DoubleToInt inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 8b: double-to-long
	public void visit(ast.stm.Instruction.DoubleToLong inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 8c: double-to-float
	public void visit(ast.stm.Instruction.DoubleToFloat inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 8d: int-to-byte
	public void visit(ast.stm.Instruction.IntToByte inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 8e: int-to-char
	public void visit(ast.stm.Instruction.IntToChar inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// 8f: int-to-short
	public void visit(ast.stm.Instruction.IntToShort inst) {
		this.position += instLen.get(inst.op);
		this.say(inst.op + " " + inst.dest + ", " + inst.src);
	}

	//
	// 90..af 23x binop vAA, vBB, vCC
	// 90: add-int
	public void visit(ast.stm.Instruction.AddInt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 91: sub-int public void visit(ast.stm.Instruction.AddInt inst)
	public void visit(ast.stm.Instruction.SubInt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 92: mul-int
	public void visit(ast.stm.Instruction.MulInt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 93: div-int
	public void visit(ast.stm.Instruction.DivInt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 94: rem-int
	public void visit(ast.stm.Instruction.RemInt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 95: and-int
	public void visit(ast.stm.Instruction.AndInt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 96: or-int
	public void visit(ast.stm.Instruction.OrInt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 97: xor-int
	public void visit(ast.stm.Instruction.XorInt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 98: shl-int
	public void visit(ast.stm.Instruction.ShlInt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 99: shr-int
	public void visit(ast.stm.Instruction.ShrInt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 9a: ushr-int
	public void visit(ast.stm.Instruction.UshrInt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 9b: add-long
	public void visit(ast.stm.Instruction.AddLong inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 9c: sub-long
	public void visit(ast.stm.Instruction.SubLong inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 9d: mul-long
	public void visit(ast.stm.Instruction.MulLong inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 9e: div-long
	public void visit(ast.stm.Instruction.DivLong inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// 9f: rem-long
	public void visit(ast.stm.Instruction.RemLong inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// a0: and-long
	public void visit(ast.stm.Instruction.AndLong inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// a1: or-long
	public void visit(ast.stm.Instruction.OrLong inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// a2: xor-long
	public void visit(ast.stm.Instruction.XorLong inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// a3: shl-long
	public void visit(ast.stm.Instruction.ShlLong inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// a4: shr-long
	public void visit(ast.stm.Instruction.ShrLong inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// a5: ushr-long
	public void visit(ast.stm.Instruction.UshrLong inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// a6: add-float
	public void visit(ast.stm.Instruction.AddFloat inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// a7: sub-float
	public void visit(ast.stm.Instruction.SubFloat inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// a8: mul-float
	public void visit(ast.stm.Instruction.MulFloat inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// a9: div-float
	public void visit(ast.stm.Instruction.DivFloat inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// aa: rem-float
	public void visit(ast.stm.Instruction.RemFloat inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// ab: add-double
	public void visit(ast.stm.Instruction.AddDouble inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// ac: sub-double
	public void visit(ast.stm.Instruction.SubDouble inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// ad: mul-double
	public void visit(ast.stm.Instruction.MulDouble inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// ae: div-double
	public void visit(ast.stm.Instruction.DivDouble inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	// af: rem-double
	public void visit(ast.stm.Instruction.RemDouble inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.first + ", "
				+ inst.second);
	}

	//
	// b0..cf 12x binop/2addr vA, vB

	// b0: add-int/2addr
	public void visit(ast.stm.Instruction.AddInt2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// b1: sub-int/2addr
	public void visit(ast.stm.Instruction.SubInt2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// b2: mul-int/2addr
	public void visit(ast.stm.Instruction.MulInt2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// b3: div-int/2addr
	public void visit(ast.stm.Instruction.DivInt2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// b4: rem-int/2addr
	public void visit(ast.stm.Instruction.RemInt2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// b5: and-int/2addr
	public void visit(ast.stm.Instruction.AndInt2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// b6: or-int/2addr
	public void visit(ast.stm.Instruction.OrInt2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// b7: xor-int/2addr
	public void visit(ast.stm.Instruction.XorInt2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// b8: shl-int/2addr
	public void visit(ast.stm.Instruction.ShlInt2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// b9: shr-int/2addr
	public void visit(ast.stm.Instruction.ShrInt2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// ba: ushr-int/2addr
	public void visit(ast.stm.Instruction.UshrInt2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// bb: add-long/2addr
	public void visit(ast.stm.Instruction.AddLong2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// bc: sub-long/2addr
	public void visit(ast.stm.Instruction.SubLong2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// bd: mul-long/2addr
	public void visit(ast.stm.Instruction.MulLong2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// be: div-long/2addr
	public void visit(ast.stm.Instruction.DivLong2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// bf: rem-long/2addr
	public void visit(ast.stm.Instruction.RemLong2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// c0: and-long/2addr
	public void visit(ast.stm.Instruction.AndLong2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// c1: or-long/2addr
	public void visit(ast.stm.Instruction.OrLong2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// c2: xor-long/2addr
	public void visit(ast.stm.Instruction.XorLong2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// c3: shl-long/2addr
	public void visit(ast.stm.Instruction.ShlLong2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// c4: shr-long/2addr
	public void visit(ast.stm.Instruction.ShrLong2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// c5: ushr-long/2addr
	public void visit(ast.stm.Instruction.UshrLong2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// c6: add-float/2addr
	public void visit(ast.stm.Instruction.AddFloat2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// c7: sub-float/2addr
	public void visit(ast.stm.Instruction.SubFloat2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// c8: mul-float/2addr
	public void visit(ast.stm.Instruction.MulFloat2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// c9: div-float/2addr
	public void visit(ast.stm.Instruction.DivFloat2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// ca: rem-float/2addr
	public void visit(ast.stm.Instruction.RemFloat2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// cb: add-double/2addr
	public void visit(ast.stm.Instruction.AddDouble2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// cc: sub-double/2addr
	public void visit(ast.stm.Instruction.SubDouble2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// cd: mul-double/2addr
	public void visit(ast.stm.Instruction.MulDouble2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// ce: div-double/2addr
	public void visit(ast.stm.Instruction.DivDouble2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	// cf: rem-double/2addr
	public void visit(ast.stm.Instruction.RemDouble2Addr inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src);
	}

	//
	// d0..d7 22s binop/lit16 vA, vB, #+CCCC
	// d0: add-int/lit16
	public void visit(ast.stm.Instruction.AddIntLit16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// d1: rsub-int (reverse subtract)
	public void visit(ast.stm.Instruction.RsubInt inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// d2: mul-int/lit16
	public void visit(ast.stm.Instruction.MulIntLit16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// d3: div-int/lit16
	public void visit(ast.stm.Instruction.DivIntLit16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// d4: rem-int/lit16
	public void visit(ast.stm.Instruction.RemIntLit16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// d5: and-int/lit16
	public void visit(ast.stm.Instruction.AndIntLit16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// d6: or-int/lit16
	public void visit(ast.stm.Instruction.OrIntLit16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// d7: xor-int/lit16
	public void visit(ast.stm.Instruction.XorIntLit16 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	//
	// d8..e2 22b binop/lit8 vAA, vBB, #+CC
	// d8: add-int/lit8
	public void visit(ast.stm.Instruction.AddIntLit8 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// d9: rsub-int/lit8
	public void visit(ast.stm.Instruction.RsubIntLit8 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// da: mul-int/lit8
	public void visit(ast.stm.Instruction.MulIntLit8 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// db: div-int/lit8
	public void visit(ast.stm.Instruction.DivIntLit8 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// dc: rem-int/lit8
	public void visit(ast.stm.Instruction.RemIntLit8 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// dd: and-int/lit8
	public void visit(ast.stm.Instruction.AndIntLit8 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// de: or-int/lit8
	public void visit(ast.stm.Instruction.OrIntLit8 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// df: xor-int/lit8
	public void visit(ast.stm.Instruction.XorIntLit8 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// e0: shl-int/lit8
	public void visit(ast.stm.Instruction.ShlIntLit8 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// e1: shr-int/lit8
	public void visit(ast.stm.Instruction.ShrIntLit8 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	// e2: ushr-int/lit8
	public void visit(ast.stm.Instruction.UshrIntLit8 inst) {
		this.position += instLen.get(inst.op);
		this.sayln(inst.op + " " + inst.dest + ", " + inst.src + ", "
				+ inst.value);
	}

	@Override
	public void visit(Instruction instruction) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(ast.stm.Instruction.ArrayDataDirective inst) {
		this.sayln(".array-data " + inst.size);
		this.indent();
		this.printSpace();
		int cnt = 0;
		for (String str : inst.elementList) {
			this.say(str + " ");
			cnt++;
			if ((cnt % Integer.decode(inst.size).intValue()) == 0) {
				this.sayln("");
				this.printSpace();
			}
		}
		this.sayln("");
		this.unIndent();
		this.printSpace();
		this.sayln(".end array-data");
	}

	@Override
	public void visit(ast.stm.Instruction.PackedSwitchDirective inst) {
		this.sayln(".packed-switch " + inst.key);
		this.indent();
		for (String str : inst.labList) {
			this.printSpace();
			this.sayln(":" + str);
		}
		this.unIndent();
		this.printSpace();
		this.sayln(".end packed-switch");

	}

	@Override
	public void visit(ast.stm.Instruction.SparseSwitchDirective inst) {
		this.sayln(".sparse-switch ");
		this.indent();
		for (int i = 0; i < inst.labList.size(); i++) {
			this.printSpace();
			this.sayln(inst.keyList.get(i) + " -> :" + inst.labList.get(i));
		}
		this.unIndent();
		this.printSpace();
		this.sayln(".end sparse-switch");
	}

	public PrettyPrintVisitor() {
		this.filePath = null;
		this.folderName = null;
	}

	/* *
	 * static body is thread safe according to
	 * http://docs.oracle.com/javase/specs/jls/se7/html/jls-12.html#jls-12.4.2
	 */
	static {
		instLen = new HashMap<String, Integer>();
		// 00 10x nop
		instLen.put("nop", 1);
		// 01 12x move
		instLen.put("move", 1);
		// 02 22x move/from16
		instLen.put("move/from16", 2);
		// 03 32x move/16
		instLen.put("move/16", 3);
		// 04 12x move-wide
		instLen.put("move-wide", 1);
		// 05 22x move-wide/from16
		instLen.put("move-wide/from16", 2);
		// 06 32x move-wide/16
		instLen.put("move-wide/16", 3);
		// 07 12x move-object
		instLen.put("move-object", 1);
		// 08 22x move-object/from16
		instLen.put("move-object/from16", 2);
		// 09 32x move-object/16
		instLen.put("move-object/16", 3);
		// 0a 11x move-result
		instLen.put("move-result", 1);
		// 0b 11x move-result-wide
		instLen.put("move-result-wide", 1);
		// 0c 11x move-result-object
		instLen.put("move-result-object", 1);
		// 0d 11x move-exception
		instLen.put("move-exception", 1);
		// 0e 10x return-void
		instLen.put("return-void", 1);
		// 0f 11x return
		instLen.put("return", 1);
		// 10 11x return-wide
		instLen.put("return-wide", 1);
		// 11 11x return-object
		instLen.put("return-object", 1);
		// 12 11n const/4
		instLen.put("const/4", 1);
		// 13 21s const/16
		instLen.put("const/16", 2);
		// 14 31i const
		instLen.put("const", 3);
		// 15 21h const/high16
		instLen.put("const/high16", 2);
		// 16 21s const-wide/16
		instLen.put("const-wide/16", 2);
		// 17 31i const-wide/32
		instLen.put("const-wide/32", 3);
		// 18 51l const-wide
		instLen.put("const-wide", 5);
		// 19 21h const-wide/high16
		instLen.put("const-wide/high16", 2);
		// 1a 21c const-string
		instLen.put("const-string", 2);
		// 1b 31c const-string/jumbo
		instLen.put("const-string/jumbo", 3);
		// 1c 21c const-class
		instLen.put("const-class", 2);
		// 1d 11x monitor-enter
		instLen.put("monitor-enter", 1);
		// 1e 11x monitor-exit
		instLen.put("monitor-exit", 1);
		// 1f 21c check-cast
		instLen.put("check-cast", 2);
		// 20 22c instance-of
		instLen.put("instance-of", 2);
		// 21 12x array-length
		instLen.put("array-length", 1);
		// 22 21c new-instance
		instLen.put("new-instance", 2);
		// 23 22c new-array
		instLen.put("new-array", 2);
		// 24 35c filled-new-array
		instLen.put("filled-new-array", 3);
		// 25 3rc filled-new-array/range {vCCCC .. vNNNN}, type@BBBB
		instLen.put("filled-new-array/range", 3);
		// 26 31t fill-array-data
		instLen.put("fill-array-data", 3);
		// 27 11x throw
		instLen.put("throw", 1);
		// 28 10t goto
		instLen.put("goto", 1);
		// 29 20t goto/16
		instLen.put("goto/16", 2);
		// 2a 30t goto/32
		instLen.put("goto/32", 3);
		// 2b 31t packed-switch
		instLen.put("packed-switch", 3);
		// 2c 31t sparse-switch
		instLen.put("sparse-switch", 3);
		// 2d..31 23x cmpkind
		// 2d: cmpl-float
		instLen.put("cmpl-float", 2);
		// 2e: cmpg-float
		instLen.put("cmpg-float", 2);
		// 2f: cmpl-double
		instLen.put("cmpl-double", 2);
		// 30: cmpg-double
		instLen.put("cmpg-double", 2);
		// 31: cmp-long
		instLen.put("cmp-long", 2);
		// 32..37 22t if-test vA, vB, +CCCC
		// 32: if-eq
		instLen.put("if-eq", 2);
		// 33: if-ne
		instLen.put("if-ne", 2);
		// 34: if-lt
		instLen.put("if-lt", 2);
		// 35: if-ge
		instLen.put("if-ge", 2);
		// 36: if-gt
		instLen.put("if-gt", 2);
		// 37: if-le
		instLen.put("if-le", 2);
		// 38..3d 21t if-testz vAA, +BBBB
		// 38: if-eqz
		instLen.put("if-eqz", 2);
		// 39: if-nez
		instLen.put("if-nez", 2);
		// 3a: if-ltz
		instLen.put("if-ltz", 2);
		// 3b: if-gez
		instLen.put("if-gez", 2);
		// 3c: if-gtz
		instLen.put("if-gtz", 2);
		// 3d: if-lez
		instLen.put("if-lez", 2);
		//
		//
		// 3e..43 10x (unused)
		// 44..51 23x arrayop vAA, vBB, vCC
		// 44: aget
		instLen.put("aget", 2);
		// 45: aget-wide
		instLen.put("aget-wide", 2);
		// 46: aget-object
		instLen.put("aget-object", 2);
		// 47: aget-boolean
		instLen.put("aget-boolean", 2);
		// 48: aget-byte
		instLen.put("aget-byte", 2);
		// 49: aget-char
		instLen.put("aget-char", 2);
		// 4a: aget-short
		instLen.put("aget-short", 2);
		// 4b: aput
		instLen.put("aput", 2);
		// 4c: aput-wide
		instLen.put("aput-wide", 2);
		// 4d: aput-object
		instLen.put("aput-object", 2);
		// 4e: aput-boolean
		instLen.put("aput-boolean", 2);
		// 4f: aput-byte
		instLen.put("aput-byte", 2);
		// 50: aput-char
		instLen.put("aput-char", 2);
		// 51: aput-short
		instLen.put("aput-short", 2);
		//
		// 52..5f 22c iinstanceop vA, vB, field@CCCC
		// 52: iget
		instLen.put("iget", 2);
		// 53: iget-wide
		instLen.put("iget-wide", 2);
		// 54: iget-object
		instLen.put("iget-object", 2);
		// 55: iget-boolean
		instLen.put("iget-boolean", 2);
		// 56: iget-byte
		instLen.put("iget-byte", 2);
		// 57: iget-char
		instLen.put("iget-char", 2);
		// 58: iget-short
		instLen.put("iget-short", 2);
		// 59: iput
		instLen.put("iput", 2);
		// 5a: iput-wide
		instLen.put("iput-wide", 2);
		// 5b: iput-object
		instLen.put("iput-object", 2);
		// 5c: iput-boolean
		instLen.put("iput-boolean", 2);
		// 5d: iput-byte
		instLen.put("iput-byte", 2);
		// 5e: iput-char
		instLen.put("iput-char", 2);
		// 5f: iput-short
		instLen.put("iput-short", 2);
		//
		// 60..6d 21c sstaticop vAA, field@BBBB
		// 60: sget
		instLen.put("sget", 2);
		// 61: sget-wide
		instLen.put("sget-wide", 2);
		// 62: sget-object
		instLen.put("sget-object", 2);
		// 63: sget-boolean
		instLen.put("sget-boolean", 2);
		// 64: sget-byte
		instLen.put("sget-byte", 2);
		// 65: sget-char
		instLen.put("sget-char", 2);
		// 66: sget-short
		instLen.put("sget-short", 2);
		// 67: sput
		instLen.put("sput", 2);
		// 68: sput-wide
		instLen.put("sput-wide", 2);
		// 69: sput-object
		instLen.put("sput-object", 2);
		// 6a: sput-boolean
		instLen.put("sput-boolean", 2);
		// 6b: sput-byte
		instLen.put("sput-byte", 2);
		// 6c: sput-char
		instLen.put("sput-char", 2);
		// 6d: sput-short
		instLen.put("sput-short", 2);
		//
		// 6e..72 35c invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB
		// 6e: invoke-virtual
		instLen.put("invoke-virtual", 3);
		// 6f: invoke-super
		instLen.put("invoke-super", 3);
		// 70: invoke-direct
		instLen.put("invoke-direct", 3);
		// 71: invoke-static
		instLen.put("invoke-static", 3);
		// 72: invoke-interface
		instLen.put("invoke-interface", 3);
		//
		//
		// 73 10x (unused)
		// 74..78 3rc invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB
		// 74: invoke-virtual/range
		instLen.put("invoke-virtual/range", 3);
		// 75: invoke-super/range
		instLen.put("invoke-super/range", 3);
		// 76: invoke-direct/range
		instLen.put("invoke-direct/range", 3);
		// 77: invoke-static/range
		instLen.put("invoke-static/range", 3);
		// 78: invoke-interface/range
		instLen.put("invoke-interface/range", 3);
		//
		// 79..7a 10x (unused)
		// 7b..8f 12x unop vA, vB
		// 7b: neg-int
		instLen.put("neg-int", 1);
		// 7c: not-int
		instLen.put("not-int", 1);
		// 7d: neg-long
		instLen.put("neg-long", 1);
		// 7e: not-long
		instLen.put("not-long", 1);
		// 7f: neg-float
		instLen.put("neg-float", 1);
		// 80: neg-double
		instLen.put("neg-double", 1);
		// 81: int-to-long
		instLen.put("int-to-long", 1);
		// 82: int-to-float
		instLen.put("int-to-float", 1);
		// 83: int-to-double
		instLen.put("int-to-double", 1);
		// 84: long-to-int
		instLen.put("long-to-int", 1);
		// 85: long-to-float
		instLen.put("long-to-float", 1);
		// 86: long-to-double
		instLen.put("long-to-double", 1);
		// 87: float-to-int
		instLen.put("float-to-int", 1);
		// 88: float-to-long
		instLen.put("float-to-long", 1);
		// 89: float-to-double
		instLen.put("float-to-double", 1);
		// 8a: double-to-int
		instLen.put("double-to-int", 1);
		// 8b: double-to-long
		instLen.put("double-to-long", 1);
		// 8c: double-to-float
		instLen.put("double-to-float", 1);
		// 8d: int-to-byte
		instLen.put("int-to-byte", 1);
		// 8e: int-to-char
		instLen.put("int-to-char", 1);
		// 8f: int-to-short
		instLen.put("int-to-short", 1);
		//
		// 90..af 23x binop vAA, vBB, vCC
		// 90: add-int
		instLen.put("add-int", 2);
		// 91: sub-int
		instLen.put("sub-int", 2);
		// 92: mul-int
		instLen.put("mul-int", 2);
		// 93: div-int
		instLen.put("div-int", 2);
		// 94: rem-int
		instLen.put("rem-int", 2);
		// 95: and-int
		instLen.put("and-int", 2);
		// 96: or-int
		instLen.put("or-int", 2);
		// 97: xor-int
		instLen.put("xor-int", 2);
		// 98: shl-int
		instLen.put("shl-int", 2);
		// 99: shr-int
		instLen.put("shr-int", 2);
		// 9a: ushr-int
		instLen.put("ushr-int", 2);
		// 9b: add-long
		instLen.put("add-long", 2);
		// 9c: sub-long
		instLen.put("sub-long", 2);
		// 9d: mul-long
		instLen.put("mul-long", 2);
		// 9e: div-long
		instLen.put("div-long", 2);
		// 9f: rem-long
		instLen.put("rem-long", 2);
		// a0: and-long
		instLen.put("and-long", 2);
		// a1: or-long
		instLen.put("or-long", 2);
		// a2: xor-long
		instLen.put("xor-long", 2);
		// a3: shl-long
		instLen.put("shl-long", 2);
		// a4: shr-long
		instLen.put("shr-long", 2);
		// a5: ushr-long
		instLen.put("ushr-long", 2);
		// a6: add-float
		instLen.put("add-float", 2);
		// a7: sub-float
		instLen.put("sub-float", 2);
		// a8: mul-float
		instLen.put("mul-float", 2);
		// a9: div-float
		instLen.put("div-float", 2);
		// aa: rem-float
		instLen.put("rem-float", 2);
		// ab: add-double
		instLen.put("add-double", 2);
		// ac: sub-double
		instLen.put("sub-double", 2);
		// ad: mul-double
		instLen.put("mul-double", 2);
		// ae: div-double
		instLen.put("div-double", 2);
		// af: rem-double
		instLen.put("rem-double", 2);
		//
		// b0..cf 12x binop/2addr vA, vB
		// b0: add-int/2addr
		instLen.put("add-int/2addr", 1);
		// b1: sub-int/2addr
		instLen.put("sub-int/2addr", 1);
		// b2: mul-int/2addr
		instLen.put("mul-int/2addr", 1);
		// b3: div-int/2addr
		instLen.put("div-int/2addr", 1);
		// b4: rem-int/2addr
		instLen.put("rem-int/2addr", 1);
		// b5: and-int/2addr
		instLen.put("and-int/2addr", 1);
		// b6: or-int/2addr
		instLen.put("or-int/2addr", 1);
		// b7: xor-int/2addr
		instLen.put("xor-int/2addr", 1);
		// b8: shl-int/2addr
		instLen.put("shl-int/2addr", 1);
		// b9: shr-int/2addr
		instLen.put("shr-int/2addr", 1);
		// ba: ushr-int/2addr
		instLen.put("ushr-int/2addr", 1);
		// bb: add-long/2addr
		instLen.put("add-long/2addr", 1);
		// bc: sub-long/2addr
		instLen.put("sub-long/2addr", 1);
		// bd: mul-long/2addr
		instLen.put("mul-long/2addr", 1);
		// be: div-long/2addr
		instLen.put("div-long/2addr", 1);
		// bf: rem-long/2addr
		instLen.put("rem-long/2addr", 1);
		// c0: and-long/2addr
		instLen.put("and-long/2addr", 1);
		// c1: or-long/2addr
		instLen.put("or-long/2addr", 1);
		// c2: xor-long/2addr
		instLen.put("xor-long/2addr", 1);
		// c3: shl-long/2addr
		instLen.put("shl-long/2addr", 1);
		// c4: shr-long/2addr
		instLen.put("shr-long/2addr", 1);
		// c5: ushr-long/2addr
		instLen.put("ushr-long/2addr", 1);
		// c6: add-float/2addr
		instLen.put("add-float/2addr", 1);
		// c7: sub-float/2addr
		instLen.put("sub-float/2addr", 1);
		// c8: mul-float/2addr
		instLen.put("mul-float/2addr", 1);
		// c9: div-float/2addr
		instLen.put("div-float/2addr", 1);
		// ca: rem-float/2addr
		instLen.put("rem-float/2addr", 1);
		// cb: add-double/2addr
		instLen.put("add-double/2addr", 1);
		// cc: sub-double/2addr
		instLen.put("sub-double/2addr", 1);
		// cd: mul-double/2addr
		instLen.put("mul-double/2addr", 1);
		// ce: div-double/2addr
		instLen.put("div-double/2addr", 1);
		// cf: rem-double/2addr
		instLen.put("rem-double/2addr", 1);
		//
		// d0..d7 22s binop/lit16 vA, vB, #+CCCC
		// d0: add-int/lit16
		instLen.put("add-int/lit16", 2);
		// d1: rsub-int (reverse subtract)
		instLen.put("rsub-int", 2);
		// d2: mul-int/lit16
		instLen.put("mul-int/lit16", 2);
		// d3: div-int/lit16
		instLen.put("div-int/lit16", 2);
		// d4: rem-int/lit16
		instLen.put("rem-int/lit16", 2);
		// d5: and-int/lit16
		instLen.put("and-int/lit16", 2);
		// d6: or-int/lit16
		instLen.put("or-int/lit16", 2);
		// d7: xor-int/lit16
		instLen.put("xor-int/lit16", 2);
		//
		// d8..e2 22b binop/lit8 vAA, vBB, #+CC
		// d8: add-int/lit8
		instLen.put("add-int/lit8", 2);
		// d9: rsub-int/lit8
		instLen.put("rsub-int/lit8", 2);
		// da: mul-int/lit8
		instLen.put("mul-int/lit8", 2);
		// db: div-int/lit8
		instLen.put("div-int/lit8", 2);
		// dc: rem-int/lit8
		instLen.put("rem-int/lit8", 2);
		// dd: and-int/lit8
		instLen.put("and-int/lit8", 2);
		// de: or-int/lit8
		instLen.put("or-int/lit8", 2);
		// df: xor-int/lit8
		instLen.put("xor-int/lit8", 2);
		// e0: shl-int/lit8
		instLen.put("shl-int/lit8", 2);
		// e1: shr-int/lit8
		instLen.put("shr-int/lit8", 2);
		// e2: ushr-int/lit8
		instLen.put("ushr-int/lit8", 2);
		//
		// e3..ff 10x (unused)///
	}

}