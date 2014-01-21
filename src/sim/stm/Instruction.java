package sim.stm;

import java.util.List;

import sim.Visitor;

public class Instruction {
	public static class Nop extends T {
		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Move extends T {
		public String op;
		public String dst;
		public String src;

		public Move(String op, String dst, String src) {
			super();
			this.op = op;
			this.dst = dst;
			this.src = src;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class MoveResult extends T {
		public String op;
		public String dst;

		public MoveResult(String op, String dst) {
			super();
			this.op = op;
			this.dst = dst;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class ReturnVoid extends T {
		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Return extends T {
		public String op;
		public String src;

		public Return(String op, String src) {
			super();
			this.op = op;
			this.src = src;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Const extends T {
		public String op;
		public String dst;
		public String value;

		public Const(String op, String dst, String value) {
			super();
			this.op = op;
			this.dst = dst;
			this.value = value;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Monitor extends T {
		public String op;
		public String ref;

		public Monitor(String op, String ref) {
			super();
			this.op = op;
			this.ref = ref;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class CheckCast extends T {
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
		public String dst;
		public String ref;
		public String type;

		public InstanceOf(String dst, String ref, String type) {
			super();
			this.dst = dst;
			this.ref = ref;
			this.type = type;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class ArrayLength extends T {
		public String dst;
		public String ref;

		public ArrayLength(String dst, String ref) {
			super();
			this.dst = dst;
			this.ref = ref;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class NewInstance extends T {
		public String dst;
		public String type;

		public NewInstance(String dst, String type) {
			super();
			this.dst = dst;
			this.type = type;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class NewArray extends T {
		public String dst;
		public String size;
		public String type;

		public NewArray(String dst, String size, String type) {
			super();
			this.dst = dst;
			this.size = size;
			this.type = type;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class FilledNewArray extends T {
		public String op;
		public List<String> argList;
		public String type;

		public FilledNewArray(String op, List<String> argList, String type) {
			super();
			this.op = op;
			this.argList = argList;
			this.type = type;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class FillArrayData extends T {
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
		public String op;
		public String label;

		public Goto(String op, String label) {
			super();
			this.op = op;
			this.label = label;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Switch extends T {
		public String op;
		public String test;
		public String label;

		public Switch(String op, String test, String label) {
			super();
			this.op = op;
			this.test = test;
			this.label = label;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Cmp extends T {
		public String op;
		public String dst;
		public String firstSrc;
		public String secondSrc;

		public Cmp(String op, String dst, String firstSrc, String secondSrc) {
			super();
			this.op = op;
			this.dst = dst;
			this.firstSrc = firstSrc;
			this.secondSrc = secondSrc;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class IfTest extends T {
		public String op;
		public String firstSrc;
		public String secondSrc;
		public String label;

		public IfTest(String op, String firstSrc, String secondSrc, String label) {
			super();
			this.op = op;
			this.firstSrc = firstSrc;
			this.secondSrc = secondSrc;
			this.label = label;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class IfTestz extends T {
		public String op;
		public String src;
		public String label;

		public IfTestz(String op, String src, String label) {
			super();
			this.op = op;
			this.src = src;
			this.label = label;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Aget extends T {
		public String op;
		public String dst;
		public String array;
		public String index;

		public Aget(String op, String dst, String array, String index) {
			super();
			this.op = op;
			this.dst = dst;
			this.array = array;
			this.index = index;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Aput extends T {
		public String op;
		public String src;
		public String array;
		public String index;

		public Aput(String op, String src, String array, String index) {
			super();
			this.op = op;
			this.src = src;
			this.array = array;
			this.index = index;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Iget extends T {
		public String op;
		public String dst;
		public String obj;
		public String field;

		public Iget(String op, String dst, String obj, String field) {
			super();
			this.op = op;
			this.dst = dst;
			this.obj = obj;
			this.field = field;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Iput extends T {
		public String op;
		public String src;
		public String obj;
		public String field;

		public Iput(String op, String src, String obj, String field) {
			super();
			this.op = op;
			this.src = src;
			this.obj = obj;
			this.field = field;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Sget extends T {
		public String op;
		public String dst;
		public String field;

		public Sget(String op, String dst, String field) {
			super();
			this.op = op;
			this.dst = dst;
			this.field = field;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Sput extends T {
		public String op;
		public String src;
		public String field;

		public Sput(String op, String src, String field) {
			super();
			this.op = op;
			this.src = src;
			this.field = field;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class Invoke extends T {
		public String op;
		public String method;
		public List<String> args;

		public Invoke(String op, String method, List<String> args) {
			super();
			this.op = op;
			this.method = method;
			this.args = args;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class UnOp extends T {
		public String op;
		public String dst;
		public String src;

		public UnOp(String op, String dst, String src) {
			super();
			this.op = op;
			this.dst = dst;
			this.src = src;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class BinOp extends T {
		public String op;
		public String dst;
		public String firstSrc;
		public String secondSrc;

		public BinOp(String op, String dst, String firstSrc, String secondSrc) {
			super();
			this.op = op;
			this.dst = dst;
			this.firstSrc = firstSrc;
			this.secondSrc = secondSrc;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class BinOpLit extends T {
		public String op;
		public String dst;
		public String src;
		public String constt;

		public BinOpLit(String op, String dst, String src, String constt) {
			super();
			this.op = op;
			this.dst = dst;
			this.src = src;
			this.constt = constt;
		}

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}

	public static class ArrayDataDirective extends T {
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
}