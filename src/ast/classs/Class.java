package ast.classs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ast.Visitor;

public class Class extends T {
	//public boolean isMain;
	public String FullyQualifiedName;
	public List<String> accessList;
	public String superName;
	public String source;
	//
	public List<String> implementsList;
	//methods
	public List<ast.method.Method> methods;
	
	
	//fields
	public List<Field> fieldList;
	//annotations
	
	
	public static class Field
	{
		public String name;
		public List<String> accessList;
		public String type;
		public Field(String name, List<String> accessList, String type)
		{
			super();
			this.name = name;
			this.accessList = accessList;
			this.type = type;
		}
		
	}
	public Class() {
		//isMain = false;
		methods = new LinkedList<ast.method.Method>();
		implementsList = new ArrayList<String>();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}