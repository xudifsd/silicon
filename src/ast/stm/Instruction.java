package ast.stm;

import java.util.List;

import ast.Visitor;
import ast.classs.FieldItem;
import ast.classs.MethodItem;

public class Instruction{ /* This is just a opspace. YKG */
	

 /*   
    //insn_format41c_type:INSTRUCTION_FORMAT41c_TYPE REGISTER reference_type_descriptor
    //e.g. const-class/jumbo v2, org/jf/HelloWorld2/HelloWorld2
    public static class F41c_type extends T
    {
        public String name;
        public String reg;
        public String ref;
        @Override
        public void accept(Visitor v)
        {
            v.visit(this);
        }
    }
    
    //insn_format41c_field:INSTRUCTION_FORMAT41c_FIELD REGISTER fully_qualified_field
    //e.g. sget-object/jumbo v0, Ljava/lang/System;->out:LJava/io/PrintStream;
    public static class F41c_field extends T
    {
        public String name;
        public String reg;
        public ast.classs.FieldItem fieldItem;
        
        @Override
        public void accept(Visitor v)
        {
            v.visit(this);
        }
    }
    
    //insn_format52c_field:INSTRUCTION_FORMAT52c_FIELD REGISTER REGISTER fully_qualified_field
    //e.g. iput-object/jumbo v1, v0, Lorg/jf/HelloWorld2/HelloWorld2;->helloWorld:Ljava/lang/String;
     public static class F52c_field extends T
     {
         public String name;
         public String rega;
         public String regb;
         public ast.classs.FieldItem fieldItem;
         
         @Override
         public void accept(Visitor v)
         {
             v.visit(this);
         }
     } 
	
    
   //insn_format5rc_method: INSTRUCTION_FORMAT5rc_METHOD register_range fully_qualified_method
   //e.g. invoke-virtual/jumbo {} java/lang/StringBuilder/append(Ljava/lang/String;)
    public static class F5rc_method extends T
    {
        public String name;
        public String startReg;
        public String endReg;
        public ast.classs.MethodItem methodItem ;
        
        @Override
        public void accept(Visitor v)
        {
            v.visit(this);
        }
    }
    
    //insn_format5rc_type:INSTRUCTION_FORMAT5rc_TYPE register_range nonvoid_type_descriptor
    //e.g. filled-new-array/jumbo {} I
    public static class F5rc_type extends T
    {
        public String name;
        public String startReg;
        public String endReg;
        public String type;
        
        @Override
        public void accept(Visitor v)
        {
            v.visit(this);
        }
    }
    
    //insn_array_data_directive:^(I_ARRAY_ELEMENT_SIZE short_integral_literal) array_elements
    //e.g. .array-data 4 1000000 .end array-data
    public static class Array_data_dir extends T
    {
        public String name;
        public String value;
        public List<String> array;
        
        @Override
        public void accept(Visitor v)
        {
            v.visit(this);
        }
    }
    */
    
    
    
    
    ///////////////////////////////////////////////////////////////////////////
    
	//insn_format10t  INSTRUCTION_FORMAT10t offset_or_label
    //e.g. goto endloop:

	//28 10t	goto +AA
	public static class Goto extends T
	{
		public String op;
		public String dest;
		public Goto(String op, String dest)
		{
			super();
			this.op = op;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
		
		
	}
	
	
	//insn_format10x:INSTRUCTION_FORMAT10x
	//e.g. return 
	
