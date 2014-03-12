package vm;

import java.util.HashMap;
import java.util.Map;

import util.MultiThreadUtils.TranslateWorker;

public class Source {
	public static Map<String,TranslateWorker> classMap;
	public static Map<String,Integer>instLen;

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
