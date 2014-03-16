package sym;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import sim.method.Method;
import sym.SymbolicExecutor.ThreadSafeFileWriter;
import sym.op.IOp;
import sym.pred.IPrediction;
import clojure.lang.IPersistentMap;
import clojure.lang.PersistentVector;

/* *
 * This class is helper class for symbolic execution, execute the whole
 * basic block, create another Kagebunsin upon branch
 * */
public class Kagebunsin implements Runnable {
	public static final int countThreshold = 4;
	private Z3Stub z3;
	private ThreadPoolExecutor executor;
	private HashMap<String, AtomicInteger> labelCount;
	private final Method currentMethod;
	private int pc;
	private IPersistentMap mapToSym; //register to their value(represented by sym.op.IOp)
	private PersistentVector conditions; //vector of IPrediction
	private ThreadSafeFileWriter writer;

	public Kagebunsin(Z3Stub z3, ThreadPoolExecutor executor,
			HashMap<String, AtomicInteger> labelCount, Method currentMethod,
			int pc, IPersistentMap mapToSym, PersistentVector conditions,
			ThreadSafeFileWriter writer) {
		this.z3 = z3;
		this.executor = executor;
		this.labelCount = labelCount;
		this.currentMethod = currentMethod;
		this.pc = pc;
		this.mapToSym = mapToSym;
		this.conditions = conditions;
		this.writer = writer;
	}

	private void unknow(sim.stm.T i) {
		System.err.format("unknow instruction %s in method %s\n",
				i.getClass().getName(), currentMethod.name);
	}

	private void unsupport(sim.stm.T i) {
		System.err.format("unsupport instruction %s in method %s\n",
				i.getClass().getName(), currentMethod.name);
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
		while (it.hasNext()) {
			sym.pred.IPrediction cond = (IPrediction) it.next();
			sb.append(cond.toString());
			sb.append(" ");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}

	@Override
	public void run() {
		for (; pc < currentMethod.statements.size(); pc++) {
			sim.stm.T currentInstruction = currentMethod.statements.get(pc);
			// sort according to instruction popularity
			if (currentInstruction instanceof sim.stm.Instruction.Invoke) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.MoveResult) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.Iget) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.Const) {
				sim.stm.Instruction.Const ci = (sim.stm.Instruction.Const) currentInstruction;
				switch (ci.op) {
				case "const":
					mapToSym = mapToSym.assoc(ci.dst, new sym.op.Const(
							hex2long(ci.value)));
					break;
				default:
					unsupport(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.ReturnVoid) {
				return; //abort executing
			} else if (currentInstruction instanceof sim.stm.Instruction.NewInstance) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.Goto) {
				sim.stm.Instruction.Goto ci = (sim.stm.Instruction.Goto) currentInstruction;
				if (labelCount.get(ci.label).addAndGet(1) < countThreshold) {
					pc = currentMethod.labels.get(ci.label) - 1;
					continue;
				} else
					return; //abort executing when count exceed countThreshold
			} else if (currentInstruction instanceof sim.stm.Instruction.IfTestz) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.Iput) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.CheckCast) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.Return) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.Sget) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.CheckCast) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.Move) {
				unsupport(currentInstruction);
				return;
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
					writer.writeln("trying to index '" + array + "'[" + index
							+ "] under condition " + andAllCond());
					continue;
				default:
					unknow(currentInstruction);
					return;
				}
			} else if (currentInstruction instanceof sim.stm.Instruction.BinOpLit) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.UnOp) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.BinOp) {
				unsupport(currentInstruction);
				return;
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
					unsupport(currentInstruction);
					return;
				}
				// condition to walk true branch
				PersistentVector rc = conditions.cons(r);

				// we walk the false branch
				conditions = conditions.cons(new sym.pred.Not(r));

				//Kagebunsin no jyutu!
				if (labelCount.get(ci.label).addAndGet(1) < countThreshold) {
					executor.submit(new Kagebunsin(z3, executor, labelCount,
							currentMethod, currentMethod.labels.get(ci.label),
							mapToSym, rc, writer));
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
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.Sput) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.Aget) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.ArrayLength) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.Monitor) {
				unsupport(currentInstruction);
				return;
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