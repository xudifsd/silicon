package statistics;

public class StatisticsVisitor implements ast.Visitor {

	public int nopInstNum = 0;
	public int moveInstNum = 0;
	public int moveFrom16InstNum = 0;
	public int move16InstNum = 0;
	public int moveWideInstNum = 0;
	public int moveWideFrom16InstNum = 0;
	public int moveWide16InstNum = 0;
	public int moveObjectInstNum = 0;
	public int moveOjbectFrom16InstNum = 0;
	public int moveObject16InstNum = 0;
	public int moveResultInstNum = 0;
	public int moveResultWideInstNum = 0;
	public int moveResultObjectInstNum = 0;
	public int moveExceptionInstNum = 0;
	public int returnVoidInstNum = 0;
	public int returnInstNum = 0;
	public int returnWideInstNum = 0;
	public int returnObjectInstNum = 0;
	public int const4InstNum = 0;
	public int const16InstNum = 0;
	public int constInstNum = 0;
	public int constHighInstNum = 0;
	public int constWide16InstNum = 0;
	public int constWide32InstNum = 0;
	public int constWideInstNum = 0;
	public int constWideHighInstNum = 0;
	public int constStringJumboInstNum = 0;
	public int constClassInstNum = 0;
	public int monitorEnterInstNum = 0;
	public int monitorExitInstNum = 0;
	public int checkCastInstNum = 0;
	public int instanceOfInstNum = 0;
	public int arrayLengthInstNum = 0;
	public int newInstanceInstNum = 0;
	public int newArrayInstNum = 0;
	public int filledNewArrayInstNum = 0;
	public int filledNewArrayRangeInstNum = 0;
	public int fillArrayDataInstNum = 0;
	public int throwInstNum = 0;
	public int gotoInstNum = 0;
	public int goto16InstNum = 0;
	public int goto32InstNum = 0;
	public int packedSwitchInstNum = 0;
	public int sparseSwitchInstNum = 0;
	public int cmplFloatInstNum = 0;
	public int cmpgFloatInstNum = 0;
	public int cmplDoubleInstNum = 0;
	public int cmpgDoubleInstNum = 0;
	public int cmpLongInstNum = 0;
	public int ifEqInstNum = 0;
	public int ifNeInstNum = 0;
	public int ifLtInstNum = 0;
	public int ifGeInstNum = 0;
	public int ifGtInstNum = 0;
	public int ifLeInstNum = 0;
	public int ifEqzInstNum = 0;
	public int ifNezInstNum = 0;
	public int ifLtzInstNum = 0;
	public int ifGezInstNum = 0;
	public int ifGtzInstNum = 0;
	public int ifLezInstNum = 0;
	public int agetInstNum = 0;
	public int agetWideInstNum = 0;
	public int agetObjectInstNum = 0;
	public int agetByteInstNum = 0;
	public int agetBooleanInstNum = 0;
	public int agetCharInstNum = 0;
	public int agetShortInstNum = 0;
	public int aputInstNum = 0;
	public int igetInstNum = 0;
	public int aputWideInstNum = 0;
	public int aputObjectInstNum = 0;
	public int aputBooleanInstNum = 0;
	public int aputByteInstNum = 0;
	public int aputCharInstNum = 0;
	public int aputShortInstNum = 0;
	public int igetWideInstNum = 0;
	public int igetObjectInstNum = 0;
	public int igetBooleanInstNum = 0;
	public int igetByteInstNum = 0;
	public int igetCharInstNum = 0;
	public int igetShortInstNum = 0;
	public int iputInstNum = 0;
	public int iputWideInstNum = 0;
	public int iputObjectInstNum = 0;
	public int iputBooleanInstNum = 0;
	public int iputByteInstNum = 0;
	public int iputCharInstNum = 0;
	public int iputShortInstNum = 0;
	public int sgetInstNum = 0;
	public int sgetWideInstNum = 0;
	public int sgetObjectInstNum = 0;
	public int sgetBooleanInstNum = 0;
	public int sgetByteInstNum = 0;
	public int sgetCharInstNum = 0;
	public int sgetShortInstNum = 0;
	public int sputInstNum = 0;
	public int sputWideInstNum = 0;
	public int sputObjectInstNum = 0;
	public int sputBooleanInstNum = 0;
	public int sputByteInstNum = 0;
	public int sputCharInstNum = 0;
	public int sputShortInstNum = 0;
	public int invokeVirtualInstNum = 0;
	public int invokeSuperInstNum = 0;
	public int invokeDirectInstNum = 0;
	public int invokeStaticInstNum = 0;
	public int invokeInterfaceInstNum = 0;
	public int invokeVirtualRangeInstNum = 0;
	public int invokeSuperRangeInstNum = 0;
	public int invokeDirectRangeInstNum = 0;
	public int invokeStaticRangeInstNum = 0;
	public int invokeInterfaceRangeInstNum = 0;
	public int negIntInstNum = 0;
	public int notIntInstNum = 0;
	public int negLongInstNum = 0;
	public int notLongInstNum = 0;
	public int negFloatInstNum = 0;
	public int negDoubleInstNum = 0;
	public int intToLongInstNum = 0;
	public int intToFloatInstNum = 0;
	public int intToDoubleInstNum = 0;
	public int longToIntInstNum = 0;
	public int longToFloatInstNum = 0;
	public int longToDoubleInstNum = 0;
	public int floatToIntInstNum = 0;
	public int floatToLongInstNum = 0;
	public int floatToDoubleInstNum = 0;
	public int doubleToIntInstNum = 0;
	public int doubleToLongInstNum = 0;
	public int doubleToFloatInstNum = 0;
	public int intToByteInstNum = 0;
	public int intToCharInstNum = 0;
	public int intToShortInstNum = 0;
	public int addIntInstNum = 0;
	public int subIntInstNum = 0;
	public int mulIntInstNum = 0;
	public int divIntInstNum = 0;
	public int remIntInstNum = 0;
	public int andIntInstNum = 0;
	public int orIntInstNum = 0;
	public int xorIntInstNum = 0;
	public int shlIntInstNum = 0;
	public int shrIntInstNum = 0;
	public int ushrIntInstNum = 0;
	public int addLongInstNum = 0;
	public int subLongInstNum = 0;
	public int divLongInstNum = 0;
	public int remLongInstNum = 0;
	public int andLongInstNum = 0;
	public int orLongInstNum = 0;
	public int xorLongInstNum = 0;
	public int shlLongInstNum = 0;
	public int shrLongInstNum = 0;
	public int ushrLongInstNum = 0;
	public int addFloatInstNum = 0;
	public int subFloatInstNum = 0;
	public int mulFloatInstNum = 0;
	public int divFloatInstNum = 0;
	public int remFloatInstNum = 0;
	public int mulLongInstNum = 0;
	public int addDoubleInstNum = 0;
	public int subDoubleInstNum = 0;
	public int mulDoubleInstNum = 0;
	public int divDoubleInstNum = 0;
	public int remDoubleInstNum = 0;
	public int addInt2AddrInstNum = 0;
	public int subInt2AddrInstNum = 0;
	public int mulInt2AddrInstNum = 0;
	public int divInt2AddrInstNum = 0;
	public int remInt2AddrInstNum = 0;
	public int andInt2AddrInstNum = 0;
	public int orInt2AddrInstNum = 0;
	public int xorInt2AddrInstNum = 0;
	public int shlInt2AddrInstNum = 0;
	public int shrInt2AddrInstNum = 0;
	public int ushrInt2AddrInstNum = 0;
	public int addLong2AddrInstNum = 0;
	public int subLong2AddrInstNum = 0;
	public int mulLong2AddrInstNum = 0;
	public int divLong2AddrInstNum = 0;
	public int remLong2AddrInstNum = 0;
	public int andLong2AddrInstNum = 0;
	public int orLong2AddrInstNum = 0;
	public int xorLong2AddrInstNum = 0;
	public int shlLong2AddrInstNum = 0;
	public int shrLong2AddrInstNum = 0;
	public int ushrLong2AddrInstNum = 0;
	public int addFloat2AddrInstNum = 0;
	public int subFoat2AddrInstNum = 0;
	public int mulFloat2AddrInstNum = 0;
	public int divFLoat2AddrInstNum = 0;
	public int remFloat2AddrInstNum = 0;
	public int addDouble2AddrInstNum = 0;
	public int subDouble2AddrInstNum = 0;
	public int divDouble2AddrInstNum = 0;
	public int mulDouble2AddrInstNum = 0;
	public int remDouble2AddrInstNum = 0;
	public int addIntLit16InstNum = 0;
	public int rsubIntInstNum = 0;
	public int mulIntLit16InstNum = 0;
	public int divIntLit16InstNum = 0;
	public int remIntLit16InstNum = 0;
	public int andIntLit16InstNum = 0;
	public int orIntLit16InstNum = 0;
	public int xorIntLit16InstNum = 0;
	public int addIntLit8InstNum = 0;
	public int rsubIntLit8InstNum = 0;
	public int mulIntLit8InstNum = 0;
	public int divIntLit8InstNum = 0;
	public int remIntLit8InstNum = 0;
	public int andIntLit8InstNum = 0;
	public int orIntLit8InstNum = 0;
	public int xorIntLit8InstNum = 0;
	public int shlIntLit8InstNum = 0;
	public int shrIntLit8InstNum = 0;
	public int ushrIntLit8InstNum = 0;
	public int arrayDataDirectiveInstNum = 0;
	public int packedSwitchDirectiveInstNum = 0;
	public int sparseSwitchDirectiveInstNum = 0;

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
		nopInstNum++;
	}

	// 01 12x move vA, vB
	@Override
	public void visit(ast.stm.Instruction.Move inst) {
		moveInstNum++;
	}

	// 02 22x move/from16 vAA, vBBBB
	@Override
	public void visit(ast.stm.Instruction.MoveFrom16 inst) {
		moveFrom16InstNum++;
	}

	// 03 32x move/16 vAAAA, vBBBB ------
	@Override
	public void visit(ast.stm.Instruction.Move16 inst) {
		move16InstNum++;
	}

	// 04 12x move-wide vA, vB
	@Override
	public void visit(ast.stm.Instruction.MoveWide inst) {
		moveWideInstNum++;

	}

	// 05 22x move-wide/from16 vAA, vBBBB
	@Override
	public void visit(ast.stm.Instruction.MoveWideFrom16 inst) {
		moveWideFrom16InstNum++;
	}

	// 06 32x move-wide/16 vAAAA, vBBBB -----
	@Override
	public void visit(ast.stm.Instruction.MoveWide16 inst) {
		moveWide16InstNum++;
	}

	// 07 12x move-object vA, vB
	@Override
	public void visit(ast.stm.Instruction.MoveObject inst) {
		moveObjectInstNum++;
	}

	// 08 22x move-object/from16 vAA, vBBBB --
	@Override
	public void visit(ast.stm.Instruction.MoveOjbectFrom16 inst) {
		moveOjbectFrom16InstNum++;
	}

	// 09 32x move-object/16 vAAAA, vBBBB
	@Override
	public void visit(ast.stm.Instruction.MoveObject16 inst) {
		moveObject16InstNum++;
	}

	// 0a 11x move-result vAA
	@Override
	public void visit(ast.stm.Instruction.MoveResult inst) {
		moveResultInstNum++;
	}

	// 0b 11x move-result-wide vAA
	@Override
	public void visit(ast.stm.Instruction.MoveResultWide inst) {
		moveResultWideInstNum++;
	}

	// 0c 11x move-result-object vAA
	@Override
	public void visit(ast.stm.Instruction.MoveResultObject inst) {
		moveResultObjectInstNum++;
	}

	// 0d 11x move-exception vAA
	@Override
	public void visit(ast.stm.Instruction.MoveException inst) {
		moveExceptionInstNum++;
	}

	// 0e 10x return-void
	@Override
	public void visit(ast.stm.Instruction.ReturnVoid inst) {
		returnVoidInstNum++;
	}

	// 0f 11x return vAA
	@Override
	public void visit(ast.stm.Instruction.Return inst) {
		returnInstNum++;
	}

	// 10 11x return-wide vAA
	@Override
	public void visit(ast.stm.Instruction.ReturnWide inst) {
		returnWideInstNum++;
	}

	// 11 11x return-object vAA
	@Override
	public void visit(ast.stm.Instruction.ReturnObject inst) {
		returnObjectInstNum++;
	}

	// 12 11n const/4 vA, #+B
	@Override
	public void visit(ast.stm.Instruction.Const4 inst) {
		const4InstNum++;
	}

	// 13 21s const/16 vAA, #+BBBB
	@Override
	public void visit(ast.stm.Instruction.Const16 inst) {
		const16InstNum++;
	}

	// 14 31i const vAA, #+BBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.Const inst) {
		constInstNum++;
	}

	// 15 21h const/high16 vAA, #+BBBB0000
	@Override
	public void visit(ast.stm.Instruction.ConstHigh16 inst) {
		constHighInstNum++;
	}

	// 16 21s const-wide/16 vAA, #+BBBB
	@Override
	public void visit(ast.stm.Instruction.ConstWide16 inst) {
		constWide16InstNum++;
	}

	// 17 31i const-wide/32 vAA, #+BBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.ConstWide32 inst) {
		constWide32InstNum++;
	}

	// 18 51l const-wide vAA, #+BBBBBBBBBBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.ConstWide inst) {
		constWideInstNum++;
	}

	// 19 21h const-wide/high16 vAA, #+BBBB000000000000
	// const-wide/high16 v2,0x4024 -- > const-wide v2,0x4024000000000000L
	@Override
	public void visit(ast.stm.Instruction.ConstWideHigh16 inst) {
		constWideHighInstNum++;
	}

	// 1a 21c const-string vAA, string@BBBB
	@Override
	public void visit(ast.stm.Instruction.ConstString inst) {
		constWideInstNum++;
	}

	// 1b 31c const-string/jumbo vAA, string@BBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.ConstStringJumbo inst) {
		constStringJumboInstNum++;
	}

	// 1c 21c const-class vAA, type@BBBB
	@Override
	public void visit(ast.stm.Instruction.ConstClass inst) {
		constClassInstNum++;
	}

	// 1d 11x monitor-enter vAA
	@Override
	public void visit(ast.stm.Instruction.MonitorEnter inst) {
		monitorEnterInstNum++;
	}

	// 1e 11x monitor-exit vAA
	@Override
	public void visit(ast.stm.Instruction.MonitorExit inst) {
		monitorExitInstNum++;
	}

	// 1f 21c check-cast vAA, type@BBBB
	@Override
	public void visit(ast.stm.Instruction.CheckCast inst) {
		checkCastInstNum++;
	}

	// 20 22c instance-of vA, vB, type@CCCC
	@Override
	public void visit(ast.stm.Instruction.InstanceOf inst) {
		instanceOfInstNum++;
	}

	// 21 12x array-length vA, vB
	@Override
	public void visit(ast.stm.Instruction.arrayLength inst) {
		arrayLengthInstNum++;
	}

	// 22 21c new-instance vAA, type@BBBB
	@Override
	public void visit(ast.stm.Instruction.NewInstance inst) {
		newInstanceInstNum++;
	}

	// 23 22c new-array vA, vB, type@CCCC
	// new-array v0,p1 [Landro......
	@Override
	public void visit(ast.stm.Instruction.NewArray inst) {
		newArrayInstNum++;
	}

	// 24 35c filled-new-array {vC, vD, vE, vF, vG}, type@BBBB
	// filled-new-array {v7, v9}, [I
	@Override
	public void visit(ast.stm.Instruction.FilledNewArray inst) {
		filledNewArrayInstNum++;
	}

	// 25 3rc filled-new-array/range {vCCCC .. vNNNN}, type@BBBB ----
	@Override
	public void visit(ast.stm.Instruction.FilledNewArrayRange inst) {
		filledNewArrayRangeInstNum++;
	}

	// 26 31t fill-array-data vAA, +BBBBBBBB (with supplemental data as
	// specified below in "fill-array-data-payloadFormat") ------
	@Override
	public void visit(ast.stm.Instruction.FillArrayData inst) {
		fillArrayDataInstNum++;
	}

	// 27 11x throw vAA
	@Override
	public void visit(ast.stm.Instruction.Throw inst) {
		throwInstNum++;
	}

	// 28 10t goto +AA ??
	// !!!!! goto :goto_0
	@Override
	public void visit(ast.stm.Instruction.Goto inst) {
		gotoInstNum++;
	}

	// 29 20t goto/16 +AAAA ??
	@Override
	public void visit(ast.stm.Instruction.Goto16 inst) {
		goto16InstNum++;
	}

	// 2a 30t goto/32 +AAAAAAAA ??
	@Override
	public void visit(ast.stm.Instruction.Goto32 inst) {
		goto32InstNum++;
	}

	// 2b 31t packed-switch vAA, +BBBBBBBB (with supplemental data as specified
	// below in "packed-switch- ??????????
	@Override
	public void visit(ast.stm.Instruction.PackedSwitch inst) {
		packedSwitchInstNum++;
	}

	// 2c 31t sparse-switch vAA, +BBBBBBBB (with supplemental data as specified
	// below in "sparse-switch-payloadFormat") ??????????
	@Override
	public void visit(ast.stm.Instruction.SparseSwitch inst) {
		sparseSwitchInstNum++;
	}

	//
	// 2d..31 23x cmpkind vAA, vBB, vCC
	// 2d: cmpl-float (lt bias)
	@Override
	public void visit(ast.stm.Instruction.CmplFloat inst) {
		cmplFloatInstNum++;
	}

	// 2e: cmpg-float (gt bias)
	@Override
	public void visit(ast.stm.Instruction.CmpgFloat inst) {
		cmpgFloatInstNum++;
	}

	// 2f: cmpl-double (lt bias)
	@Override
	public void visit(ast.stm.Instruction.CmplDouble inst) {
		cmplDoubleInstNum++;
	}

	// 30: cmpg-double (gt bias)
	@Override
	public void visit(ast.stm.Instruction.Cmpgdouble inst) {
		cmpgDoubleInstNum++;
	}

	// 31: cmp-long
	@Override
	public void visit(ast.stm.Instruction.CmpLong inst) {
		cmpLongInstNum++;
	}

	//
	// 32..37 22t if-test vA, vB, +CCCC
	// !!!!!!! if-eq v1, v0, :cond_1
	// 32: if-eq
	@Override
	public void visit(ast.stm.Instruction.IfEq inst) {
		ifEqInstNum++;
	}

	// 33: if-ne
	@Override
	public void visit(ast.stm.Instruction.IfNe inst) {
		ifNeInstNum++;
	}

	// 34: if-lt
	@Override
	public void visit(ast.stm.Instruction.IfLt inst) {
		ifLtInstNum++;
	}

	// 35: if-ge
	@Override
	public void visit(ast.stm.Instruction.IfGe inst) {
		ifGeInstNum++;
	}

	// 36: if-gt
	@Override
	public void visit(ast.stm.Instruction.IfGt inst) {
		ifGtInstNum++;
	}

	// 37: if-le
	@Override
	public void visit(ast.stm.Instruction.IfLe inst) {
		ifLeInstNum++;
	}

	//
	//
	// 38..3d 21t if-testz vAA, +BBBB
	// !!!! if-eqz v0, :cond_0
	// 38: if-eqz
	@Override
	public void visit(ast.stm.Instruction.IfEqz inst) {
		ifEqzInstNum++;
	}

	// 39: if-nez
	@Override
	public void visit(ast.stm.Instruction.IfNez inst) {
		ifNezInstNum++;
	}

	// 3a: if-ltz
	@Override
	public void visit(ast.stm.Instruction.IfLtz inst) {
		ifLtzInstNum++;
	}

	// 3b: if-gez
	@Override
	public void visit(ast.stm.Instruction.IfGez inst) {
		ifGezInstNum++;
	}

	// 3c: if-gtz
	@Override
	public void visit(ast.stm.Instruction.IfGtz inst) {
		ifGtzInstNum++;
	}

	// 3d: if-lez
	@Override
	public void visit(ast.stm.Instruction.IfLez inst) {
		ifLezInstNum++;
	}

	//
	//
	// 3e..43 10x (unused)
	// 44..51 23x arrayop vAA, vBB, vCC

	// 44: aget
	@Override
	public void visit(ast.stm.Instruction.Aget inst) {
		agetInstNum++;
	}

	// 45: aget-wide
	@Override
	public void visit(ast.stm.Instruction.AgetWide inst) {
		agetWideInstNum++;
	}

	// 46: aget-object
	@Override
	public void visit(ast.stm.Instruction.AgetObject inst) {
		agetObjectInstNum++;
	}

	// 47: aget-boolean
	@Override
	public void visit(ast.stm.Instruction.AgetBoolean inst) {
		agetBooleanInstNum++;
	}

	// 48: aget-byte
	@Override
	public void visit(ast.stm.Instruction.AgetByte inst) {
		agetByteInstNum++;
	}

	// 49: aget-char
	@Override
	public void visit(ast.stm.Instruction.AgetChar inst) {
		agetCharInstNum++;
	}

	// 4a: aget-short
	@Override
	public void visit(ast.stm.Instruction.AgetShort inst) {
		agetShortInstNum++;
	}

	// 4b: aput
	@Override
	public void visit(ast.stm.Instruction.Aput inst) {
		aputInstNum++;
	}

	// 4c: aput-wide
	@Override
	public void visit(ast.stm.Instruction.AputWide inst) {
		aputWideInstNum++;
	}

	// 4d: aput-object
	@Override
	public void visit(ast.stm.Instruction.AputObject inst) {
		aputObjectInstNum++;
	}

	// 4e: aput-boolean
	@Override
	public void visit(ast.stm.Instruction.AputBoolean inst) {
		aputBooleanInstNum++;
	}

	// 4f: aput-byte
	@Override
	public void visit(ast.stm.Instruction.AputByte inst) {
		aputByteInstNum++;
	}

	// 50: aput-char
	@Override
	public void visit(ast.stm.Instruction.AputChar inst) {
		aputCharInstNum++;
	}

	// 51: aput-short
	@Override
	public void visit(ast.stm.Instruction.AputShort inst) {
		aputShortInstNum++;
	}

	//
	// 52..5f 22c iinstanceop vA, vB, field@CCCC
	// 52: iget
	@Override
	public void visit(ast.stm.Instruction.Iget inst) {
		igetInstNum++;
	}

	// 53: iget-wide
	@Override
	public void visit(ast.stm.Instruction.IgetWide inst) {
		igetWideInstNum++;
	}

	// 54: iget-object
	@Override
	public void visit(ast.stm.Instruction.IgetOjbect inst) {
		igetObjectInstNum++;
	}

	// 55: iget-boolean
	@Override
	public void visit(ast.stm.Instruction.IgetBoolean inst) {
		igetBooleanInstNum++;
	}

	// 56: iget-byte
	@Override
	public void visit(ast.stm.Instruction.IgetByte inst) {
		igetByteInstNum++;
	}

	// 57: iget-char
	@Override
	public void visit(ast.stm.Instruction.IgetChar inst) {
		igetCharInstNum++;
	}

	// 58: iget-short
	@Override
	public void visit(ast.stm.Instruction.IgetShort inst) {
		igetShortInstNum++;
	}

	// 59: iput
	@Override
	public void visit(ast.stm.Instruction.Iput inst) {
		iputInstNum++;
	}

	// 5a: iput-wide
	@Override
	public void visit(ast.stm.Instruction.IputWide inst) {
		iputWideInstNum++;
	}

	// 5b: iput-object
	@Override
	public void visit(ast.stm.Instruction.IputObject inst) {
		iputObjectInstNum++;
	}

	// 5c: iput-boolean
	@Override
	public void visit(ast.stm.Instruction.IputBoolean inst) {
		iputBooleanInstNum++;
	}

	// 5d: iput-byte
	@Override
	public void visit(ast.stm.Instruction.IputByte inst) {
		iputByteInstNum++;
	}

	// 5e: iput-char
	@Override
	public void visit(ast.stm.Instruction.IputChar inst) {
		iputCharInstNum++;
	}

	// 5f: iput-short
	@Override
	public void visit(ast.stm.Instruction.IputShort inst) {
		iputShortInstNum++;
	}

	//
	// 60..6d 21c sstaticop vAA, field@BBBB
	// 60: sget
	@Override
	public void visit(ast.stm.Instruction.Sget inst) {
		sgetInstNum++;
	}

	// 61: sget-wide
	@Override
	public void visit(ast.stm.Instruction.SgetWide inst) {
		sgetWideInstNum++;
	}

	// 62: sget-object
	@Override
	public void visit(ast.stm.Instruction.SgetObject inst) {
		sgetObjectInstNum++;
	}

	// 63: sget-boolean
	@Override
	public void visit(ast.stm.Instruction.SgetBoolean inst) {
		sgetBooleanInstNum++;
	}

	// 64: sget-byte
	@Override
	public void visit(ast.stm.Instruction.SgetByte inst) {
		sgetByteInstNum++;
	}

	// 65: sget-char
	@Override
	public void visit(ast.stm.Instruction.SgetChar inst) {
		sgetCharInstNum++;
	}

	// 66: sget-short
	@Override
	public void visit(ast.stm.Instruction.SgetShort inst) {
		sgetShortInstNum++;
	}

	// 67: sput
	@Override
	public void visit(ast.stm.Instruction.Sput inst) {
		sputInstNum++;
	}

	// 68: sput-wide
	@Override
	public void visit(ast.stm.Instruction.SputWide inst) {
		sputWideInstNum++;
	}

	// 69: sput-object
	@Override
	public void visit(ast.stm.Instruction.SputObject inst) {
		sputObjectInstNum++;
	}

	// 6a: sput-boolean
	@Override
	public void visit(ast.stm.Instruction.SputBoolean inst) {
		sputBooleanInstNum++;
	}

	// 6b: sput-byte
	@Override
	public void visit(ast.stm.Instruction.SputByte inst) {
		sputByteInstNum++;
	}

	// 6c: sput-char
	@Override
	public void visit(ast.stm.Instruction.SputChar inst) {
		sputCharInstNum++;
	}

	// 6d: sput-short
	@Override
	public void visit(ast.stm.Instruction.SputShort inst) {
		sputShortInstNum++;
	}

	// 6e..72 35c invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB
	// 6e: invoke-virtual
	@Override
	public void visit(ast.stm.Instruction.InvokeVirtual inst) {
		invokeVirtualInstNum++;
	}

	// 6f: invoke-super
	@Override
	public void visit(ast.stm.Instruction.InvokeSuper inst) {
		invokeSuperInstNum++;
	}

	// 70: invoke-direct
	@Override
	public void visit(ast.stm.Instruction.InvokeDirect inst) {
		invokeDirectInstNum++;
	}

	// 71: invoke-static
	@Override
	public void visit(ast.stm.Instruction.InvokeStatic inst) {
		invokeStaticInstNum++;
	}

	// 72: invoke-interface
	@Override
	public void visit(ast.stm.Instruction.InvokeInterface inst) {
		invokeInterfaceInstNum++;
	}

	//
	//
	// 73 10x (unused)
	// 74..78 3rc invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB
	// 74: invoke-virtual/range
	@Override
	public void visit(ast.stm.Instruction.InvokeVirtualRange inst) {
		invokeVirtualRangeInstNum++;

	}

	// 75: invoke-super/range
	@Override
	public void visit(ast.stm.Instruction.InvokeSuperRange inst) {
		invokeSuperRangeInstNum++;
	}

	// 76: invoke-direct/range
	@Override
	public void visit(ast.stm.Instruction.InvokeDirectRange inst) {
		invokeDirectRangeInstNum++;
	}

	// 77: invoke-static/range
	@Override
	public void visit(ast.stm.Instruction.InvokeStaticRange inst) {
		invokeStaticRangeInstNum++;
	}

	// 78: invoke-interface/range
	@Override
	public void visit(ast.stm.Instruction.InvokeInterfaceRange inst) {
		invokeInterfaceRangeInstNum++;
	}

	//
	// 79..7a 10x (unused)
	// 7b..8f 12x unop vA, vB
	// 7b: neg-int
	@Override
	public void visit(ast.stm.Instruction.NegInt inst) {
		negIntInstNum++;
	}

	// 7c: not-int
	@Override
	public void visit(ast.stm.Instruction.NotInt inst) {
		notIntInstNum++;
	}

	// 7d: neg-long
	@Override
	public void visit(ast.stm.Instruction.NegLong inst) {
		negLongInstNum++;
	}

	// 7e: not-long
	@Override
	public void visit(ast.stm.Instruction.NotLong inst) {
		notLongInstNum++;
	}

	// 7f: neg-float
	@Override
	public void visit(ast.stm.Instruction.NegFloat inst) {
		negFloatInstNum++;
	}

	// 80: neg-double
	@Override
	public void visit(ast.stm.Instruction.NegDouble inst) {
		negDoubleInstNum++;
	}

	// 81: int-to-long
	@Override
	public void visit(ast.stm.Instruction.IntToLong inst) {
		intToLongInstNum++;
	}

	// 82: int-to-float
	@Override
	public void visit(ast.stm.Instruction.IntToFloat inst) {
		intToFloatInstNum++;
	}

	// 83: int-to-double
	@Override
	public void visit(ast.stm.Instruction.IntToDouble inst) {
		intToDoubleInstNum++;
	}

	// 84: long-to-int
	@Override
	public void visit(ast.stm.Instruction.LongToInt inst) {
		longToIntInstNum++;
	}

	// 85: long-to-float
	@Override
	public void visit(ast.stm.Instruction.LongToFloat inst) {
		longToFloatInstNum++;
	}

	// 86: long-to-double
	@Override
	public void visit(ast.stm.Instruction.LongToDouble inst) {
		longToDoubleInstNum++;
	}

	// 87: float-to-int
	@Override
	public void visit(ast.stm.Instruction.FloatToInt inst) {
		floatToIntInstNum++;
	}

	// 88: float-to-long
	@Override
	public void visit(ast.stm.Instruction.FloatToLong inst) {
		floatToLongInstNum++;
	}

	// 89: float-to-double
	@Override
	public void visit(ast.stm.Instruction.FloatToDouble inst) {
		floatToDoubleInstNum++;
	}

	// 8a: double-to-int
	@Override
	public void visit(ast.stm.Instruction.DoubleToInt inst) {
		doubleToIntInstNum++;
	}

	// 8b: double-to-long
	@Override
	public void visit(ast.stm.Instruction.DoubleToLong inst) {
		doubleToLongInstNum++;
	}

	// 8c: double-to-float
	@Override
	public void visit(ast.stm.Instruction.DoubleToFloat inst) {
		doubleToFloatInstNum++;
	}

	// 8d: int-to-byte
	@Override
	public void visit(ast.stm.Instruction.IntToByte inst) {
		intToByteInstNum++;
	}

	// 8e: int-to-char
	@Override
	public void visit(ast.stm.Instruction.IntToChar inst) {
		intToCharInstNum++;
	}

	// 8f: int-to-short
	@Override
	public void visit(ast.stm.Instruction.IntToShort inst) {
		intToShortInstNum++;
	}

	//
	// 90..af 23x binop vAA, vBB, vCC
	// 90: add-int
	@Override
	public void visit(ast.stm.Instruction.AddInt inst) {
		addIntInstNum++;
	}

	// 91: sub-int public void visit(ast.stm.Instruction.AddInt inst)
	@Override
	public void visit(ast.stm.Instruction.SubInt inst) {
		subIntInstNum++;
	}

	// 92: mul-int
	@Override
	public void visit(ast.stm.Instruction.MulInt inst) {
		mulIntInstNum++;
	}

	// 93: div-int
	@Override
	public void visit(ast.stm.Instruction.DivInt inst) {
		divIntInstNum++;
	}

	// 94: rem-int
	@Override
	public void visit(ast.stm.Instruction.RemInt inst) {
		remIntInstNum++;
	}

	// 95: and-int
	@Override
	public void visit(ast.stm.Instruction.AndInt inst) {
		andIntInstNum++;
	}

	// 96: or-int
	@Override
	public void visit(ast.stm.Instruction.OrInt inst) {
		orIntInstNum++;
	}

	// 97: xor-int
	@Override
	public void visit(ast.stm.Instruction.XorInt inst) {
		xorIntInstNum++;
	}

	// 98: shl-int
	@Override
	public void visit(ast.stm.Instruction.ShlInt inst) {
		shlIntInstNum++;
	}

	// 99: shr-int
	@Override
	public void visit(ast.stm.Instruction.ShrInt inst) {
		shrIntInstNum++;
	}

	// 9a: ushr-int
	@Override
	public void visit(ast.stm.Instruction.UshrInt inst) {
		ushrIntInstNum++;
	}

	// 9b: add-long
	@Override
	public void visit(ast.stm.Instruction.AddLong inst) {
		addLongInstNum++;
	}

	// 9c: sub-long
	@Override
	public void visit(ast.stm.Instruction.SubLong inst) {
		subLongInstNum++;
	}

	// 9d: mul-long
	@Override
	public void visit(ast.stm.Instruction.MulLong inst) {
		mulLongInstNum++;
	}

	// 9e: div-long
	@Override
	public void visit(ast.stm.Instruction.DivLong inst) {
		divLongInstNum++;
	}

	// 9f: rem-long
	@Override
	public void visit(ast.stm.Instruction.RemLong inst) {
		remLongInstNum++;
	}

	// a0: and-long
	@Override
	public void visit(ast.stm.Instruction.AndLong inst) {
		andLongInstNum++;
	}

	// a1: or-long
	@Override
	public void visit(ast.stm.Instruction.OrLong inst) {
		orLongInstNum++;
	}

	// a2: xor-long
	@Override
	public void visit(ast.stm.Instruction.XorLong inst) {
		xorLongInstNum++;
	}

	// a3: shl-long
	@Override
	public void visit(ast.stm.Instruction.ShlLong inst) {
		shlLongInstNum++;
	}

	// a4: shr-long
	@Override
	public void visit(ast.stm.Instruction.ShrLong inst) {
		shrLongInstNum++;
	}

	// a5: ushr-long
	@Override
	public void visit(ast.stm.Instruction.UshrLong inst) {
		ushrLongInstNum++;
	}

	// a6: add-float
	@Override
	public void visit(ast.stm.Instruction.AddFloat inst) {
		addFloatInstNum++;
	}

	// a7: sub-float
	@Override
	public void visit(ast.stm.Instruction.SubFloat inst) {
		subFloatInstNum++;
	}

	// a8: mul-float
	@Override
	public void visit(ast.stm.Instruction.MulFloat inst) {
		mulFloatInstNum++;
	}

	// a9: div-float
	@Override
	public void visit(ast.stm.Instruction.DivFloat inst) {
		divFloatInstNum++;
	}

	// aa: rem-float
	@Override
	public void visit(ast.stm.Instruction.RemFloat inst) {
		remFloatInstNum++;
	}

	// ab: add-double
	@Override
	public void visit(ast.stm.Instruction.AddDouble inst) {
		addDoubleInstNum++;
	}

	// ac: sub-double
	@Override
	public void visit(ast.stm.Instruction.SubDouble inst) {
		subDoubleInstNum++;
	}

	// ad: mul-double
	@Override
	public void visit(ast.stm.Instruction.MulDouble inst) {
		mulDoubleInstNum++;
	}

	// ae: div-double
	@Override
	public void visit(ast.stm.Instruction.DivDouble inst) {
		divDoubleInstNum++;
	}

	// af: rem-double
	@Override
	public void visit(ast.stm.Instruction.RemDouble inst) {
		remDoubleInstNum++;
	}

	//
	// b0..cf 12x binop/2addr vA, vB

	// b0: add-int/2addr
	@Override
	public void visit(ast.stm.Instruction.AddInt2Addr inst) {
		addInt2AddrInstNum++;
	}

	// b1: sub-int/2addr
	@Override
	public void visit(ast.stm.Instruction.SubInt2Addr inst) {
		subInt2AddrInstNum++;
	}

	// b2: mul-int/2addr
	@Override
	public void visit(ast.stm.Instruction.MulInt2Addr inst) {
		mulInt2AddrInstNum++;
	}

	// b3: div-int/2addr
	@Override
	public void visit(ast.stm.Instruction.DivInt2Addr inst) {
		divInt2AddrInstNum++;
	}

	// b4: rem-int/2addr
	@Override
	public void visit(ast.stm.Instruction.RemInt2Addr inst) {
		remInt2AddrInstNum++;
	}

	// b5: and-int/2addr
	@Override
	public void visit(ast.stm.Instruction.AndInt2Addr inst) {
		andInt2AddrInstNum++;
	}

	// b6: or-int/2addr
	@Override
	public void visit(ast.stm.Instruction.OrInt2Addr inst) {
		orInt2AddrInstNum++;
	}

	// b7: xor-int/2addr
	@Override
	public void visit(ast.stm.Instruction.XorInt2Addr inst) {
		xorInt2AddrInstNum++;
	}

	// b8: shl-int/2addr
	@Override
	public void visit(ast.stm.Instruction.ShlInt2Addr inst) {
		shlInt2AddrInstNum++;
	}

	// b9: shr-int/2addr
	@Override
	public void visit(ast.stm.Instruction.ShrInt2Addr inst) {
		shrInt2AddrInstNum++;
	}

	// ba: ushr-int/2addr
	@Override
	public void visit(ast.stm.Instruction.UshrInt2Addr inst) {
		ushrInt2AddrInstNum++;
	}

	// bb: add-long/2addr
	@Override
	public void visit(ast.stm.Instruction.AddLong2Addr inst) {
		addLong2AddrInstNum++;
	}

	// bc: sub-long/2addr
	@Override
	public void visit(ast.stm.Instruction.SubLong2Addr inst) {
		subLong2AddrInstNum++;
	}

	// bd: mul-long/2addr
	@Override
	public void visit(ast.stm.Instruction.MulLong2Addr inst) {
		mulLong2AddrInstNum++;
	}

	// be: div-long/2addr
	@Override
	public void visit(ast.stm.Instruction.DivLong2Addr inst) {
		divLong2AddrInstNum++;
	}

	// bf: rem-long/2addr
	@Override
	public void visit(ast.stm.Instruction.RemLong2Addr inst) {
		remLong2AddrInstNum++;
	}

	// c0: and-long/2addr
	@Override
	public void visit(ast.stm.Instruction.AndLong2Addr inst) {
		andLong2AddrInstNum++;
	}

	// c1: or-long/2addr
	@Override
	public void visit(ast.stm.Instruction.OrLong2Addr inst) {
		orLong2AddrInstNum++;
	}

	// c2: xor-long/2addr
	@Override
	public void visit(ast.stm.Instruction.XorLong2Addr inst) {
		xorLong2AddrInstNum++;
	}

	// c3: shl-long/2addr
	@Override
	public void visit(ast.stm.Instruction.ShlLong2Addr inst) {
		shlLong2AddrInstNum++;
	}

	// c4: shr-long/2addr
	@Override
	public void visit(ast.stm.Instruction.ShrLong2Addr inst) {
		shrLong2AddrInstNum++;
	}

	// c5: ushr-long/2addr
	@Override
	public void visit(ast.stm.Instruction.UshrLong2Addr inst) {
		ushrLong2AddrInstNum++;
	}

	// c6: add-float/2addr
	@Override
	public void visit(ast.stm.Instruction.AddFloat2Addr inst) {
		addFloat2AddrInstNum++;
	}

	// c7: sub-float/2addr
	@Override
	public void visit(ast.stm.Instruction.SubFloat2Addr inst) {
		subFoat2AddrInstNum++;
	}

	// c8: mul-float/2addr
	@Override
	public void visit(ast.stm.Instruction.MulFloat2Addr inst) {
		mulFloat2AddrInstNum++;
	}

	// c9: div-float/2addr
	@Override
	public void visit(ast.stm.Instruction.DivFloat2Addr inst) {
		divFLoat2AddrInstNum++;
	}

	// ca: rem-float/2addr
	@Override
	public void visit(ast.stm.Instruction.RemFloat2Addr inst) {
		remFloat2AddrInstNum++;
	}

	// cb: add-double/2addr
	@Override
	public void visit(ast.stm.Instruction.AddDouble2Addr inst) {
		addDouble2AddrInstNum++;
	}

	// cc: sub-double/2addr
	@Override
	public void visit(ast.stm.Instruction.SubDouble2Addr inst) {
		subDouble2AddrInstNum++;
	}

	// cd: mul-double/2addr
	@Override
	public void visit(ast.stm.Instruction.MulDouble2Addr inst) {
		mulDouble2AddrInstNum++;
	}

	// ce: div-double/2addr
	@Override
	public void visit(ast.stm.Instruction.DivDouble2Addr inst) {
		divDouble2AddrInstNum++;
	}

	// cf: rem-double/2addr
	@Override
	public void visit(ast.stm.Instruction.RemDouble2Addr inst) {
		remDouble2AddrInstNum++;
	}

	//
	// d0..d7 22s binop/lit16 vA, vB, #+CCCC
	// d0: add-int/lit16
	@Override
	public void visit(ast.stm.Instruction.AddIntLit16 inst) {
		addIntLit16InstNum++;
	}

	// d1: rsub-int (reverse subtract)
	@Override
	public void visit(ast.stm.Instruction.RsubInt inst) {
		rsubIntInstNum++;
	}

	// d2: mul-int/lit16
	@Override
	public void visit(ast.stm.Instruction.MulIntLit16 inst) {
		mulIntLit16InstNum++;
	}

	// d3: div-int/lit16
	@Override
	public void visit(ast.stm.Instruction.DivIntLit16 inst) {
		divIntLit16InstNum++;
	}

	// d4: rem-int/lit16
	@Override
	public void visit(ast.stm.Instruction.RemIntLit16 inst) {
		remIntLit16InstNum++;
	}

	// d5: and-int/lit16
	@Override
	public void visit(ast.stm.Instruction.AndIntLit16 inst) {
		andIntLit16InstNum++;
	}

	// d6: or-int/lit16
	@Override
	public void visit(ast.stm.Instruction.OrIntLit16 inst) {
		orIntLit16InstNum++;
	}

	// d7: xor-int/lit16
	@Override
	public void visit(ast.stm.Instruction.XorIntLit16 inst) {
		xorIntLit16InstNum++;
	}

	//
	// d8..e2 22b binop/lit8 vAA, vBB, #+CC
	// d8: add-int/lit8
	@Override
	public void visit(ast.stm.Instruction.AddIntLit8 inst) {
		addIntLit8InstNum++;
	}

	// d9: rsub-int/lit8
	@Override
	public void visit(ast.stm.Instruction.RsubIntLit8 inst) {
		rsubIntLit8InstNum++;
	}

	// da: mul-int/lit8
	@Override
	public void visit(ast.stm.Instruction.MulIntLit8 inst) {
		mulIntLit8InstNum++;
	}

	// db: div-int/lit8
	@Override
	public void visit(ast.stm.Instruction.DivIntLit8 inst) {
		divIntLit8InstNum++;
	}

	// dc: rem-int/lit8
	@Override
	public void visit(ast.stm.Instruction.RemIntLit8 inst) {
		remIntLit8InstNum++;
	}

	// dd: and-int/lit8
	@Override
	public void visit(ast.stm.Instruction.AndIntLit8 inst) {
		andIntLit8InstNum++;
	}

	// de: or-int/lit8
	@Override
	public void visit(ast.stm.Instruction.OrIntLit8 inst) {
		orIntLit8InstNum++;
	}

	// df: xor-int/lit8
	@Override
	public void visit(ast.stm.Instruction.XorIntLit8 inst) {
		xorIntLit8InstNum++;
	}

	// e0: shl-int/lit8
	@Override
	public void visit(ast.stm.Instruction.ShlIntLit8 inst) {
		shlIntLit8InstNum++;
	}

	// e1: shr-int/lit8
	@Override
	public void visit(ast.stm.Instruction.ShrIntLit8 inst) {
		shrIntLit8InstNum++;
	}

	// e2: ushr-int/lit8
	@Override
	public void visit(ast.stm.Instruction.UshrIntLit8 inst) {
		ushrIntLit8InstNum++;
	}

	@Override
	public void visit(ast.stm.Instruction instruction) {

	}

	@Override
	public void visit(ast.stm.Instruction.ArrayDataDirective inst) {
		arrayDataDirectiveInstNum++;
	}

	@Override
	public void visit(ast.stm.Instruction.PackedSwitchDirective inst) {
		packedSwitchDirectiveInstNum++;
	}

	@Override
	public void visit(ast.stm.Instruction.SparseSwitchDirective inst) {
		sparseSwitchDirectiveInstNum++;
	}
}