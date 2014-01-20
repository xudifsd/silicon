package sim;

public class SimplifyVisitor implements ast.Visitor {
	public sim.classs.Class simplifiedClass;

	// program
	@Override
	public void visit(ast.program.Program p) {
		// nop
	}

	@Override
	public void visit(ast.classs.Class clazz) {
	}

	@Override
	public void visit(ast.method.Method method) {
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
	public void visit(ast.annotation.Annotation annotation) {
	}

	@Override
	public void visit(ast.annotation.Annotation.SubAnnotation subAnnotation) {
	}

	@Override
	public void visit(ast.annotation.Annotation.ElementLiteral elementLiteral) {
	}

	// ////////////////////////////////////////////////////////
	// 00 10x nop
	@Override
	public void visit(ast.stm.Instruction.Nop inst) {
	}

	// 01 12x move vA, vB
	@Override
	public void visit(ast.stm.Instruction.Move inst) {
	}

	// 02 22x move/from16 vAA, vBBBB
	@Override
	public void visit(ast.stm.Instruction.MoveFrom16 inst) {
	}

	// 03 32x move/16 vAAAA, vBBBB ------
	@Override
	public void visit(ast.stm.Instruction.Move16 inst) {
	}

	// 04 12x move-wide vA, vB
	@Override
	public void visit(ast.stm.Instruction.MoveWide inst) {
	}

	// 05 22x move-wide/from16 vAA, vBBBB
	@Override
	public void visit(ast.stm.Instruction.MoveWideFrom16 inst) {
	}

	// 06 32x move-wide/16 vAAAA, vBBBB -----
	@Override
	public void visit(ast.stm.Instruction.MoveWide16 inst) {
	}

	// 07 12x move-object vA, vB
	@Override
	public void visit(ast.stm.Instruction.MoveObject inst) {
	}

	// 08 22x move-object/from16 vAA, vBBBB --
	@Override
	public void visit(ast.stm.Instruction.MoveOjbectFrom16 inst) {
	}

	// 09 32x move-object/16 vAAAA, vBBBB
	@Override
	public void visit(ast.stm.Instruction.MoveObject16 inst) {
	}

	// 0a 11x move-result vAA
	@Override
	public void visit(ast.stm.Instruction.MoveResult inst) {
	}

	// 0b 11x move-result-wide vAA
	@Override
	public void visit(ast.stm.Instruction.MoveResultWide inst) {
	}

	// 0c 11x move-result-object vAA
	@Override
	public void visit(ast.stm.Instruction.MoveResultObject inst) {
	}

	// 0d 11x move-exception vAA
	@Override
	public void visit(ast.stm.Instruction.MoveException inst) {
	}

	// 0e 10x return-void
	@Override
	public void visit(ast.stm.Instruction.ReturnVoid inst) {
	}

	// 0f 11x return vAA
	@Override
	public void visit(ast.stm.Instruction.Return inst) {
	}

	// 10 11x return-wide vAA
	@Override
	public void visit(ast.stm.Instruction.ReturnWide inst) {
	}

	// 11 11x return-object vAA
	@Override
	public void visit(ast.stm.Instruction.ReturnObject inst) {
	}

	// 12 11n const/4 vA, #+B
	@Override
	public void visit(ast.stm.Instruction.Const4 inst) {
	}

	// 13 21s const/16 vAA, #+BBBB
	@Override
	public void visit(ast.stm.Instruction.Const16 inst) {
	}

	// 14 31i const vAA, #+BBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.Const inst) {
	}

	// 15 21h const/high16 vAA, #+BBBB0000
	@Override
	public void visit(ast.stm.Instruction.ConstHigh16 inst) {
	}

	// 16 21s const-wide/16 vAA, #+BBBB
	@Override
	public void visit(ast.stm.Instruction.ConstWide16 inst) {
	}

