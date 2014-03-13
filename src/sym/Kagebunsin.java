package sym;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import sim.method.Method;
import sym.SymbolicExecutor.ThreadSafeFileWriter;
import clojure.lang.IPersistentMap;
import clojure.lang.PersistentVector;

/* *
 * This class is helper class for symbolic execution, execute the whole
 * basic block, create another Kagebunsin upon branch
 * */
public class Kagebunsin implements Runnable {
	public static final int countThreshold = 3;
	private Z3Stub z3;
	private ExecutorService executor;
	private ConcurrentHashMap<String, AtomicInteger> labelCount;
	private final Method currentMethod;
	private int pc;
	private IPersistentMap mapToSym; //register to their value(represented by sym.op.IOp)
	private PersistentVector conditions; //vector of IPrediction
	private ThreadSafeFileWriter writer;

	public Kagebunsin(Z3Stub z3, ExecutorService executor,
			ConcurrentHashMap<String, AtomicInteger> labelCount,
			Method currentMethod, int pc, IPersistentMap mapToSym,
			PersistentVector conditions, ThreadSafeFileWriter writer) {
		this.z3 = z3;
		this.executor = executor;
		this.labelCount = labelCount;
		this.currentMethod = currentMethod;
		this.pc = pc;
		this.mapToSym = mapToSym;
		this.conditions = conditions;
		this.writer = writer;
	}

	private void unsupport(sim.stm.T i) {
		System.err.format("unknow instrcution %s in method %s\n", i.getClass()
				.getName(), currentMethod.name);
	}

	@Override
	public void run() {
		for (sim.stm.T currentInstruction = currentMethod.statements.get(pc);; pc++) {
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
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.ReturnVoid) {
				return; //abort executing
			} else if (currentInstruction instanceof sim.stm.Instruction.NewInstance) {
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.Goto) {
				sim.stm.Instruction.Goto ci = (sim.stm.Instruction.Goto) currentInstruction;
				if (labelCount.get(ci.label).addAndGet(1) > sym.SymbolicExecutor.threadPoolSize)
					return; //abort executing when count exceed threshold
				else {
					//FIXME pc is unsure
					pc = currentMethod.labels.get(ci.label) - 1;
					continue;
				}
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
				unsupport(currentInstruction);
				return;
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
				unsupport(currentInstruction);
				return;
			} else if (currentInstruction instanceof sim.stm.Instruction.NewArray) {
				unsupport(currentInstruction);
				return;
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
				System.err.format("unknow instrcution %s in method %s\n",
						currentInstruction.getClass().getName(),
						currentMethod.name);
				return;
			}
		}
	}
}