package sim;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import control.Control;
import sim.annotation.Annotation.ElementLiteral;
import sim.classs.FieldItem;
import sim.classs.MethodItem;
import sim.stm.Instruction.Catch;

public class PrettyPrintVisitor implements Visitor {
	private final static int TAB = 4;
	private int indents = 0;
	private String filePath;
	private String folderName;
	private FileWriter fileWrite;

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
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(sim.annotation.Annotation annotation) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(sim.annotation.Annotation.SubAnnotation subAnnotation) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(sim.method.Method.MethodPrototype methodPrototype) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(ElementLiteral elementLiteral) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.ReturnVoid inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Return inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Const inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Monitor inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.CheckCast inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.InstanceOf inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.ArrayLength inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.NewInstance inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.NewArray inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.FilledNewArray inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.FillArrayData inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Throw inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Goto inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Switch inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Cmp inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.IfTest inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.IfTestz inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Aget inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Aput inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Iget inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Iput inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Sget inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Sput inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.Invoke inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.UnOp inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.BinOp inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.BinOpLit inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.ArrayDataDirective inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.PackedSwitchDirective inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(sim.stm.Instruction.SparseSwitchDirective inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Catch inst) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(FieldItem inst) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MethodItem inst) {
		// TODO Auto-generated method stub

	}
}