	// 17 31i const-wide/32 vAA, #+BBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.ConstWide32 inst) {
	}

	// 18 51l const-wide vAA, #+BBBBBBBBBBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.ConstWide inst) {
	}

	// 19 21h const-wide/high16 vAA, #+BBBB000000000000
	@Override
	public void visit(ast.stm.Instruction.ConstWideHigh16 inst) {
	}

	// 1a 21c const-string vAA, string@BBBB
	@Override
	public void visit(ast.stm.Instruction.ConstString inst) {
	}

	// 1b 31c const-string/jumbo vAA, string@BBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.ConstStringJumbo inst) {
	}

	// 1c 21c const-class vAA, type@BBBB
	@Override
	public void visit(ast.stm.Instruction.ConstClass inst) {
	}

	// 1d 11x monitor-enter vAA
	@Override
	public void visit(ast.stm.Instruction.MonitorEnter inst) {
	}

	// 1e 11x monitor-exit vAA
	@Override
	public void visit(ast.stm.Instruction.MonitorExit inst) {
	}

	// 1f 21c check-cast vAA, type@BBBB
	@Override
	public void visit(ast.stm.Instruction.CheckCast inst) {
	}

	// 20 22c instance-of vA, vB, type@CCCC
	@Override
	public void visit(ast.stm.Instruction.InstanceOf inst) {
	}

	// 21 12x array-length vA, vB
	@Override
	public void visit(ast.stm.Instruction.arrayLength inst) {
	}

	// 22 21c new-instance vAA, type@BBBB
	@Override
	public void visit(ast.stm.Instruction.NewInstance inst) {
	}

	// 23 22c new-array vA, vB, type@CCCC
	// new-array v0,p1 [Landro......
	@Override
	public void visit(ast.stm.Instruction.NewArray inst) {
	}

	// 24 35c filled-new-array {vC, vD, vE, vF, vG}, type@BBBB
	// filled-new-array {v7, v9}, [I
	@Override
	public void visit(ast.stm.Instruction.FilledNewArray inst) {
	}

	// 25 3rc filled-new-array/range {vCCCC .. vNNNN}, type@BBBB ----
	@Override
	public void visit(ast.stm.Instruction.FilledNewArrayRange inst) {
	}

	// 26 31t fill-array-data vAA, +BBBBBBBB (with supplemental data as
	// specified below in "fill-array-data-payloadFormat") ------
	@Override
	public void visit(ast.stm.Instruction.FillArrayData inst) {
	}

	// 27 11x throw vAA
	@Override
	public void visit(ast.stm.Instruction.Throw inst) {
	}

	// 28 10t goto +AA ??
	// !!!!! goto :goto_0
	@Override
	public void visit(ast.stm.Instruction.Goto inst) {
	}

	// 29 20t goto/16 +AAAA ??
	@Override
	public void visit(ast.stm.Instruction.Goto16 inst) {
	}

	// 2a 30t goto/32 +AAAAAAAA ??
	@Override
	public void visit(ast.stm.Instruction.Goto32 inst) {
	}

	// 2b 31t packed-switch vAA, +BBBBBBBB (with supplemental data as specified
	// below in "packed-switch- ??????????
	@Override
	public void visit(ast.stm.Instruction.PackedSwitch inst) {
	}

	// 2c 31t sparse-switch vAA, +BBBBBBBB (with supplemental data as specified
	// below in "sparse-switch-payloadFormat") ??????????
	@Override
	public void visit(ast.stm.Instruction.SparseSwitch inst) {
	}

	//
	// 2d..31 23x cmpkind vAA, vBB, vCC
	// 2d: cmpl-float (lt bias)
	@Override
	public void visit(ast.stm.Instruction.CmplFloat inst) {
	}

	// 2e: cmpg-float (gt bias)
	@Override
	public void visit(ast.stm.Instruction.CmpgFloat inst) {
	}

	// 2f: cmpl-double (lt bias)
	@Override
	public void visit(ast.stm.Instruction.CmplDouble inst) {
	}

	// 30: cmpg-double (gt bias)
	@Override
	public void visit(ast.stm.Instruction.Cmpgdouble inst) {
	}

	// 31: cmp-long
	@Override
	public void visit(ast.stm.Instruction.CmpLong inst) {
	}

	//
	// 32..37 22t if-test vA, vB, +CCCC
	// !!!!!!! if-eq v1, v0, :cond_1
	// 32: if-eq
	@Override
	public void visit(ast.stm.Instruction.IfEq inst) {
	}

	// 33: if-ne
	@Override
	public void visit(ast.stm.Instruction.IfNe inst) {
	}

	// 34: if-lt
	@Override
	public void visit(ast.stm.Instruction.IfLt inst) {
	}

	// 35: if-ge
	@Override
	public void visit(ast.stm.Instruction.IfGe inst) {
	}

	// 36: if-gt
	@Override
	public void visit(ast.stm.Instruction.IfGt inst) {
	}

	// 37: if-le
	@Override
	public void visit(ast.stm.Instruction.IfLe inst) {
	}

	//
	//
	// 38..3d 21t if-testz vAA, +BBBB
	// !!!! if-eqz v0, :cond_0
	// 38: if-eqz
	@Override
	public void visit(ast.stm.Instruction.IfEqz inst) {
	}

	// 39: if-nez
	@Override
	public void visit(ast.stm.Instruction.IfNez inst) {
	}

	// 3a: if-ltz
	@Override
	public void visit(ast.stm.Instruction.IfLtz inst) {
	}

	// 3b: if-gez
	@Override
	public void visit(ast.stm.Instruction.IfGez inst) {
	}

	// 3c: if-gtz
	@Override
	public void visit(ast.stm.Instruction.IfGtz inst) {
	}

	// 3d: if-lez
	@Override
	public void visit(ast.stm.Instruction.IfLez inst) {
	}

	//
	//
	// 3e..43 10x (unused)
	// 44..51 23x arrayop vAA, vBB, vCC

	// 44: aget
	@Override
	public void visit(ast.stm.Instruction.Aget inst) {
	}

	// 45: aget-wide
	@Override
	public void visit(ast.stm.Instruction.AgetWide inst) {
	}

	// 46: aget-object
	@Override
	public void visit(ast.stm.Instruction.AgetObject inst) {
	}

	// 47: aget-boolean
	@Override
	public void visit(ast.stm.Instruction.AgetBoolean inst) {
	}

	// 48: aget-byte
	@Override
	public void visit(ast.stm.Instruction.AgetByte inst) {
	}

	// 49: aget-char
	@Override
	public void visit(ast.stm.Instruction.AgetChar inst) {
	}

	// 4a: aget-short\
	@Override
	public void visit(ast.stm.Instruction.AgetShort inst) {
	}

	// 4b: aput
	@Override
	public void visit(ast.stm.Instruction.Aput inst) {
	}

	// 4c: aput-wide
	@Override
	public void visit(ast.stm.Instruction.AputWide inst) {
	}

	// 4d: aput-object
	@Override
	public void visit(ast.stm.Instruction.AputObject inst) {
	}

	// 4e: aput-boolean
	@Override
	public void visit(ast.stm.Instruction.AputBoolean inst) {
	}

	// 4f: aput-byte
	@Override
	public void visit(ast.stm.Instruction.AputByte inst) {
	}

	// 50: aput-char
	@Override
	public void visit(ast.stm.Instruction.AputChar inst) {
	}

	// 51: aput-short
	@Override
	public void visit(ast.stm.Instruction.AputShort inst) {
	}

	//
	// 52..5f 22c iinstanceop vA, vB, field@CCCC
	// 52: iget
	@Override
	public void visit(ast.stm.Instruction.Iget inst) {
	}

	// 53: iget-wide
	@Override
	public void visit(ast.stm.Instruction.IgetWide inst) {
	}

	// 54: iget-object
	@Override
	public void visit(ast.stm.Instruction.IgetOjbect inst) {
	}

	// 55: iget-boolean
	@Override
	public void visit(ast.stm.Instruction.IgetBoolean inst) {
	}

	// 56: iget-byte
	@Override
	public void visit(ast.stm.Instruction.IgetByte inst) {
	}

	// 57: iget-char
	@Override
	public void visit(ast.stm.Instruction.IgetChar inst) {
	}

	// 58: iget-short
	@Override
	public void visit(ast.stm.Instruction.IgetShort inst) {
	}

	// 59: iput
	@Override
	public void visit(ast.stm.Instruction.Iput inst) {
	}

	// 5a: iput-wide
	@Override
	public void visit(ast.stm.Instruction.IputWide inst) {
	}

	// 5b: iput-object
	@Override
	public void visit(ast.stm.Instruction.IputObject inst) {
	}

	// 5c: iput-boolean
	@Override
	public void visit(ast.stm.Instruction.IputBoolean inst) {
	}

	// 5d: iput-byte
	@Override
	public void visit(ast.stm.Instruction.IputByte inst) {
	}

	// 5e: iput-char
	@Override
	public void visit(ast.stm.Instruction.IputChar inst) {
	}

	// 5f: iput-short
	@Override
	public void visit(ast.stm.Instruction.IputShort inst) {
	}

	//
	// 60..6d 21c sstaticop vAA, field@BBBB
	// 60: sget
	@Override
	public void visit(ast.stm.Instruction.Sget inst) {
	}

	// 61: sget-wide
	@Override
	public void visit(ast.stm.Instruction.SgetWide inst) {
	}

	// 62: sget-object
	@Override
	public void visit(ast.stm.Instruction.SgetObject inst) {
	}

	// 63: sget-boolean
	@Override
	public void visit(ast.stm.Instruction.SgetBoolean inst) {
	}

	// 64: sget-byte
	@Override
	public void visit(ast.stm.Instruction.SgetByte inst) {
	}

	// 65: sget-char
	@Override
	public void visit(ast.stm.Instruction.SgetChar inst) {
	}

	// 66: sget-short
	@Override
	public void visit(ast.stm.Instruction.SgetShort inst) {
	}

	// 67: sput
	@Override
	public void visit(ast.stm.Instruction.Sput inst) {
	}

	// 68: sput-wide
	@Override
	public void visit(ast.stm.Instruction.SputWide inst) {
	}

	// 69: sput-object
	@Override
	public void visit(ast.stm.Instruction.SputObject inst) {
	}

	// 6a: sput-boolean
	@Override
	public void visit(ast.stm.Instruction.SputBoolean inst) {
	}

	// 6b: sput-byte
	@Override
	public void visit(ast.stm.Instruction.SputByte inst) {
	}

	// 6c: sput-char
	@Override
	public void visit(ast.stm.Instruction.SputChar inst) {
	}

	// 6d: sput-short
	@Override
	public void visit(ast.stm.Instruction.SputShort inst) {
	}

	// 6e..72 35c invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB
	// 6e: invoke-virtual
	@Override
	public void visit(ast.stm.Instruction.InvokeVirtual inst) {
	}

	// 6f: invoke-super
	@Override
	public void visit(ast.stm.Instruction.InvokeSuper inst) {
	}

	// 70: invoke-direct
	@Override
	public void visit(ast.stm.Instruction.InvokeDirect inst) {
	}

	// 71: invoke-static
	@Override
	public void visit(ast.stm.Instruction.InvokeStatic inst) {
	}

	// 72: invoke-interface
	@Override
	public void visit(ast.stm.Instruction.InvokeInterface inst) {
	}

	//
	//
	// 73 10x (unused)
	// 74..78 3rc invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB
	// 74: invoke-virtual/range
	@Override
	public void visit(ast.stm.Instruction.InvokeVirtualRange inst) {
	}

	// 75: invoke-super/range
	@Override
	public void visit(ast.stm.Instruction.InvokeSuperRange inst) {
	}

	// 76: invoke-direct/range
	@Override
	public void visit(ast.stm.Instruction.InvokeDirectRange inst) {
	}

	// 77: invoke-static/range
	@Override
	public void visit(ast.stm.Instruction.InvokeStaticRange inst) {
	}

	// 78: invoke-interface/range
	@Override
	public void visit(ast.stm.Instruction.InvokeInterfaceRange inst) {
	}

	//
	// 79..7a 10x (unused)
	// 7b..8f 12x unop vA, vB
	// 7b: neg-int
	@Override
	public void visit(ast.stm.Instruction.NegInt inst) {
	}

	// 7c: not-int
	@Override
	public void visit(ast.stm.Instruction.NotInt inst) {
	}

	// 7d: neg-long
	@Override
	public void visit(ast.stm.Instruction.NegLong inst) {
	}

	// 7e: not-long
	@Override
	public void visit(ast.stm.Instruction.NotLong inst) {
	}

	// 7f: neg-float
	@Override
	public void visit(ast.stm.Instruction.NegFloat inst) {
	}

	// 80: neg-double
	@Override
	public void visit(ast.stm.Instruction.NegDouble inst) {
	}

	// 81: int-to-long
	@Override
	public void visit(ast.stm.Instruction.IntToLong inst) {
	}

	// 82: int-to-float
	@Override
	public void visit(ast.stm.Instruction.IntToFloat inst) {
	}

	// 83: int-to-double
	@Override
	public void visit(ast.stm.Instruction.IntToDouble inst) {
	}

	// 84: long-to-int
	@Override
	public void visit(ast.stm.Instruction.LongToInt inst) {
	}

	// 85: long-to-float
	@Override
	public void visit(ast.stm.Instruction.LongToFloat inst) {
	}

	// 86: long-to-double
	@Override
	public void visit(ast.stm.Instruction.LongToDouble inst) {
	}

	// 87: float-to-int
	@Override
	public void visit(ast.stm.Instruction.FloatToInt inst) {
	}

	// 88: float-to-long
	@Override
	public void visit(ast.stm.Instruction.FloatToLong inst) {
	}

	// 89: float-to-double
	@Override
	public void visit(ast.stm.Instruction.FloatToDouble inst) {
	}

	// 8a: double-to-int
	@Override
	public void visit(ast.stm.Instruction.DoubleToInt inst) {
	}

	// 8b: double-to-long
	@Override
	public void visit(ast.stm.Instruction.DoubleToLong inst) {
	}

	// 8c: double-to-float
	@Override
	public void visit(ast.stm.Instruction.DoubleToFloat inst) {
	}

	// 8d: int-to-byte
	@Override
	public void visit(ast.stm.Instruction.IntToByte inst) {
	}

	// 8e: int-to-char
	@Override
	public void visit(ast.stm.Instruction.IntToChar inst) {
	}

	// 8f: int-to-short
	@Override
	public void visit(ast.stm.Instruction.IntToShort inst) {
	}

	//
	// 90..af 23x binop vAA, vBB, vCC
	// 90: add-int
	@Override
	public void visit(ast.stm.Instruction.AddInt inst) {
	}

	// 91: sub-int public void visit(ast.stm.Instruction.AddInt inst)
	@Override
	public void visit(ast.stm.Instruction.SubInt inst) {
	}

	// 92: mul-int
	@Override
	public void visit(ast.stm.Instruction.MulInt inst) {
	}

	// 93: div-int
	@Override
	public void visit(ast.stm.Instruction.DivInt inst) {
	}

	// 94: rem-int
	@Override
	public void visit(ast.stm.Instruction.RemInt inst) {
	}

	// 95: and-int
	@Override
	public void visit(ast.stm.Instruction.AndInt inst) {
	}

	// 96: or-int
	@Override
	public void visit(ast.stm.Instruction.OrInt inst) {
	}

	// 97: xor-int
	@Override
	public void visit(ast.stm.Instruction.XorInt inst) {
	}

	// 98: shl-int
	@Override
	public void visit(ast.stm.Instruction.ShlInt inst) {
	}

	// 99: shr-int
	@Override
	public void visit(ast.stm.Instruction.ShrInt inst) {
	}

	// 9a: ushr-int
	@Override
	public void visit(ast.stm.Instruction.UshrInt inst) {
	}

	// 9b: add-long
	@Override
	public void visit(ast.stm.Instruction.AddLong inst) {
	}

	// 9c: sub-long
	@Override
	public void visit(ast.stm.Instruction.SubLong inst) {
	}

	// 9d: mul-long
	@Override
	public void visit(ast.stm.Instruction.MulLong inst) {
	}

	// 9e: div-long
	@Override
	public void visit(ast.stm.Instruction.DivLong inst) {
	}

	// 9f: rem-long
	@Override
	public void visit(ast.stm.Instruction.RemLong inst) {
	}

	// a0: and-long
	@Override
	public void visit(ast.stm.Instruction.AndLong inst) {
	}

	// a1: or-long
	@Override
	public void visit(ast.stm.Instruction.OrLong inst) {
	}

	// a2: xor-long
	@Override
	public void visit(ast.stm.Instruction.XorLong inst) {
	}

	// a3: shl-long
	@Override
	public void visit(ast.stm.Instruction.ShlLong inst) {
	}

	// a4: shr-long
	@Override
	public void visit(ast.stm.Instruction.ShrLong inst) {
	}

	// a5: ushr-long
	@Override
	public void visit(ast.stm.Instruction.UshrLong inst) {
	}

	// a6: add-float
	@Override
	public void visit(ast.stm.Instruction.AddFloat inst) {
	}

	// a7: sub-float
	@Override
	public void visit(ast.stm.Instruction.SubFloat inst) {
	}

	// a8: mul-float
	@Override
	public void visit(ast.stm.Instruction.MulFloat inst) {
	}

	// a9: div-float
	@Override
	public void visit(ast.stm.Instruction.DivFloat inst) {
	}

	// aa: rem-float
	@Override
	public void visit(ast.stm.Instruction.RemFloat inst) {
	}

	// ab: add-double
	@Override
	public void visit(ast.stm.Instruction.AddDouble inst) {
	}

	// ac: sub-double
	@Override
	public void visit(ast.stm.Instruction.SubDouble inst) {
	}

	// ad: mul-double
	@Override
	public void visit(ast.stm.Instruction.MulDouble inst) {
	}

	// ae: div-double
	@Override
	public void visit(ast.stm.Instruction.DivDouble inst) {
	}

	// af: rem-double
	@Override
	public void visit(ast.stm.Instruction.RemDouble inst) {
	}

	//
	// b0..cf 12x binop/2addr vA, vB

	// b0: add-int/2addr
	@Override
	public void visit(ast.stm.Instruction.AddInt2Addr inst) {
	}

	// b1: sub-int/2addr
	@Override
	public void visit(ast.stm.Instruction.SubInt2Addr inst) {
	}

	// b2: mul-int/2addr
	@Override
	public void visit(ast.stm.Instruction.MulInt2Addr inst) {
	}

	// b3: div-int/2addr
	@Override
	public void visit(ast.stm.Instruction.DivInt2Addr inst) {
	}

	// b4: rem-int/2addr
	@Override
	public void visit(ast.stm.Instruction.RemInt2Addr inst) {
	}

	// b5: and-int/2addr
	@Override
	public void visit(ast.stm.Instruction.AndInt2Addr inst) {
	}

	// b6: or-int/2addr
	@Override
	public void visit(ast.stm.Instruction.OrInt2Addr inst) {
	}

	// b7: xor-int/2addr
	@Override
	public void visit(ast.stm.Instruction.XorInt2Addr inst) {
	}

	// b8: shl-int/2addr
	@Override
	public void visit(ast.stm.Instruction.ShlInt2Addr inst) {
	}

	// b9: shr-int/2addr
	@Override
	public void visit(ast.stm.Instruction.ShrInt2Addr inst) {
	}

	// ba: ushr-int/2addr
	@Override
	public void visit(ast.stm.Instruction.UshrInt2Addr inst) {
	}

	// bb: add-long/2addr
	@Override
	public void visit(ast.stm.Instruction.AddLong2Addr inst) {
	}

	// bc: sub-long/2addr
	@Override
	public void visit(ast.stm.Instruction.SubLong2Addr inst) {
	}

	// bd: mul-long/2addr
	@Override
	public void visit(ast.stm.Instruction.MulLong2Addr inst) {
	}

	// be: div-long/2addr
	@Override
	public void visit(ast.stm.Instruction.DivLong2Addr inst) {
	}

	// bf: rem-long/2addr
	@Override
	public void visit(ast.stm.Instruction.RemLong2Addr inst) {
	}

	// c0: and-long/2addr
	@Override
	public void visit(ast.stm.Instruction.AndLong2Addr inst) {
	}

	// c1: or-long/2addr
	@Override
	public void visit(ast.stm.Instruction.OrLong2Addr inst) {
	}

	// c2: xor-long/2addr
	@Override
	public void visit(ast.stm.Instruction.XorLong2Addr inst) {
	}

	// c3: shl-long/2addr
	@Override
	public void visit(ast.stm.Instruction.ShlLong2Addr inst) {
	}

	// c4: shr-long/2addr
	@Override
	public void visit(ast.stm.Instruction.ShrLong2Addr inst) {
	}

	// c5: ushr-long/2addr
	@Override
	public void visit(ast.stm.Instruction.UshrLong2Addr inst) {
	}

	// c6: add-float/2addr
	@Override
	public void visit(ast.stm.Instruction.AddFloat2Addr inst) {
	}

	// c7: sub-float/2addr
	@Override
	public void visit(ast.stm.Instruction.SubFloat2Addr inst) {
	}

	// c8: mul-float/2addr
	@Override
	public void visit(ast.stm.Instruction.MulFloat2Addr inst) {
	}

	// c9: div-float/2addr
	@Override
	public void visit(ast.stm.Instruction.DivFloat2Addr inst) {
	}

	// ca: rem-float/2addr
	@Override
	public void visit(ast.stm.Instruction.RemFloat2Addr inst) {
	}

	// cb: add-double/2addr
	@Override
	public void visit(ast.stm.Instruction.AddDouble2Addr inst) {
	}

	// cc: sub-double/2addr
	@Override
	public void visit(ast.stm.Instruction.SubDouble2Addr inst) {
	}

	// cd: mul-double/2addr
	@Override
	public void visit(ast.stm.Instruction.MulDouble2Addr inst) {
	}

	// ce: div-double/2addr
	@Override
	public void visit(ast.stm.Instruction.DivDouble2Addr inst) {
	}

	// cf: rem-double/2addr
	@Override
	public void visit(ast.stm.Instruction.RemDouble2Addr inst) {
	}

	//
	// d0..d7 22s binop/lit16 vA, vB, #+CCCC
	// d0: add-int/lit16
	@Override
	public void visit(ast.stm.Instruction.AddIntLit16 inst) {
	}

	// d1: rsub-int (reverse subtract)
	@Override
	public void visit(ast.stm.Instruction.RsubInt inst) {
	}

	// d2: mul-int/lit16
	@Override
	public void visit(ast.stm.Instruction.MulIntLit16 inst) {
	}

	// d3: div-int/lit16
	@Override
	public void visit(ast.stm.Instruction.DivIntLit16 inst) {
	}

	// d4: rem-int/lit16
	@Override
	public void visit(ast.stm.Instruction.RemIntLit16 inst) {
	}

	// d5: and-int/lit16
	@Override
	public void visit(ast.stm.Instruction.AndIntLit16 inst) {
	}

	// d6: or-int/lit16
	@Override
	public void visit(ast.stm.Instruction.OrIntLit16 inst) {
	}

	// d7: xor-int/lit16
	@Override
	public void visit(ast.stm.Instruction.XorIntLit16 inst) {
	}

	//
	// d8..e2 22b binop/lit8 vAA, vBB, #+CC
	// d8: add-int/lit8
	@Override
	public void visit(ast.stm.Instruction.AddIntLit8 inst) {
	}

	// d9: rsub-int/lit8
	@Override
	public void visit(ast.stm.Instruction.RsubIntLit8 inst) {
	}

	// da: mul-int/lit8
	@Override
	public void visit(ast.stm.Instruction.MulIntLit8 inst) {
	}

	// db: div-int/lit8
	@Override
	public void visit(ast.stm.Instruction.DivIntLit8 inst) {
	}

	// dc: rem-int/lit8
	@Override
	public void visit(ast.stm.Instruction.RemIntLit8 inst) {
	}

	// dd: and-int/lit8
	@Override
	public void visit(ast.stm.Instruction.AndIntLit8 inst) {
	}

	// de: or-int/lit8
	@Override
	public void visit(ast.stm.Instruction.OrIntLit8 inst) {
	}

	// df: xor-int/lit8
	@Override
	public void visit(ast.stm.Instruction.XorIntLit8 inst) {
	}

	// e0: shl-int/lit8
	@Override
	public void visit(ast.stm.Instruction.ShlIntLit8 inst) {
	}

	// e1: shr-int/lit8
	@Override
	public void visit(ast.stm.Instruction.ShrIntLit8 inst) {
	}

	// e2: ushr-int/lit8
	@Override
	public void visit(ast.stm.Instruction.UshrIntLit8 inst) {
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