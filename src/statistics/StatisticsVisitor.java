package statistics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import control.Control;

public class StatisticsVisitor implements ast.Visitor {
	public static HashMap<String, Integer> instMap;
	private String filePath;
	private String fileName;
	private String folderName;
	private FileWriter fileWrite;
	/* *
	 * static body is thread safe according to
	 * http://docs.oracle.com/javase/specs/jls/se7/html/jls-12.html#jls-12.4.2
	 */
	static {
		instMap = new HashMap<String, Integer>();
		// 00 10x nop
		instMap.put("nop", 0);
		// 01 12x move
		instMap.put("move", 0);
		// 02 22x move/from16
		instMap.put("move/from16", 0);
		// 03 32x move/16
		instMap.put("move/16", 0);
		// 04 12x move-wide
		instMap.put("move-wide", 0);
		// 05 22x move-wide/from16
		instMap.put("move-wide/from16", 0);
		// 06 32x move-wide/16
		instMap.put("move-wide/16", 0);
		// 07 12x move-object
		instMap.put("move-object", 0);
		// 08 22x move-object/from16
		instMap.put("move-object/from16", 0);
		// 09 32x move-object/16
		instMap.put("move-object/16", 0);
		// 0a 11x move-result
		instMap.put("move-result", 0);
		// 0b 11x move-result-wide
		instMap.put("move-result-wide", 0);
		// 0c 11x move-result-object
		instMap.put("move-result-object", 0);
		// 0d 11x move-exception
		instMap.put("move-exception", 0);
		// 0e 10x return-void
		instMap.put("return-void", 0);
		// 0f 11x return
		instMap.put("return", 0);
		// 10 11x return-wide
		instMap.put("return-wide", 0);
		// 11 11x return-object
		instMap.put("return-object", 0);
		// 12 11n const/4
		instMap.put("const/4", 0);
		// 13 21s const/16
		instMap.put("const/16", 0);
		// 14 31i const
		instMap.put("const", 0);
		// 15 21h const/high16
		instMap.put("const/high16", 0);
		// 16 21s const-wide/16
		instMap.put("const-wide/16", 0);
		// 17 31i const-wide/32
		instMap.put("const-wide/32", 0);
		// 18 51l const-wide
		instMap.put("const-wide", 0);
		// 19 21h const-wide/high16
		instMap.put("const-wide/high16", 0);
		// 1a 21c const-string
		instMap.put("const-string", 0);
		// 1b 31c const-string/jumbo
		instMap.put("const-string/jumbo", 0);
		// 1c 21c const-class
		instMap.put("const-class", 0);
		// 1d 11x monitor-enter
		instMap.put("monitor-enter", 0);
		// 1e 11x monitor-exit
		instMap.put("monitor-exit", 0);
		// 1f 21c check-cast
		instMap.put("check-cast", 0);
		// 20 22c instance-of
		instMap.put("instance-of", 0);
		// 21 12x array-length
		instMap.put("array-length", 0);
		// 22 21c new-instance
		instMap.put("new-instance", 0);
		// 23 22c new-array
		instMap.put("new-array", 0);
		// 24 35c filled-new-array
		instMap.put("filled-new-array", 0);
		// 25 3rc filled-new-array/range {vCCCC .. vNNNN}, type@BBBB
		instMap.put("filled-new-array/range", 0);
		// 26 31t fill-array-data
		instMap.put("fill-array-data", 0);
		// 27 11x throw
		instMap.put("throw", 0);
		// 28 10t goto
		instMap.put("goto", 0);
		// 29 20t goto/16
		instMap.put("goto/16", 0);
		// 2a 30t goto/32
		instMap.put("goto/32", 0);
		// 2b 31t packed-switch
		instMap.put("packed-switch", 0);
		// 2c 31t sparse-switch
		instMap.put("sparse-switch", 0);
		// 2d..31 23x cmpkind
		// 2d: cmpl-float
		instMap.put("cmpl-float", 0);
		// 2e: cmpg-float
		instMap.put("cmpg-float", 0);
		// 2f: cmpl-double
		instMap.put("cmpl-double", 0);
		// 30: cmpg-double
		instMap.put("cmpg-double", 0);
		// 31: cmp-long
		instMap.put("cmp-long", 0);
		// 32..37 22t if-test vA, vB, +CCCC
		// 32: if-eq
		instMap.put("if-eq", 0);
		// 33: if-ne
		instMap.put("if-ne", 0);
		// 34: if-lt
		instMap.put("if-lt", 0);
		// 35: if-ge
		instMap.put("if-ge", 0);
		// 36: if-gt
		instMap.put("if-gt", 0);
		// 37: if-le
		instMap.put("if-le", 0);
		// 38..3d 21t if-testz vAA, +BBBB
		// 38: if-eqz
		instMap.put("if-eqz", 0);
		// 39: if-nez
		instMap.put("if-nez", 0);
		// 3a: if-ltz
		instMap.put("if-ltz", 0);
		// 3b: if-gez
		instMap.put("if-gez", 0);
		// 3c: if-gtz
		instMap.put("if-gtz", 0);
		// 3d: if-lez
		instMap.put("if-lez", 0);
		//
		//
		// 3e..43 10x (unused)
		// 44..51 23x arrayop vAA, vBB, vCC
		// 44: aget
		instMap.put("aget", 0);
		// 45: aget-wide
		instMap.put("aget-wide", 0);
		// 46: aget-object
		instMap.put("aget-object", 0);
		// 47: aget-boolean
		instMap.put("aget-boolean", 0);
		// 48: aget-byte
		instMap.put("aget-byte", 0);
		// 49: aget-char
		instMap.put("aget-char", 0);
		// 4a: aget-short
		instMap.put("aget-short", 0);
		// 4b: aput
		instMap.put("aput", 0);
		// 4c: aput-wide
		instMap.put("aput-wide", 0);
		// 4d: aput-object
		instMap.put("aput-object", 0);
		// 4e: aput-boolean
		instMap.put("aput-boolean", 0);
		// 4f: aput-byte
		instMap.put("aput-byte", 0);
		// 50: aput-char
		instMap.put("aput-char", 0);
		// 51: aput-short
		instMap.put("aput-short", 0);
		//
		// 52..5f 22c iinstanceop vA, vB, field@CCCC
		// 52: iget
		instMap.put("iget", 0);
		// 53: iget-wide
		instMap.put("iget-wide", 0);
		// 54: iget-object
		instMap.put("iget-object", 0);
		// 55: iget-boolean
		instMap.put("iget-boolean", 0);
		// 56: iget-byte
		instMap.put("iget-byte", 0);
		// 57: iget-char
		instMap.put("iget-char", 0);
		// 58: iget-short
		instMap.put("iget-short", 0);
		// 59: iput
		instMap.put("iput", 0);
		// 5a: iput-wide
		instMap.put("iput-wide", 0);
		// 5b: iput-object
		instMap.put("iput-object", 0);
		// 5c: iput-boolean
		instMap.put("iput-boolean", 0);
		// 5d: iput-byte
		instMap.put("iput-byte", 0);
		// 5e: iput-char
		instMap.put("iput-char", 0);
		// 5f: iput-short
		instMap.put("iput-short", 0);
		//
		// 60..6d 21c sstaticop vAA, field@BBBB
		// 60: sget
		instMap.put("sget", 0);
		// 61: sget-wide
		instMap.put("sget-wide", 0);
		// 62: sget-object
		instMap.put("sget-object", 0);
		// 63: sget-boolean
		instMap.put("sget-boolean", 0);
		// 64: sget-byte
		instMap.put("sget-byte", 0);
		// 65: sget-char
		instMap.put("sget-char", 0);
		// 66: sget-short
		instMap.put("sget-short", 0);
		// 67: sput
		instMap.put("sput", 0);
		// 68: sput-wide
		instMap.put("sput-wide", 0);
		// 69: sput-object
		instMap.put("sput-object", 0);
		// 6a: sput-boolean
		instMap.put("sput-boolean", 0);
		// 6b: sput-byte
		instMap.put("sput-byte", 0);
		// 6c: sput-char
		instMap.put("sput-char", 0);
		// 6d: sput-short
		instMap.put("sput-short", 0);
		//
		// 6e..72 35c invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB
		// 6e: invoke-virtual
		instMap.put("invoke-virtual", 0);
		// 6f: invoke-super
		instMap.put("invoke-super", 0);
		// 70: invoke-direct
		instMap.put("invoke-direct", 0);
		// 71: invoke-static
		instMap.put("invoke-static", 0);
		// 72: invoke-interface
		instMap.put("invoke-interface", 0);
		//
		//
		// 73 10x (unused)
		// 74..78 3rc invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB
		// 74: invoke-virtual/range
		instMap.put("invoke-virtual/range", 0);
		// 75: invoke-super/range
		instMap.put("invoke-super/range", 0);
		// 76: invoke-direct/range
		instMap.put("invoke-direct/range", 0);
		// 77: invoke-static/range
		instMap.put("invoke-static/range", 0);
		// 78: invoke-interface/range
		instMap.put("invoke-interface/range", 0);
		//
		// 79..7a 10x (unused)
		// 7b..8f 12x unop vA, vB
		// 7b: neg-int
		instMap.put("neg-int", 0);
		// 7c: not-int
		instMap.put("not-int", 0);
		// 7d: neg-long
		instMap.put("neg-long", 0);
		// 7e: not-long
		instMap.put("not-long", 0);
		// 7f: neg-float
		instMap.put("neg-float", 0);
		// 80: neg-double
		instMap.put("neg-double", 0);
		// 81: int-to-long
		instMap.put("int-to-long", 0);
		// 82: int-to-float
		instMap.put("int-to-float", 0);
		// 83: int-to-double
		instMap.put("int-to-double", 0);
		// 84: long-to-int
		instMap.put("long-to-int", 0);
		// 85: long-to-float
		instMap.put("long-to-float", 0);
		// 86: long-to-double
		instMap.put("long-to-double", 0);
		// 87: float-to-int
		instMap.put("float-to-int", 0);
		// 88: float-to-long
		instMap.put("float-to-long", 0);
		// 89: float-to-double
		instMap.put("float-to-double", 0);
		// 8a: double-to-int
		instMap.put("double-to-int", 0);
		// 8b: double-to-long
		instMap.put("double-to-long", 0);
		// 8c: double-to-float
		instMap.put("double-to-float", 0);
		// 8d: int-to-byte
		instMap.put("int-to-byte", 0);
		// 8e: int-to-char
		instMap.put("int-to-char", 0);
		// 8f: int-to-short
		instMap.put("int-to-short", 0);
		//
		// 90..af 23x binop vAA, vBB, vCC
		// 90: add-int
		instMap.put("add-int", 0);
		// 91: sub-int
		instMap.put("sub-int", 0);
		// 92: mul-int
		instMap.put("mul-int", 0);
		// 93: div-int
		instMap.put("div-int", 0);
		// 94: rem-int
		instMap.put("rem-int", 0);
		// 95: and-int
		instMap.put("and-int", 0);
		// 96: or-int
		instMap.put("or-int", 0);
		// 97: xor-int
		instMap.put("xor-int", 0);
		// 98: shl-int
		instMap.put("shl-int", 0);
		// 99: shr-int
		instMap.put("shr-int", 0);
		// 9a: ushr-int
		instMap.put("ushr-int", 0);
		// 9b: add-long
		instMap.put("add-long", 0);
		// 9c: sub-long
		instMap.put("sub-long", 0);
		// 9d: mul-long
		instMap.put("mul-long", 0);
		// 9e: div-long
		instMap.put("div-long", 0);
		// 9f: rem-long
		instMap.put("rem-long", 0);
		// a0: and-long
		instMap.put("and-long", 0);
		// a1: or-long
		instMap.put("or-long", 0);
		// a2: xor-long
		instMap.put("xor-long", 0);
		// a3: shl-long
		instMap.put("shl-long", 0);
		// a4: shr-long
		instMap.put("shr-long", 0);
		// a5: ushr-long
		instMap.put("ushr-long", 0);
		// a6: add-float
		instMap.put("add-float", 0);
		// a7: sub-float
		instMap.put("sub-float", 0);
		// a8: mul-float
		instMap.put("mul-float", 0);
		// a9: div-float
		instMap.put("div-float", 0);
		// aa: rem-float
		instMap.put("rem-float", 0);
		// ab: add-double
		instMap.put("add-double", 0);
		// ac: sub-double
		instMap.put("sub-double", 0);
		// ad: mul-double
		instMap.put("mul-double", 0);
		// ae: div-double
		instMap.put("div-double", 0);
		// af: rem-double
		instMap.put("rem-double", 0);
		//
		// b0..cf 12x binop/2addr vA, vB
		// b0: add-int/2addr
		instMap.put("add-int/2addr", 0);
		// b1: sub-int/2addr
		instMap.put("sub-int/2addr", 0);
		// b2: mul-int/2addr
		instMap.put("mul-int/2addr", 0);
		// b3: div-int/2addr
		instMap.put("div-int/2addr", 0);
		// b4: rem-int/2addr
		instMap.put("rem-int/2addr", 0);
		// b5: and-int/2addr
		instMap.put("and-int/2addr", 0);
		// b6: or-int/2addr
		instMap.put("or-int/2addr", 0);
		// b7: xor-int/2addr
		instMap.put("xor-int/2addr", 0);
		// b8: shl-int/2addr
		instMap.put("shl-int/2addr", 0);
		// b9: shr-int/2addr
		instMap.put("shr-int/2addr", 0);
		// ba: ushr-int/2addr
		instMap.put("ushr-int/2addr", 0);
		// bb: add-long/2addr
		instMap.put("add-long/2addr", 0);
		// bc: sub-long/2addr
		instMap.put("sub-long/2addr", 0);
		// bd: mul-long/2addr
		instMap.put("mul-long/2addr", 0);
		// be: div-long/2addr
		instMap.put("div-long/2addr", 0);
		// bf: rem-long/2addr
		instMap.put("rem-long/2addr", 0);
		// c0: and-long/2addr
		instMap.put("and-long/2addr", 0);
		// c1: or-long/2addr
		instMap.put("or-long/2addr", 0);
		// c2: xor-long/2addr
		instMap.put("xor-long/2addr", 0);
		// c3: shl-long/2addr
		instMap.put("shl-long/2addr", 0);
		// c4: shr-long/2addr
		instMap.put("shr-long/2addr", 0);
		// c5: ushr-long/2addr
		instMap.put("ushr-long/2addr", 0);
		// c6: add-float/2addr
		instMap.put("add-float/2addr", 0);
		// c7: sub-float/2addr
		instMap.put("sub-float/2addr", 0);
		// c8: mul-float/2addr
		instMap.put("mul-float/2addr", 0);
		// c9: div-float/2addr
		instMap.put("div-float/2addr", 0);
		// ca: rem-float/2addr
		instMap.put("rem-float/2addr", 0);
		// cb: add-double/2addr
		instMap.put("add-double/2addr", 0);
		// cc: sub-double/2addr
		instMap.put("sub-double/2addr", 0);
		// cd: mul-double/2addr
		instMap.put("mul-double/2addr", 0);
		// ce: div-double/2addr
		instMap.put("div-double/2addr", 0);
		// cf: rem-double/2addr
		instMap.put("rem-double/2addr", 0);
		//
		// d0..d7 22s binop/lit16 vA, vB, #+CCCC
		// d0: add-int/lit16
		instMap.put("add-int/lit16", 0);
		// d1: rsub-int (reverse subtract)
		instMap.put("rsub-int", 0);
		// d2: mul-int/lit16
		instMap.put("mul-int/lit16", 0);
		// d3: div-int/lit16
		instMap.put("div-int/lit16", 0);
		// d4: rem-int/lit16
		instMap.put("rem-int/lit16", 0);
		// d5: and-int/lit16
		instMap.put("and-int/lit16", 0);
		// d6: or-int/lit16
		instMap.put("or-int/lit16", 0);
		// d7: xor-int/lit16
		instMap.put("xor-int/lit16", 0);
		//
		// d8..e2 22b binop/lit8 vAA, vBB, #+CC
		// d8: add-int/lit8
		instMap.put("add-int/lit8", 0);
		// d9: rsub-int/lit8
		instMap.put("rsub-int/lit8", 0);
		// da: mul-int/lit8
		instMap.put("mul-int/lit8", 0);
		// db: div-int/lit8
		instMap.put("div-int/lit8", 0);
		// dc: rem-int/lit8
		instMap.put("rem-int/lit8", 0);
		// dd: and-int/lit8
		instMap.put("and-int/lit8", 0);
		// de: or-int/lit8
		instMap.put("or-int/lit8", 0);
		// df: xor-int/lit8
		instMap.put("xor-int/lit8", 0);
		// e0: shl-int/lit8
		instMap.put("shl-int/lit8", 0);
		// e1: shr-int/lit8
		instMap.put("shr-int/lit8", 0);
		// e2: ushr-int/lit8
		instMap.put("ushr-int/lit8", 0);
		//
		// e3..ff 10x (unused)///
	}

