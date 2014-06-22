package sym;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import control.Control;
import sim.method.Method;
import sym.Z3Stub.Z3Result;
import sym.op.IOp;
import sym.op.Obj;
import sym.pred.IPrediction;
import clojure.lang.IPersistentMap;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentVector;

/* *
 * This class is helper class for symbolic execution, execute the whole
 * basic block, create another Kagebunsin upon branch
 * */
public class Kagebunsin implements Runnable {
	public static final int countThreshold = 4;
	private sym.SymbolicExecutor executor;
	private HashMap<String, AtomicInteger> labelCount;
	private final String startClassName; // this help us have no reference to big obj
	private sim.classs.Class clazz;
	private Method currentMethod;
	private int pc;
	private IPersistentMap mapToSym; // register to their IOp(represented by
										// sym.op.IOp)
	private PersistentVector conditions; // vector of IPrediction
	private final sym.SymGenerator symGen;
	private PersistentVector stack; // stack of StackItem
	private sym.op.IOp result; // result returned from last method

	public Kagebunsin(sym.SymbolicExecutor executor,
			HashMap<String, AtomicInteger> labelCount, String startClassName,
			Method currentMethod, int pc, IPersistentMap mapToSym,
			PersistentVector conditions, sym.SymGenerator symGen,
			PersistentVector stack) {
		this.executor = executor;
		this.labelCount = labelCount;
		this.startClassName = startClassName;
		this.currentMethod = currentMethod;
		this.pc = pc;
		this.mapToSym = mapToSym;
		this.conditions = conditions;
		this.symGen = symGen;
		this.stack = stack;
	}

	private void unknow(sim.stm.T i) {
		executor.printlnErr(String.format(
				"unknow instruction %s in method %s\n", i.getClass().getName(),
				currentMethod.name));
	}

	private void unsupport(sim.stm.T i) {
		executor.printlnErr(String.format(
				"unsupport instruction %s in method %s\n",
				i.getClass().getName(), currentMethod.name));
	}

	public static long hex2long(String s) {
		if (s.endsWith("L")) {
			s = s.substring(0, s.indexOf("L"));
			if (s.startsWith("-"))
				return -(Long.parseLong(s.substring(3), 16));
			else
				return Long.parseLong(s.substring(2), 16);
		} else {
			if (s.startsWith("-"))
				return -(Integer.parseInt(s.substring(3), 16));
			else
				return Integer.parseInt(s.substring(2), 16);
		}
	}

	private IOp getSym(String reg) {
		return (IOp) mapToSym.valAt(reg);
	}