	//00 10x nop
	public static class Nop extends T
	{
		public String op;
		public Nop(String op)
		{
			this.op = op;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//0e 10x	return-void
	public static class ReturnVoid extends T
	{
		public String op;
		public ReturnVoid(String op)
		{
			this.op = op;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	
	//insn_format11n : INSTRUCTION_FORMAT11n REGISTER short_integral_literal
	//e.g. const/4 v0, 5
	
	//12 11n	const/4 vA, #+B
	public static class Const4 extends T
	{
		public String op;
		public String dest;
		public String value;
		public Const4(String op, String dest, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
		
	}
	
	
	//insn_format11x:INSTRUCTION_FORMAT11x REGISTER
	//e.g. move-result-object v1
	
	//0a 11x	move-result vAA
	public static class MoveResult extends T
	{
		public String op;
		public String dest;
		public MoveResult(String op, String dest)
		{
			super();
			this.op = op;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//0b 11x	move-result-wide vAA
	public static class MoveResultWide extends T
	{
		public String op;
		public String dest;
		public MoveResultWide(String op, String dest)
		{
			super();
			this.op = op;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//0c 11x	move-result-object vAA
	public static class MoveResultObject extends T
	{
		public String op;
		public String dest;
		public MoveResultObject(String op, String dest)
		{
			super();
			this.op = op;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//0d 11x	move-exception vAA
	public static class MoveException extends T
	{
		public String op;
		public String dest;
		public MoveException(String op, String dest)
		{
			super();
			this.op = op;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	// 0f 11x	return vAA
	public static class Return extends T
	{
		public String op;
		public String ret;
		public Return(String op, String ret)
		{
			super();
			this.op = op;
			this.ret = ret;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//10 11x	return-wide vAA
	public static class ReturnWide extends T
	{
		public String op;
		public String ret;
		public ReturnWide(String op, String ret)
		{
			super();
			this.op = op;
			this.ret = ret;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//11 11x	return-object vAA
	public static class ReturnObject extends T
	{
		public String op;
		public String ret;
		public ReturnObject(String op, String ret)
		{
			super();
			this.op = op;
			this.ret = ret;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//1d 11x monitor-enter vAA
	public static class MonitorEnter extends T
	{
		public String op;
		public String ref;
		public MonitorEnter(String op, String ref)
		{
			super();
			this.op = op;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//1e 11x	monitor-exit vAA
	public static class MonitorExit extends T
	{
		public String op;
		public String ref;
		public MonitorExit(String op, String ref)
		{
			super();
			this.op = op;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//27 11x	throw vAA
	
	public static class Throw extends T
	{
		public String op;
		public String kind;
		public Throw(String op, String kind)
		{
			super();
			this.op = op;
			this.kind = kind;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	
	
	//insn_format12x:INSTRUCTION_FORMAT12x registerA=REGISTER registerB=REGISTER
	//e.g. move v1 v2i

	
	//01 12x	move vA, vB
	public static class Move extends T
	{
		public String op;
		public String dest;
		public String src;
		public Move(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//04 12x	move-wide vA, vB
	public static class MoveWide extends T
	{
		public String op;
		public String dest;
		public String src;
		public MoveWide(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//07 12x	move-object vA, vB
	public static class MoveObject extends T
	{
		public String op;
		public String dest;
		public String src;
		public MoveObject(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//21 12x	array-length vA, vB
	//Store in the given destination register the length of the indicated array, in entries
	public static class arrayLength extends T
	{
		public String op;
		public String dest;
		public String src;
		public arrayLength(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//unop vA, vB
	//A: destination register or pair (4 bits)
	//B: source register or pair (4 bits)	
	//Perform the identified unary operation on the source register, storing the result in the destination register.

	//7b: neg-int
	public static class NegInt extends T
	{
		public String op;
		public String dest;
		public String src;
		public NegInt(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//7c: not-int
	public static class NotInt extends T
	{
		public String op;
		public String dest;
		public String src;
		public NotInt(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//7d: neg-long
	public static class NegLong extends T
	{
		public String op;
		public String dest;
		public String src;
		public NegLong(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//7e: not-long
	public static class NotLong extends T
	{
		public String op;
		public String dest;
		public String src;
		public NotLong(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//7f: neg-float
	public static class NegFloat extends T
	{
		public String op;
		public String dest;
		public String src;
		public NegFloat(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//80: neg-double
	public static class NegDouble extends T
	{
		public String op;
		public String dest;
		public String src;
		public NegDouble(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//81: int-to-long
	public static class IntToLong extends T
	{
		public String op;
		public String dest;
		public String src;
		public IntToLong(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//82: int-to-float
	public static class IntToFloat extends T
	{
		public String op;
		public String dest;
		public String src;
		public IntToFloat(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//83: int-to-double
	public static class IntToDouble extends T
	{
		public String op;
		public String dest;
		public String src;
		public IntToDouble(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//84: long-to-int
	public static class LongToInt extends T
	{
		public String op;
		public String dest;
		public String src;
		public LongToInt(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//85: long-to-float
	public static class LongToFloat extends T
	{
		public String op;
		public String dest;
		public String src;
		public LongToFloat(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//86: long-to-double
	public static class LongToDouble extends T
	{
		public String op;
		public String dest;
		public String src;
		public LongToDouble(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//87: float-to-int
	public static class FloatToInt extends T
	{
		public String op;
		public String dest;
		public String src;
		public FloatToInt(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//88: float-to-long
	public static class FloatToLong extends T
	{
		public String op;
		public String dest;
		public String src;
		public FloatToLong(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//89: float-to-double
	public static class FloatToDouble extends T
	{
		public String op;
		public String src;
		public String dest;
		public FloatToDouble(String op, String src, String dest)
		{
			super();
			this.op = op;
			this.src = src;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//8a: double-to-int
	public static class DoubleToInt extends T
	{
		public String op;
		public String dest;
		public String src;
		public DoubleToInt(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//8b: double-to-long
	public static class DoubleToLong extends T
	{
		public String op;
		public String dest;
		public String src;
		public DoubleToLong(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//8c: double-to-float
	public static class DoubleToFloat extends T
	{
		public String op;
		public String dest;
		public String src;
		public DoubleToFloat(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//8d: int-to-byte
	public static class IntToByte extends T
	{
		public String op;
		public String dest;
		public String src;
		public IntToByte(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//8e: int-to-char
	public static class IntToChar extends T
	{
		public String op;
		public String dest;
		public String src;
		public IntToChar(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//8f: int-to-short
	public static class IntToShort extends T
	{
		public String op;
		public String dest;
		public String src;
		public IntToShort(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//binop/2addr vA, vB
	//A: destination and first source register or pair (4 bits)
	//B: second source register or pair (4 bits)
	//b0: add-int/2addr
	public static class AddInt2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public AddInt2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//b1: sub-int/2addr
	public static class SubInt2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public SubInt2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//b2: mul-int/2addr
	public static class MulInt2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public MulInt2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//b3: div-int/2addr
	public static class DivInt2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public DivInt2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//b4: rem-int/2addr
	public static class RemInt2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public RemInt2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//b5: and-int/2addr
	public static class AndInt2Addr extends T
	{
		public String op;
		public String src;
		public String dest;
		public AndInt2Addr(String op, String src, String dest)
		{
			super();
			this.op = op;
			this.src = src;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//b6: or-int/2addr
	public static class OrInt2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public OrInt2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//b7: xor-int/2addr
	public static class XorInt2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public XorInt2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//b8: shl-int/2addr
	public static class ShlInt2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public ShlInt2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//b9: shr-int/2addr
	public static class ShrInt2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public ShrInt2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//ba: ushr-int/2addr
	public static class UshrInt2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public UshrInt2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//bb: add-long/2addr
	public static class AddLong2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public AddLong2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//bc: sub-long/2addr
	public static class SubLong2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public SubLong2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//bd: mul-long/2addr
	public static class MulLong2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public MulLong2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//be: div-long/2addr
	public static class DivLong2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public DivLong2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//bf: rem-long/2addr
	public static class RemLong2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public RemLong2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//c0: and-long/2addr
	public static class AndLong2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public AndLong2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}

	}
	//c1: or-long/2addr
	public static class OrLong2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public OrLong2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//c2: xor-long/2addr
	public static class XorLong2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public XorLong2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//c3: shl-long/2addr
	public static class ShlLong2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public ShlLong2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//c4: shr-long/2addr
	public static class ShrLong2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public ShrLong2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//c5: ushr-long/2addr
	public static class UshrLong2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public UshrLong2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//c6: add-float/2addr
	public static class AddFloat2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public AddFloat2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//c7: sub-float/2addr
	public static class SubFloat2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public SubFloat2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}		
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
		
	}
	//c8: mul-float/2addr
	public static class MulFloat2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public MulFloat2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//c9: div-float/2addr
	public static class DivFloat2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public DivFloat2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//ca: rem-float/2addr
	public static class RemFloat2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public RemFloat2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//cb: add-double/2addr
	public static class AddDouble2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public AddDouble2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
		
	}
	//cc: sub-double/2addr
	public static class SubDouble2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public SubDouble2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
		
	}
	//cd: mul-double/2addr
	public static class MulDouble2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public MulDouble2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
		
	}
	//ce: div-double/2addr
	public static class DivDouble2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public DivDouble2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//cf: rem-double/2addr
	public static class RemDouble2Addr extends T
	{
		public String op;
		public String dest;
		public String src;
		public RemDouble2Addr(String op, String dest, String src)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.src = src;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
		
	
	//insn_format20t:INSTRUCTION_FORMAT20t offset_or_label
	//e.g. goto/16 endloop:

	
	//29 20t	goto/16 +AAAA
	public static class Goto16 extends T
	{
		public String op;
		public String dest;
		public Goto16(String op, String dest)
		{
			super();
			this.op = op;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	
	//insn_format21c_field:  
	//(INSTRUCTION_FORMAT21c_FIELD | INSTRUCTION_FORMAT21c_FIELD_ODEX) REGISTER fully_qualified_field
	//e.g. sget_object v0, java/lang/System/out LJava/io/PrintStream;

	
	//1a 21c	const-string vAA, string@BBBB
	public static class ConstString extends T
	{
		public String op;
		public String dest;
		public String str;
		public ConstString(String op, String dest, String str)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.str = str;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//1c 21c	const-class vAA, type@BBBB
	//1c 21c	const-class vAA, type@BBBB
	public static class ConstClass extends T
	{
		public String op;
		public String dest;
		public String type;
		public ConstClass(String op, String dest, String type)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//1f 21c	check-cast vAA, type@BBBB
	//1f 21c	check-cast vAA, type@BBBB
	public static class CheckCast extends T
	{
		public String op;
		public String ref;
		public String type;
		public CheckCast(String op, String ref, String type)
		{
			super();
			this.op = op;
			this.ref = ref;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}

	//22 21c	new-instance vAA, type@BBBB
	public static class NewInstance extends T
	{
		public String op;
		public String dest;
		public String type;
		public NewInstance(String op, String dest, String type)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//sstaticop vAA, field@BBBB
	//60: sget
	//61: sget-wide
	//62: sget-object
	//63: sget-boolean
	//64: sget-byte
	//65: sget-char
	//66: sget-short
	//67: sput
	//68: sput-wide
	//69: sput-object
	//6a: sput-boolean
	//6b: sput-byte
	//6c: sput-char
	//6d: sput-short
	//sstaticop vAA, field@BBBB
	//A: value register or pair; may be source or dest (8 bits)
	//B: static field reference index (16 bits)
	//60: sget
	public static class Sget extends T

	{
		public String op;
		public String dest;
		public ast.classs.FieldItem ref;
		public Sget(String op, String dest, FieldItem ref)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//61: sget-wide
	public static class SgetWide extends T
	{
		public String op;
		public String dest;
		public ast.classs.FieldItem ref;
		public SgetWide(String op, String dest, FieldItem ref)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//62: sget-object
	public static class SgetObject extends T
	{
		public String op;
		public String dest;
		public ast.classs.FieldItem ref;
		public SgetObject(String op, String dest, FieldItem ref)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//63: sget-boolean
	public static class SgetBoolean extends T
	{
		public String op;
		public String dest;
		public ast.classs.FieldItem ref;
		public SgetBoolean(String op, String dest, FieldItem ref)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//64: sget-byte
	public static class SgetByte extends T
	{
		public String op;
		public String dest;
		public ast.classs.FieldItem ref;
		public SgetByte(String op, String dest, FieldItem ref)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//65: sget-char
	public static class SgetChar extends T
	{
		public String op;
		public String dest;
		public ast.classs.FieldItem ref;
		public SgetChar(String op, String dest, FieldItem ref)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//66: sget-short
	public static class SgetShort extends T
	{
		public String op;
		public String dest;
		public ast.classs.FieldItem ref;
		public SgetShort(String op, String dest, FieldItem ref)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//67: sput
	public static class Sput extends T
	{
		public String op;
		public String src;
		public ast.classs.FieldItem ref;
		public Sput(String op, String src, FieldItem ref)
		{
			super();
			this.op = op;
			this.src = src;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//68: sput-wide
	public static class SputWide extends T
	{
		public String op;
		public String src;
		public ast.classs.FieldItem ref;
		public SputWide(String op, String src, FieldItem ref)
		{
			super();
			this.op = op;
			this.src = src;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//69: sput-object
	public static class SputObject extends T
	{
		public String op;
		public String src;
		public ast.classs.FieldItem ref;
		public SputObject(String op, String src, FieldItem ref)
		{
			super();
			this.op = op;
			this.src = src;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//6a: sput-boolean
	public static class SputBoolean extends T
	{
		public String op;
		public String src;
		public ast.classs.FieldItem ref;
		public SputBoolean(String op, String src, FieldItem ref)
		{
			super();
			this.op = op;
			this.src = src;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//6b: sput-byte
	public static class SputByte extends T
	{
		public String op;
		public String src;
		public ast.classs.FieldItem ref;
		public SputByte(String op, String src, FieldItem ref)
		{
			super();
			this.op = op;
			this.src = src;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//6c: sput-char
	public static class SputChar extends T
	{
		public String op;
		public String src;
		public ast.classs.FieldItem ref;
		public SputChar(String op, String src, FieldItem ref)
		{
			super();
			this.op = op;
			this.src = src;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//6d: sput-short
	public static class SputShort extends T
	{
		public String op;
		public String src;
		public ast.classs.FieldItem ref;
		public SputShort(String op, String src, FieldItem ref)
		{
			super();
			this.op = op;
			this.src = src;
			this.ref = ref;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	
	//insn_format21h:INSTRUCTION_FORMAT21h REGISTER short_integral_literal
	//e.g. const/high16 v1, 1234


	//15 21h	const/high16 vAA, #+BBBB0000
	public static class ConstHigh16 extends T
	{
		public String op;
		public String dest;
		public String value;
		public ConstHigh16(String op, String dest, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//19 21h	const-wide/high16 vAA, #+BBBB000000000000
	public static class ConstWideHigh16 extends T
	{
		public String op;
		public String dest;
		public String value;
		public ConstWideHigh16(String op, String dest, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	
	
	//insn_format21s :INSTRUCTION_FORMAT21s REGISTER short_integral_literal
	//e.g. const/16 v1, 1234
	
	//13 21s	const/16 vAA, #+BBBB
	public static class Const16 extends T
	{
		public String op;
		public String dest;
		public String value;
		public Const16(String op, String dest, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//16 21s	const-wide/16 vAA, #+BBBB
	public static class ConstWide16 extends T
	{
		public String op;
		public String dest;
		public String value;
		public ConstWide16(String op, String dest, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//insn_format21t:INSTRUCTION_FORMAT21t REGISTER offset_or_label
	//e.g. if-eqz v0, endloop:
	
	
	//38..3d 21t	
	//if-testz vAA, +BBBB
	//38: if-eqz
	public static class IfEqz extends T
	{
		public String op;
		public String test;
		public String dest;
		public IfEqz(String op, String test, String dest)
		{
			super();
			this.op = op;
			this.test = test;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
		
	//39: if-nez
	public static class IfNez extends T
	{
		public String op;
		public String test;
		public String dest;
		public IfNez(String op, String test, String dest)
		{
			super();
			this.op = op;
			this.test = test;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//3a: if-ltz
	public static class IfLtz extends T
	{
		public String op;
		public String test;
		public String dest;
		public IfLtz(String op, String test, String dest)
		{
			super();
			this.op = op;
			this.test = test;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//3b: if-gez
	public static class IfGez extends T
	{
		public String op;
		public String test;
		public String dest;
		public IfGez(String op, String test, String dest)
		{
			super();
			this.op = op;
			this.test = test;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//3c: if-gtz
	public static class IfGtz extends T
	{
		public String op;
		public String test;
		public String dest;
		public IfGtz(String op, String test, String dest)
		{
			super();
			this.op = op;
			this.test = test;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//3d: if-lez
	public static class IfLez extends T
	{
		public String op;
		public String test;
		public String dest;
		public IfLez(String op, String test, String dest)
		{
			super();
			this.op = op;
			this.test = test;
			this.dest = dest;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	
	//insn_format22b:INSTRUCTION_FORMAT22b registerA=REGISTER registerB=REGISTER 
	//e.g. add-int v0, v1, 123
	
	//d8..e2 22b	
	//binop/lit8 vAA, vBB, #+CC
	//d8: add-int/lit8
	public static class AddIntLit8 extends T
	{
		public String op;
		public String dest;
		public String source;
		public String value;
		public AddIntLit8(String op, String dest, String source, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.source = source;
			this.value = value;
		}
		
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//d9: rsub-int/lit8
	public static class RsubIntLit8 extends T
	{
		public String op;
		public String dest;
		public String source;
		public String value;
		public RsubIntLit8(String op, String dest, String source, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.source = source;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//da: mul-int/lit8
	public static class MulIntLit8 extends T
	{
		public String op;
		public String dest;
		public String source;
		public String value;
		public MulIntLit8(String op, String dest, String source, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.source = source;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//db: div-int/lit8
	public static class DivIntLit8 extends T
	{
		public String op;
		public String dest;
		public String source;
		public String value;
		public DivIntLit8(String op, String dest, String source, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.source = source;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//dc: rem-int/lit8
	public static class RemIntLit8 extends T
	{
		public String op;
		public String dest;
		public String source;
		public String value;
		public RemIntLit8(String op, String dest, String source, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.source = source;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//dd: and-int/lit8
	public static class AndIntLit8 extends T
	{
		public String op;
		public String dest;
		public String source;
		public String value;
		public AndIntLit8(String op, String dest, String source, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.source = source;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//de: or-int/lit8
	public static class OrIntLit8 extends T
	{
		public String op;
		public String dest;
		public String source;
		public String value;
		public OrIntLit8(String op, String dest, String source, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.source = source;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//df: xor-int/lit8
	public static class XorIntLit8 extends T
	{
		public String op;
		public String dest;
		public String source;
		public String value;
		public XorIntLit8(String op, String dest, String source, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.source = source;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//e0: shl-int/lit8
	public static class ShlIntLit8 extends T
	{
		public String op;
		public String dest;
		public String source;
		public String value;
		public ShlIntLit8(String op, String dest, String source, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.source = source;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//e1: shr-int/lit8
	public static class ShrIntLit8 extends T
	{
		public String op;
		public String dest;
		public String source;
		public String value;
		public ShrIntLit8(String op, String dest, String source, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.source = source;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//e2: ushr-int/lit8
	public static class UshrIntLit8 extends T
	{
		public String op;
		public String dest;
		public String source;
		public String value;
		public UshrIntLit8(String op, String dest, String source, String value)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.source = source;
			this.value = value;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//insn_format22c_field:
	 //(INSTRUCTION_FORMAT22c_FIELD | INSTRUCTION_FORMAT22c_FIELD_ODEX) 
	 //REGISTER REGISTER fully_qualified_field
	//e.g. iput-object v1, v0, org/jf/HelloWorld2/HelloWorld2.helloWorld Ljava/lang/String;

	//insn_format22c_type:
	//INSTRUCTION_FORMAT22c_TYPE REGISTER REGISTER nonvoid_type_descriptor
	//e.g. instance-of v0, v1, Ljava/lang/String;

	
	//20 22c	instance-of vA, vB, type@CCCC
	public static class InstanceOf extends T
	{
		public String op;
		public String dest;
		public String ref;
		public String type;
		public InstanceOf(String op,String dest, String ref, String type)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.ref = ref;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//23 22c	new-array vA, vB, type@CCCC
	public static class NewArray extends T
	{
		public String op;
		public String dest;
		public String size;
		public String type;
		public NewArray(String op,String dest, String size, String type)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.size = size;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	} 
	
	//52..5f 22c	
	//iinstanceop vA, vB, field@CCCC
	
	//52: iget
	public static  class Iget extends T
	{
		public String op;
		public String dest;
		public String obj;
		public ast.classs.FieldItem type;
		public Iget(String op, String dest, String obj, ast.classs.FieldItem type)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//53: iget-wide
	public static  class IgetWide extends T
	{
		public String op;
		public String dest;
		public String obj;
		public ast.classs.FieldItem type;
		public IgetWide(String op, String dest, String obj, ast.classs.FieldItem type)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//54: iget-object
	public static  class IgetOjbect extends T
	{
		public String op;
		public String dest;
		public String obj;
		public ast.classs.FieldItem type;
		public IgetOjbect(String op, String dest, String obj, ast.classs.FieldItem  type)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//55: iget-boolean
	public static  class IgetBoolean extends T
	{
		public String op;
		public String dest;
		public String obj;
		public ast.classs.FieldItem type;
		public IgetBoolean(String op, String dest, String obj, ast.classs.FieldItem type)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//56: iget-byte
	public static  class IgetByte extends T
	{
		public String op;
		public String dest;
		public String obj;
		public ast.classs.FieldItem type;
		public IgetByte(String op, String dest, String obj, ast.classs.FieldItem type)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//57: iget-char
	public static  class IgetChar extends T
	{
		public String op;
		public String dest;
		public String obj;
		public ast.classs.FieldItem type;
		public IgetChar(String op, String dest, String obj, ast.classs.FieldItem type)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//58: iget-short
	public static  class IgetShort extends T
	{
		public String op;
		public String dest;
		public String obj;
		public ast.classs.FieldItem type;
		public IgetShort(String op, String dest, String obj, ast.classs.FieldItem type)
		{
			super();
			this.op = op;
			this.dest = dest;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//59: iput
	public static  class Iput extends T
	{
		public String op;
		public String source;
		public String obj;
		public ast.classs.FieldItem type;
		public Iput(String op, String source, String obj, ast.classs.FieldItem type)
		{
			super();
			this.op = op;
			this.source = source;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//5a: iput-wide
	public static  class IputWide extends T
	{
		public String op;
		public String source;
		public String obj;
		public ast.classs.FieldItem type;
		public IputWide(String op, String source, String obj, ast.classs.FieldItem type)
		{
			super();
			this.op = op;
			this.source = source;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	
	//5b: iput-object
	public static  class IputObject extends T
	{
		public String op;
		public String source;
		public String obj;
		public ast.classs.FieldItem type;
		public IputObject(String op, String source, String obj, ast.classs.FieldItem type)
		{
			super();
			this.op = op;
			this.source = source;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//5c: iput-boolean
	public static  class IputBoolean extends T
	{
		public String op;
		public String source;
		public String obj;
		public String field;
		public ast.classs.FieldItem type;
		public IputBoolean(String op, String source, String obj, ast.classs.FieldItem type)
		{
			super();
			this.op = op;
			this.source = source;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//5d: iput-byte
	public static  class IputByte extends T
	{
		public String op;
		public String source;
		public String obj;
		public ast.classs.FieldItem type;
		public IputByte(String op, String source, String obj, ast.classs.FieldItem type)
		{
			super();
			this.op = op;
			this.source = source;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//5e: iput-char
	public static  class IputChar extends T
	{
		public String op;
		public String source;
		public String obj;
		public ast.classs.FieldItem type;
		public IputChar(String op, String source, String obj, ast.classs.FieldItem type)
		{
			super();
			this.op = op;
			this.source = source;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	//5f: iput-short
	public static  class IputShort extends T
	{
		public String op;
		public String source;
		public String obj;
		public ast.classs.FieldItem type;
		public IputShort(String op, String source, String obj, ast.classs.FieldItem type)
		{
			super();
			this.op = op;
			this.source = source;
			this.obj = obj;
			this.type = type;
		}
		@Override
		public void accept(Visitor v)
		{
			v.visit(this);
		}
	}
	

	
	
	//insn_format22s:INSTRUCTION_FORMAT22s registerA=REGISTER registerB=REGISTER short_integral_literal
		//e.g. add-int/lit16 v0, v1, 12345
	
		//d0..d7 22s	
		//binop/lit16 vA, vB, #+CCCC
		//d0: add-int/lit16
		public static class AddIntLit16 extends T
		{
			public String op;
			public String dest;
			public String source;
			public String value;
			public AddIntLit16(String op, String dest, String source,
					String value)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.source = source;
				this.value = value;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//d1: rsub-int (reverse subtract)
		public static class RsubInt extends T
		{
			public String op;
			public String dest;
			public String source;
			public String value;
			public RsubInt(String op, String dest, String source, String value)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.source = source;
				this.value = value;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//d2: mul-int/lit16
		public static class MulIntLit16 extends T
		{
			public String op;
			public String dest;
			public String source;
			public String value;
			public MulIntLit16(String op, String dest, String source,
					String value)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.source = source;
				this.value = value;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//d3: div-int/lit16
		public static class DivIntLit16 extends T
		{
			public String op;
			public String dest;
			public String source;
			public String value;
			public DivIntLit16(String op, String dest, String source,
					String value)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.source = source;
				this.value = value;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//d4: rem-int/lit16
		public static class RemIntLit16 extends T
		{
			public String op;
			public String dest;
			public String source;
			public String value;
			public RemIntLit16(String op, String dest, String source,
					String value)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.source = source;
				this.value = value;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//d5: and-int/lit16
		public static class AndIntLit16 extends T
		{
			public String op;
			public String dest;
			public String source;
			public String value;
			public AndIntLit16(String op, String dest, String source,
					String value)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.source = source;
				this.value = value;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//d6: or-int/lit16
		public static class OrIntLit16 extends T
		{
			public String op;
			public String dest;
			public String source;
			public String value;
			public OrIntLit16(String op, String dest, String source,
					String value)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.source = source;
				this.value = value;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//d7: xor-int/lit16
		public static class XorIntLit16 extends T
		{
			public String op;
			public String dest;
			public String source;
			public String value;
			public XorIntLit16(String op, String dest, String source,
					String value)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.source = source;
				this.value = value;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//insn_format22t:INSTRUCTION_FORMAT22t registerA=REGISTER registerB=REGISTER offset_or_label
		//e.g. if-eq v0, v1, endloop:

		
		//32..37 22t	
		//if-test vA, vB, +CCCC
		
		//32: if-eq
		public static class IfEq extends T
		{
			public String op;
			public String first;
			public String second;
			public String dest;
			public IfEq(String op, String first, String second, String dest)
			{
				super();
				this.op = op;
				this.first = first;
				this.second = second;
				this.dest = dest;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//33: if-ne
		public static class IfNe extends T
		{
			public String op;
			public String first;
			public String second;
			public String dest;
			public IfNe(String op, String first, String second, String dest)
			{
				super();
				this.op = op;
				this.first = first;
				this.second = second;
				this.dest = dest;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//34: if-lt
		public static class IfLt extends T
		{
			public String op;
			public String first;
			public String second;
			public String dest;
			public IfLt(String op, String first, String second, String dest)
			{
				super();
				this.op = op;
				this.first = first;
				this.second = second;
				this.dest = dest;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//35: if-ge
		public static class IfGe extends T
		{
			public String op;
			public String first;
			public String second;
			public String dest;
			public IfGe(String op, String first, String second, String dest)
			{
				super();
				this.op = op;
				this.first = first;
				this.second = second;
				this.dest = dest;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//36: if-gt
		public static class IfGt extends T
		{
			public String op;
			public String first;
			public String second;
			public String dest;
			public IfGt(String op, String first, String second, String dest)
			{
				super();
				this.op = op;
				this.first = first;
				this.second = second;
				this.dest = dest;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//37: if-le
		public static class IfLe extends T
		{
			public String op;
			public String first;
			public String second;
			public String dest;
			public IfLe(String op, String first, String second, String dest)
			{
				super();
				this.op = op;
				this.first = first;
				this.second = second;
				this.dest = dest;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		
		//insn_format22x:INSTRUCTION_FORMAT22x registerA=REGISTER registerB=REGISTER
		//e.g. move/from16 v1, v1234

	
		//02 22x	move/from16 vAA, vBBBB
		public static class MoveFrom16 extends T
		{
			public String op;
			public String dest;
			public String src;
			public MoveFrom16(String op, String dest, String src)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.src = src;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//05 22x	move-wide/from16 vAA, vBBBB
		public static class MoveWideFrom16 extends T
		{
			public String op;
			public String dest;
			public String src;
			public MoveWideFrom16(String op, String dest, String src)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.src = src;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//08 22x	move-object/from16 vAA, vBBBB
		public static class MoveOjbectFrom16 extends T
		{
			public String op;
			public String dest;
			public String src;
			public MoveOjbectFrom16(String op, String dest, String src)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.src = src;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		
		//insn_format23x:INSTRUCTION_FORMAT23x REGISTER REGISTER REGISTER
		//e.g. add-int v1, v2, v3

		
		//2d..31 23x	
		//cmpkind vAA, vBB, vCC
		//2d: cmpl-float (lt bias)
		public static class CmplFloat extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public CmplFloat(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//2e: cmpg-float (gt bias)
		public static class CmpgFloat extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public CmpgFloat(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//2f: cmpl-double (lt bias)
		public static class CmplDouble extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public CmplDouble(String op, String dest, String first,
					String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//30: cmpg-double (gt bias)
		public static class Cmpgdouble extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public Cmpgdouble(String op, String dest, String first,
					String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//31: cmp-long
		public static class CmpLong extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public CmpLong(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//44..51 23x	arrayop vAA, vBB, vCC
		//A: value register or pair; may be source or dest (8 bits)
		//B: array register (8 bits)
		//C: index register (8 bits)
		
		//44: aget
		public static class Aget extends T
		{
			public String op;
			public String dest;
			public String array;
			public String index;
			public Aget(String op, String dest, String array, String index)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//45: aget-wide
		public static class AgetWide extends T
		{
			public String op;
			public String dest;
			public String array;
			public String index;
			public AgetWide(String op, String dest, String array, String index)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//46: aget-object
		public static class AgetObject extends T
		{
			public String op;
			public String dest;
			public String array;
			public String index;
			public AgetObject(String op, String dest, String array, String index)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//47: aget-boolean
		public static class AgetBoolean extends T
		{
			public String op;
			public String dest;
			public String array;
			public String index;
			public AgetBoolean(String op, String dest, String array,
					String index)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//48: aget-byte
		public static class AgetByte extends T
		{
			public String op;
			public String dest;
			public String array;
			public String index;
			public AgetByte(String op, String dest, String array, String index)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//49: aget-char
		public static class AgetChar extends T
		{
			public String op;
			public String dest;
			public String array;
			public String index;
			public AgetChar(String op, String dest, String array, String index)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//4a: aget-short
		public static class AgetShort extends T
		{
			public String op;
			public String dest;
			public String array;
			public String index;
			public AgetShort(String op, String dest, String array, String index)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//4b: aput
		public static class Aput extends T
		{
			public String op;
			public String src;
			public String array;
			public String index;
			public Aput(String op, String src, String array, String index)
			{
				super();
				this.op = op;
				this.src = src;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//4c: aput-wide
		public static class AputWide extends T
		{
			public String op;
			public String src;
			public String array;
			public String index;
			public AputWide(String op, String src, String array, String index)
			{
				super();
				this.op = op;
				this.src = src;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//4d: aput-object
		public static class AputObject extends T
		{
			public String op;
			public String src;
			public String array;
			public String index;
			public AputObject(String op, String src, String array, String index)
			{
				super();
				this.op = op;
				this.src = src;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//4e: aput-boolean
		public static class AputBoolean extends T
		{
			public String op;
			public String src;
			public String array;
			public String index;
			public AputBoolean(String op, String src, String array, String index)
			{
				super();
				this.op = op;
				this.src = src;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//4f: aput-byte
		public static class AputByte extends T
		{
			public String op;
			public String src;
			public String array;
			public String index;
			public AputByte(String op, String src, String array, String index)
			{
				super();
				this.op = op;
				this.src = src;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//50: aput-char
		public static class AputChar extends T
		{
			public String op;
			public String src;
			public String array;
			public String index;
			public AputChar(String op, String src, String array, String index)
			{
				super();
				this.op = op;
				this.src = src;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//51: aput-short
		public static class AputShort extends T
		{
			public String op;
			public String src;
			public String array;
			public String index;
			public AputShort(String op, String src, String array, String index)
			{
				super();
				this.op = op;
				this.src = src;
				this.array = array;
				this.index = index;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//90..af 23x	binop vAA, vBB, vCC
		//A: destination register or pair (8 bits)
		//B: first source register or pair (8 bits)
		//C: second source register or pair (8 bits)
		//90: add-int
		public static class AddInt extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public AddInt(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//91: sub-int
		public static class SubInt extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public SubInt(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}		
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}			
		}
		//92: mul-int
		public static class MulInt extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public MulInt(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//93: div-int
		public static class DivInt extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public DivInt(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//94: rem-int
		public static class RemInt extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public RemInt(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//95: and-int
		public static class AndInt extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public AndInt(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//96: or-int
		public static class OrInt extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public OrInt(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//97: xor-int
		public static class XorInt extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public XorInt(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//98: shl-int
		public static class ShlInt extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public ShlInt(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//99: shr-int
		public static class ShrInt extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public ShrInt(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//9a: ushr-int
		public static class UshrInt extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public UshrInt(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//9b: add-long
		public static class AddLong extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public AddLong(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//9c: sub-long
		public static class SubLong extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public SubLong(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//9d: mul-long
		public static class MulLong extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public MulLong(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//9e: div-long
		public static class DivLong extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public DivLong(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//9f: rem-long
		public static class RemLong extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public RemLong(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//a0: and-long
		public static class AndLong extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public AndLong(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//a1: or-long
		public static class OrLong extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public OrLong(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//a2: xor-long
		public static class XorLong extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public XorLong(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//a3: shl-long
		public static class ShlLong extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public ShlLong(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//a4: shr-long
		public static class ShrLong extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public ShrLong(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//a5: ushr-long
		public static class UshrLong extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public UshrLong(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//a6: add-float
		public static class AddFloat extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public AddFloat(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//a7: sub-float
		public static class SubFloat extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public SubFloat(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//a8: mul-float
		public static class MulFloat extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public MulFloat(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//a9: div-float
		public static class DivFloat extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public DivFloat(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//aa: rem-float
		public static class RemFloat extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public RemFloat(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//ab: add-double
		public static class AddDouble extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public AddDouble(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//ac: sub-double
		public static class SubDouble extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public SubDouble(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//ad: mul-double
		public static class MulDouble extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public MulDouble(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//ae: div-double
		public static class DivDouble extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public DivDouble(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//af: rem-double
		public static class RemDouble extends T
		{
			public String op;
			public String dest;
			public String first;
			public String second;
			public RemDouble(String op, String dest, String first, String second)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.first = first;
				this.second = second;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		
		
		//insn_format30t:INSTRUCTION_FORMAT30t offset_or_label
		//e.g. goto/32 endloop:
		
		//2a 30t	goto/32 +AAAAAAAA
		public static class Goto32 extends T
		{
			public String op;
			public String dest;
			public Goto32(String op, String dest)
			{
				super();
				this.op = op;
				this.dest = dest;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//insn_format31c:INSTRUCTION_FORMAT31c REGISTER string_literal
		//e.g. const-string/jumbo v1 "Hello World!"

		//1b 31c	const-string/jumbo vAA, string@BBBBBBBB
		public static class ConstStringJumbo extends T
		{
			public String op;
			public String dest;
			public String str;
			public ConstStringJumbo(String op, String dest, String str)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.str = str;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//insn_format31i:INSTRUCTION_FORMAT31i REGISTER fixed_32bit_literal
		//e.g. const v0, 123456

		
		//14 31i	const vAA, #+BBBBBBBB
		public static class Const extends T
		{
			public String op;
			public String dest;
			public String value;
			public Const(String op, String dest,String value)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.value = value;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//17 31i	const-wide/32 vAA, #+BBBBBBBB
		public static class ConstWide32 extends T
		{
			public String op;
			public String dest;
			public String value;
			public ConstWide32(String op, String dest,String value)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.value = value;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//insn_format31t :INSTRUCTION_FORMAT31t REGISTER offset_or_label
		//e.g. fill-array-data v0, ArrayData:
		
		
		//?????????????????????????????????????????????
		//26 31t	fill-array-data vAA, +BBBBBBBB 
		//(with supplemental data as specified below in "fill-array-data-payload Format")
		public static class FillArrayData extends T
		{
			public String op;
			public String dest;
			public String src;
			public FillArrayData(String op, String dest, String src)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.src = src;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//?????????????????????????????????????????????
		//2b 31t	packed-switch vAA, +BBBBBBBB 
		//s(with supplemental data as specified below in "packed-switch-payload Format")	
		//A: register to test
		//B: signed "branch" offset to table data pseudo-instruction (32 bits)
		public static class PackedSwitch extends T
		{
			public String op;
			public String test;
			public String offset;
			public PackedSwitch() //throws Exception
			{
				//throw new Exception();
				System.out.println("Instruction: packed-switch");
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//?????????????????????????????????????????????
		//2c 31t	sparse-switch vAA, +BBBBBBBB 
		//(with supplemental data as specified below in "sparse-switch-payload Format")
		//A: register to test
		//B: signed "branch" offset to table data pseudo-instruction (32 bits)	
		public static class SparseSwitch extends T
		{
			public String op;
			public String test;
			public String offset;
			public SparseSwitch() //throws Exception
			{
				//throw new Exception();
				System.out.println("Instruction: sparse-switch");
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}

		
		//insn_format32x:INSTRUCTION_FORMAT32x rREGISTER REGISTER
	    //e.g. move/16 v5678, v1234

		
		//03 32x	move/16 vAAAA, vBBBB
		public static class Move16 extends T
		{
			public String op;
			public String dest;
			public String src;
			public Move16(String op, String dest, String src)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.src = src;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//06 32x	move-wide/16 vAAAA, vBBBB
		public static class MoveWide16 extends T
		{
			public String op;
			public String dest;
			public String src;
			public MoveWide16(String op, String dest, String src)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.src = src;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//09 32x	move-object/16 vAAAA, vBBBB
		public static class MoveObject16 extends T
		{
			public String op;
			public String dest;
			public String src;
			public MoveObject16(String op, String dest, String src)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.src = src;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		
		//insn_format35c_method:INSTRUCTION_FORMAT35c_METHOD register_list fully_qualified_method
		//e.g. invoke-virtual {} java/io/PrintStream/print(Ljava/lang/Stream;)V

		//insn_format35c_type £º:INSTRUCTION_FORMAT35c_TYPE register_list nonvoid_type_descriptor
		//e.g. filled-new-array {}, I

	    
	    //24 35c	filled-new-array {vC, vD, vE, vF, vG}, type@BBBB
		public static class FilledNewArray extends T
		{
			public String op;
			public List<String> argList;
			public String type;
			
			public FilledNewArray() //throws Exception 
			{
				//throw new Exception();
				System.out.println("Instruction: filled-new-array");
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//6e..72 35c	
		//invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB
		//A: argument word count (4 bits)
		//B: method reference index (16 bits)
		//C..G: argument registers (4 bits each)
		//6e: invoke-virtual

		
		//6e: invoke-virtual
		public static class InvokeVirtual extends T
		{
			public String op;
			public List<String> argList;
			public ast.classs.MethodItem methodRef;
			public InvokeVirtual(String op, List<String> argList,
					MethodItem methodRef)
			{
				super();
				this.op = op;
				this.argList = argList;
				this.methodRef = methodRef;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		//6f: invoke-super
		public static class InvokeSuper extends T
		{
			public String op;
			public List<String> argList;
			public ast.classs.MethodItem methodRef;
			public InvokeSuper(String op, List<String> argList,
					MethodItem methodRef)
			{
				super();
				this.op = op;
				this.argList = argList;
				this.methodRef = methodRef;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//70: invoke-direct
		public static class InvokeDirect extends T
		{
			public String op;
			public List<String> argList;
			public ast.classs.MethodItem methodRef;
			public InvokeDirect(String op, List<String> argList,
					MethodItem methodRef)
			{
				super();
				this.op = op;
				this.argList = argList;
				this.methodRef = methodRef;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//71: invoke-static
		public static class InvokeStatic extends T
		{
			public String op;
			public List<String> argList;
			public ast.classs.MethodItem methodRef;
			public InvokeStatic(String op, List<String> argList,
					MethodItem methodRef)
			{
				super();
				this.op = op;
				this.argList = argList;
				this.methodRef = methodRef;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		//72: invoke-interface	
		public static class InvokeInterface extends T
		{
			public String op;
			public List<String> argList;
			public ast.classs.MethodItem methodRef;
			public InvokeInterface(String op, List<String> argList,
					MethodItem methodRef)
			{
				super();
				this.op = op;
				this.argList = argList;
				this.methodRef = methodRef;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
		}
		
		
		 //insn_format3rc_method:INSTRUCTION_FORMAT3rc_METHOD register_range fully_qualified_method
	    //e.g. invoke-virtual/range {} java/lang/StringBuilder/append(Ljava/lang/String;)

	    
	    //insn_format3rc_type:INSTRUCTION_FORMAT3rc_TYPE register_range nonvoid_type_descriptor
	    //e.g. filled-new-array/range {} 

		
	   // 74..78 3rc	invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB
	    //A: argument word count (8 bits)
	   // B: method reference index (16 bits)
	   // C: first argument register (16 bits)
	   //N = A + C - 1
	   // 74: invoke-virtual/range
	    public static class InvokeVirtualRange extends T
	    {
	        public String op;
	        public String start;
	        public String end;
	        public ast.classs.MethodItem methodItem;
			public InvokeVirtualRange(String op, String start, String end,
					MethodItem methodItem)
			{
				super();
				this.op = op;
				this.start = start;
				this.end = end;
				this.methodItem = methodItem;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
	    }
	   // 75: invoke-super/range
	    public static class InvokeSuperRange extends T
	    {
	        public String op;
	        public String start;
	        public String end;
	        public ast.classs.MethodItem methodItem;
			public InvokeSuperRange(String op, String start, String end,
					MethodItem methodItem)
			{
				super();
				this.op = op;
				this.start = start;
				this.end = end;
				this.methodItem = methodItem;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			} 
	    }
	   // 76: invoke-direct/range
	    public static class InvokeDirectRange extends T
	    {
	        public String op;
	        public String start;
	        public String end;
	        public ast.classs.MethodItem methodItem;
			public InvokeDirectRange(String op, String start, String end,
					MethodItem methodItem)
			{
				super();
				this.op = op;
				this.start = start;
				this.end = end;
				this.methodItem = methodItem;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
	    }
	   // 77: invoke-static/range
	    public static class InvokeStaticRange extends T
	    {
	        public String op;
	        public String start;
	        public String end;
	        public ast.classs.MethodItem methodItem;
			public InvokeStaticRange(String op, String start, String end,
					MethodItem methodItem)
			{
				super();
				this.op = op;
				this.start = start;
				this.end = end;
				this.methodItem = methodItem;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
	    }
	   // 78: invoke-interface/range	
	    public static class InvokeInterfaceRange extends T
	    {
	        public String op;
	        public String start;
	        public String end;
	        public ast.classs.MethodItem methodItem;
			public InvokeInterfaceRange(String op, String start, String end,
					MethodItem methodItem)
			{
				super();
				this.op = op;
				this.start = start;
				this.end = end;
				this.methodItem = methodItem;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
	    }
	    
	    //25 3rc	filled-new-array/range {vCCCC .. vNNNN}, type@BBBB	
	    //A: array size and argument word count (8 bits)
	    //B: type index (16 bits)
	    //C: first argument register (16 bits)
	    //N = A + C - 1
	    public static class FilledNewArrayRange extends T
	    {
	        public String op;
	        public String start;
	        public String end;
	        public String type;
	        public FilledNewArrayRange() //throws Exception
	        {
	        	//throw new Exception();
	        	System.out.println("Instruction:filled-new-array/range");
	        }
			public void accept(Visitor v)
			{
				v.visit(this);
			}
	    }
	    
	    //insn_format51l_type:INSTRUCTION_FORMAT51l REGISTER fixed_64bit_literal
	    ///e.g. const-wide v0, 5000000000L


		//18 51l	const-wide vAA, #+BBBBBBBBBBBBBBBB
	    public static class ConstWide extends T
	    {
	        public String op;
	        public String dest;
	        public String value;
			public ConstWide(String op, String dest, String value)
			{
				super();
				this.op = op;
				this.dest = dest;
				this.value = value;
			}
			@Override
			public void accept(Visitor v)
			{
				v.visit(this);
			}
	    }
		

		
	    
	    
}
