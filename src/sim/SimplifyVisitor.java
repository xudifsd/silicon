package sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimplifyVisitor implements ast.Visitor {
	public sim.classs.Class simplifiedClass;
	public HashMap<String, Integer> labels;
	public sim.annotation.Annotation annotation;
	public sim.annotation.Annotation.SubAnnotation subAnnotation;
	public sim.annotation.Annotation.ElementLiteral elementLiteral;
	public sim.method.Method method;
	public List<ast.stm.T> oldStmList;
	public int oldStmIndex;
	public boolean containLabel;
	public int position;
	public int labelIndex;
	public int catchIndex;
	public int labelValue;
	public int catchValue;
	public sim.method.Method.Label currentLabel;
	public sim.method.Method.Catch currentCatch;

	private List<sim.annotation.Annotation> translateAnnotation(
			List<ast.annotation.Annotation> annotationList) {
		List<sim.annotation.Annotation> ret = new ArrayList<sim.annotation.Annotation>();
		for (ast.annotation.Annotation ann : annotationList) {
			ann.accept(this);
			ret.add(annotation);
		}
		return ret;
	}

	private sim.classs.FieldItem translateField(ast.classs.FieldItem field) {
		return new sim.classs.FieldItem(field.classType, field.fieldName,
				field.fieldType);
	}

	private sim.classs.MethodItem translateMethod(ast.classs.MethodItem method) {
		return new sim.classs.MethodItem(method.classType, method.methodName,
				method.prototype);
	}

	private void methodInit() {
		this.oldStmIndex = 0;
		this.labels.clear();
		this.containLabel = false;
		this.position = 0;
		this.catchIndex = 0;
		this.labelIndex = 0;
		this.labelValue = -1;
		this.catchValue = -1;
		this.currentLabel = null;
		this.currentCatch = null;
		if (this.method.labelList.size() != 0) {
			this.currentLabel = this.method.labelList.get(this.labelIndex);
			this.labelValue = Integer.parseInt(this.currentLabel.add);
			this.containLabel = true;
		}
		if (this.method.catchList.size() != 0) {
			this.currentCatch = this.method.catchList.get(this.catchIndex);
			this.catchValue = Integer.parseInt(this.currentCatch.add);
		}
	}

	private void addCatchInstruction() {
		// change the catch to instruction;
		List<sim.stm.Instruction.Catch> catchs = new ArrayList<sim.stm.Instruction.Catch>();
		while (this.catchIndex < this.method.catchList.size()
				&& this.position == this.catchValue) {
			if (this.currentCatch.isAll)
				catchs.add(new sim.stm.Instruction.Catch(".catchall", true,
						null, this.currentCatch.startLab,
						this.currentCatch.endLab, this.currentCatch.catchLab));
			else
				catchs.add(new sim.stm.Instruction.Catch(".catch", false,
						currentCatch.type, this.currentCatch.startLab,
						this.currentCatch.endLab, this.currentCatch.catchLab));
			catchIndex++;
			if (this.catchIndex < this.method.catchList.size()) {
				this.currentCatch = this.method.catchList.get(this.catchIndex);
				this.catchValue = Integer.parseInt(this.currentCatch.add);
			}
		}
		// add the catchs to the method.statements
		for (sim.stm.Instruction.Catch ca : catchs) {
			this.method.statements.add(ca);
		}
	}

	private void methodAfter() {
		while (labelIndex < method.labelList.size()
				&& this.position == labelValue) {
			this.labels.put(currentLabel.lab, this.method.statements.size());
			if (this.currentLabel.lab.startsWith("try_end")) {
				this.addCatchInstruction();
			}
			labelIndex++;
			if (this.labelIndex < this.method.labelList.size()) {
				this.currentLabel = this.method.labelList.get(this.labelIndex);
				this.labelValue = Integer.parseInt(this.currentLabel.add);
			}
		}
		this.method.labels = this.labels;
	}

	private void emit(sim.stm.T inst, String op) {
		this.method.statements.add(inst);
		position += ast.PrettyPrintVisitor.instLen.get(op);
		if (this.containLabel) {
			// occur special label
			// there my occur nop instruction between two directive
			// oldStmList <-----> ast.stm.Instruction
			if (labelIndex < this.method.labelList.size()
					&& (this.currentLabel.lab.startsWith("sswitch_data")
							|| this.currentLabel.lab.startsWith("pswitch_data") || this.currentLabel.lab
								.startsWith("array_"))
					&& (this.oldStmList.get(this.oldStmIndex) instanceof ast.stm.Instruction.PackedSwitchDirective
							|| this.oldStmList.get(this.oldStmIndex) instanceof ast.stm.Instruction.SparseSwitchDirective || this.oldStmList
								.get(this.oldStmIndex) instanceof ast.stm.Instruction.ArrayDataDirective)) {
				this.labels.put(this.currentLabel.lab,
						this.method.statements.size());
				this.labelIndex++;
				if (this.labelIndex < this.method.labelList.size()) {
					this.currentLabel = this.method.labelList
							.get(this.labelIndex);
					this.labelValue = Integer.parseInt(this.currentLabel.add);
				}
			} else {
				// normal labels && position == labelValue
				while (labelIndex < this.method.labelList.size()
						&& this.position == labelValue) {
					// put the normal label to the HashMap
					this.labels.put(currentLabel.lab,
							this.method.statements.size());
					if (this.currentLabel.lab.startsWith("try_end")) {
						this.addCatchInstruction();
					}
					this.labelIndex++;
					if (this.labelIndex < this.method.labelList.size()) {
						this.currentLabel = this.method.labelList
								.get(this.labelIndex);
						this.labelValue = Integer
								.parseInt(this.currentLabel.add);
					}
				}
			}
		}
	}

	// program
	@Override
	public void visit(ast.program.Program p) {
		throw new RuntimeException(
				"visiting ast.program.Program?! are you out of your mind?");
	}

	@Override
	public void visit(ast.classs.Class clazz) {
		simplifiedClass = new sim.classs.Class();
		simplifiedClass.name = clazz.FullyQualifiedName;
		simplifiedClass.source = clazz.source;
		simplifiedClass.superr = clazz.superName;
		simplifiedClass.implementsList = clazz.implementsList;
		simplifiedClass.accessList = clazz.accessList;
		simplifiedClass.annotationList = this
				.translateAnnotation(clazz.annotationList);
		simplifiedClass.fieldList = new ArrayList<sim.classs.Class.Field>();
		for (ast.classs.Class.Field f : clazz.fieldList) {
			sim.annotation.Annotation.ElementLiteral elementLiteral = new sim.annotation.Annotation.ElementLiteral();
			elementLiteral.arrayLiteralType = f.elementLiteral.arrayLiteralType;
			elementLiteral.element = f.elementLiteral.element;
			elementLiteral.type = f.elementLiteral.type;
			List<sim.annotation.Annotation> annotationList = this
					.translateAnnotation(f.annotationList);
			simplifiedClass.fieldList.add(new sim.classs.Class.Field(f.name,
					f.accessList, f.type, elementLiteral, annotationList));
		}
		simplifiedClass.methods = new ArrayList<sim.method.Method>();
		for (ast.method.Method m : clazz.methods) {
			this.methodInit();
			m.accept(this);
			this.methodAfter();
			simplifiedClass.methods.add(this.method);

		}
	}

	@Override
	public void visit(ast.method.Method m) {
		this.method.accessList = m.accessList;
		this.method.annotationList = this.translateAnnotation(m.annotationList);
		this.method.catchList = new ArrayList<sim.method.Method.Catch>();
		this.method.labelList = new ArrayList<sim.method.Method.Label>();
		for (ast.method.Method.Label label : m.labelList) {
			this.method.labelList.add(new sim.method.Method.Label(label.lab,
					label.add));
		}
		for (ast.method.Method.Catch cat : m.catchList) {
			this.method.catchList
					.add(new sim.method.Method.Catch(cat.add, cat.isAll,
							cat.type, cat.startLab, cat.endLab, cat.catchLab));
		}
		this.method.name = m.name;
		this.method.parameterList = new ArrayList<sim.method.Method.Parameter>();
		for (ast.method.Method.Parameter p : m.parameterList) {
			this.method.parameterList.add(new sim.method.Method.Parameter(
					p.value, this.translateAnnotation(p.annotationList)));
		}
		this.method.prototype = new sim.method.Method.MethodPrototype(
				m.prototype.returnType, m.prototype.argsType);
		this.method.registers_directive = m.registers_directive;
		this.method.registers_directive_count = m.registers_directive_count;
		this.method.statements = new ArrayList<sim.stm.T>();
		this.oldStmList = m.statements;
		for (ast.stm.T stm : m.statements) {
			this.oldStmIndex++;
			stm.accept(this);
		}
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
		anno.subAnnotation.accept(this);
		this.annotation = new sim.annotation.Annotation(anno.visibility,
				this.subAnnotation);
	}

	@Override
	public void visit(ast.annotation.Annotation.SubAnnotation subAnno) {
		this.subAnnotation = new sim.annotation.Annotation.SubAnnotation();
		this.subAnnotation.classType = subAnno.classType;
		this.subAnnotation.elementList = new ArrayList<sim.annotation.Annotation.AnnotationElement>();
		for (ast.annotation.Annotation.AnnotationElement elem : subAnno.elementList) {
			elem.elementLiteral.accept(this);
			this.subAnnotation.elementList
					.add(new sim.annotation.Annotation.AnnotationElement(
							elem.name, this.elementLiteral));
		}
	}

	@Override
	public void visit(ast.annotation.Annotation.ElementLiteral elem) {
		this.elementLiteral = new sim.annotation.Annotation.ElementLiteral(
				elem.element, elem.type, elem.arrayLiteralType);
	}

	// ////////////////////////////////////////////////////////
	// 00 10x nop
	@Override
	public void visit(ast.stm.Instruction.Nop inst) {
		emit(new sim.stm.Instruction.Nop(), inst.op);
	}

	// 01 12x move vA, vB
	@Override
	public void visit(ast.stm.Instruction.Move inst) {
		emit(new sim.stm.Instruction.Move("move/16", inst.dest, inst.src),
				inst.op);
	}

	// 02 22x move/from16 vAA, vBBBB
	@Override
	public void visit(ast.stm.Instruction.MoveFrom16 inst) {
		emit(new sim.stm.Instruction.Move("move/16", inst.dest, inst.src),
				inst.op);
	}

	// 03 32x move/16 vAAAA, vBBBB ------
	@Override
	public void visit(ast.stm.Instruction.Move16 inst) {
		emit(new sim.stm.Instruction.Move(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 04 12x move-wide vA, vB
	@Override
	public void visit(ast.stm.Instruction.MoveWide inst) {
		emit(new sim.stm.Instruction.Move("move-wide/16", inst.dest, inst.src),
				inst.op);
	}

	// 05 22x move-wide/from16 vAA, vBBBB
	@Override
	public void visit(ast.stm.Instruction.MoveWideFrom16 inst) {
		emit(new sim.stm.Instruction.Move("move-wide/16", inst.dest, inst.src),
				inst.op);
	}

	// 06 32x move-wide/16 vAAAA, vBBBB -----
	@Override
	public void visit(ast.stm.Instruction.MoveWide16 inst) {
		emit(new sim.stm.Instruction.Move(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 07 12x move-object vA, vB
	@Override
	public void visit(ast.stm.Instruction.MoveObject inst) {
		emit(new sim.stm.Instruction.Move("move-object/16", inst.dest, inst.src),
				inst.op);
	}

	// 08 22x move-object/from16 vAA, vBBBB --
	@Override
	public void visit(ast.stm.Instruction.MoveOjbectFrom16 inst) {
		emit(new sim.stm.Instruction.Move("move-object/16", inst.dest, inst.src),
				inst.op);
	}

	// 09 32x move-object/16 vAAAA, vBBBB
	@Override
	public void visit(ast.stm.Instruction.MoveObject16 inst) {
		emit(new sim.stm.Instruction.Move(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 0a 11x move-result vAA
	@Override
	public void visit(ast.stm.Instruction.MoveResult inst) {
		emit(new sim.stm.Instruction.MoveResult(inst.op, inst.dest), inst.op);
	}

	// 0b 11x move-result-wide vAA
	@Override
	public void visit(ast.stm.Instruction.MoveResultWide inst) {
		emit(new sim.stm.Instruction.MoveResult(inst.op, inst.dest), inst.op);
	}

	// 0c 11x move-result-object vAA
	@Override
	public void visit(ast.stm.Instruction.MoveResultObject inst) {
		emit(new sim.stm.Instruction.MoveResult(inst.op, inst.dest), inst.op);
	}

	// 0d 11x move-exception vAA
	@Override
	public void visit(ast.stm.Instruction.MoveException inst) {
		emit(new sim.stm.Instruction.MoveResult(inst.op, inst.dest), inst.op);
	}

	// 0e 10x return-void
	@Override
	public void visit(ast.stm.Instruction.ReturnVoid inst) {
		emit(new sim.stm.Instruction.ReturnVoid(), inst.op);
	}

	// 0f 11x return vAA
	@Override
	public void visit(ast.stm.Instruction.Return inst) {
		emit(new sim.stm.Instruction.Return(inst.op, inst.ret), inst.op);
	}

	// 10 11x return-wide vAA
	@Override
	public void visit(ast.stm.Instruction.ReturnWide inst) {
		emit(new sim.stm.Instruction.Return(inst.op, inst.ret), inst.op);
	}

	// 11 11x return-object vAA
	@Override
	public void visit(ast.stm.Instruction.ReturnObject inst) {
		emit(new sim.stm.Instruction.Return(inst.op, inst.ret), inst.op);
	}

	// 12 11n const/4 vA, #+B
	@Override
	public void visit(ast.stm.Instruction.Const4 inst) {
		emit(new sim.stm.Instruction.Const("const", inst.dest, inst.value),
				inst.op);
	}

	// 13 21s const/16 vAA, #+BBBB
	@Override
	public void visit(ast.stm.Instruction.Const16 inst) {
		emit(new sim.stm.Instruction.Const("const", inst.dest, inst.value),
				inst.op);
	}

	// 14 31i const vAA, #+BBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.Const inst) {
		emit(new sim.stm.Instruction.Const(inst.op, inst.dest, inst.value),
				inst.op);
	}

	// 15 21h const/high16 vAA, #+BBBB0000
	@Override
	public void visit(ast.stm.Instruction.ConstHigh16 inst) {
		emit(new sim.stm.Instruction.Const("const", inst.dest, inst.value
				+ "0000"), inst.op);
	}

	// 16 21s const-wide/16 vAA, #+BBBB
	@Override
	public void visit(ast.stm.Instruction.ConstWide16 inst) {
		emit(new sim.stm.Instruction.Const("const-wide", inst.dest, inst.value),
				inst.op);
	}

	// 17 31i const-wide/32 vAA, #+BBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.ConstWide32 inst) {
		emit(new sim.stm.Instruction.Const("const-wide", inst.dest, inst.value),
				inst.op);
	}

	// 18 51l const-wide vAA, #+BBBBBBBBBBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.ConstWide inst) {
		emit(new sim.stm.Instruction.Const(inst.op, inst.dest, inst.value),
				inst.op);
	}

	// 19 21h const-wide/high16 vAA, #+BBBB000000000000
	@Override
	public void visit(ast.stm.Instruction.ConstWideHigh16 inst) {
		emit(new sim.stm.Instruction.Const("const-wide", inst.dest, inst.value
				+ "000000000000"), inst.op);
	}

	// 1a 21c const-string vAA, string@BBBB
	@Override
	public void visit(ast.stm.Instruction.ConstString inst) {
		emit(new sim.stm.Instruction.Const("const-string/jumbo", inst.dest,
				inst.str), inst.op);
	}

	// 1b 31c const-string/jumbo vAA, string@BBBBBBBB
	@Override
	public void visit(ast.stm.Instruction.ConstStringJumbo inst) {
		emit(new sim.stm.Instruction.Const(inst.op, inst.dest, inst.str),
				inst.op);
	}

	// 1c 21c const-class vAA, type@BBBB
	@Override
	public void visit(ast.stm.Instruction.ConstClass inst) {
		emit(new sim.stm.Instruction.Const(inst.op, inst.dest, inst.type),
				inst.op);
	}

	// 1d 11x monitor-enter vAA
	@Override
	public void visit(ast.stm.Instruction.MonitorEnter inst) {
		emit(new sim.stm.Instruction.Monitor(inst.op, inst.ref), inst.op);
	}

	// 1e 11x monitor-exit vAA
	@Override
	public void visit(ast.stm.Instruction.MonitorExit inst) {
		emit(new sim.stm.Instruction.Monitor(inst.op, inst.ref), inst.op);
	}

	// 1f 21c check-cast vAA, type@BBBB
	@Override
	public void visit(ast.stm.Instruction.CheckCast inst) {
		emit(new sim.stm.Instruction.CheckCast(inst.ref, inst.type), inst.op);
	}

	// 20 22c instance-of vA, vB, type@CCCC
	@Override
	public void visit(ast.stm.Instruction.InstanceOf inst) {
		emit(new sim.stm.Instruction.InstanceOf(inst.dest, inst.ref, inst.type),
				inst.op);
	}

	// 21 12x array-length vA, vB
	@Override
	public void visit(ast.stm.Instruction.arrayLength inst) {
		emit(new sim.stm.Instruction.ArrayLength(inst.dest, inst.src), inst.op);
	}

	// 22 21c new-instance vAA, type@BBBB
	@Override
	public void visit(ast.stm.Instruction.NewInstance inst) {
		emit(new sim.stm.Instruction.NewInstance(inst.dest, inst.type), inst.op);
	}

	// 23 22c new-array vA, vB, type@CCCC
	// new-array v0,p1 [Landro......
	@Override
	public void visit(ast.stm.Instruction.NewArray inst) {
		emit(new sim.stm.Instruction.NewArray(inst.dest, inst.size, inst.type),
				inst.op);
	}

	// 24 35c filled-new-array {vC, vD, vE, vF, vG}, type@BBBB
	// filled-new-array {v7, v9}, [I
	@Override
	public void visit(ast.stm.Instruction.FilledNewArray inst) {
		emit(new sim.stm.Instruction.FilledNewArray(inst.op, inst.argList,
				inst.type), inst.op);
	}

	// 25 3rc filled-new-array/range {vCCCC .. vNNNN}, type@BBBB ----
	@Override
	public void visit(ast.stm.Instruction.FilledNewArrayRange inst) {
		List<String> argList = new ArrayList<String>();
		argList.add(inst.start);
		argList.add(inst.end);
		emit(new sim.stm.Instruction.FilledNewArray(inst.op, argList, inst.type),
				inst.op);
	}

	// 26 31t fill-array-data vAA, +BBBBBBBB (with supplemental data as
	// specified below in "fill-array-data-payloadFormat") ------
	@Override
	public void visit(ast.stm.Instruction.FillArrayData inst) {
		emit(new sim.stm.Instruction.FillArrayData(inst.dest, inst.src),
				inst.op);
	}

	// 27 11x throw vAA
	@Override
	public void visit(ast.stm.Instruction.Throw inst) {
		emit(new sim.stm.Instruction.Throw(inst.kind), inst.op);
	}

	// 28 10t goto +AA ??
	// !!!!! goto :goto_0
	@Override
	public void visit(ast.stm.Instruction.Goto inst) {
		emit(new sim.stm.Instruction.Goto("goto/32", inst.dest), inst.op);
	}

	// 29 20t goto/16 +AAAA ??
	@Override
	public void visit(ast.stm.Instruction.Goto16 inst) {
		emit(new sim.stm.Instruction.Goto("goto/32", inst.dest), inst.op);
	}

	// 2a 30t goto/32 +AAAAAAAA ??
	@Override
	public void visit(ast.stm.Instruction.Goto32 inst) {
		emit(new sim.stm.Instruction.Goto(inst.op, inst.dest), inst.op);
	}

	// 2b 31t packed-switch vAA, +BBBBBBBB (with supplemental data as specified
	// below in "packed-switch- ??????????
	@Override
	public void visit(ast.stm.Instruction.PackedSwitch inst) {
		emit(new sim.stm.Instruction.Switch(inst.op, inst.test, inst.offset),
				inst.op);
	}

	// 2c 31t sparse-switch vAA, +BBBBBBBB (with supplemental data as specified
	// below in "sparse-switch-payloadFormat") ??????????
	@Override
	public void visit(ast.stm.Instruction.SparseSwitch inst) {
		emit(new sim.stm.Instruction.Switch(inst.op, inst.test, inst.offset),
				inst.op);
	}

	//
	// 2d..31 23x cmpkind vAA, vBB, vCC
	// 2d: cmpl-float (lt bias)
	@Override
	public void visit(ast.stm.Instruction.CmplFloat inst) {
		emit(new sim.stm.Instruction.Cmp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 2e: cmpg-float (gt bias)
	@Override
	public void visit(ast.stm.Instruction.CmpgFloat inst) {
		emit(new sim.stm.Instruction.Cmp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 2f: cmpl-double (lt bias)
	@Override
	public void visit(ast.stm.Instruction.CmplDouble inst) {
		emit(new sim.stm.Instruction.Cmp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 30: cmpg-double (gt bias)
	@Override
	public void visit(ast.stm.Instruction.Cmpgdouble inst) {
		emit(new sim.stm.Instruction.Cmp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 31: cmp-long
	@Override
	public void visit(ast.stm.Instruction.CmpLong inst) {
		emit(new sim.stm.Instruction.Cmp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	//
	// 32..37 22t if-test vA, vB, +CCCC
	// !!!!!!! if-eq v1, v0, :cond_1
	// 32: if-eq
	@Override
	public void visit(ast.stm.Instruction.IfEq inst) {
		emit(new sim.stm.Instruction.IfTest(inst.op, inst.first, inst.second,
				inst.dest), inst.op);
	}

	// 33: if-ne
	@Override
	public void visit(ast.stm.Instruction.IfNe inst) {
		emit(new sim.stm.Instruction.IfTest(inst.op, inst.first, inst.second,
				inst.dest), inst.op);
	}

	// 34: if-lt
	@Override
	public void visit(ast.stm.Instruction.IfLt inst) {
		emit(new sim.stm.Instruction.IfTest(inst.op, inst.first, inst.second,
				inst.dest), inst.op);
	}

	// 35: if-ge
	@Override
	public void visit(ast.stm.Instruction.IfGe inst) {
		emit(new sim.stm.Instruction.IfTest(inst.op, inst.first, inst.second,
				inst.dest), inst.op);
	}

	// 36: if-gt
	@Override
	public void visit(ast.stm.Instruction.IfGt inst) {
		emit(new sim.stm.Instruction.IfTest(inst.op, inst.first, inst.second,
				inst.dest), inst.op);
	}

	// 37: if-le
	@Override
	public void visit(ast.stm.Instruction.IfLe inst) {
		emit(new sim.stm.Instruction.IfTest(inst.op, inst.first, inst.second,
				inst.dest), inst.op);
	}

	//
	//
	// 38..3d 21t if-testz vAA, +BBBB
	// !!!! if-eqz v0, :cond_0
	// 38: if-eqz
	@Override
	public void visit(ast.stm.Instruction.IfEqz inst) {
		emit(new sim.stm.Instruction.IfTestz(inst.op, inst.test, inst.dest),
				inst.op);

	}

	// 39: if-nez
	@Override
	public void visit(ast.stm.Instruction.IfNez inst) {
		emit(new sim.stm.Instruction.IfTestz(inst.op, inst.test, inst.dest),
				inst.op);
	}

	// 3a: if-ltz
	@Override
	public void visit(ast.stm.Instruction.IfLtz inst) {
		emit(new sim.stm.Instruction.IfTestz(inst.op, inst.test, inst.dest),
				inst.op);
	}

	// 3b: if-gez
	@Override
	public void visit(ast.stm.Instruction.IfGez inst) {
		emit(new sim.stm.Instruction.IfTestz(inst.op, inst.test, inst.dest),
				inst.op);
	}

	// 3c: if-gtz
	@Override
	public void visit(ast.stm.Instruction.IfGtz inst) {
		emit(new sim.stm.Instruction.IfTestz(inst.op, inst.test, inst.dest),
				inst.op);
	}

	// 3d: if-lez
	@Override
	public void visit(ast.stm.Instruction.IfLez inst) {
		emit(new sim.stm.Instruction.IfTestz(inst.op, inst.test, inst.dest),
				inst.op);
	}

	//
	//
	// 3e..43 10x (unused)
	// 44..51 23x arrayop vAA, vBB, vCC

	// 44: aget
	@Override
	public void visit(ast.stm.Instruction.Aget inst) {
		emit(new sim.stm.Instruction.Aget(inst.op, inst.dest, inst.array,
				inst.index), inst.op);
	}

	// 45: aget-wide
	@Override
	public void visit(ast.stm.Instruction.AgetWide inst) {
		emit(new sim.stm.Instruction.Aget(inst.op, inst.dest, inst.array,
				inst.index), inst.op);
	}

	// 46: aget-object
	@Override
	public void visit(ast.stm.Instruction.AgetObject inst) {
		emit(new sim.stm.Instruction.Aget(inst.op, inst.dest, inst.array,
				inst.index), inst.op);
	}

	// 47: aget-boolean
	@Override
	public void visit(ast.stm.Instruction.AgetBoolean inst) {
		emit(new sim.stm.Instruction.Aget(inst.op, inst.dest, inst.array,
				inst.index), inst.op);
	}

	// 48: aget-byte
	@Override
	public void visit(ast.stm.Instruction.AgetByte inst) {
		emit(new sim.stm.Instruction.Aget(inst.op, inst.dest, inst.array,
				inst.index), inst.op);
	}

	// 49: aget-char
	@Override
	public void visit(ast.stm.Instruction.AgetChar inst) {
		emit(new sim.stm.Instruction.Aget(inst.op, inst.dest, inst.array,
				inst.index), inst.op);
	}

	// 4a: aget-short
	@Override
	public void visit(ast.stm.Instruction.AgetShort inst) {
		emit(new sim.stm.Instruction.Aget(inst.op, inst.dest, inst.array,
				inst.index), inst.op);
	}

	// 4b: aput
	@Override
	public void visit(ast.stm.Instruction.Aput inst) {
		emit(new sim.stm.Instruction.Aput(inst.op, inst.src, inst.array,
				inst.index), inst.op);
	}

	// 4c: aput-wide
	@Override
	public void visit(ast.stm.Instruction.AputWide inst) {
		emit(new sim.stm.Instruction.Aput(inst.op, inst.src, inst.array,
				inst.index), inst.op);
	}

	// 4d: aput-object
	@Override
	public void visit(ast.stm.Instruction.AputObject inst) {
		emit(new sim.stm.Instruction.Aput(inst.op, inst.src, inst.array,
				inst.index), inst.op);
	}

	// 4e: aput-boolean
	@Override
	public void visit(ast.stm.Instruction.AputBoolean inst) {
		emit(new sim.stm.Instruction.Aput(inst.op, inst.src, inst.array,
				inst.index), inst.op);
	}

	// 4f: aput-byte
	@Override
	public void visit(ast.stm.Instruction.AputByte inst) {
		emit(new sim.stm.Instruction.Aput(inst.op, inst.src, inst.array,
				inst.index), inst.op);
	}

	// 50: aput-char
	@Override
	public void visit(ast.stm.Instruction.AputChar inst) {
		emit(new sim.stm.Instruction.Aput(inst.op, inst.src, inst.array,
				inst.index), inst.op);
	}

	// 51: aput-short
	@Override
	public void visit(ast.stm.Instruction.AputShort inst) {
		emit(new sim.stm.Instruction.Aput(inst.op, inst.src, inst.array,
				inst.index), inst.op);
	}

	//
	// 52..5f 22c iinstanceop vA, vB, field@CCCC
	// 52: iget
	@Override
	public void visit(ast.stm.Instruction.Iget inst) {
		emit(new sim.stm.Instruction.Iget(inst.op, inst.dest, inst.field,
				this.translateField(inst.type)), inst.op);
	}

	// 53: iget-wide
	@Override
	public void visit(ast.stm.Instruction.IgetWide inst) {
		emit(new sim.stm.Instruction.Iget(inst.op, inst.dest, inst.field,
				this.translateField(inst.type)), inst.op);
	}

	// 54: iget-object
	@Override
	public void visit(ast.stm.Instruction.IgetOjbect inst) {
		emit(new sim.stm.Instruction.Iget(inst.op, inst.dest, inst.field,
				this.translateField(inst.type)), inst.op);
	}

	// 55: iget-boolean
	@Override
	public void visit(ast.stm.Instruction.IgetBoolean inst) {
	}

	// 56: iget-byte
	@Override
	public void visit(ast.stm.Instruction.IgetByte inst) {
		emit(new sim.stm.Instruction.Iget(inst.op, inst.dest, inst.field,
				this.translateField(inst.type)), inst.op);
	}

	// 57: iget-char
	@Override
	public void visit(ast.stm.Instruction.IgetChar inst) {
		emit(new sim.stm.Instruction.Iget(inst.op, inst.dest, inst.field,
				this.translateField(inst.type)), inst.op);
	}

	// 58: iget-short
	@Override
	public void visit(ast.stm.Instruction.IgetShort inst) {
		emit(new sim.stm.Instruction.Iget(inst.op, inst.dest, inst.field,
				this.translateField(inst.type)), inst.op);
	}

	// 59: iput
	@Override
	public void visit(ast.stm.Instruction.Iput inst) {
		emit(new sim.stm.Instruction.Iput(inst.op, inst.src, inst.field,
				this.translateField(inst.type)), inst.op);
	}

	// 5a: iput-wide
	@Override
	public void visit(ast.stm.Instruction.IputWide inst) {
		emit(new sim.stm.Instruction.Iput(inst.op, inst.src, inst.field,
				this.translateField(inst.type)), inst.op);
	}

	// 5b: iput-object
	@Override
	public void visit(ast.stm.Instruction.IputObject inst) {
		emit(new sim.stm.Instruction.Iput(inst.op, inst.src, inst.field,
				this.translateField(inst.type)), inst.op);
	}

	// 5c: iput-boolean
	@Override
	public void visit(ast.stm.Instruction.IputBoolean inst) {
		emit(new sim.stm.Instruction.Iput(inst.op, inst.src, inst.field,
				this.translateField(inst.type)), inst.op);
	}

	// 5d: iput-byte
	@Override
	public void visit(ast.stm.Instruction.IputByte inst) {
		emit(new sim.stm.Instruction.Iput(inst.op, inst.src, inst.field,
				this.translateField(inst.type)), inst.op);
	}

	// 5e: iput-char
	@Override
	public void visit(ast.stm.Instruction.IputChar inst) {
		emit(new sim.stm.Instruction.Iput(inst.op, inst.src, inst.field,
				this.translateField(inst.type)), inst.op);
	}

	// 5f: iput-short
	@Override
	public void visit(ast.stm.Instruction.IputShort inst) {
		emit(new sim.stm.Instruction.Iput(inst.op, inst.src, inst.field,
				this.translateField(inst.type)), inst.op);
	}

	//
	// 60..6d 21c sstaticop vAA, field@BBBB
	// 60: sget
	@Override
	public void visit(ast.stm.Instruction.Sget inst) {
		emit(new sim.stm.Instruction.Sget(inst.op, inst.dest,
				this.translateField(inst.type)), inst.op);

	}

	// 61: sget-wide
	@Override
	public void visit(ast.stm.Instruction.SgetWide inst) {
		emit(new sim.stm.Instruction.Sget(inst.op, inst.dest,
				this.translateField(inst.type)), inst.op);
	}

	// 62: sget-object
	@Override
	public void visit(ast.stm.Instruction.SgetObject inst) {
		emit(new sim.stm.Instruction.Sget(inst.op, inst.dest,
				this.translateField(inst.type)), inst.op);
	}

	// 63: sget-boolean
	@Override
	public void visit(ast.stm.Instruction.SgetBoolean inst) {
		emit(new sim.stm.Instruction.Sget(inst.op, inst.dest,
				this.translateField(inst.type)), inst.op);
	}

	// 64: sget-byte
	@Override
	public void visit(ast.stm.Instruction.SgetByte inst) {
		emit(new sim.stm.Instruction.Sget(inst.op, inst.dest,
				this.translateField(inst.type)), inst.op);
	}

	// 65: sget-char
	@Override
	public void visit(ast.stm.Instruction.SgetChar inst) {
		emit(new sim.stm.Instruction.Sget(inst.op, inst.dest,
				this.translateField(inst.type)), inst.op);
	}

	// 66: sget-short
	@Override
	public void visit(ast.stm.Instruction.SgetShort inst) {
		emit(new sim.stm.Instruction.Sget(inst.op, inst.dest,
				this.translateField(inst.type)), inst.op);
	}

	// 67: sput
	@Override
	public void visit(ast.stm.Instruction.Sput inst) {
		emit(new sim.stm.Instruction.Sput(inst.op, inst.src,
				this.translateField(inst.type)), inst.op);
	}

	// 68: sput-wide
	@Override
	public void visit(ast.stm.Instruction.SputWide inst) {
		emit(new sim.stm.Instruction.Sput(inst.op, inst.src,
				this.translateField(inst.type)), inst.op);
	}

	// 69: sput-object
	@Override
	public void visit(ast.stm.Instruction.SputObject inst) {
		emit(new sim.stm.Instruction.Sput(inst.op, inst.src,
				this.translateField(inst.type)), inst.op);
	}

	// 6a: sput-boolean
	@Override
	public void visit(ast.stm.Instruction.SputBoolean inst) {
		emit(new sim.stm.Instruction.Sput(inst.op, inst.src,
				this.translateField(inst.type)), inst.op);
	}

	// 6b: sput-byte
	@Override
	public void visit(ast.stm.Instruction.SputByte inst) {
		emit(new sim.stm.Instruction.Sput(inst.op, inst.src,
				this.translateField(inst.type)), inst.op);
	}

	// 6c: sput-char
	@Override
	public void visit(ast.stm.Instruction.SputChar inst) {
		emit(new sim.stm.Instruction.Sput(inst.op, inst.src,
				this.translateField(inst.type)), inst.op);
	}

	// 6d: sput-short
	@Override
	public void visit(ast.stm.Instruction.SputShort inst) {
		emit(new sim.stm.Instruction.Sput(inst.op, inst.src,
				this.translateField(inst.type)), inst.op);
	}

	// 6e..72 35c invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB
	// 6e: invoke-virtual
	@Override
	public void visit(ast.stm.Instruction.InvokeVirtual inst) {
		emit(new sim.stm.Instruction.Invoke(inst.op, inst.argList,
				this.translateMethod(inst.type)), inst.op);
	}

	// 6f: invoke-super
	@Override
	public void visit(ast.stm.Instruction.InvokeSuper inst) {
		emit(new sim.stm.Instruction.Invoke(inst.op, inst.argList,
				this.translateMethod(inst.type)), inst.op);
	}

	// 70: invoke-direct
	@Override
	public void visit(ast.stm.Instruction.InvokeDirect inst) {
		emit(new sim.stm.Instruction.Invoke(inst.op, inst.argList,
				this.translateMethod(inst.type)), inst.op);
	}

	// 71: invoke-static
	@Override
	public void visit(ast.stm.Instruction.InvokeStatic inst) {
		emit(new sim.stm.Instruction.Invoke(inst.op, inst.argList,
				this.translateMethod(inst.type)), inst.op);
	}

	// 72: invoke-interface
	@Override
	public void visit(ast.stm.Instruction.InvokeInterface inst) {
		emit(new sim.stm.Instruction.Invoke(inst.op, inst.argList,
				this.translateMethod(inst.type)), inst.op);
	}

	//
	//
	// 73 10x (unused)
	// 74..78 3rc invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB
	// 74: invoke-virtual/range
	@Override
	public void visit(ast.stm.Instruction.InvokeVirtualRange inst) {
		List<String> argList = new ArrayList<String>();
		argList.add(inst.start);
		argList.add(inst.end);
		emit(new sim.stm.Instruction.Invoke(inst.op, argList,
				this.translateMethod(inst.type)), inst.op);

	}

	// 75: invoke-super/range
	@Override
	public void visit(ast.stm.Instruction.InvokeSuperRange inst) {
		List<String> argList = new ArrayList<String>();
		argList.add(inst.start);
		argList.add(inst.end);
		emit(new sim.stm.Instruction.Invoke(inst.op, argList,
				this.translateMethod(inst.type)), inst.op);
	}

	// 76: invoke-direct/range
	@Override
	public void visit(ast.stm.Instruction.InvokeDirectRange inst) {
		List<String> argList = new ArrayList<String>();
		argList.add(inst.start);
		argList.add(inst.end);
		emit(new sim.stm.Instruction.Invoke(inst.op, argList,
				this.translateMethod(inst.type)), inst.op);
	}

	// 77: invoke-static/range
	@Override
	public void visit(ast.stm.Instruction.InvokeStaticRange inst) {
		List<String> argList = new ArrayList<String>();
		argList.add(inst.start);
		argList.add(inst.end);
		emit(new sim.stm.Instruction.Invoke(inst.op, argList,
				this.translateMethod(inst.type)), inst.op);
	}

	// 78: invoke-interface/range
	@Override
	public void visit(ast.stm.Instruction.InvokeInterfaceRange inst) {
		List<String> argList = new ArrayList<String>();
		argList.add(inst.start);
		argList.add(inst.end);
		emit(new sim.stm.Instruction.Invoke(inst.op, argList,
				this.translateMethod(inst.type)), inst.op);
	}

	//
	// 79..7a 10x (unused)
	// 7b..8f 12x unop vA, vB
	// 7b: neg-int
	@Override
	public void visit(ast.stm.Instruction.NegInt inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 7c: not-int
	@Override
	public void visit(ast.stm.Instruction.NotInt inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 7d: neg-long
	@Override
	public void visit(ast.stm.Instruction.NegLong inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 7e: not-long
	@Override
	public void visit(ast.stm.Instruction.NotLong inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 7f: neg-float
	@Override
	public void visit(ast.stm.Instruction.NegFloat inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 80: neg-double
	@Override
	public void visit(ast.stm.Instruction.NegDouble inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 81: int-to-long
	@Override
	public void visit(ast.stm.Instruction.IntToLong inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 82: int-to-float
	@Override
	public void visit(ast.stm.Instruction.IntToFloat inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 83: int-to-double
	@Override
	public void visit(ast.stm.Instruction.IntToDouble inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 84: long-to-int
	@Override
	public void visit(ast.stm.Instruction.LongToInt inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 85: long-to-float
	@Override
	public void visit(ast.stm.Instruction.LongToFloat inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 86: long-to-double
	@Override
	public void visit(ast.stm.Instruction.LongToDouble inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 87: float-to-int
	@Override
	public void visit(ast.stm.Instruction.FloatToInt inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 88: float-to-long
	@Override
	public void visit(ast.stm.Instruction.FloatToLong inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 89: float-to-double
	@Override
	public void visit(ast.stm.Instruction.FloatToDouble inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 8a: double-to-int
	@Override
	public void visit(ast.stm.Instruction.DoubleToInt inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 8b: double-to-long
	@Override
	public void visit(ast.stm.Instruction.DoubleToLong inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 8c: double-to-float
	@Override
	public void visit(ast.stm.Instruction.DoubleToFloat inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 8d: int-to-byte
	@Override
	public void visit(ast.stm.Instruction.IntToByte inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 8e: int-to-char
	@Override
	public void visit(ast.stm.Instruction.IntToChar inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	// 8f: int-to-short
	@Override
	public void visit(ast.stm.Instruction.IntToShort inst) {
		emit(new sim.stm.Instruction.UnOp(inst.op, inst.dest, inst.src),
				inst.op);
	}

	//
	// 90..af 23x binop vAA, vBB, vCC
	// 90: add-int
	@Override
	public void visit(ast.stm.Instruction.AddInt inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 91: sub-int public void visit(ast.stm.Instruction.AddInt inst)
	@Override
	public void visit(ast.stm.Instruction.SubInt inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 92: mul-int
	@Override
	public void visit(ast.stm.Instruction.MulInt inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 93: div-int
	@Override
	public void visit(ast.stm.Instruction.DivInt inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 94: rem-int
	@Override
	public void visit(ast.stm.Instruction.RemInt inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 95: and-int
	@Override
	public void visit(ast.stm.Instruction.AndInt inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 96: or-int
	@Override
	public void visit(ast.stm.Instruction.OrInt inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 97: xor-int
	@Override
	public void visit(ast.stm.Instruction.XorInt inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 98: shl-int
	@Override
	public void visit(ast.stm.Instruction.ShlInt inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 99: shr-int
	@Override
	public void visit(ast.stm.Instruction.ShrInt inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 9a: ushr-int
	@Override
	public void visit(ast.stm.Instruction.UshrInt inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 9b: add-long
	@Override
	public void visit(ast.stm.Instruction.AddLong inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 9c: sub-long
	@Override
	public void visit(ast.stm.Instruction.SubLong inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 9d: mul-long
	@Override
	public void visit(ast.stm.Instruction.MulLong inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 9e: div-long
	@Override
	public void visit(ast.stm.Instruction.DivLong inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// 9f: rem-long
	@Override
	public void visit(ast.stm.Instruction.RemLong inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// a0: and-long
	@Override
	public void visit(ast.stm.Instruction.AndLong inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// a1: or-long
	@Override
	public void visit(ast.stm.Instruction.OrLong inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// a2: xor-long
	@Override
	public void visit(ast.stm.Instruction.XorLong inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// a3: shl-long
	@Override
	public void visit(ast.stm.Instruction.ShlLong inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// a4: shr-long
	@Override
	public void visit(ast.stm.Instruction.ShrLong inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// a5: ushr-long
	@Override
	public void visit(ast.stm.Instruction.UshrLong inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// a6: add-float
	@Override
	public void visit(ast.stm.Instruction.AddFloat inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// a7: sub-float
	@Override
	public void visit(ast.stm.Instruction.SubFloat inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// a8: mul-float
	@Override
	public void visit(ast.stm.Instruction.MulFloat inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// a9: div-float
	@Override
	public void visit(ast.stm.Instruction.DivFloat inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// aa: rem-float
	@Override
	public void visit(ast.stm.Instruction.RemFloat inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// ab: add-double
	@Override
	public void visit(ast.stm.Instruction.AddDouble inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// ac: sub-double
	@Override
	public void visit(ast.stm.Instruction.SubDouble inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// ad: mul-double
	@Override
	public void visit(ast.stm.Instruction.MulDouble inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// ae: div-double
	@Override
	public void visit(ast.stm.Instruction.DivDouble inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	// af: rem-double
	@Override
	public void visit(ast.stm.Instruction.RemDouble inst) {
		emit(new sim.stm.Instruction.BinOp(inst.op, inst.dest, inst.first,
				inst.second), inst.op);
	}

	//
	// b0..cf 12x binop/2addr vA, vB

	// b0: add-int/2addr
	@Override
	public void visit(ast.stm.Instruction.AddInt2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("add-int", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// b1: sub-int/2addr
	@Override
	public void visit(ast.stm.Instruction.SubInt2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("sub-int", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// b2: mul-int/2addr
	@Override
	public void visit(ast.stm.Instruction.MulInt2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("mul-int", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// b3: div-int/2addr
	@Override
	public void visit(ast.stm.Instruction.DivInt2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("div-int", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// b4: rem-int/2addr
	@Override
	public void visit(ast.stm.Instruction.RemInt2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("rem-int", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// b5: and-int/2addr
	@Override
	public void visit(ast.stm.Instruction.AndInt2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("and-int", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// b6: or-int/2addr
	@Override
	public void visit(ast.stm.Instruction.OrInt2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("or-int", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// b7: xor-int/2addr
	@Override
	public void visit(ast.stm.Instruction.XorInt2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("xor-int", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// b8: shl-int/2addr
	@Override
	public void visit(ast.stm.Instruction.ShlInt2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("shl-int", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// b9: shr-int/2addr
	@Override
	public void visit(ast.stm.Instruction.ShrInt2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("shr-int", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// ba: ushr-int/2addr
	@Override
	public void visit(ast.stm.Instruction.UshrInt2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("ushr-int", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// bb: add-long/2addr
	@Override
	public void visit(ast.stm.Instruction.AddLong2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("add-long", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// bc: sub-long/2addr
	@Override
	public void visit(ast.stm.Instruction.SubLong2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("sub-long", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// bd: mul-long/2addr
	@Override
	public void visit(ast.stm.Instruction.MulLong2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("mul-long", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// be: div-long/2addr
	@Override
	public void visit(ast.stm.Instruction.DivLong2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("div-long", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// bf: rem-long/2addr
	@Override
	public void visit(ast.stm.Instruction.RemLong2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("rem-long", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// c0: and-long/2addr
	@Override
	public void visit(ast.stm.Instruction.AndLong2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("and-long", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// c1: or-long/2addr
	@Override
	public void visit(ast.stm.Instruction.OrLong2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("or-long", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// c2: xor-long/2addr
	@Override
	public void visit(ast.stm.Instruction.XorLong2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("xor-long", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// c3: shl-long/2addr
	@Override
	public void visit(ast.stm.Instruction.ShlLong2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("shl-long", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// c4: shr-long/2addr
	@Override
	public void visit(ast.stm.Instruction.ShrLong2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("shr-long", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// c5: ushr-long/2addr
	@Override
	public void visit(ast.stm.Instruction.UshrLong2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("ushr-long", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// c6: add-float/2addr
	@Override
	public void visit(ast.stm.Instruction.AddFloat2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("add-float", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// c7: sub-float/2addr
	@Override
	public void visit(ast.stm.Instruction.SubFloat2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("sub-float", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// c8: mul-float/2addr
	@Override
	public void visit(ast.stm.Instruction.MulFloat2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("mul-float", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// c9: div-float/2addr
	@Override
	public void visit(ast.stm.Instruction.DivFloat2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("div-float", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// ca: rem-float/2addr
	@Override
	public void visit(ast.stm.Instruction.RemFloat2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("rem-float", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// cb: add-double/2addr
	@Override
	public void visit(ast.stm.Instruction.AddDouble2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("add-double", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// cc: sub-double/2addr
	@Override
	public void visit(ast.stm.Instruction.SubDouble2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("sub-double", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// cd: mul-double/2addr
	@Override
	public void visit(ast.stm.Instruction.MulDouble2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("mul-double", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// ce: div-double/2addr
	@Override
	public void visit(ast.stm.Instruction.DivDouble2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("div-double", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	// cf: rem-double/2addr
	@Override
	public void visit(ast.stm.Instruction.RemDouble2Addr inst) {
		emit(new sim.stm.Instruction.BinOp("rem-double", inst.dest, inst.dest,
				inst.src), inst.op);
	}

	//
	// d0..d7 22s binop/lit16 vA, vB, #+CCCC
	// d0: add-int/lit16
	@Override
	public void visit(ast.stm.Instruction.AddIntLit16 inst) {
		emit(new sim.stm.Instruction.BinOpLit(inst.op, inst.dest, inst.src,
				inst.value), inst.op);
	}

	// d1: rsub-int (reverse subtract)
	@Override
	public void visit(ast.stm.Instruction.RsubInt inst) {
		emit(new sim.stm.Instruction.BinOpLit(inst.op, inst.dest, inst.src,
				inst.value), inst.op);
	}

	// d2: mul-int/lit16
	@Override
	public void visit(ast.stm.Instruction.MulIntLit16 inst) {
		emit(new sim.stm.Instruction.BinOpLit(inst.op, inst.dest, inst.src,
				inst.value), inst.op);
	}

	// d3: div-int/lit16
	@Override
	public void visit(ast.stm.Instruction.DivIntLit16 inst) {
		emit(new sim.stm.Instruction.BinOpLit(inst.op, inst.dest, inst.src,
				inst.value), inst.op);
	}

	// d4: rem-int/lit16
	@Override
	public void visit(ast.stm.Instruction.RemIntLit16 inst) {
		emit(new sim.stm.Instruction.BinOpLit(inst.op, inst.dest, inst.src,
				inst.value), inst.op);
	}

	// d5: and-int/lit16
	@Override
	public void visit(ast.stm.Instruction.AndIntLit16 inst) {
		emit(new sim.stm.Instruction.BinOpLit(inst.op, inst.dest, inst.src,
				inst.value), inst.op);
	}

	// d6: or-int/lit16
	@Override
	public void visit(ast.stm.Instruction.OrIntLit16 inst) {
		emit(new sim.stm.Instruction.BinOpLit(inst.op, inst.dest, inst.src,
				inst.value), inst.op);
	}

	// d7: xor-int/lit16
	@Override
	public void visit(ast.stm.Instruction.XorIntLit16 inst) {
		emit(new sim.stm.Instruction.BinOpLit(inst.op, inst.dest, inst.src,
				inst.value), inst.op);
	}

	//
	// d8..e2 22b binop/lit8 vAA, vBB, #+CC
	// d8: add-int/lit8
	@Override
	public void visit(ast.stm.Instruction.AddIntLit8 inst) {
		emit(new sim.stm.Instruction.BinOpLit("add-int/lit16", inst.dest,
				inst.src, inst.value), inst.op);
	}

	// d9: rsub-int/lit8
	@Override
	public void visit(ast.stm.Instruction.RsubIntLit8 inst) {
		emit(new sim.stm.Instruction.BinOpLit("rsub-int/lit16", inst.dest,
				inst.src, inst.value), inst.op);
	}

	// da: mul-int/lit8
	@Override
	public void visit(ast.stm.Instruction.MulIntLit8 inst) {
		emit(new sim.stm.Instruction.BinOpLit("mul-int/lit16", inst.dest,
				inst.src, inst.value), inst.op);
	}

	// db: div-int/lit8
	@Override
	public void visit(ast.stm.Instruction.DivIntLit8 inst) {
		emit(new sim.stm.Instruction.BinOpLit("div-int/lit16", inst.dest,
				inst.src, inst.value), inst.op);
	}

	// dc: rem-int/lit8
	@Override
	public void visit(ast.stm.Instruction.RemIntLit8 inst) {
		emit(new sim.stm.Instruction.BinOpLit("rem-int/lit16", inst.dest,
				inst.src, inst.value), inst.op);
	}

	// dd: and-int/lit8
	@Override
	public void visit(ast.stm.Instruction.AndIntLit8 inst) {
		emit(new sim.stm.Instruction.BinOpLit("and-int/lit16", inst.dest,
				inst.src, inst.value), inst.op);
	}

	// de: or-int/lit8
	@Override
	public void visit(ast.stm.Instruction.OrIntLit8 inst) {
		emit(new sim.stm.Instruction.BinOpLit("or-int/lit16", inst.dest,
				inst.src, inst.value), inst.op);
	}

	// df: xor-int/lit8
	@Override
	public void visit(ast.stm.Instruction.XorIntLit8 inst) {
		emit(new sim.stm.Instruction.BinOpLit("xor-int/lit16", inst.dest,
				inst.src, inst.value), inst.op);
	}

	// e0: shl-int/lit8
	@Override
	public void visit(ast.stm.Instruction.ShlIntLit8 inst) {
		emit(new sim.stm.Instruction.BinOpLit(inst.op, inst.dest, inst.src,
				inst.value), inst.op);
	}

	// e1: shr-int/lit8
	@Override
	public void visit(ast.stm.Instruction.ShrIntLit8 inst) {
		emit(new sim.stm.Instruction.BinOpLit(inst.op, inst.dest, inst.src,
				inst.value), inst.op);
	}

	// e2: ushr-int/lit8
	@Override
	public void visit(ast.stm.Instruction.UshrIntLit8 inst) {
		emit(new sim.stm.Instruction.BinOpLit(inst.op, inst.dest, inst.src,
				inst.value), inst.op);
	}

	@Override
	public void visit(ast.stm.Instruction instruction) {
		throw new RuntimeException(
				"visiting ast.stm.Instruction?! are you out of your mind?");
	}

	@Override
	public void visit(ast.stm.Instruction.ArrayDataDirective inst) {
		method.statements.add(new sim.stm.Instruction.ArrayDataDirective(
				inst.size, inst.elementList));
	}

	@Override
	public void visit(ast.stm.Instruction.PackedSwitchDirective inst) {
		method.statements.add(new sim.stm.Instruction.PackedSwitchDirective(
				inst.key, inst.count, inst.labList));
	}

	@Override
	public void visit(ast.stm.Instruction.SparseSwitchDirective inst) {
		method.statements.add(new sim.stm.Instruction.SparseSwitchDirective(
				inst.count, inst.keyList, inst.labList));
	}
}