	private void createFile(String fullyQualifiedName) throws IOException {
		this.fileName = fullyQualifiedName.substring(1,
				fullyQualifiedName.length() - 1);

		File file = new File(this.fileName);
		file.mkdirs();
		file = new File(this.filePath);
		if (file.createNewFile()) {
			this.fileWrite = new FileWriter(file, true);
		} else {
			System.err.println("name conflicting " + fullyQualifiedName);
			System.exit(1);
		}
	}

	// program
	@Override
	public void visit(ast.program.Program p) {

	}

	@Override
	public void visit(ast.classs.Class clazz) {

	}

	@Override
	public void visit(ast.method.Method m) {
	}

	@Override
	public void visit(ast.method.Method.MethodPrototype prototype) {
	}

	@Override
	public void visit(ast.classs.MethodItem item) {
	}

	@Override
	public void visit(ast.classs.FieldItem item) {
	}

	@Override
	public void visit(ast.annotation.Annotation anno) {
	}

	@Override
	public void visit(ast.annotation.Annotation.SubAnnotation subAnno) {
	}

	@Override
	public void visit(ast.annotation.Annotation.ElementLiteral elem) {
	}

	// ////////////////////////////////////////////////////////
	// 00 10x nop
	@Override
	public void visit(ast.stm.Instruction.Nop inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 01 12x move vA, vB
	@Override
	public void visit(ast.stm.Instruction.Move inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 02 22x move/from16 vAA, vBBBB
	@Override
	public void visit(ast.stm.Instruction.MoveFrom16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 03 32x move/16 vAAAA, vBBBB ------
	@Override
	public void visit(ast.stm.Instruction.Move16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 04 12x move-wide vA, vB
	@Override
	public void visit(ast.stm.Instruction.MoveWide inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 05 22x move-wide/from16 vAA, vBBBB
	@Override
	public void visit(ast.stm.Instruction.MoveWideFrom16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 06 32x move-wide/16 vAAAA, vBBBB -----
	@Override
	public void visit(ast.stm.Instruction.MoveWide16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 07 12x move-object vA, vB
	@Override
	public void visit(ast.stm.Instruction.MoveObject inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 08 22x move-object/from16 vAA, vBBBB --
	@Override
	public void visit(ast.stm.Instruction.MoveOjbectFrom16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 09 32x move-object/16 vAAAA, vBBBB
	@Override
	public void visit(ast.stm.Instruction.MoveObject16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 0a 11x move-result vAA
	@Override
	public void visit(ast.stm.Instruction.MoveResult inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 0b 11x move-result-wide vAA
	@Override
	public void visit(ast.stm.Instruction.MoveResultWide inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 0c 11x move-result-object vAA
	@Override
	public void visit(ast.stm.Instruction.MoveResultObject inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 0d 11x move-exception vAA
	@Override
	public void visit(ast.stm.Instruction.MoveException inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 0e 10x return-void
	@Override
	public void visit(ast.stm.Instruction.ReturnVoid inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 0f 11x return vAA
	@Override
	public void visit(ast.stm.Instruction.Return inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 10 11x return-wide vAA
	@Override
	public void visit(ast.stm.Instruction.ReturnWide inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 11 11x return-object vAA
	@Override
	public void visit(ast.stm.Instruction.ReturnObject inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 12 11n const/4 vA, #+B
	@Override
	public void visit(ast.stm.Instruction.Const4 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 13 21s const/16 vAA, #+BBBB
	@Override
	public void visit(ast.stm.Instruction.Const16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 14 31i const vAA, #+BBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.Const inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 15 21h const/high16 vAA, #+BBBB0000
	@Override
	public void visit(ast.stm.Instruction.ConstHigh16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 16 21s const-wide/16 vAA, #+BBBB
	@Override
	public void visit(ast.stm.Instruction.ConstWide16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 17 31i const-wide/32 vAA, #+BBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.ConstWide32 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 18 51l const-wide vAA, #+BBBBBBBBBBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.ConstWide inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 19 21h const-wide/high16 vAA, #+BBBB000000000000
	// const-wide/high16 v2,0x4024 -- > const-wide v2,0x4024000000000000L
	@Override
	public void visit(ast.stm.Instruction.ConstWideHigh16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 1a 21c const-string vAA, string@BBBB
	@Override
	public void visit(ast.stm.Instruction.ConstString inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 1b 31c const-string/jumbo vAA, string@BBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.ConstStringJumbo inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 1c 21c const-class vAA, type@BBBB
	@Override
	public void visit(ast.stm.Instruction.ConstClass inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 1d 11x monitor-enter vAA
	@Override
	public void visit(ast.stm.Instruction.MonitorEnter inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 1e 11x monitor-exit vAA
	@Override
	public void visit(ast.stm.Instruction.MonitorExit inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 1f 21c check-cast vAA, type@BBBB
	@Override
	public void visit(ast.stm.Instruction.CheckCast inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 20 22c instance-of vA, vB, type@CCCC
	@Override
	public void visit(ast.stm.Instruction.InstanceOf inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 21 12x array-length vA, vB
	@Override
	public void visit(ast.stm.Instruction.arrayLength inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 22 21c new-instance vAA, type@BBBB
	@Override
	public void visit(ast.stm.Instruction.NewInstance inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 23 22c new-array vA, vB, type@CCCC
	// new-array v0,p1 [Landro......
	@Override
	public void visit(ast.stm.Instruction.NewArray inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 24 35c filled-new-array {vC, vD, vE, vF, vG}, type@BBBB
	// filled-new-array {v7, v9}, [I
	@Override
	public void visit(ast.stm.Instruction.FilledNewArray inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 25 3rc filled-new-array/range {vCCCC .. vNNNN}, type@BBBB ----
	@Override
	public void visit(ast.stm.Instruction.FilledNewArrayRange inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 26 31t fill-array-data vAA, +BBBBBBBB (with supplemental data as
	// specified below in "fill-array-data-payloadFormat") ------
	@Override
	public void visit(ast.stm.Instruction.FillArrayData inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 27 11x throw vAA
	@Override
	public void visit(ast.stm.Instruction.Throw inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 28 10t goto +AA ??
	// !!!!! goto :goto_0
	@Override
	public void visit(ast.stm.Instruction.Goto inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 29 20t goto/16 +AAAA ??
	@Override
	public void visit(ast.stm.Instruction.Goto16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 2a 30t goto/32 +AAAAAAAA ??
	@Override
	public void visit(ast.stm.Instruction.Goto32 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 2b 31t packed-switch vAA, +BBBBBBBB (with supplemental data as specified
	// below in "packed-switch- ??????????
	@Override
	public void visit(ast.stm.Instruction.PackedSwitch inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 2c 31t sparse-switch vAA, +BBBBBBBB (with supplemental data as specified
	// below in "sparse-switch-payloadFormat") ??????????
	@Override
	public void visit(ast.stm.Instruction.SparseSwitch inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	//
	// 2d..31 23x cmpkind vAA, vBB, vCC
	// 2d: cmpl-float (lt bias)
	@Override
	public void visit(ast.stm.Instruction.CmplFloat inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 2e: cmpg-float (gt bias)
	@Override
	public void visit(ast.stm.Instruction.CmpgFloat inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 2f: cmpl-double (lt bias)
	@Override
	public void visit(ast.stm.Instruction.CmplDouble inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 30: cmpg-double (gt bias)
	@Override
	public void visit(ast.stm.Instruction.Cmpgdouble inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 31: cmp-long
	@Override
	public void visit(ast.stm.Instruction.CmpLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	//
	// 32..37 22t if-test vA, vB, +CCCC
	// !!!!!!! if-eq v1, v0, :cond_1
	// 32: if-eq
	@Override
	public void visit(ast.stm.Instruction.IfEq inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 33: if-ne
	@Override
	public void visit(ast.stm.Instruction.IfNe inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 34: if-lt
	@Override
	public void visit(ast.stm.Instruction.IfLt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 35: if-ge
	@Override
	public void visit(ast.stm.Instruction.IfGe inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 36: if-gt
	@Override
	public void visit(ast.stm.Instruction.IfGt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 37: if-le
	@Override
	public void visit(ast.stm.Instruction.IfLe inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	//
	//
	// 38..3d 21t if-testz vAA, +BBBB
	// !!!! if-eqz v0, :cond_0
	// 38: if-eqz
	@Override
	public void visit(ast.stm.Instruction.IfEqz inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 39: if-nez
	@Override
	public void visit(ast.stm.Instruction.IfNez inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 3a: if-ltz
	@Override
	public void visit(ast.stm.Instruction.IfLtz inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 3b: if-gez
	@Override
	public void visit(ast.stm.Instruction.IfGez inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 3c: if-gtz
	@Override
	public void visit(ast.stm.Instruction.IfGtz inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 3d: if-lez
	@Override
	public void visit(ast.stm.Instruction.IfLez inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	//
	//
	// 3e..43 10x (unused)
	// 44..51 23x arrayop vAA, vBB, vCC

	// 44: aget
	@Override
	public void visit(ast.stm.Instruction.Aget inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 45: aget-wide
	@Override
	public void visit(ast.stm.Instruction.AgetWide inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 46: aget-object
	@Override
	public void visit(ast.stm.Instruction.AgetObject inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 47: aget-boolean
	@Override
	public void visit(ast.stm.Instruction.AgetBoolean inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 48: aget-byte
	@Override
	public void visit(ast.stm.Instruction.AgetByte inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 49: aget-char
	@Override
	public void visit(ast.stm.Instruction.AgetChar inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 4a: aget-short
	@Override
	public void visit(ast.stm.Instruction.AgetShort inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 4b: aput
	@Override
	public void visit(ast.stm.Instruction.Aput inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 4c: aput-wide
	@Override
	public void visit(ast.stm.Instruction.AputWide inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 4d: aput-object
	@Override
	public void visit(ast.stm.Instruction.AputObject inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 4e: aput-boolean
	@Override
	public void visit(ast.stm.Instruction.AputBoolean inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 4f: aput-byte
	@Override
	public void visit(ast.stm.Instruction.AputByte inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 50: aput-char
	@Override
	public void visit(ast.stm.Instruction.AputChar inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 51: aput-short
	@Override
	public void visit(ast.stm.Instruction.AputShort inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	//
	// 52..5f 22c iinstanceop vA, vB, field@CCCC
	// 52: iget
	@Override
	public void visit(ast.stm.Instruction.Iget inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 53: iget-wide
	@Override
	public void visit(ast.stm.Instruction.IgetWide inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 54: iget-object
	@Override
	public void visit(ast.stm.Instruction.IgetOjbect inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 55: iget-boolean
	@Override
	public void visit(ast.stm.Instruction.IgetBoolean inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 56: iget-byte
	@Override
	public void visit(ast.stm.Instruction.IgetByte inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 57: iget-char
	@Override
	public void visit(ast.stm.Instruction.IgetChar inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 58: iget-short
	@Override
	public void visit(ast.stm.Instruction.IgetShort inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 59: iput
	@Override
	public void visit(ast.stm.Instruction.Iput inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 5a: iput-wide
	@Override
	public void visit(ast.stm.Instruction.IputWide inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 5b: iput-object
	@Override
	public void visit(ast.stm.Instruction.IputObject inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 5c: iput-boolean
	@Override
	public void visit(ast.stm.Instruction.IputBoolean inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 5d: iput-byte
	@Override
	public void visit(ast.stm.Instruction.IputByte inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 5e: iput-char
	@Override
	public void visit(ast.stm.Instruction.IputChar inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 5f: iput-short
	@Override
	public void visit(ast.stm.Instruction.IputShort inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	//
	// 60..6d 21c sstaticop vAA, field@BBBB
	// 60: sget
	@Override
	public void visit(ast.stm.Instruction.Sget inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 61: sget-wide
	@Override
	public void visit(ast.stm.Instruction.SgetWide inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 62: sget-object
	@Override
	public void visit(ast.stm.Instruction.SgetObject inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 63: sget-boolean
	@Override
	public void visit(ast.stm.Instruction.SgetBoolean inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 64: sget-byte
	@Override
	public void visit(ast.stm.Instruction.SgetByte inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 65: sget-char
	@Override
	public void visit(ast.stm.Instruction.SgetChar inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 66: sget-short
	@Override
	public void visit(ast.stm.Instruction.SgetShort inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 67: sput
	@Override
	public void visit(ast.stm.Instruction.Sput inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 68: sput-wide
	@Override
	public void visit(ast.stm.Instruction.SputWide inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 69: sput-object
	@Override
	public void visit(ast.stm.Instruction.SputObject inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 6a: sput-boolean
	@Override
	public void visit(ast.stm.Instruction.SputBoolean inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 6b: sput-byte
	@Override
	public void visit(ast.stm.Instruction.SputByte inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 6c: sput-char
	@Override
	public void visit(ast.stm.Instruction.SputChar inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 6d: sput-short
	@Override
	public void visit(ast.stm.Instruction.SputShort inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 6e..72 35c invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB
	// 6e: invoke-virtual
	@Override
	public void visit(ast.stm.Instruction.InvokeVirtual inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 6f: invoke-super
	@Override
	public void visit(ast.stm.Instruction.InvokeSuper inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 70: invoke-direct
	@Override
	public void visit(ast.stm.Instruction.InvokeDirect inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 71: invoke-static
	@Override
	public void visit(ast.stm.Instruction.InvokeStatic inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 72: invoke-interface
	@Override
	public void visit(ast.stm.Instruction.InvokeInterface inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	//
	//
	// 73 10x (unused)
	// 74..78 3rc invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB
	// 74: invoke-virtual/range
	@Override
	public void visit(ast.stm.Instruction.InvokeVirtualRange inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);

	}

	// 75: invoke-super/range
	@Override
	public void visit(ast.stm.Instruction.InvokeSuperRange inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 76: invoke-direct/range
	@Override
	public void visit(ast.stm.Instruction.InvokeDirectRange inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 77: invoke-static/range
	@Override
	public void visit(ast.stm.Instruction.InvokeStaticRange inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 78: invoke-interface/range
	@Override
	public void visit(ast.stm.Instruction.InvokeInterfaceRange inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	//
	// 79..7a 10x (unused)
	// 7b..8f 12x unop vA, vB
	// 7b: neg-int
	@Override
	public void visit(ast.stm.Instruction.NegInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 7c: not-int
	@Override
	public void visit(ast.stm.Instruction.NotInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 7d: neg-long
	@Override
	public void visit(ast.stm.Instruction.NegLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 7e: not-long
	@Override
	public void visit(ast.stm.Instruction.NotLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 7f: neg-float
	@Override
	public void visit(ast.stm.Instruction.NegFloat inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 80: neg-double
	@Override
	public void visit(ast.stm.Instruction.NegDouble inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 81: int-to-long
	@Override
	public void visit(ast.stm.Instruction.IntToLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 82: int-to-float
	@Override
	public void visit(ast.stm.Instruction.IntToFloat inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 83: int-to-double
	@Override
	public void visit(ast.stm.Instruction.IntToDouble inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 84: long-to-int
	@Override
	public void visit(ast.stm.Instruction.LongToInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 85: long-to-float
	@Override
	public void visit(ast.stm.Instruction.LongToFloat inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 86: long-to-double
	@Override
	public void visit(ast.stm.Instruction.LongToDouble inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 87: float-to-int
	@Override
	public void visit(ast.stm.Instruction.FloatToInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 88: float-to-long
	@Override
	public void visit(ast.stm.Instruction.FloatToLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 89: float-to-double
	@Override
	public void visit(ast.stm.Instruction.FloatToDouble inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 8a: double-to-int
	@Override
	public void visit(ast.stm.Instruction.DoubleToInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 8b: double-to-long
	@Override
	public void visit(ast.stm.Instruction.DoubleToLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 8c: double-to-float
	@Override
	public void visit(ast.stm.Instruction.DoubleToFloat inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 8d: int-to-byte
	@Override
	public void visit(ast.stm.Instruction.IntToByte inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 8e: int-to-char
	@Override
	public void visit(ast.stm.Instruction.IntToChar inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 8f: int-to-short
	@Override
	public void visit(ast.stm.Instruction.IntToShort inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	//
	// 90..af 23x binop vAA, vBB, vCC
	// 90: add-int
	@Override
	public void visit(ast.stm.Instruction.AddInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 91: sub-int public void visit(ast.stm.Instruction.AddInt inst)
	@Override
	public void visit(ast.stm.Instruction.SubInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 92: mul-int
	@Override
	public void visit(ast.stm.Instruction.MulInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 93: div-int
	@Override
	public void visit(ast.stm.Instruction.DivInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 94: rem-int
	@Override
	public void visit(ast.stm.Instruction.RemInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 95: and-int
	@Override
	public void visit(ast.stm.Instruction.AndInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 96: or-int
	@Override
	public void visit(ast.stm.Instruction.OrInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 97: xor-int
	@Override
	public void visit(ast.stm.Instruction.XorInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 98: shl-int
	@Override
	public void visit(ast.stm.Instruction.ShlInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 99: shr-int
	@Override
	public void visit(ast.stm.Instruction.ShrInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 9a: ushr-int
	@Override
	public void visit(ast.stm.Instruction.UshrInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 9b: add-long
	@Override
	public void visit(ast.stm.Instruction.AddLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 9c: sub-long
	@Override
	public void visit(ast.stm.Instruction.SubLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 9d: mul-long
	@Override
	public void visit(ast.stm.Instruction.MulLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 9e: div-long
	@Override
	public void visit(ast.stm.Instruction.DivLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// 9f: rem-long
	@Override
	public void visit(ast.stm.Instruction.RemLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// a0: and-long
	@Override
	public void visit(ast.stm.Instruction.AndLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// a1: or-long
	@Override
	public void visit(ast.stm.Instruction.OrLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// a2: xor-long
	@Override
	public void visit(ast.stm.Instruction.XorLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// a3: shl-long
	@Override
	public void visit(ast.stm.Instruction.ShlLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// a4: shr-long
	@Override
	public void visit(ast.stm.Instruction.ShrLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// a5: ushr-long
	@Override
	public void visit(ast.stm.Instruction.UshrLong inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// a6: add-float
	@Override
	public void visit(ast.stm.Instruction.AddFloat inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// a7: sub-float
	@Override
	public void visit(ast.stm.Instruction.SubFloat inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// a8: mul-float
	@Override
	public void visit(ast.stm.Instruction.MulFloat inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// a9: div-float
	@Override
	public void visit(ast.stm.Instruction.DivFloat inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// aa: rem-float
	@Override
	public void visit(ast.stm.Instruction.RemFloat inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// ab: add-double
	@Override
	public void visit(ast.stm.Instruction.AddDouble inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// ac: sub-double
	@Override
	public void visit(ast.stm.Instruction.SubDouble inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// ad: mul-double
	@Override
	public void visit(ast.stm.Instruction.MulDouble inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// ae: div-double
	@Override
	public void visit(ast.stm.Instruction.DivDouble inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// af: rem-double
	@Override
	public void visit(ast.stm.Instruction.RemDouble inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	//
	// b0..cf 12x binop/2addr vA, vB

	// b0: add-int/2addr
	@Override
	public void visit(ast.stm.Instruction.AddInt2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// b1: sub-int/2addr
	@Override
	public void visit(ast.stm.Instruction.SubInt2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// b2: mul-int/2addr
	@Override
	public void visit(ast.stm.Instruction.MulInt2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// b3: div-int/2addr
	@Override
	public void visit(ast.stm.Instruction.DivInt2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// b4: rem-int/2addr
	@Override
	public void visit(ast.stm.Instruction.RemInt2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// b5: and-int/2addr
	@Override
	public void visit(ast.stm.Instruction.AndInt2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// b6: or-int/2addr
	@Override
	public void visit(ast.stm.Instruction.OrInt2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// b7: xor-int/2addr
	@Override
	public void visit(ast.stm.Instruction.XorInt2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// b8: shl-int/2addr
	@Override
	public void visit(ast.stm.Instruction.ShlInt2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// b9: shr-int/2addr
	@Override
	public void visit(ast.stm.Instruction.ShrInt2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// ba: ushr-int/2addr
	@Override
	public void visit(ast.stm.Instruction.UshrInt2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// bb: add-long/2addr
	@Override
	public void visit(ast.stm.Instruction.AddLong2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// bc: sub-long/2addr
	@Override
	public void visit(ast.stm.Instruction.SubLong2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// bd: mul-long/2addr
	@Override
	public void visit(ast.stm.Instruction.MulLong2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// be: div-long/2addr
	@Override
	public void visit(ast.stm.Instruction.DivLong2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// bf: rem-long/2addr
	@Override
	public void visit(ast.stm.Instruction.RemLong2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// c0: and-long/2addr
	@Override
	public void visit(ast.stm.Instruction.AndLong2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// c1: or-long/2addr
	@Override
	public void visit(ast.stm.Instruction.OrLong2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// c2: xor-long/2addr
	@Override
	public void visit(ast.stm.Instruction.XorLong2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// c3: shl-long/2addr
	@Override
	public void visit(ast.stm.Instruction.ShlLong2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// c4: shr-long/2addr
	@Override
	public void visit(ast.stm.Instruction.ShrLong2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// c5: ushr-long/2addr
	@Override
	public void visit(ast.stm.Instruction.UshrLong2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// c6: add-float/2addr
	@Override
	public void visit(ast.stm.Instruction.AddFloat2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// c7: sub-float/2addr
	@Override
	public void visit(ast.stm.Instruction.SubFloat2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// c8: mul-float/2addr
	@Override
	public void visit(ast.stm.Instruction.MulFloat2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// c9: div-float/2addr
	@Override
	public void visit(ast.stm.Instruction.DivFloat2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// ca: rem-float/2addr
	@Override
	public void visit(ast.stm.Instruction.RemFloat2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// cb: add-double/2addr
	@Override
	public void visit(ast.stm.Instruction.AddDouble2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// cc: sub-double/2addr
	@Override
	public void visit(ast.stm.Instruction.SubDouble2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// cd: mul-double/2addr
	@Override
	public void visit(ast.stm.Instruction.MulDouble2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// ce: div-double/2addr
	@Override
	public void visit(ast.stm.Instruction.DivDouble2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// cf: rem-double/2addr
	@Override
	public void visit(ast.stm.Instruction.RemDouble2Addr inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	//
	// d0..d7 22s binop/lit16 vA, vB, #+CCCC
	// d0: add-int/lit16
	@Override
	public void visit(ast.stm.Instruction.AddIntLit16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// d1: rsub-int (reverse subtract)
	@Override
	public void visit(ast.stm.Instruction.RsubInt inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// d2: mul-int/lit16
	@Override
	public void visit(ast.stm.Instruction.MulIntLit16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// d3: div-int/lit16
	@Override
	public void visit(ast.stm.Instruction.DivIntLit16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// d4: rem-int/lit16
	@Override
	public void visit(ast.stm.Instruction.RemIntLit16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// d5: and-int/lit16
	@Override
	public void visit(ast.stm.Instruction.AndIntLit16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// d6: or-int/lit16
	@Override
	public void visit(ast.stm.Instruction.OrIntLit16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// d7: xor-int/lit16
	@Override
	public void visit(ast.stm.Instruction.XorIntLit16 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	//
	// d8..e2 22b binop/lit8 vAA, vBB, #+CC
	// d8: add-int/lit8
	@Override
	public void visit(ast.stm.Instruction.AddIntLit8 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// d9: rsub-int/lit8
	@Override
	public void visit(ast.stm.Instruction.RsubIntLit8 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// da: mul-int/lit8
	@Override
	public void visit(ast.stm.Instruction.MulIntLit8 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// db: div-int/lit8
	@Override
	public void visit(ast.stm.Instruction.DivIntLit8 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// dc: rem-int/lit8
	@Override
	public void visit(ast.stm.Instruction.RemIntLit8 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// dd: and-int/lit8
	@Override
	public void visit(ast.stm.Instruction.AndIntLit8 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// de: or-int/lit8
	@Override
	public void visit(ast.stm.Instruction.OrIntLit8 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// df: xor-int/lit8
	@Override
	public void visit(ast.stm.Instruction.XorIntLit8 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// e0: shl-int/lit8
	@Override
	public void visit(ast.stm.Instruction.ShlIntLit8 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// e1: shr-int/lit8
	@Override
	public void visit(ast.stm.Instruction.ShrIntLit8 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	// e2: ushr-int/lit8
	@Override
	public void visit(ast.stm.Instruction.UshrIntLit8 inst) {
		instMap.put(inst.op, instMap.get(inst.op) + 1);
	}

	@Override
	public void visit(ast.stm.Instruction instruction) {

	}

	@Override
	public void visit(ast.stm.Instruction.ArrayDataDirective inst) {

	}

	@Override
	public void visit(ast.stm.Instruction.PackedSwitchDirective inst) {

	}

	@Override
	public void visit(ast.stm.Instruction.SparseSwitchDirective inst) {

	}
}