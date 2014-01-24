package sim;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sim.annotation.Annotation.ElementLiteral;
import control.Control;

public class PrettyPrintVisitor implements Visitor {
	private final static int TAB = 4;
	private int indents = 0;
	private String filePath;
	private String folderName;
	private FileWriter fileWrite;
	private HashMap<Integer, List<String>> reversedLabels;

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
	private void printLiteral(String literal, String type, boolean hasNext) {
		if (literal.startsWith("\""))
			this.say(this.processString(literal));
		else if (literal.startsWith("\'"))
			this.say(this.processChar(literal));
		else {
			if (type.equals("enum"))
				this.say(".enum " + literal);
			else
				this.say(literal);
		}
		if (hasNext)
			this.sayln(",");
		else
			this.sayln("");
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

	private void reverseLabels(HashMap<String, Integer> labels) {
		Iterator<Map.Entry<String, Integer>> it = labels.entrySet().iterator();
		HashMap<Integer, List<String>> result = new HashMap<Integer, List<String>>();
		while (it.hasNext()) {
			Map.Entry<String, Integer> entry = it.next();
			if (!result.containsKey(entry.getValue()))
				result.put(entry.getValue(), new ArrayList<String>());
			result.get(entry.getValue()).add(entry.getKey());
		}
		this.reversedLabels = result;
	}

	private List<String> getLabelAtPosition(HashMap<String, Integer> labels,
			int position) {
		return reversedLabels.get(position);
	}

	private void printLabel(HashMap<String, Integer> labels, int position) {
		List<String> labelsAtCurrentPos;
		labelsAtCurrentPos = this.getLabelAtPosition(labels, position);
		if (labelsAtCurrentPos != null)
			for (String str : labelsAtCurrentPos) {
				this.printSpace();
				this.sayln(":" + str);
			}
	}

	@Override
	public void visit(sim.program.Program program) {
		throw new RuntimeException(
				"visiting ast.program.Program?! are you out of your mind?");
	}

	@Override
	public void visit(sim.classs.Class clazz) {
		try {
			this.createFile(clazz.name);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.say(".class ");
		for (String access : clazz.accessList) {
			this.say(access + " ");
		}
		this.sayln(clazz.name);

		this.say(".super ");
		this.sayln(clazz.superr);
		if (clazz.source != null) {
			this.say(".source ");
			this.sayln(clazz.source);
		}

		// implementsList
		for (String s : clazz.implementsList) {
			this.sayln(".implements " + s);
		}

		// annotations
		for (sim.annotation.Annotation annotation : clazz.annotationList)
			annotation.accept(this);

		// fileds
		for (sim.classs.Class.Field field : clazz.fieldList) {
			this.say(".field ");
			for (String str : field.accessList)
				this.say(str + " ");
			this.say(field.name + ":");
			this.say(field.type);
			// literal
			if (field.elementLiteral != null
					&& field.elementLiteral.type != null) {
				this.say(" = ");
				field.elementLiteral.accept(this);
				this.sayln("");
			} else
				this.sayln("");
			// annotations
			if (field.annotationList != null) {
				for (sim.annotation.Annotation annotation : field.annotationList) {
					annotation.accept(this);
				}
				if (field.annotationList.size() > 0)
					this.sayln(".end field");
			}
		}
		for (sim.method.Method method : clazz.methods) {
			method.accept(this);
		}
		try {
			this.fileWrite.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void visit(sim.method.Method method) {
		boolean ignorePrologue = false;
		boolean endParameter = false;
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
		// if there exist annotation in parameter then all the .parameter should add .end parameter at the end
		for (sim.method.Method.Parameter parameter : method.parameterList) {
			if (parameter.annotationList.size() > 0) {
				endParameter = true;
			}
		}
		for (sim.method.Method.Parameter parameter : method.parameterList) {
			this.printSpace();
			if (parameter.value != null)
				this.sayln(".parameter " + parameter.value);
			else
				this.sayln(".parameter");
			// annotations
			for (sim.annotation.Annotation annotation : parameter.annotationList) {
				annotation.accept(this);
			}
			if (endParameter) {
				this.printSpace();
				this.sayln(".end parameter");
			}
		}
		if (!ignorePrologue) {
			this.printSpace();
			this.sayln(".prologue");
		}
		// annotations
		if (method.annotationList != null)
			for (sim.annotation.Annotation annotation : method.annotationList) {
				annotation.accept(this);
			}
		this.sayln("");
		this.reverseLabels(method.labels);
		for (int i = 0; i < method.statements.size(); i++) {
			this.printLabel(method.labels, i);
			this.printSpace();
			method.statements.get(i).accept(this);
		}
		this.printLabel(method.labels, method.statements.size());
		this.unIndent();
		this.sayln(".end method");
		this.sayln("");
	}

	@Override
	public void visit(sim.annotation.Annotation annotation) {
		this.printSpace();
		this.say(".annotation ");
		this.say(annotation.visibility + " ");
		annotation.subAnnotation.accept(this);
		this.printSpace();
		this.sayln(".end annotation");
	}

	@Override
	public void visit(sim.annotation.Annotation.SubAnnotation subAnnotation) {
		this.sayln(subAnnotation.classType);
		this.indent();
		for (sim.annotation.Annotation.AnnotationElement element : subAnnotation.elementList) {
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
			((sim.annotation.Annotation.SubAnnotation) elementLiteral.element
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
					System.out
							.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					System.out
							.println("W:find subannotation in array_literal in PrettyPrint");
					System.out
							.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				} else if (elementLiteral.arrayLiteralType
						.equals("subannotation")) {
					System.out
							.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					System.out
							.println("W:find array in array_literal in PrettyPrint");
					System.out
							.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				}
				this.indent();
				for (int i = 0; i < elementLiteral.element.size(); i++) {
					Object object = elementLiteral.element.get(i);
					String arrayLiteralTypet = elementLiteral.arrayLiteralType
							.get(i);
					this.printSpace();
					boolean hasNext = false;
					if (i < elementLiteral.element.size() - 1)
						hasNext = true;

					if (arrayLiteralTypet.equals("subannotation")) {
						this.say(".subannotation ");
						((sim.annotation.Annotation.SubAnnotation) object)
								.accept(this);
						this.printSpace();
						this.say(".end subannotation");
						if (hasNext)
							this.sayln(",");
						else
							this.sayln("");
					} else {
						String str = new String();
						str = (String) (object);
						this.printLiteral(str, arrayLiteralTypet, hasNext);
					}
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
					elementLiteral.type, false);
			this.sayln("");
		}
	}

	@Override
	public void visit(sim.method.Method.MethodPrototype prototype) {
		this.say("(");
		for (String type : prototype.argsType) {
			this.say(type);
		}
		this.say(")");
		this.sayln(prototype.returnType);
	}

	@Override
	public void visit(sim.classs.FieldItem item) {
		say(item.classType + "->");
		say(item.fieldName + ":");
		say(item.fieldType);
	}

	@Override
	public void visit(sim.classs.MethodItem item) {
		say(item.classType + "->");
		say(item.methodName);
		item.prototype.accept(this);
	}

	@Override
	public void visit(sim.stm.Instruction.Nop nop) {
		this.sayln("nop");
	}

	@Override
	public void visit(sim.stm.Instruction.Move inst) {
		this.sayln(inst.op + " " + inst.dst + ", " + inst.src);
	}

	@Override
	public void visit(sim.stm.Instruction.MoveResult inst) {
		this.sayln(inst.op + " " + inst.dst);
	}

	@Override
	public void visit(sim.stm.Instruction.ReturnVoid inst) {
		this.sayln("return-void");
	}

	@Override
	public void visit(sim.stm.Instruction.Return inst) {
		this.sayln(inst.op + " " + inst.src);
	}

	@Override
	public void visit(sim.stm.Instruction.Const inst) {
		if (inst.op.equals("const-string")
				|| inst.op.equals("const-string/jumbo")) {
			inst.value = this.processString(inst.value);
			this.sayln(inst.op + " " + inst.dst + ", " + inst.value);
		} else if (inst.op.equals("const-class"))
			this.sayln(inst.op + " " + inst.dst + ", " + inst.value);
		else {
			if (inst.value.startsWith("\'"))
				inst.value = this.processChar(inst.value);
			this.sayln(inst.op + " " + inst.dst + ", " + inst.value);
		}
	}

	@Override
	public void visit(sim.stm.Instruction.Monitor inst) {
		this.sayln(inst.op + " " + inst.ref);
	}

	@Override
	public void visit(sim.stm.Instruction.CheckCast inst) {
		this.sayln("check-cast " + inst.ref + ", " + inst.type);
	}

	@Override
	public void visit(sim.stm.Instruction.InstanceOf inst) {
		this.sayln("instance-of " + inst.dst + ", " + inst.ref + ", "
				+ inst.type);
	}

	@Override
	public void visit(sim.stm.Instruction.ArrayLength inst) {
		this.sayln("array-length " + inst.dst + ", " + inst.ref);
	}

	@Override
	public void visit(sim.stm.Instruction.NewInstance inst) {
		this.sayln("new-instance " + inst.dst + ", " + inst.type);
	}

	@Override
	public void visit(sim.stm.Instruction.NewArray inst) {
		this.sayln("new-array " + inst.dst + ", " + inst.size + ", "
				+ inst.type);
	}

	@Override
	public void visit(sim.stm.Instruction.FilledNewArray inst) {
		String op = inst.op.trim();
		if (op.equals("filled-new-array")) {
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
		} else {
			this.say(inst.op);
			this.say(" {");
			this.say(inst.argList.get(0) + " .. " + inst.argList.get(1));
			this.say("}, ");
			this.sayln(inst.type);
		}
	}

	@Override
	public void visit(sim.stm.Instruction.FillArrayData inst) {
		this.sayln("fill-array-data" + " " + inst.ref + ", :" + inst.label);

	}

	@Override
	public void visit(sim.stm.Instruction.Throw inst) {
		this.sayln("throw" + " " + inst.exception);
	}

	@Override
	public void visit(sim.stm.Instruction.Goto inst) {
		this.sayln(inst.op + " :" + inst.label);
	}

	@Override
	public void visit(sim.stm.Instruction.Switch inst) {
		this.sayln(inst.op + " " + inst.test + ", :" + inst.label);
	}

	@Override
	public void visit(sim.stm.Instruction.Cmp inst) {
		this.sayln(inst.op + " " + inst.dst + ", " + inst.firstSrc + ", "
				+ inst.secondSrc);
	}

	@Override
	public void visit(sim.stm.Instruction.IfTest inst) {
		this.sayln(inst.op + " " + inst.firstSrc + ", " + inst.secondSrc
				+ ", :" + inst.label);
	}

	@Override
	public void visit(sim.stm.Instruction.IfTestz inst) {
		this.sayln(inst.op + " " + inst.src + ", :" + inst.label);
	}

	@Override
	public void visit(sim.stm.Instruction.Aget inst) {
		this.sayln(inst.op + " " + inst.dst + ", " + inst.array + ", "
				+ inst.index);
	}

	@Override
	public void visit(sim.stm.Instruction.Aput inst) {
		this.sayln(inst.op + " " + inst.src + ", " + inst.array + ", "
				+ inst.index);
	}

	@Override
	public void visit(sim.stm.Instruction.Iget inst) {
		this.sayln(inst.op + " " + inst.dst + ", " + inst.obj + ", "
				+ inst.field.toString());
	}

	@Override
	public void visit(sim.stm.Instruction.Iput inst) {
		this.sayln(inst.op + " " + inst.src + ", " + inst.obj + ", "
				+ inst.field.toString());
	}

	@Override
	public void visit(sim.stm.Instruction.Sget inst) {
		this.sayln(inst.op + " " + inst.dst + ", " + inst.field.toString());
	}

	@Override
	public void visit(sim.stm.Instruction.Sput inst) {
		this.sayln(inst.op + " " + inst.src + ", " + inst.field.toString());
	}

	@Override
	public void visit(sim.stm.Instruction.Invoke inst) {
		if (inst.op.endsWith("/range")) {
			this.say(inst.op);
			this.say(" {");
			this.say(inst.args.get(0) + " .. " + inst.args.get(1));
			this.say("}, ");
			this.sayln(inst.method.toString());
		} else {
			this.say(inst.op);
			this.say(" {");
			int cnt = 0;
			for (String s : inst.args) {
				cnt++;
				if (cnt < inst.args.size())
					this.say(s + ", ");
				else
					this.say(s);
			}
			this.say("}, ");
			this.sayln(inst.method.toString());
		}
	}

	@Override
	public void visit(sim.stm.Instruction.UnOp inst) {
		this.sayln(inst.op + " " + inst.dst + ", " + inst.src);
	}

	@Override
	public void visit(sim.stm.Instruction.BinOp inst) {
		this.sayln(inst.op + " " + inst.dst + ", " + inst.firstSrc + ", "
				+ inst.secondSrc);
	}

	@Override
	public void visit(sim.stm.Instruction.BinOpLit inst) {
		if (inst.constt.startsWith("\'"))
			inst.constt = this.processChar(inst.constt);
		this.sayln(inst.op + " " + inst.dst + ", " + inst.src + ", "
				+ inst.constt);
	}

	@Override
	public void visit(sim.stm.Instruction.ArrayDataDirective inst) {
		this.sayln(".array-data " + inst.size);
		this.indent();
		this.printSpace();
		int cnt = 0;
		for (String str : inst.elementList) {
			if (str.startsWith("\'"))
				str = this.processChar(str);
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
	public void visit(sim.stm.Instruction.PackedSwitchDirective inst) {
		if (inst.key.startsWith("\'"))
			inst.key = this.processChar(inst.key);
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
	public void visit(sim.stm.Instruction.SparseSwitchDirective inst) {
		String str;
		this.sayln(".sparse-switch ");
		this.indent();
		for (int i = 0; i < inst.labList.size(); i++) {
			this.printSpace();
			str = inst.keyList.get(i);
			if (str.startsWith("\'"))
				str = this.processChar(str);
			this.sayln(str + " -> :" + inst.labList.get(i));
		}
		this.unIndent();
		this.printSpace();
		this.sayln(".end sparse-switch");
	}

	@Override
	public void visit(sim.stm.Instruction.Catch inst) {
		this.sayln(inst.toString());
	}
}
