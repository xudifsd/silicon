package sim.stm;

import java.util.List;

import sim.Visitor;
import sim.classs.FieldItem;
import sim.classs.MethodItem;

public class Instruction {
	public static class Nop extends T {
		private static final long serialVersionUID = 1L;

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Move extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String dst;
		public String src;

		public Move(String op, String dst, String src) {
			super();
			this.op = op.intern();
			this.dst = dst.intern();
			this.src = src.intern();
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class MoveResult extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String dst;

		public MoveResult(String op, String dst) {
			super();
			this.op = op.intern();
			this.dst = dst.intern();
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class ReturnVoid extends T {
		private static final long serialVersionUID = 1L;

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Return extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String src;

		public Return(String op, String src) {
			super();
			this.op = op.intern();
			this.src = src.intern();
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Const extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String dst;
		public String value;

		public Const(String op, String dst, String value) {
			super();
			this.op = op.intern();
			this.dst = dst.intern();
			this.value = value;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Monitor extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String ref;

		public Monitor(String op, String ref) {
			super();
			this.op = op.intern();
			this.ref = ref;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class CheckCast extends T {
		private static final long serialVersionUID = 1L;
		public String ref;
		public String type;

		public CheckCast(String ref, String type) {
			super();
			this.ref = ref;
			this.type = type;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class InstanceOf extends T {
		private static final long serialVersionUID = 1L;
		public String dst;
		public String ref;
		public String type;

		public InstanceOf(String dst, String ref, String type) {
			super();
			this.dst = dst.intern();
			this.ref = ref;
			this.type = type;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class ArrayLength extends T {
		private static final long serialVersionUID = 1L;
		public String dst;
		public String ref;

		public ArrayLength(String dst, String ref) {
			super();
			this.dst = dst.intern();
			this.ref = ref;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class NewInstance extends T {
		private static final long serialVersionUID = 1L;
		public String dst;
		public String type;

		public NewInstance(String dst, String type) {
			super();
			this.dst = dst.intern();
			this.type = type;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class NewArray extends T {
		private static final long serialVersionUID = 1L;
		public String dst;
		public String size;
		public String type;

		public NewArray(String dst, String size, String type) {
			super();
			this.dst = dst.intern();
			this.size = size.intern();
			this.type = type;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class FilledNewArray extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public List<String> argList;
		public String type;

		public FilledNewArray(String op, List<String> argList, String type) {
			super();
			this.op = op.intern();
			this.argList = argList;
			this.type = type;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class FillArrayData extends T {
		private static final long serialVersionUID = 1L;
		public String ref;
		public String label;

		public FillArrayData(String ref, String label) {
			super();
			this.ref = ref;
			this.label = label;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Throw extends T {
		private static final long serialVersionUID = 1L;
		public String exception;

		public Throw(String exception) {
			super();
			this.exception = exception;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Goto extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String label;

		public Goto(String op, String label) {
			super();
			this.op = op.intern();
			this.label = label;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Switch extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String test;
		public String label;

		public Switch(String op, String test, String label) {
			super();
			this.op = op.intern();
			this.test = test;
			this.label = label;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Cmp extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String dst;
		public String firstSrc;
		public String secondSrc;

		public Cmp(String op, String dst, String firstSrc, String secondSrc) {
			super();
			this.op = op.intern();
			this.dst = dst.intern();
			this.firstSrc = firstSrc.intern();
			this.secondSrc = secondSrc.intern();
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class IfTest extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String firstSrc;
		public String secondSrc;
		public String label;

		public IfTest(String op, String firstSrc, String secondSrc, String label) {
			super();
			this.op = op.intern();
			this.firstSrc = firstSrc.intern();
			this.secondSrc = secondSrc.intern();
			this.label = label;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class IfTestz extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String src;
		public String label;

		public IfTestz(String op, String src, String label) {
			super();
			this.op = op.intern();
			this.src = src.intern();
			this.label = label;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Aget extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String dst;
		public String array;
		public String index;

		public Aget(String op, String dst, String array, String index) {
			super();
			this.op = op.intern();
			this.dst = dst.intern();
			this.array = array.intern();
			this.index = index.intern();
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Aput extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String src;
		public String array;
		public String index;

		public Aput(String op, String src, String array, String index) {
			super();
			this.op = op.intern();
			this.src = src.intern();
			this.array = array.intern();
			this.index = index.intern();
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Iget extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String dst;
		public String obj;
		public sim.classs.FieldItem field;

		public Iget(String op, String dst, String obj, FieldItem field) {
			super();
			this.op = op.intern();
			this.dst = dst.intern();
			this.obj = obj.intern();
			this.field = field;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Iput extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String src;
		public String obj;
		public sim.classs.FieldItem field;

		public Iput(String op, String src, String obj, FieldItem field) {
			super();
			this.op = op.intern();
			this.src = src.intern();
			this.obj = obj.intern();
			this.field = field;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Sget extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String dst;
		public sim.classs.FieldItem field;

		public Sget(String op, String dst, FieldItem field) {
			super();
			this.op = op.intern();
			this.dst = dst.intern();
			this.field = field;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Sput extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String src;
		public sim.classs.FieldItem field;

		public Sput(String op, String src, FieldItem field) {
			super();
			this.op = op.intern();
			this.src = src.intern();
			this.field = field;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Invoke extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public List<String> args;
		public sim.classs.MethodItem method;

		public Invoke(String op, List<String> args, MethodItem method) {
			super();
			this.op = op.intern();
			this.args = args;
			this.method = method;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class UnOp extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String dst;
		public String src;

		public UnOp(String op, String dst, String src) {
			super();
			this.op = op.intern();
			this.dst = dst.intern();
			this.src = src.intern();
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class BinOp extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String dst;
		public String firstSrc;
		public String secondSrc;

		public BinOp(String op, String dst, String firstSrc, String secondSrc) {
			super();
			this.op = op.intern();
			this.dst = dst.intern();
			this.firstSrc = firstSrc.intern();
			this.secondSrc = secondSrc.intern();
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class BinOpLit extends T {
		private static final long serialVersionUID = 1L;
		public String op;
		public String dst;
		public String src;
		public String constt;

		public BinOpLit(String op, String dst, String src, String constt) {
			super();
			this.op = op.intern();
			this.dst = dst.intern();
			this.src = src.intern();
			this.constt = constt;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class ArrayDataDirective extends T {
		private static final long serialVersionUID = 1L;
		public String size;
		public List<String> elementList;

		public ArrayDataDirective(String size, List<String> elementList) {
			super();
			this.size = size;
			this.elementList = elementList;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class PackedSwitchDirective extends T {
		private static final long serialVersionUID = 1L;
		public String key;
		public String count;
		public List<String> labList;

		public PackedSwitchDirective(String key, String count,
				List<String> labList) {
			super();
			this.key = key;
			this.count = count;
			this.labList = labList;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);

		}
	}

	public static class SparseSwitchDirective extends T {
		private static final long serialVersionUID = 1L;
		public String count;
		public List<String> keyList;
		public List<String> labList;

		public SparseSwitchDirective(String count, List<String> keyList,
				List<String> labList) {
			super();
			this.count = count;
			this.keyList = keyList;
			this.labList = labList;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Catch extends T {
		private static final long serialVersionUID = 1L;
		// .catch / . catchall
		public String op;
		public boolean isAll;
		public String type;
		public String startLab;
		public String endLab;
		public String catchLab;

		public Catch(String op, boolean isAll, String type, String startLab,
				String endLab, String catchLab) {
			super();
			this.op = op.intern();
			this.isAll = isAll;
			this.type = type;
			this.startLab = startLab;
			this.endLab = endLab;
			this.catchLab = catchLab;
		}

		@Override
		public String toString() {
			if (this.isAll == false)
				return ".catch " + this.type + " {:" + this.startLab + " .. :"
						+ this.endLab + "} :" + this.catchLab;
			else
				return ".catchall " + "{:" + this.startLab + " .. :"
						+ this.endLab + "} :" + this.catchLab;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}
}