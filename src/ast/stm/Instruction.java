package ast.stm;

import java.util.List;
import ast.Visitor;

public class Instruction{ /* This is just a namespace. YKG */
	
	public static class F21c_field extends T{
		public String name;
		public String reg;
		public ast.classs.FieldItem fieldItem;
		
		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}
	
	public static class F21c_string extends T{
		public String name;
		public String reg;
		public String str;

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}
	
	public static class F10x extends T{
		public String name;

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}
	
	public static class F35c_method extends T{
		public String name;
		public List<String> regList;
		public ast.classs.MethodItem methodItem;

		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}
}