	@SuppressWarnings("rawtypes")
	private String andAllCond() {
		Iterator it = conditions.iterator();
		StringBuilder sb = new StringBuilder("(and ");
		if (!it.hasNext())
			sb.append(" ");
		while (it.hasNext()) {
			sym.pred.IPrediction cond = (IPrediction) it.next();
			sb.append(cond.toString());
			sb.append(" ");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}

	// return true on success, false on error
	private boolean invoke(sim.stm.Instruction.Invoke invoke) {
		String className = invoke.method.classType;
		sim.classs.Class clazz = executor.getClass(className.substring(1,
				className.length() - 1));
		if (clazz == null)
			return false;
		sim.method.Method m = executor.getMethod(clazz, invoke.method);
		if (m == null)
			return false;

		// target method found

		IPersistentMap pReg = PersistentHashMap.EMPTY;
		// arguments is stored at p{0..} register
		for (int index = 0; index < invoke.args.size(); index++) {
			String src = invoke.args.get(index);
			String reg = "p" + index;

			pReg = pReg.assoc(reg, mapToSym.valAt(src));
		}

		stack = stack.cons(new StackItem(clazz.name, currentMethod, pc,
				mapToSym, labelCount));

		this.clazz = clazz;
		this.currentMethod = m;
		this.pc = -1;
		this.mapToSym = pReg;
		this.labelCount = new HashMap<String, AtomicInteger>();
		for (sim.method.Method.Label label : m.labelList) {
			labelCount.put(label.lab, new AtomicInteger(0));
		}

		return true;
	}

	private void popStack() {
		StackItem item = (StackItem) stack.peek();
		this.clazz = executor.getClass(item.clazzName.substring(1,
				item.clazzName.length() - 1));
		this.currentMethod = item.method;
		this.pc = item.pc; // isn't `item.pc - 1`
		this.mapToSym = item.mapToSym;
		this.labelCount = item.labelCount;
		stack = stack.pop();
	}

	private static String getMethodString(sim.classs.MethodItem method) {
		int len = method.classType.length();
		return method.classType.substring(1, len - 1) + "." + method.methodName;
	}

	private String getPReg(IPersistentMap mapToSym) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0;; i++) {
			sym.op.IOp op = (IOp) mapToSym.valAt("p" + i);
			if (op == null)
				break;
			else {
				sb.append(op);
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	private String getMethodString(String className, Method method) {
		int len = className.length();
		return className.substring(1, len - 1) + "." + method.name;
	}

	private void printSymStackTrace(String method) {
		synchronized (executor) {
			executor.apiWrite(String.format(
					"----- stack trace for call to %s, most recent call first-----\n",
					method));

			executor.apiWrite("path condition is " + this.andAllCond() + "\n");

			executor.apiWrite(String.format("%s(%s) at pc %d\n",
					getMethodString(this.clazz.name, this.currentMethod),
					getPReg(mapToSym), this.pc));

			@SuppressWarnings("unchecked")
			Iterator<StackItem> it = stack.iterator();

			while (it.hasNext()) {
				StackItem item = it.next();
				executor.apiWrite(String.format("%s(%s) at pc %d\n",
						getMethodString(item.clazzName, item.method),
						getPReg(mapToSym), item.pc));
			}
			executor.apiWrite("\n");
		}
	}

	@Override
	public void run() {
		clazz = executor.getClass(startClassName.substring(1,
				startClassName.length() - 1));
		for (; pc < currentMethod.statements.size(); pc++) {
			sim.stm.T currentInstruction = currentMethod.statements.get(pc);
			if (control.Control.debug) {
				executor.println(String.format(
						"instruction to be execute is %s, current mapToSym is %s\n",
						currentInstruction.toString(), mapToSym));
			}
			// sort according to instruction popularity
			if (currentInstruction instanceof sim.stm.Instruction.Invoke) {
				sim.stm.Instruction.Invoke ci = (sim.stm.Instruction.Invoke) currentInstruction;

				String method = getMethodString(ci.method);
				if (Control.interestAPI.contains(method)) {
					printSymStackTrace(method);
					return; // abort execution
				}

				boolean succ;
				switch (ci.op) {
				case "invoke-direct":
					if (ci.method.classType.equals("Ljava/lang/Object;")
							&& ci.method.methodName.equals("<init>"))
						continue;
				case "invoke-static":
					String className = ci.method.classType;
					className = className.substring(1, className.length() - 1);
					executor.getClass(className); // to init the class

					// NOTE: we also handle invoke-direct here
					succ = invoke(ci);
					if (succ)
						continue;
					else {
						executor.printlnErr("not found " + ci.method);
						return; // abort execution
					}
				case "invoke-interface":
				case "invoke-virtual":
				case "invoke-super":
					IOp obj = (IOp) mapToSym.valAt(ci.args.get(0));
					if (obj == null || obj instanceof sym.op.Const) {
						String diagnose = String.format(
								"NullRef: %s.%s at pc %d, %s on null obj under condition %s",
								clazz.name, currentMethod.name, pc, ci.op,
								andAllCond());
						executor.writeln(diagnose);
					} else {
						succ = invoke(ci);
						if (succ)
							continue;
						else {
							executor.printlnErr("not found method " + ci.method
									+ " in invoke-virtual etc, abort");
							return; // abort execution
						}
					}
					return;
				case "invoke-virtual/range":
				case "invoke-super/range":
				case "invoke-direct/range":
				case "invoke-static/range":
				case "invoke-interface/range":
				default:
					unsupport(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.MoveResult) {
				sim.stm.Instruction.MoveResult ci = (sim.stm.Instruction.MoveResult) currentInstruction;
				switch (ci.op) {
				case "move-result":
				case "move-result-wide":
				case "move-result-object":
					mapToSym = mapToSym.assoc(ci.dst, result);
					continue;
				case "move-exception":
				default:
					unsupport(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.Iget) {
				sim.stm.Instruction.Iget ci = (sim.stm.Instruction.Iget) currentInstruction;
				switch (ci.op) {
				case "iget":
				case "iget-wide":
				case "iget-object":
				case "iget-boolean":
				case "iget-byte":
				case "iget-char":
				case "iget-short":
					sym.op.IOp value = (IOp) mapToSym.valAt(ci.obj);
					if (value instanceof sym.op.Const
							&& ((sym.op.Const) value).value == 0) {
						String diagnose = String.format(
								"NullRef: %s.%s at pc %d, %s on null obj under condition %s",
								clazz.name, currentMethod.name, pc, ci.op,
								andAllCond());
						executor.writeln(diagnose);
					}
					sym.op.Obj obj = (sym.op.Obj) value;
					IOp result = obj.iget(ci.field.fieldName);
					// TODO if ci is iget-object then it possible that result is null
					if (result == null) {
						String diagnose = String.format(
								"GetNull: %s.%s at pc %d, %s returns null under condition %s",
								clazz.name, currentMethod.name, pc, ci.op,
								andAllCond());
						executor.writeln(diagnose);
						return;
					}
					mapToSym = mapToSym.assoc(ci.dst, result);
					continue;
				default:
					unknow(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.Const) {
				sim.stm.Instruction.Const ci = (sim.stm.Instruction.Const) currentInstruction;
				switch (ci.op) {
				case "const":
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Const(
							hex2long(ci.value)));
					continue;
				case "const-string/jumbo":
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Str(ci.value));
					continue;
				default:
					unsupport(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.ReturnVoid) {
				if (stack.count() == 0) {
					return; // abort executing
				} else {
					popStack();
					continue;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.NewInstance) {
				sim.stm.Instruction.NewInstance ci = (sim.stm.Instruction.NewInstance) currentInstruction;

				String className = ci.type.substring(1, ci.type.length() - 1);
				executor.getClass(className); // to init the class

				mapToSym = mapToSym.assoc(ci.dst, new sym.op.Obj(ci.type));
				// NOTE we shouldn't call its constructor here
				// dalvik will generate invoke-direct for us
				continue;
			} else if (currentInstruction instanceof sim.stm.Instruction.Goto) {
				sim.stm.Instruction.Goto ci = (sim.stm.Instruction.Goto) currentInstruction;
				if (labelCount.get(ci.label).addAndGet(1) < countThreshold) {
					pc = currentMethod.labels.get(ci.label) - 1;
					continue;
				} else
					return; // abort executing when count exceed countThreshold
			} else if (currentInstruction instanceof sim.stm.Instruction.IfTestz) {
				sim.stm.Instruction.IfTestz ci = (sim.stm.Instruction.IfTestz) currentInstruction;
				sym.pred.IPrediction r;
				switch (ci.op) {
				case "if-eqz":
					r = new sym.pred.Eq(getSym(ci.src), new sym.op.Const(0));
					break;
				case "if-nez":
					r = new sym.pred.Ne(getSym(ci.src), new sym.op.Const(0));
					break;
				case "if-ltz":
					r = new sym.pred.Lt(getSym(ci.src), new sym.op.Const(0));
					break;
				case "if-gez":
					r = new sym.pred.Ge(getSym(ci.src), new sym.op.Const(0));
					break;
				case "if-gtz":
					r = new sym.pred.Gt(getSym(ci.src), new sym.op.Const(0));
					break;
				case "if-lez":
					r = new sym.pred.Le(getSym(ci.src), new sym.op.Const(0));
					break;
				default:
					unknow(currentInstruction);
					return;
				}
				// condition to walk true branch
				PersistentVector rc = conditions.cons(r);

				// we walk the false branch
				conditions = conditions.cons(new sym.pred.Not(r));

				// Kagebunsin no jyutu!
				if (labelCount.get(ci.label).addAndGet(1) < countThreshold) {
					Z3Result z3result = executor.calculate(symGen.types, rc);
					if (z3result.satOrNot) {
						executor.submit(new Kagebunsin(executor, labelCount,
								clazz.name, currentMethod,
								currentMethod.labels.get(ci.label), mapToSym,
								rc, symGen.clone(), stack));
					}
				}

				Z3Result z3result = executor.calculate(symGen.types, conditions);
				if (!z3result.satOrNot) {
					return;
				}

				continue;
			} else if (currentInstruction instanceof sim.stm.Instruction.Iput) {
				sim.stm.Instruction.Iput ci = (sim.stm.Instruction.Iput) currentInstruction;

				switch (ci.op) {
				case "iput":
				case "iput-wide":
				case "iput-object":
				case "iput-boolean":
				case "iput-byte":
				case "iput-char":
				case "iput-short":
					sym.op.IOp v = (IOp) mapToSym.valAt(ci.obj);
					if (v instanceof sym.op.Const
							&& ((sym.op.Const) v).value == 0) {
						String diagnose = String.format(
								"NullRef: %s.%s at pc %d, %s on null obj under condition %s",
								clazz.name, currentMethod.name, pc, ci.op,
								andAllCond());
						executor.writeln(diagnose);
					}
					sym.op.Obj obj = (Obj) v;
					sym.op.IOp value = (IOp) mapToSym.valAt(ci.src);
					obj.iput(ci.field.fieldName, value);
					continue;
				default:
					unsupport(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.CheckCast) {
				continue; // TODO we should check its type or super type
			} else if (currentInstruction instanceof sim.stm.Instruction.Return) {
				sim.stm.Instruction.Return ci = (sim.stm.Instruction.Return) currentInstruction;
				switch (ci.op) {
				case "return":
				case "return-wide":
				case "return-object":
					result = (IOp) mapToSym.valAt(ci.src);
					if (stack.count() == 0) {
						return; // abort executing
					} else {
						popStack();
						continue;
					}
				default:
					unsupport(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.Sget) {
				sim.stm.Instruction.Sget ci = (sim.stm.Instruction.Sget) currentInstruction;
				switch (ci.op) {
				case "sget":
				case "sget-wide":
				case "sget-object":
				case "sget-boolean":
				case "sget-byte":
				case "sget-char":
				case "sget-short":
					String className = ci.field.classType;
					className = className.substring(1, className.length() - 1);

					sym.op.IOp r = executor.sget(className, ci.field.fieldName);
					if (r == null) {
						String diagnose = String.format(
								"GetNull: %s.%s at pc %d, %s get null under condition %s",
								clazz.name, currentMethod.name, pc, ci.op,
								andAllCond());
						executor.writeln(diagnose);
					}
					mapToSym = mapToSym.assoc(ci.dst, r);
					continue;
				default:
					unsupport(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.Move) {
				sim.stm.Instruction.Move ci = (sim.stm.Instruction.Move) currentInstruction;
				switch (ci.op) {
				case "move/16":
				case "move-wide/16":
				case "move-object/16":
					mapToSym.assoc(ci.dst, getSym(ci.src));
					continue;
				default:
					unknow(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.Aput) {
				sim.stm.Instruction.Aput ci = (sim.stm.Instruction.Aput) currentInstruction;

				// TODO we should also check it's type
				switch (ci.op) {
				case "aput":
				case "aput-wide":
				case "aput-object":
				case "aput-boolean":
				case "aput-byte":
				case "aput-char":
				case "aput-short":
					// current ignore src
					// IOp src = getSym(ci.src);
					IOp index = getSym(ci.index);
					IOp array = getSym(ci.array);

					sym.pred.IPrediction r;
					sym.pred.IPrediction r1;
					r = new sym.pred.Ge(index, ((sym.op.Array) array).length);
					r1 = new sym.pred.Ge(((sym.op.Array) array).length,
							new sym.op.Const(1));
					Z3Result z3result = executor.calculate(symGen.types,
							conditions.cons(r).cons(r1));
					if (z3result.satOrNot) {
						String diagnose = String.format(
								"OOB: %s.%s at pc %d, %s index '%s'[%s] under condition %s\n",
								clazz.name, currentMethod.name, pc, ci.op,
								array, index, andAllCond());
						executor.writeln(diagnose + "    " + z3result);
					}
					continue;
				default:
					unknow(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.BinOpLit) {
				sim.stm.Instruction.BinOpLit ci = (sim.stm.Instruction.BinOpLit) currentInstruction;
				IOp left;
				IOp right;
				switch (ci.op) {
				case "add-int/lit16":
					left = getSym(ci.src);
					right = new sym.op.Const(hex2long(ci.constt));
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Add(left,
							right));
					continue;
				case "rsub-int":
					left = getSym(ci.src);
					right = new sym.op.Const(hex2long(ci.constt));
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Minus(right,
							left));
					continue;
				case "mul-int/lit16":
					left = getSym(ci.src);
					right = new sym.op.Const(hex2long(ci.constt));
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Mul(left,
							right));
					continue;
				case "div-int/lit16":
					left = getSym(ci.src);
					right = new sym.op.Const(hex2long(ci.constt));
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Div(left,
							right));
					continue;
				case "rem-int/lit16":
					left = getSym(ci.src);
					right = new sym.op.Const(hex2long(ci.constt));
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Rem(left,
							right));
					continue;
				case "add-int/lit8":
					left = getSym(ci.src);
					right = new sym.op.Const(hex2long(ci.constt));
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Add(left,
							right));
					continue;
				case "rsub-int/lit8":
					left = getSym(ci.src);
					right = new sym.op.Const(hex2long(ci.constt));
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Minus(right,
							left));
					continue;
				case "mul-int/lit8":
					left = getSym(ci.src);
					right = new sym.op.Const(hex2long(ci.constt));
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Mul(left,
							right));
					continue;
				case "div-int/lit8":
					left = getSym(ci.src);
					right = new sym.op.Const(hex2long(ci.constt));
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Div(left,
							right));
					continue;
				case "rem-int/lit8":
					left = getSym(ci.src);
					right = new sym.op.Const(hex2long(ci.constt));
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Rem(left,
							right));
					continue;
				default:
					unsupport(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.UnOp) {
				sim.stm.Instruction.UnOp ci = (sim.stm.Instruction.UnOp) currentInstruction;
				switch (ci.op) {
				case "neg-int":
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Minus(
							new sym.op.Const(0), getSym(ci.src)));
					continue;
				default:
					unsupport(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.BinOp) {
				sim.stm.Instruction.BinOp ci = (sim.stm.Instruction.BinOp) currentInstruction;
				IOp left;
				IOp right;
				switch (ci.op) {
				case "add-int":
					left = getSym(ci.firstSrc);
					right = getSym(ci.secondSrc);
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Add(left,
							right));
					continue;
				case "sub-int":
					left = getSym(ci.firstSrc);
					right = getSym(ci.secondSrc);
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Minus(left,
							right));
					continue;
				case "mul-int":
					left = getSym(ci.firstSrc);
					right = getSym(ci.secondSrc);
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Mul(left,
							right));
					continue;
				case "div-int":
					left = getSym(ci.firstSrc);
					right = getSym(ci.secondSrc);
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Div(left,
							right));
					continue;
				case "rem-int":
					left = getSym(ci.firstSrc);
					right = getSym(ci.secondSrc);
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Rem(left,
							right));
					continue;
				default:
					unsupport(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.FilledNewArray) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.IfTest) {
				sim.stm.Instruction.IfTest ci = (sim.stm.Instruction.IfTest) currentInstruction;
				sym.pred.IPrediction r;
				switch (ci.op) {
				case "if-eq":
					r = new sym.pred.Eq(getSym(ci.firstSrc),
							getSym(ci.secondSrc));
					break;
				case "if-ne":
					r = new sym.pred.Ne(getSym(ci.firstSrc),
							getSym(ci.secondSrc));
					break;
				case "if-lt":
					r = new sym.pred.Lt(getSym(ci.firstSrc),
							getSym(ci.secondSrc));
					break;
				case "if-ge":
					r = new sym.pred.Ge(getSym(ci.firstSrc),
							getSym(ci.secondSrc));
					break;
				case "if-gt":
					r = new sym.pred.Gt(getSym(ci.firstSrc),
							getSym(ci.secondSrc));
					break;
				case "if-le":
					r = new sym.pred.Le(getSym(ci.firstSrc),
							getSym(ci.secondSrc));
					break;
				default:
					unknow(currentInstruction);
					return;
				}
				// condition to walk true branch
				PersistentVector rc = conditions.cons(r);

				// we walk the false branch
				conditions = conditions.cons(new sym.pred.Not(r));

				// Kagebunsin no jyutu!
				if (labelCount.get(ci.label).addAndGet(1) < countThreshold) {
					Z3Result z3result = executor.calculate(symGen.types, rc);

					if (z3result.satOrNot) {
						executor.submit(new Kagebunsin(executor, labelCount,
								clazz.name, currentMethod,
								currentMethod.labels.get(ci.label), mapToSym,
								rc, symGen.clone(), stack));
					}
				}

				Z3Result z3result = executor.calculate(symGen.types, conditions);
				if (!z3result.satOrNot) {
					return;
				}

				continue;
			} else if (currentInstruction instanceof sim.stm.Instruction.NewArray) {
				sim.stm.Instruction.NewArray ci = (sim.stm.Instruction.NewArray) currentInstruction;
				IOp len = getSym(ci.size);
				assert len != null;

				mapToSym = mapToSym.assoc(ci.dst,
						new sym.op.Array(ci.type, len));
				continue;
			} else if (currentInstruction instanceof sim.stm.Instruction.Throw) {
				return; // abort execution TODO add support for catching it
			} else if (currentInstruction instanceof sim.stm.Instruction.Sput) {
				sim.stm.Instruction.Sput ci = (sim.stm.Instruction.Sput) currentInstruction;

				switch (ci.op) {
				case "sput":
				case "sput-wide":
				case "sput-object":
				case "sput-boolean":
				case "sput-byte":
				case "sput-char":
				case "sput-short":
					String className = ci.field.classType;
					className = className.substring(1, className.length() - 1);

					executor.sput(className, ci.field.fieldName,
							(IOp) mapToSym.valAt(ci.src));
					continue;
				default:
					unsupport(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.Aget) {
				sim.stm.Instruction.Aget ci = (sim.stm.Instruction.Aget) currentInstruction;

				// TODO we should also check it's type
				switch (ci.op) {
				case "aget":
				case "aget-wide":
				case "aget-object":
				case "aget-boolean":
				case "aget-byte":
				case "aget-char":
				case "aget-short":
					// current ignore src
					// IOp src = getSym(ci.src);
					IOp index = getSym(ci.index);
					IOp array = getSym(ci.array);
					sym.pred.IPrediction r;
					sym.pred.IPrediction r1;
					r = new sym.pred.Ge(index, ((sym.op.Array) array).length);
					r1 = new sym.pred.Ge(((sym.op.Array) array).length,
							new sym.op.Const(1));
					Z3Result z3result = executor.calculate(symGen.types,
							conditions.cons(r).cons(r1));
					if (z3result.satOrNot) {
						String diagnose = String.format(
								"OOB: %s.%s at pc %d, %s index '%s'[%s] under condition %s\n",
								clazz.name, currentMethod.name, pc, ci.op,
								array, index, andAllCond());
						executor.writeln(diagnose + "    " + z3result);
					}
					continue;
				default:
					unknow(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.ArrayLength) {
				sim.stm.Instruction.ArrayLength ci = (sim.stm.Instruction.ArrayLength) currentInstruction;
				IOp array = getSym(ci.ref);
				mapToSym = mapToSym.assoc(ci.dst, ((sym.op.Array) array).length);
				continue;
			} else if (currentInstruction instanceof sim.stm.Instruction.Monitor) {
				continue;
			} else if (currentInstruction instanceof sim.stm.Instruction.InstanceOf) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.Cmp) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.PackedSwitchDirective) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.SparseSwitchDirective) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.FillArrayData) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.Nop) {
				continue;
			} else {
				unknow(currentInstruction);
				return;
			}
		}
	}
}