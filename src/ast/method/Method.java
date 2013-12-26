package ast.method;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ast.Visitor;

public class Method extends T 
{	
	//1.method_name_and_prototype
	public String name;  
	public MethodPrototype prototype;
	//2.access_list
	public List<String> accessList;  
	//3.registers_directive
	public String registers_directive; 
	public String registers_directive_count; 
	// 4.labels--
	public List<ast.method.Method.Label> labelList;
	//7.statements
	public List<ast.stm.T> statements; 
	
	//5.packed_switch_declarations
	public List<ast.method.Method.PSwitchDec> pSwitchDecList;
	
	//6.sparse_switch_declarations
	public List<ast.method.Method.SSwitchDec> sSwitchDecList;
	//8.catches
	public List<ast.method.Method.Catch> catchList;
	
	
	//9.parameters
	//10.ordered_debug_directives
	//11.annotations
	
	public static class Label implements Comparable<Label>
	{
		public String lab;
		public String add;
		public Label(String lab, String add)
		{
			super();
			this.lab = lab;
			this.add = add;
			
		}
		public int compareTo(Label la)
		{
			return Integer.parseInt(this.add) - Integer.parseInt(la.add);
		}
		@Override
		public String toString()
		{
			return ":"+this.lab;
		}
		
	}
	public static class Parameter
	{
	}
	public static class Catch implements Comparable<Catch>
	{
		// catch / catchall
		public String add;
		public boolean isAll;
		public String type;
		public String startLab;
		public String endLab;
		public String catchLab;
		public Catch(String add,String type, String startLab,
				String endLab, String catchLab)
		{
			super();
			this.add = add;
			this.isAll = false;
			this.type = type;
			this.startLab = startLab;
			this.endLab = endLab;
			this.catchLab = catchLab;
		}
		public Catch(String add,String startLab, String endLab,
				String catchLab)
		{
			super();
			this.add = add;
			this.isAll = true;
			this.startLab = startLab;
			this.endLab = endLab;
			this.catchLab = catchLab;
		}
		@Override
		public String toString()
		{
			if(this.isAll == false)
				return ".catch "+this.type +" {:" + this.startLab +" .. :"+ this.endLab+"} :"+this.catchLab;
			else
				return ".catchall "+ "{:" + this.startLab +" .. :"+ this.endLab+"} :"+this.catchLab;
		}
		public int compareTo(Catch cat)
		{
			return Integer.parseInt(this.add)- (Integer.parseInt(cat.add));
		}
	}
	public static class PSwitchDec 
	{
		public String add;
		public String dest;
		public PSwitchDec(String add, String dest)
		{
			super();
			this.add = add;
			this.dest = dest;
		}
		
	}
	public static class SSwitchDec 
	{
		public String add;
		public String dest;
		public SSwitchDec(String add, String dest)
		{
			super();
			this.add = add;
			this.dest = dest;
		}
		
	}
	public static class MethodPrototype extends T{
		public String returnType;
		public List<String> argsType;

		public MethodPrototype() {
		}
		public MethodPrototype(String returnType, List<String> argsType) {
			this.returnType = returnType;
			this.argsType = argsType;
		}
		@Override
		public void accept(Visitor v) {
			v.visit(this);
		}
	}
	

	public Method() {
		accessList = new ArrayList<String>();
		statements = new ArrayList<ast.stm.T>();
		labelList =  new ArrayList<ast.method.Method.Label>();
		pSwitchDecList = new ArrayList<ast.method.Method.PSwitchDec>();
		sSwitchDecList = new ArrayList<ast.method.Method.SSwitchDec>();
		catchList = new ArrayList<ast.method.Method.Catch>();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}