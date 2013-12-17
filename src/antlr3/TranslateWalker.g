/*
 * [The "BSD licence"]
 * Copyright (c) 2010 Ben Gruver
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

tree grammar TranslateWalker;

options {
  tokenVocab=smaliParser;
  ASTLabelType=CommonTree;
}

@header {
package antlr3;

import org.jf.smali.LiteralTools;
}

smali_file returns [ast.classs.Class clazz]
  : ^(I_CLASS_DEF header methods fields annotations)
  
  {
  	  $clazz = new ast.classs.Class();
  	  $clazz.FullyQualifiedName = $header.className;
  	  $clazz.superName = $header.superName;
  	  $clazz.source = $header.source;
  	  $clazz.accessList = $header.accessList;
  	  $clazz.methods = $methods.methodList;
  	  
      System.out.println("in smali_file");
  }
  ;


header returns [String className, String superName, String source, List<String> accessList, List<String> implementsList]
@init{
	$superName = "Ljava/lang/Object;";
}
  : class_spec super_spec? implements_list source_spec 
  {
  	$className = $class_spec.className; 
  	$accessList =  $class_spec.accessList;
  	$superName = $super_spec.className;
  	$source = $source_spec.source;
  }
  ;


class_spec returns [String className, List<String> accessList]
  : class_type_descriptor access_list
  {
  	$className = $class_type_descriptor.className;
  	$accessList = $access_list.accessList;
  };

super_spec returns [String className]
  : ^(I_SUPER class_type_descriptor)
  {
  	$className = $class_type_descriptor.className;
  };


implements_spec returns [List<String> implementsList]
@init {
 $implementsList = new ArrayList<String>();
}
  : ^(I_IMPLEMENTS class_type_descriptor)
  {
  	$implementsList.add($class_type_descriptor.className);
  };

implements_list
  : (implements_spec {} )*
  {
  };

source_spec returns [String source]
  :
    ^(I_SOURCE string_literal { $source = $string_literal.value; })
  | /*epsilon*/;



access_list returns [List<String> accessList]
@init{
	$accessList = new ArrayList<String>();
}
  : ^(I_ACCESS_LIST
      (
        ACCESS_SPEC
        {
        	$accessList.add($ACCESS_SPEC.text);
        }
      )*);


fields
  : ^(I_FIELDS
      (field
      {
      })*);

methods returns [List<ast.method.Method> methodList]
@init{
	$methodList = new ArrayList<ast.method.Method>(); 
}
  : ^(I_METHODS
      (method
      {
      	$methodList.add($method.method);
      })*);

field
  :^(I_FIELD SIMPLE_NAME access_list ^(I_FIELD_TYPE nonvoid_type_descriptor) field_initial_value annotations?)
  {
  };


field_initial_value
  : ^(I_FIELD_INITIAL_VALUE literal) {}
  | /*epsilon*/;

literal
  : integer_literal {}
  | long_literal {}
  | short_literal {}
  | byte_literal {}
  | float_literal {}
  | double_literal {}
  | char_literal {}
  | string_literal {}
  | bool_literal {}
  | NULL_LITERAL {}
  | type_descriptor {}
  | array_literal {}
  | subannotation {}
  | field_literal {}
  | method_literal {}
  | enum_literal {};


//everything but string
fixed_size_literal
  : integer_literal {}
  | long_literal {}
  | short_literal {}
  | byte_literal {}
  | float_literal {}
  | double_literal {}
  | char_literal {}
  | bool_literal {};

//everything but string
fixed_64bit_literal
  : integer_literal {}
  | long_literal {}
  | short_literal {}
  | byte_literal {}
  | float_literal {}
  | double_literal {}
  | char_literal {}
  | bool_literal {};

//everything but string and double
//long is allowed, but it must fit into an int
fixed_32bit_literal returns [String value]
  : a=(integer_literal {}
  | long_literal {}
  | short_literal {}
  | byte_literal {}
  | float_literal {}
  | char_literal {}
  | bool_literal {}){ $avalue = $a.value;}
  ;

array_elements
  : {}
    ^(I_ARRAY_ELEMENTS
      (fixed_size_literal
      {
      })*);

packed_switch_target_count
  : I_PACKED_SWITCH_TARGET_COUNT {};

packed_switch_targets
  :
    ^(I_PACKED_SWITCH_TARGETS
      packed_switch_target_count
      {
      }

      (offset_or_label
      {
      })*
    );

sparse_switch_target_count
  : I_SPARSE_SWITCH_TARGET_COUNT {};

sparse_switch_keys
  : ^(I_SPARSE_SWITCH_KEYS
      (fixed_32bit_literal
      {
      })*
    );


sparse_switch_targets
  : ^(I_SPARSE_SWITCH_TARGETS
      (offset_or_label
      {
      })*
    );

method returns [ast.method.Method method]
@init{
	$method = new ast.method.Method(); /* Be careful! It's better to move it here. YKG. */
}
  : ^(I_METHOD
      method_name_and_prototype
      access_list
      {
      	$method.accessList = $access_list.accessList;
      }
      (registers_directive
       {
       	$method.registers_directive = $registers_directive.type;
       	$method.registers_directive_count = $registers_directive.count;
       }
      )?
      labels
      packed_switch_declarations
      sparse_switch_declarations
      statements
      catches
      parameters
      ordered_debug_directives
      annotations
    )
  {
  	
  	$method.name = $method_name_and_prototype.method_name;
  	$method.prototype = $method_name_and_prototype.prototype;
  	
  	$method.statements = $statements.stmts;
  };

method_prototype returns [ast.method.Method.MethodPrototype prototype]
  : ^(I_METHOD_PROTOTYPE ^(I_METHOD_RETURN_TYPE type_descriptor) field_type_list)
  {
  	$prototype = new ast.method.Method.MethodPrototype();
	$prototype.returnType = $type_descriptor.type_desc;
	$prototype.argsType = $field_type_list.argsTypeList;
  };

method_name_and_prototype returns [String method_name, ast.method.Method.MethodPrototype prototype]
  : SIMPLE_NAME method_prototype
  {
  	$method_name = $SIMPLE_NAME.text;
  	$prototype = $method_prototype.prototype;
  };

field_type_list returns [List<String> argsTypeList]
@init{
	$argsTypeList = new ArrayList<String>();
}
  : (
      nonvoid_type_descriptor
      {
		$argsTypeList.add($nonvoid_type_descriptor.type_desc);
      }
    )*;


fully_qualified_method returns [ast.classs.MethodItem methodItem]
@init{
	$methodItem = new ast.classs.MethodItem();
}
  : reference_type_descriptor SIMPLE_NAME method_prototype
  {
  	$methodItem.classType = $reference_type_descriptor.ref_desc;
  	$methodItem.methodName = $SIMPLE_NAME.text;
  	$methodItem.prototype = $method_prototype.prototype;
  };

fully_qualified_field returns [ast.classs.FieldItem fieldItem]
@init{
	$fieldItem = new ast.classs.FieldItem();
}
  : reference_type_descriptor SIMPLE_NAME nonvoid_type_descriptor
  {
  	$fieldItem.classType = $reference_type_descriptor.ref_desc;
  	$fieldItem.fieldName = $SIMPLE_NAME.text;
  	$fieldItem.fieldType = $nonvoid_type_descriptor.type_desc;
  };

registers_directive returns [String type, int count]
  : {}
    ^(( I_REGISTERS { $type = ".registers"; }
      | I_LOCALS { $type = ".locals"; }
      )
      short_integral_literal { $count = $short_integral_literal.value; }
     );

labels
  : ^(I_LABELS label_def*);

label_def
  : ^(I_LABEL SIMPLE_NAME address)
    {
    };

packed_switch_declarations
  : ^(I_PACKED_SWITCH_DECLARATIONS packed_switch_declaration*);

packed_switch_declaration
  : ^(I_PACKED_SWITCH_DECLARATION address offset_or_label_absolute)
    {
    };

sparse_switch_declarations
  : ^(I_SPARSE_SWITCH_DECLARATIONS sparse_switch_declaration*);

sparse_switch_declaration
  : ^(I_SPARSE_SWITCH_DECLARATION address offset_or_label_absolute)
    {
    };

catches : ^(I_CATCHES catch_directive* catchall_directive*);

catch_directive
  : ^(I_CATCH address nonvoid_type_descriptor from=offset_or_label_absolute to=offset_or_label_absolute using=offset_or_label_absolute)
    {
    };

catchall_directive
  : ^(I_CATCHALL address from=offset_or_label_absolute to=offset_or_label_absolute using=offset_or_label_absolute)
    {
    };

address
  : I_ADDRESS
    {
    };

parameters
  : ^(I_PARAMETERS (parameter
        {
        })*
    )
  ;

parameter
  : ^(I_PARAMETER (string_literal {}
                  | {}
                  )
        annotations {}
    );

ordered_debug_directives
  : ^(I_ORDERED_DEBUG_DIRECTIVES
       ( line
       | local
       | end_local
       | restart_local
       | prologue
       | epilogue
       | source
       )*
     );

line
  : ^(I_LINE integral_literal address)
    {
    };

local
  : ^(I_LOCAL REGISTER SIMPLE_NAME nonvoid_type_descriptor string_literal? address)
    {
    };

end_local
  : ^(I_END_LOCAL REGISTER address)
    {
    };

restart_local
  : ^(I_RESTART_LOCAL REGISTER address)
    {
    };

prologue
  : ^(I_PROLOGUE address)
    {
    };

epilogue
  : ^(I_EPILOGUE address)
    {
    };

source
  : ^(I_SOURCE string_literal address)
    {
    };

statements returns [List<ast.stm.T> stmts]
@init{
	$stmts = new ArrayList<ast.stm.T>();
}
  : ^(I_STATEMENTS (instruction
        {
        	$stmts.add($instruction.stmt);
        })*);

label_ref
  : SIMPLE_NAME
    {
    };

offset
  : OFFSET
    {
    };

offset_or_label_absolute 
  : offset {}
  | label_ref {}
  ;

offset_or_label returns [String offorlab]
  : a=(offset {}
  | label_ref {}) {$offorlab=$a.text;}
  ;


register_list returns [ArrayList<String> argList]
@init{
	$regList = new ArrayList<String>();
}
  : ^(I_REGISTER_LIST
      (REGISTER
      {
    	$argList.add($REGISTER.text);  	
      })*);

register_range
  : ^(I_REGISTER_RANGE (startReg=REGISTER endReg=REGISTER?)?)
    {
    }
  ;

verification_error_reference
  : CLASS_DESCRIPTOR
  {
  }
/*
  | fully_qualified_field
  {
  }
  */
  | fully_qualified_method
  {
  };

verification_error_type
  : VERIFICATION_ERROR_TYPE
  {
  };

instruction returns [ast.stm.T stmt]
  : insn_format10t {}
  | insn_format10x {}
  | insn_format11n {}
  | insn_format11x {}
  | insn_format12x {}
  | insn_format20bc {}
  | insn_format20t {}
  | insn_format21c_field {}
  | insn_format21c_string {}
  | insn_format21c_type {}
  | insn_format21h {}
  | insn_format21s {}
  | insn_format21t {}
  | insn_format22b {}
  | insn_format22c_field {}
  | insn_format22c_type {}
  | insn_format22s {}
  | insn_format22t {}
  | insn_format22x {}
  | insn_format23x {}
  | insn_format30t {}
  | insn_format31c {}
  | insn_format31i {}
  | insn_format31t {}
  | insn_format32x {}
  | insn_format35c_method {}
  | insn_format35c_type {}
  | insn_format3rc_method {}
  | insn_format3rc_type {}
  | insn_format41c_type {}
  | insn_format41c_field {}
  | insn_format51l_type {}
  | insn_format52c_type {}
  | insn_format52c_field {}
  | insn_format5rc_method {}
  | insn_format5rc_type {}
  | insn_array_data_directive {}
  | insn_packed_switch_directive {}
  | insn_sparse_switch_directive {};


insn_format10t returns [ast.stm.T inst] 

  : //e.g. goto endloop:
    {}
    ^(I_STATEMENT_FORMAT10t a=INSTRUCTION_FORMAT10t b=offset_or_label.offorlab)
    {
        switch($a.text)
        {
          //goto Goto
          case "goto": inst =  new ast.stm.Goto($a.text,$b.offorlab);
          default: ;
        }
    };
insn_format10x returns [ast.stm.T inst]
  : //e.g. return
    ^(I_STATEMENT_FORMAT10x a=INSTRUCTION_FORMAT10x)
    {
    	     switch($a.text)
           {
              // nop Nop
             case "nop" : inst = new ast.stm.Nop($a.text);
             //return-void ReturnVoid
             case "return-void": inst = new ast.stm.ReturnVoid($a.text);
             default :;
           }
    };
insn_format11n returns [ast.stm.T inst]

  : //e.g. const/4 v0, 5
    ^(I_STATEMENT_FORMAT11n a=INSTRUCTION_FORMAT11n b=REGISTER c=short_integral_literal)
    {
        switch($a.text)
        {
          //const/4 Const4
          case "const/4" : inst = new ast.stm.Const4($a.text,$b.text,$c.value);
          default :;
        }
    };
insn_format11x returns [ast.stm.T inst]
  : //e.g. move-result-object v1
    ^(I_STATEMENT_FORMAT11x a=INSTRUCTION_FORMAT11x b=REGISTER)
    {
        switch($a.text)
        {
          //move-result MoveResult
          case "move-result" inst =  new ast.stm.MoveResult($a.text,$b.text);
          //move-result-wide MoveResultWide
          case "move-result-wide": inst =  new ast.stm.MoveResultWide($a.text,$b.text);
          //move-result-object  MoveResultObject
          case "move-result-object": inst =  new ast.stm.MoveResultObject($a.text,$b.text);
          //move-exception MoveException
          case "move-exception": inst =  new ast.stm.MoveException($a.text,$b.text);
          //return Return
          case "return": inst =  new ast.stm.Return($a.text,$b.text);
          //return-wide ReturnWide
          case "return-wide": inst =  new ast.stm.ReturnWide($a.text,$b.text);
          //return-object ReturnObject
          case "return-object": inst =  new ast.stm.ReturnObject($a.text,$b.text);
          //monitor-enter  MonitorEnter
          case "monitor-enter": inst =  new ast.stm.MonitorEnter($a.text,$b.text);
          //monitor-exit MonitorExit
          case "monitor-exit": inst =  new ast.stm.MonitorExit($a.text,$b.text);
          //throw Throw
          case "throw Throw": inst =  new ast.stm.Throw($a.text,$b.text);
          default :;
        }
    };
insn_format12x returns [ast.stm.T inst]

  : //e.g. move v1 v2
      ^(I_STATEMENT_FORMAT12x a=INSTRUCTION_FORMAT12x c=REGISTER d=REGISTER)
      {
        switch($a.text)
        {
          
          //01 12x move Move
          case "move": inst =  new ast.stm.Move($a.text,$b.text,$c.text);
          //04 12x move-wide MoveWide
          case "move-wide": inst =  new ast.stm.MoveWide($a.text,$b.text,$c.text);
          //07 12x move-object MoveObject
          case "move-object": inst =  new ast.stm.MoveObject($a.text,$b.text,$c.text);
          //21 12x array-length arrayLength
          case "array-length": inst =  new ast.stm.arrayLength($a.text,$b.text,$c.text);
          //7b: neg-int NegInt
          case "neg-int": inst =  new ast.stm.NegInt($a.text,$b.text,$c.text);
          //7c: not-int NotInt
          case "not-int": inst =  new ast.stm.NotInt($a.text,$b.text,$c.text);
          //7d: neg-long NegLong
          case "neg-long": inst =  new ast.stm.NegLong($a.text,$b.text,$c.text);
          //7e: not-long  NotLong
          case "not-long": inst =  new ast.stm.NotLong($a.text,$b.text,$c.text);
          //7f: neg-float NegFloat
          case "neg-float": inst =  new ast.stm.NegFloat($a.text,$b.text,$c.text);
          //80: neg-double NegDouble
          case "neg-double": inst =  new ast.stm.NegDouble($a.text,$b.text,$c.text);
          //81: int-to-long IntToLong
          case "int-to-long": inst =  new ast.stm.IntToLong($a.text,$b.text,$c.text);
          //82: int-to-float IntToFloat
          case "int-to-float": inst =  new ast.stm.IntToFloat($a.text,$b.text,$c.text);
          //83: int-to-double IntToDouble
          case "int-to-double": inst =  new ast.stm.IntToDouble($a.text,$b.text,$c.text);
          //84: long-to-int LongToInt
          case "long-to-int": inst =  new ast.stm.LongToInt($a.text,$b.text,$c.text);
          //85: long-to-float LongToFloat
          case "long-to-float": inst =  new ast.stm.LongToFloat($a.text,$b.text,$c.text);
          //86: long-to-double LongToDouble
          case "long-to-double": inst =  new ast.stm.LongToDouble($a.text,$b.text,$c.text);
          //87: float-to-int FloatToInt
          case "float-to-int": inst =  new ast.stm.FloatToInt($a.text,$b.text,$c.text);
          //88: float-to-long FloatToLong
          case "float-to-long": inst =  new ast.stm.FloatToLong($a.text,$b.text,$c.text);
          //89: float-to-double FloatToDouble
          case "float-to-double": inst =  new ast.stm.FloatToDouble($a.text,$b.text,$c.text);
          //8a: double-to-int DoubleToInt
          case "double-to-int": inst =  new ast.stm.DoubleToInt($a.text,$b.text,$c.text);
          //8b: double-to-long DoubleToLong
          case "double-to-long": inst =  new ast.stm.DoubleToLong($a.text,$b.text,$c.text);
          //8c: double-to-float DoubleToFloat
          case "double-to-float": inst =  new ast.stm.DoubleToFloat($a.text,$b.text,$c.text);
          //8d: int-to-byte IntToByte
          case "int-to-byte": inst =  new ast.stm.IntToByte($a.text,$b.text,$c.text);
          //8e: int-to-char  IntToChar
          case "int-to-char": inst =  new ast.stm.IntToChar($a.text,$b.text,$c.text);
          //8f: int-to-short IntToShort
          case "int-to-short": inst =  new ast.stm.IntToShort($a.text,$b.text,$c.text);

          //b0: add-int/2addr AddInt2Addr
          case "add-int/2addr": inst =  new ast.stm.AddInt2Addr($a.text,$b.text,$c.text);
          //b1: sub-int/2addr SubInt2Addr    
          case "sub-int/2addr": inst =  new ast.stm.SubInt2Addr($a.text,$b.text,$c.text);
          //b2: mul-int/2addr MulInt2Addr
          case "mul-int/2addr": inst =  new ast.stm.MulInt2Addr($a.text,$b.text,$c.text);
          //b3: div-int/2addr DivInt2Addr
          case "div-int/2addr": inst =  new ast.stm.DivInt2Addr($a.text,$b.text,$c.text);
          //b4: rem-int/2addr RemInt2Addr
          case "rem-int/2addr": inst =  new ast.stm.RemInt2Addr($a.text,$b.text,$c.text);
          //b5: and-int/2addr AndInt2Addr
          case "and-int/2add": inst =  new ast.stm.AndInt2Addr($a.text,$b.text,$c.text);
          //b6: or-int/2addr OrInt2Addr
          case "or-int/2addr": inst =  new ast.stm.OrInt2Addr($a.text,$b.text,$c.text);
          //b7: xor-int/2addr XorInt2Addr
          case "xor-int/2addr": inst =  new ast.stm.XorInt2Addr($a.text,$b.text,$c.text);
          //b8: shl-int/2addr ShlInt2Addr
          case "shl-int/2addr": inst =  new ast.stm.ShlInt2Addr($a.text,$b.text,$c.text);
          //b9: shr-int/2addr  ShrInt2Addr
          case "shr-int/2addr": inst =  new ast.stm.ShrInt2Addr($a.text,$b.text,$c.text);
          //ba: ushr-int/2addr UshrInt2Addr
          case "ushr-int/2addr": inst =  new ast.stm.UshrInt2Addr($a.text,$b.text,$c.text);
          //bb: add-long/2addr AddLong2Addr
          case "add-long/2addr": inst =  new ast.stm.AddLong2Addr($a.text,$b.text,$c.text);
          //bc: sub-long/2addr SubLong2Addr
          case "sub-long/2addr": inst =  new ast.stm.SubLong2Addr($a.text,$b.text,$c.text);
          //bd: mul-long/2addr MulLong2Addr
          case "mul-long/2addr": inst =  new ast.stm.MulLong2Addr($a.text,$b.text,$c.text);
          //be: div-long/2addr DivLong2Addr
          case "div-long/2addr": inst =  new ast.stm.DivLong2Addr($a.text,$b.text,$c.text);
          //bf: rem-long/2addr RemLong2Addr
          case "rem-long/2addr": inst =  new ast.stm.RemLong2Addr($a.text,$b.text,$c.text);
          //c0: and-long/2addr AndLong2Addr
          case "and-long/2addr": inst =  new ast.stm.AndLong2Addr($a.text,$b.text,$c.text);
          //c1: or-long/2addr OrLong2Addr
          case "or-long/2addr": inst =  new ast.stm.OrLong2Addr($a.text,$b.text,$c.text);
          //c2: xor-long/2addr XorLong2Addr
          case "xor-long/2addr": inst =  new ast.stm.XorLong2Addr($a.text,$b.text,$c.text);
          //c3: shl-long/2addr ShlLong2Addr
          case "shl-long/2addr": inst =  new ast.stm.ShlLong2Addr($a.text,$b.text,$c.text);
          //c4: shr-long/2addr ShrLong2Addr
          case "shr-long/2addr": inst =  new ast.stm.ShrLong2Addr($a.text,$b.text,$c.text);
          //c5: ushr-long/2addr UshrLong2Addr
          case "ushr-long/2addr": inst =  new ast.stm.UshrLong2Addr($a.text,$b.text,$c.text);
          //c6: add-float/2addr AddFloat2Addr
          case "add-float/2addr": inst =  new ast.stm.AddFloat2Addr($a.text,$b.text,$c.text);
          //c7: sub-float/2addr SubFloat2Addr
          case "sub-float/2addr": inst =  new ast.stm.SubFloat2Addr($a.text,$b.text,$c.text);
          //c8: mul-float/2addr MulFloat2Addr
          case "mul-float/2addr: inst =  new ast.stm.MulFloat2Addr($a.text,$b.text,$c.text);
          //c9: div-float/2addr DivFloat2Addr
          case "div-float/2addr": inst =  new ast.stm.DivFloat2Addr($a.text,$b.text,$c.text);
          //ca: rem-float/2addr RemFloat2Addr
          case "rem-float/2addr": inst =  new ast.stm.RemFloat2Addr($a.text,$b.text,$c.text);
          //cb: add-double/2addr AddDouble2Addr
          case "add-double/2addr": inst =  new ast.stm.AddDouble2Addr($a.text,$b.text,$c.text);
          //cc: sub-double/2addr SubDouble2Addr
          case "sub-double/2addr": inst =  new ast.stm.SubDouble2Addr($a.text,$b.text,$c.text);
          //cd: mul-double/2addr MulDouble2Addr
          case "mul-double/2addr": inst =  new ast.stm.MulDouble2Addr($a.text,$b.text,$c.text);
          //ce: div-double/2addr DivDouble2Addr
          case "div-double/2addr": inst =  new ast.stm.DivDouble2Addr($a.text,$b.text,$c.text);
          //cf: rem-double/2addr RemDouble2Addr
          case "rem-double/2addr": inst =  new ast.stm.RemDouble2Addr($a.text,$b.text,$c.text);
          default:;
        }
      };

insn_format20bc returns [ast.stm.T inst]  // can not find in dalvik bytecode
  : //e.g. throw-verification-error generic-error, Lsome/class;
    ^(I_STATEMENT_FORMAT20bc INSTRUCTION_FORMAT20bc verification_error_type verification_error_reference)
    {
    };

insn_format20t returns [ast.stm.T inst]
  : //e.g. goto/16 endloop:
    ^(I_STATEMENT_FORMAT20t a=INSTRUCTION_FORMAT20t b=offset_or_label)
    {
        
        switch($a.text)
        {
          //goto/16 Goto16
          case: "goto/16" inst = new ast.stm.Goto16($a.text,$b.offorlab);
          default:;
        }
    };





insn_format21c_field returns [ast.stm.T inst]
  : //e.g. sget_object v0, java/lang/System/out LJava/io/PrintStream;
    ^(I_STATEMENT_FORMAT21c_FIELD a(INSTRUCTION_FORMAT21c_FIELD | INSTRUCTION_FORMAT21c_FIELD_ODEX)
    b=REGISTER c=fully_qualified_field)
    {
        switch($a.text)
        {
          
          //60: sget Sget
          case:"sget" inst = new ast.stm.Sget($a.text,$b.text,$c.fieldItem);
          //61: sget-wide SgetWide
          case:"sget-wide" inst = new ast.stm.SgetWide($a.text,$b.text,$c.fieldItem);
          //62: sget-object SgetObject
          case:"" inst = new ast.stm.SgetObject($a.text,$b.text,$c.fieldItem);
          //63: sget-boolean SgetBoolean
          case:"sget-object" inst = new ast.stm.SgetBoolean($a.text,$b.text,$c.fieldItem);
          //64: sget-byte SgetByte
          case:"sget-byte" inst = new ast.stm.SgetByte($a.text,$b.text,$c.fieldItem);
          //65: sget-char SgetChar
          case:"sget-char" inst = new ast.stm.SgetChar($a.text,$b.text,$c.fieldItem);
          //66: sget-short SgetShort
          case:"sget-short" inst = new ast.stm.SgetShort($a.text,$b.text,$c.fieldItem);
          //67: sput Sput
          case:"sput" inst = new ast.stm.Sput($a.text,$b.text,$c.fieldItem);
          //68: sput-wide  SputWide
          case:"sput-wide" inst = new ast.stm.SputWide($a.text,$b.text,$c.fieldItem);
          //69: sput-object SputObject
          case:"sput-object" inst = new ast.stm.SputObject($a.text,$b.text,$c.fieldItem);
          //6a: sput-boolean SputBoolean
          case:"sput-boolean" inst = new ast.stm.SputBoolean($a.text,$b.text,$c.fieldItem);
          //6b: sput-byte SputByte
          case:"sput-byte" inst = new ast.stm.SputByte($a.text,$b.text,$c.fieldItem);
          //6c: sput-char SputChar
          case:"sput-char" inst = new ast.stm.SputChar($a.text,$b.text,$c.fieldItem);
          //6d: sput-short SputShort
          case:"sput-short" inst = new ast.stm.SputShort($a.text,$b.text,$c.fieldItem);
        }

    };

insn_format21c_string returns [ast.stm.T inst]
  : //e.g. const-string v1, "Hello World!"
    ^(I_STATEMENT_FORMAT21c_STRING a=INSTRUCTION_FORMAT21c_STRING b=REGISTER c=string_literal)
    {
      
      switch($a.text)
      {
        //1a 21c  const-string ConstString
        case "const-string" : inst = new ast.stm.ConstString($a.text,$b.text,$c.value);
        default:;
      }

    };

insn_format21c_type returns [ast.stm.T inst]
  : //e.g. const-class v2, org/jf/HelloWorld2/HelloWorld2
    ^(I_STATEMENT_FORMAT21c_TYPE a=INSTRUCTION_FORMAT21c_TYPE b=REGISTER c=reference_type_descriptor) 
    {
        switch($a.text)
        {
            //1c 21c  const-class ConstClass
            case "const-class" inst = new ast.stm.ConstClass($a.txt,$b.text,$c.ref);
            //1f 21c  check-cast CheckCast
            case "check-cast" inst = new ast.stm.CheckCast($a.txt,$b.text,$c.ref);
            //22 21c  new-instance NewInstance
            case "new-instance" inst = new ast.stm.NewInstance($a.txt,$b.text,$c.ref);
            default:;
        }

    };

insn_format21h returns [ast.stm.T inst]
  : //e.g. const/high16 v1, 1234
    ^(I_STATEMENT_FORMAT21h a=INSTRUCTION_FORMAT21h b=REGISTER c=short_integral_literal)
    {
        switch($a.text)
        {
          //15 21h  const/high16 ConstHigh16
          case "const/high16": inst = new ast.stm.ConstHigh16($a.text,$b.text,$c.value);
          //19 21h  const-wide/high16 ConstWideHigh16
          case "const-wide/high16": inst = new ast.stm.ConstWideHigh16($a.text,$b.text,$c.value);
          default:;
        }
    };



insn_format21s returns [ast.stm.T inst]
  : //e.g. const/16 v1, 1234
    ^(I_STATEMENT_FORMAT21s a=INSTRUCTION_FORMAT21s b=REGISTER c=short_integral_literal)
    {
      switch($a.text)
      {
        //13 21s  const/16 Const16
        case "const/16" : inst = new ast.stm.Const16($a.text,$b.text,$c.value);
        //16 21s const-wide/16 ConstWide16
        case "const-wide/16" : inst = new ast.stm.ConstWide16();
        default: ;
      }
      
    };

insn_format21t returns [ast.stm.T inst]
  : //e.g. if-eqz v0, endloop:
    ^(I_STATEMENT_FORMAT21t a=INSTRUCTION_FORMAT21t b=REGISTER c=offset_or_label)
    {
      switch($a.text)
      {
        //38..3d 21t 
        //38: if-eqz IfEqz
        case "if-eqz" : inst new = ast.stm.IfEqz($a.text,$c.offorlab);
        //39: if-nez IfNez
        case "if-nez " : inst new = ast.stm.IfNez($a.text,$c.offorlab);
        //3a: if-ltz IfLtz
        case "if-ltz" : inst new = ast.stm.IfLtz($a.text,$c.offorlab);
        //3b: if-gez IfGez
        case "if-gez" : inst new = ast.stm.IfGez($a.text,$c.offorlab);
        //3c: if-gtz IfGtz
        case "if-gtz" : inst new = ast.stm.IfGtz($a.text,$c.offorlab);
        //3d: if-lez IfLez
        case "if-lez : inst new = ast.stm.IfLez($a.text,$c.offorlab);
        default:;
      }
    };

insn_format22b returns [ast.stm.T inst]
  : //e.g. add-int v0, v1, 123
    ^(I_STATEMENT_FORMAT22b a=INSTRUCTION_FORMAT22b b=REGISTER c=REGISTER d=short_integral_literal)
    {
      switch($a.text)
      {
        //d8..e2 22b  binop/lit8 vAA, vBB, #+CC
        //d8: add-int/lit8 AddIntLit8
        case "add-int/lit8": inst = new ast.stm.AddIntLit8($a.text,$b.text,$c.text,$d.value);
        //d9: rsub-int/lit8 RsubIntLit8
        case "rsub-int/lit8": inst = new ast.stm.RsubIntLit8($a.text,$b.text,$c.text,$d.value);
        //da: mul-int/lit8 MulIntLit8
        case "mul-int/lit8": inst = new ast.stm.MulIntLit8($a.text,$b.text,$c.text,$d.value);
        //db: div-int/lit8 DivIntLit8
        case "div-int/lit8 ": inst = new ast.stm.DivIntLit8($a.text,$b.text,$c.text,$d.value);
        //dc: rem-int/lit8  RemIntLit8
        case "rem-int/lit8": inst = new ast.stm.RemIntLit8($a.text,$b.text,$c.text,$d.value);
        //dd: and-int/lit8 AndIntLit8
        case "and-int/lit8": inst = new ast.stm.AndIntLit8($a.text,$b.text,$c.text,$d.value);
        //de: or-int/lit8 OrIntLit8
        case "or-int/lit8": inst = new ast.stm.OrIntLit8($a.text,$b.text,$c.text,$d.value);
        //df: xor-int/lit8 XorIntLit8
        case "xor-int/lit8": inst = new ast.stm.XorIntLit8($a.text,$b.text,$c.text,$d.value);
        //e0: shl-int/lit8 ShlIntLit8
        case "shl-int/lit8": inst = new ast.stm.ShlIntLit8($a.text,$b.text,$c.text,$d.value);
        //e1: shr-int/lit8 ShrIntLit8
        case "shr-int/lit8": inst = new ast.stm.ShrIntLit8($a.text,$b.text,$c.text,$d.value);
        //e2: ushr-int/lit8 UshrIntLit8
        case "ushr-int/lit8": inst = new ast.stm.UshrIntLit8($a.text,$b.text,$c.text,$d.value);
        default:;
      }
    };


insn_format22c_field returns [ast.stm.T inst]
  : //e.g. iput-object v1, v0,sorg/jf/HelloWorld2/HelloWorld2.helloWorld Ljava/lang/String;
    ^(I_STATEMENT_FORMAT22c_FIELD a=(INSTRUCTION_FORMAT22c_FIELD | INSTRUCTION_FORMAT22c_FIELD_ODEX) b=REGISTER c=REGISTER d=fully_qualified_field) //fieldItem
    {
       switch($a.text)
       {
          //52..5f 22c  
          //iinstanceop vA, vB, field@CCCC
          //52: iget  Iget
          case "iget" : inst = new ast.stm.Iget($a.text,$b.text,$c.text,$d.fieldItem);
          //53: iget-wide IgetWide
          case "iget-wide" : inst = new ast.stm.IgetWide($a.text,$b.text,$c.text,$d.fieldItem);
          //54: iget-object IgetOjbect
          case "iget-object" : inst = new ast.stm.IgetOjbect($a.text,$b.text,$c.text,$d.fieldItem);
          //55: iget-boolean IgetBoolean
          case "iget-boolean" : inst = new ast.stm.IgetBoolean($a.text,$b.text,$c.text,$d.fieldItem);
          //56: iget-byte IgetByte
          case "iget-byte" : inst = new ast.stm.IgetByte($a.text,$b.text,$c.text,$d.fieldItem);
          //57: iget-char IgetChar
          case "iget-char" : inst = new ast.stm.IgetChar($a.text,$b.text,$c.text,$d.fieldItem);
          //58: iget-short IgetShort
          case "iget-short" : inst = new ast.stm.IgetShort($a.text,$b.text,$c.text,$d.fieldItem);
          //59: iput Iput
          case "iput" : inst = new ast.stm.Iput($a.text,$b.text,$c.text,$d.fieldItem);
          //5a: iput-wide IputWide 
          case "iput-wide" : inst = new ast.stm.IputWide($a.text,$b.text,$c.text,$d.fieldItem);
          //5b: iput-object IputObject
          case "iput-object" : inst = new ast.stm.IputObject($a.text,$b.text,$c.text,$d.fieldItem);
          //5c: iput-boolean IputBoolean
          case "iput-boolean" : inst = new ast.stm.IputBoolean($a.text,$b.text,$c.text,$d.fieldItem);
          //5d: iput-byte IputByte
          case "iput-byte" : inst = new ast.stm.IputByte($a.text,$b.text,$c.text,$d.fieldItem);
          //5e: iput-char IputChar
          case "iput-char" : inst = new ast.stm.IputChar($a.text,$b.text,$c.text,$d.fieldItem);
          //5f: iput-short IputShort
          case "iput-short" : inst = new ast.stm.IputShort($a.text,$b.text,$c.text,$d.fieldItem);
          default:;
       }
    };

insn_format22c_type returns [ast.stm.T inst]
  : //e.g. instance-of v0, v1, Ljava/lang/String;
    ^(I_STATEMENT_FORMAT22c_TYPE a=INSTRUCTION_FORMAT22c_TYPE b=REGISTER c=REGISTER d=nonvoid_type_descriptor) //type_desc
    {
        switch($a.text)
        {
            //20 22c  instance-of InstanceOf
            case "instance-of" : inst = new ast.stm.InstanceOf($a.text,$b.text,$c.text,$d.type_desc);
            //23 22c  new-array NewArray
            case "new-array" : inst = new ast.stm.NewArray($a.text,$b.text,$c.text,$d.type_desc);
            default:;
        }
    };

insn_format22s returns [ast.stm.T inst]
  : //e.g. add-int/lit16 v0, v1, 12345
    ^(I_STATEMENT_FORMAT22s a=INSTRUCTION_FORMAT22s b=REGISTER c=REGISTER d=short_integral_literal)//value
    {
        switch($a.text)
        {
          d0..d7 22s  binop/lit16 vA, vB, #+CCCC
          //d0: add-int/lit16 AddIntLit16
          case "add-int/lit16" : inst = new ast.stm.AddIntLit16($a.text,$b.text,$c.text,$d.value);
          //d1: rsub-int (reverse subtract) RsubInt
          case "rsub-int" : inst = new ast.stm.RsubInt($a.text,$b.text,$c.text,$d.value);
          //d2: mul-int/lit16 MulIntLit16
          case "mul-int/lit16" : inst = new ast.stm.MulIntLit16($a.text,$b.text,$c.text,$d.value);
          //d3: div-int/lit16 DivIntLit16
          case "div-int/lit16" : inst = new ast.stm.DivIntLit16($a.text,$b.text,$c.text,$d.value);
          //d4: rem-int/lit16 RemIntLit16
          case "rem-int/lit16" : inst = new ast.stm.RemIntLit16($a.text,$b.text,$c.text,$d.value);
          //d5: and-int/lit16 AndIntLit16
          case "and-int/lit16" : inst = new ast.stm.AndIntLit16($a.text,$b.text,$c.text,$d.value);
          //d6: or-int/lit16 OrIntLit16
          case "or-int/lit16" : inst = new ast.stm.OrIntLit16($a.text,$b.text,$c.text,$d.value);
          //d7: xor-int/lit16 XorIntLit16
          case "xor-int/lit16" : inst = new ast.stm.XorIntLit16($a.text,$b.text,$c.text,$d.value);
          default:;
        }
    };

insn_format22t returns [ast.stm.T inst]
  : //e.g. if-eq v0, v1, endloop:
    ^(I_STATEMENT_FORMAT22t a=INSTRUCTION_FORMAT22t b=REGISTER c=REGISTER d=offset_or_label)//offorlab
    {
      switch($a.text)
      {
        //32..37 22t  if-test vA, vB, +CCCC
        //32: if-eq IfEq
        case "if-eq" : inst = new ast.stm.IfEq($a.text,$b.text,$c.text,$d.text);
        //33: if-ne IfNe
        case "if-ne" : inst = new ast.stm.IfNe($a.text,$b.text,$c.text,$d.text);
        //34: if-lt IfLt
        case "if-lt" : inst = new ast.stm.IfLt($a.text,$b.text,$c.text,$d.text);
        //35: if-ge IfGe
        case "if-ge" : inst = new ast.stm.IfGe($a.text,$b.text,$c.text,$d.text);
        //36: if-gt IfGt
        case "if-gt" : inst = new ast.stm.IfGt($a.text,$b.text,$c.text,$d.text);
        //37: if-le IfLe
        case "if-le" : inst = new ast.stm.IfLe($a.text,$b.text,$c.text,$d.text);
        default:;
      }
    };

insn_format22x returns [ast.stm.T inst]
  : //e.g. move/from16 v1, v1234
    ^(I_STATEMENT_FORMAT22x a=INSTRUCTION_FORMAT22x b=REGISTER c=REGISTER)
    {
      switch($a.text)
      {
        //02  move/from16 MoveFrom16
        case "move/from16" : inst = new ast.stm.MoveFrom16($a.text,$b.text,$c.text);
        //05  move-wide/from16 MoveWideFrom16
        case "move-wide/from16" : inst = new ast.stm.MoveWideFrom16($a.text,$b.text,$c.text);
        //08  move-object/from16 MoveOjbectFrom16
        case "move-object/from16" : inst = new ast.stm.MoveOjbectFrom16($a.text,$b.text,$c.text);
        default:;
      }
    };

insn_format23x returns [ast.stm.T inst]
  : //e.g. add-int v1, v2, v3
    ^(I_STATEMENT_FORMAT23x a=INSTRUCTION_FORMAT23x b=REGISTER c=REGISTER d=REGISTER)
    {
     switch($a.text)
     {
          //2d..31 23x  cmpkind vAA, vBB, vCC
          //2d: cmpl-float (lt bias) CmplFloat
          case "cmpl-float" : inst = new ast.stm.CmplFloat($a.text,$b.text,$c.text,$d.text);
          //2e: cmpg-float (gt bias) CmpgFloat
          case "cmpg-float" : inst = new ast.stm.CmpgFloat($a.text,$b.text,$c.text,$d.text);
          //2f: cmpl-double (lt bias) CmplDouble
          case "cmpl-double" : inst = new ast.stm.CmplDouble($a.text,$b.text,$c.text,$d.text);
          //30: cmpg-double (gt bias) Cmpgdouble
          case "cmpg-double" : inst = new ast.stm.Cmpgdouble($a.text,$b.text,$c.text,$d.text);
          //31: cmp-long  CmpLong
          case "cmp-long" : inst = new ast.stm.CmpLong($a.text,$b.text,$c.text,$d.text);

          44..51 23x  arrayop vAA, vBB, vCC
          44:   aget  Aget
          case "aget" : inst = new ast.stm.Aget($a.text,$b.text,$c.text,$d.text);
          45:   aget-wide AgetWide
          case "aget-wide" : inst = new ast.stm.AgetWide($a.text,$b.text,$c.text,$d.text);
          46:   aget-object AgetObject
          case "aget-object" : inst = new ast.stm.AgetObject($a.text,$b.text,$c.text,$d.text);
          47:   aget-boolean AgetBoolean
          case "aget-boolean" : inst = new ast.stm.AgetBoolean($a.text,$b.text,$c.text,$d.text);
          48:   aget-byte AgetByte
          case "aget-byte" : inst = new ast.stm.AgetByte($a.text,$b.text,$c.text,$d.text);
          49:   aget-char AgetChar
          case "aget-char" : inst = new ast.stm.AgetChar($a.text,$b.text,$c.text,$d.text);
          4a:   aget-short AgetShort
          case "aget-short" : inst = new ast.stm.AgetShort($a.text,$b.text,$c.text,$d.text);
          4b:   aput Aput
          case "aput Aput" : inst = new ast.stm.Aput($a.text,$b.text,$c.text,$d.text);
          4c:   aput-wide AputWide
          case "aput-wide" : inst = new ast.stm.AputWide($a.text,$b.text,$c.text,$d.text);
          4d:   aput-object AputObject
          case "aput-object" : inst = new ast.stm.AputObject($a.text,$b.text,$c.text,$d.text);
          4e:   aput-boolean AputBoolean
          case "aput-boolean" : inst = new ast.stm.AputBoolean($a.text,$b.text,$c.text,$d.text);
          4f:   aput-byte AputByte
          case "aput-byte" : inst = new ast.stm.AputByte($a.text,$b.text,$c.text,$d.text);
          50:   aput-char AputChar
          case "aput-char" : inst = new ast.stm.AputChar($a.text,$b.text,$c.text,$d.text);
          51:   aput-short AputShort
          case "aput-short" : inst = new ast.stm.AputShort($a.text,$b.text,$c.text,$d.text);

          //90..af 23x  binop vAA, vBB, vCC
          //90: add-int AddInt
          case "add-int" : inst = new ast.stm.AddInt($a.text,$b.text,$c.text,$d.text);
          //91: sub-int  SubInt
          case "sub-int" : inst = new ast.stm.SubInt($a.text,$b.text,$c.text,$d.text);
          //92: mul-int MulInt
          case "mul-int" : inst = new ast.stm.MulInt($a.text,$b.text,$c.text,$d.text);
          //93: div-int DivInt
          case "div-int" : inst = new ast.stm.DivInt($a.text,$b.text,$c.text,$d.text);
          //94: rem-int RemInt
          case "rem-int" : inst = new ast.stm.RemInt($a.text,$b.text,$c.text,$d.text);
          //95: and-int AndInt
          case "and-int" : inst = new ast.stm.AndInt($a.text,$b.text,$c.text,$d.text);
          //96: or-int OrInt
          case "or-int" : inst = new ast.stm.OrInt($a.text,$b.text,$c.text,$d.text);
          //97: xor-int XorInt
          case "xor-int" : inst = new ast.stm.XorInt($a.text,$b.text,$c.text,$d.text);
          //98: shl-int ShlInt
          case "shl-int" : inst = new ast.stm.ShlInt($a.text,$b.text,$c.text,$d.text);
          //99: shr-int ShrInt
          case "shr-int" : inst = new ast.stm.ShrInt($a.text,$b.text,$c.text,$d.text);
          //9a: ushr-int UshrInt
          case "ushr-int" : inst = new ast.stm.UshrInt($a.text,$b.text,$c.text,$d.text);
          //9b: add-long AddLong
          case "add-long" : inst = new ast.stm.AddLong($a.text,$b.text,$c.text,$d.text);
          //9c: sub-long SubLong
          case "sub-long" : inst = new ast.stm.SubLong($a.text,$b.text,$c.text,$d.text);
          //9d: mul-long MulLong
          case "mul-long" : inst = new ast.stm.MulLong($a.text,$b.text,$c.text,$d.text);
          //9e: div-long DivLong
          case "div-long" : inst = new ast.stm.DivLong($a.text,$b.text,$c.text,$d.text);
          //9f: rem-long RemLong
          case "rem-long" : inst = new ast.stm.RemLong($a.text,$b.text,$c.text,$d.text);
          //a0: and-long AndLong
          case "and-long" : inst = new ast.stm.AndLong($a.text,$b.text,$c.text,$d.text);
          //a1: or-long OrLong
          case "or-long" : inst = new ast.stm.OrLong($a.text,$b.text,$c.text,$d.text);
          //a2: xor-long XorLong
          case "xor-long" : inst = new ast.stm.XorLong($a.text,$b.text,$c.text,$d.text);
          //a3: shl-long ShlLong
          case "shl-long" : inst = new ast.stm.ShlLong($a.text,$b.text,$c.text,$d.text);
          //a4: shr-long ShrLong
          case "shr-long" : inst = new ast.stm.ShrLong($a.text,$b.text,$c.text,$d.text);
          //a5: ushr-long UshrLong
          case "ushr-long" : inst = new ast.stm.UshrLong($a.text,$b.text,$c.text,$d.text);
          //a6: add-float AddFloat
          case "add-float" : inst = new ast.stm.AddFloat($a.text,$b.text,$c.text,$d.text);
          //a7: sub-float SubFloat
          case "sub-float" : inst = new ast.stm.SubFloat($a.text,$b.text,$c.text,$d.text);
          //a8: mul-float MulFloat
          case "mul-float" : inst = new ast.stm.MulFloat($a.text,$b.text,$c.text,$d.text);
          //a9: div-float DivFloat
          case "div-float" : inst = new ast.stm.DivFloat($a.text,$b.text,$c.text,$d.text);
          //aa: rem-float RemFloat
          case "rem-float" : inst = new ast.stm.RemFloat($a.text,$b.text,$c.text,$d.text);
          //ab: add-double AddDouble
          case "add-double" : inst = new ast.stm.AddDouble($a.text,$b.text,$c.text,$d.text);
          //ac: sub-double SubDouble
          case "sub-double" : inst = new ast.stm.SubDouble($a.text,$b.text,$c.text,$d.text);
          //ad: mul-double MulDouble
          case "mul-double" : inst = new ast.stm.MulDouble($a.text,$b.text,$c.text,$d.text);
          //ae: div-double DivDouble
          case "div-double" : inst = new ast.stm.DivDouble($a.text,$b.text,$c.text,$d.text);
          //af: rem-double RemDouble
          case "rem-double" : inst = new ast.stm.RemDouble($a.text,$b.text,$c.text,$d.text);
 

          default:;


     }

    };

insn_format30t returns [ast.stm.T inst]
  : //e.g. goto/32 endloop:
    ^(I_STATEMENT_FORMAT30t a=INSTRUCTION_FORMAT30t b=offset_or_label)//offorlab
    {
      switch($a.text)
      {
        //2a 30t  goto/32 +AAAAAAAA
        //Goto32
        case "goto/32" :  inst = new ast.stm.Goto32($a.text,$b.offorlab);
        default:;
      }

    };

insn_format31c returns [ast.stm.T inst]
  : //e.g. const-string/jumbo v1 "Hello World!"
    ^(I_STATEMENT_FORMAT31c a=INSTRUCTION_FORMAT31c b=REGISTER c=string_literal)
    {
       switch($a.text)
       {
          //1b 31c const-string/jumbo vAA, string@BBBBBBBB
          //ConstStringJumbo
          case "const-string/jumbo" : inst = new ast.stm.ConstStringJumbo($a.text,$b.text,$c.value);
          default:;

       }
      
    };

insn_format31i returns [ast.stm.T inst]
  : //e.g. const v0, 123456
    ^(I_STATEMENT_FORMAT31i a=INSTRUCTION_FORMAT31i b=REGISTER c=fixed_32bit_literal)//value
    {
      switch($a.text)
      {
        //14 31i const Const
        case "const" = new ast.stm.Const($a.text,$b.text,$c.value);
        //17 31i const-wide/32 ConstWide32
        case "const-wide/32" = new ast.stm.ConstWide32($a.text,$b.text,$c.value);
        default:;
      }

    };

insn_format31t returns [ast.stm.T inst]
  : //e.g. fill-array-data v0, ArrayData:
    ^(I_STATEMENT_FORMAT31t a=INSTRUCTION_FORMAT31t b=REGISTER c=offset_or_label)//offorlab
    {
       switch($a.text)
      {
        //26 31t  fill-array-data  FillArrayData
        case "fill-array-data" = new ast.stm.FillArrayData($a.text,$b.text,$c.offorlab);
        //2b 31t  packed-switch  PackedSwitch
        case "packed-switch" = new ast.stm.PackedSwitch($a.text,$b.text,$c.offorlab);
        //2c 31t  sparse-switch  SparseSwitch
        case "sparse-switch" = new ast.stm.SparseSwitch($a.text,$b.text,$c.offorlab);
        default:;
      }
    };

insn_format32x returns [ast.stm.T inst]
  : //e.g. move/16 v5678, v1234
    ^(I_STATEMENT_FORMAT32x a=INSTRUCTION_FORMAT32x b=REGISTER c=REGISTER)
    {
      
       switch($a.text)
       { 

         //03 32x  move/16 Move16
         case "move/16" : inst = new ast.stm.Move16($a.text,$b.text,$c.text);
         //06 32x move-wide/16 MoveWide16
         case "move-wide/16" : inst = new ast.stm.MoveWide16($a.text,$b.text,$c.text);
         //09 32x  move-object/16 MoveObject16
         case "move-object/16" : inst = new ast.stm.MoveObject16($a.text,$b.text,$c.text);
         default:;

       }

    };






insn_format35c_method returns [ast.stm.T inst]

  : //e.g. invoke-virtual {} java/io/PrintStream/print(Ljava/lang/Stream;)V
    ^(I_STATEMENT_FORMAT35c_METHOD a=INSTRUCTION_FORMAT35c_METHOD b=register_list c=fully_qualified_method)
    {
    };

insn_format35c_type returns [ast.stm.T inst]
  : //e.g. filled-new-array {}, I
    ^(I_STATEMENT_FORMAT35c_TYPE a=INSTRUCTION_FORMAT35c_TYPE b=register_list c=nonvoid_type_descriptor)
    {
      switch($a.text)
      {
        //24 35c  filled-new-array FilledNewArray
        case "filled-new-array" : inst = new ast.stm.FilledNewArray($a.text,$b.argList,$c.type_desc);
        default:;
      }
      
    };





insn_format3rc_method returns [ast.stm.T inst]
  : //e.g. invoke-virtual/range {} java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    ^(I_STATEMENT_FORMAT3rc_METHOD a=INSTRUCTION_FORMAT3rc_METHOD b=register_range c=fully_qualified_method)
    {
    };

insn_format3rc_type returns [ast.stm.T inst]
  : //e.g. filled-new-array/range {} I
    ^(I_STATEMENT_FORMAT3rc_TYPE a=INSTRUCTION_FORMAT3rc_TYPE b=register_range c=nonvoid_type_descriptor)
    {
    };




insn_format41c_type returns [ast.stm.T inst]
  : //e.g. const-class/jumbo v2, org/jf/HelloWorld2/HelloWorld2
    ^(I_STATEMENT_FORMAT41c_TYPE INSTRUCTION_FORMAT41c_TYPE REGISTER reference_type_descriptor)
    {
    };
 
insn_format41c_field returns [ast.stm.T inst]
  : //e.g. sget-object/jumbo v0, Ljava/lang/System;->out:LJava/io/PrintStream;
    ^(I_STATEMENT_FORMAT41c_FIELD INSTRUCTION_FORMAT41c_FIELD REGISTER fully_qualified_field)
    {
    };

insn_format51l_type returns [ast.stm.T inst]
  : //e.g. const-wide v0, 5000000000L
    ^(I_STATEMENT_FORMAT51l INSTRUCTION_FORMAT51l REGISTER fixed_64bit_literal)
    {
    };

insn_format52c_type returns [ast.stm.T inst]
  : //e.g. instance-of/jumbo v0, v1, Ljava/lang/String;
    ^(I_STATEMENT_FORMAT52c_TYPE INSTRUCTION_FORMAT52c_TYPE registerA=REGISTER registerB=REGISTER nonvoid_type_descriptor)
    {
    };

insn_format52c_field returns [ast.stm.T inst]
  : //e.g. iput-object/jumbo v1, v0, Lorg/jf/HelloWorld2/HelloWorld2;->helloWorld:Ljava/lang/String;
    ^(I_STATEMENT_FORMAT52c_FIELD INSTRUCTION_FORMAT52c_FIELD registerA=REGISTER registerB=REGISTER fully_qualified_field)
    {
    };

insn_format5rc_method returns [ast.stm.T inst]
  : //e.g. invoke-virtual/jumbo {} java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    ^(I_STATEMENT_FORMAT5rc_METHOD INSTRUCTION_FORMAT5rc_METHOD register_range fully_qualified_method)
    {
    };

insn_format5rc_type returns [ast.stm.T inst]
  : //e.g. filled-new-array/jumbo {} I
    ^(I_STATEMENT_FORMAT5rc_TYPE INSTRUCTION_FORMAT5rc_TYPE register_range nonvoid_type_descriptor)
    {
    };

insn_array_data_directive returns [ast.stm.T inst]
  : //e.g. .array-data 4 1000000 .end array-data
    ^(I_STATEMENT_ARRAY_DATA ^(I_ARRAY_ELEMENT_SIZE short_integral_literal) array_elements)
    {
    };

insn_packed_switch_directive returns [ast.stm.T inst]
  :
    ^(I_STATEMENT_PACKED_SWITCH ^(I_PACKED_SWITCH_START_KEY fixed_32bit_literal)
      packed_switch_targets)
  ;

insn_sparse_switch_directive returns [ast.stm.T inst]
  :
    ^(I_STATEMENT_SPARSE_SWITCH sparse_switch_target_count sparse_switch_keys
      {
      }

      sparse_switch_targets)
    {
    };

nonvoid_type_descriptor returns [String type_desc]
  : (PRIMITIVE_TYPE
  | CLASS_DESCRIPTOR
  | ARRAY_DESCRIPTOR)
  {
  	 $type_desc = $start.getText();
  };


reference_type_descriptor returns [String ref_desc]
  : a=(CLASS_DESCRIPTOR
  | ARRAY_DESCRIPTOR)
  {
  	$ref = $atext;
  }
  ;






class_type_descriptor returns [String className]
  : CLASS_DESCRIPTOR
  {
  	$className = $CLASS_DESCRIPTOR.text;
  };

type_descriptor returns [String type_desc]
  : VOID_TYPE { $type_desc = "V"; /* void */ }
  | nonvoid_type_descriptor { $type_desc = $nonvoid_type_descriptor.type_desc; }
  ;

short_integral_literal returns [String value]
  : a=(long_literal{}
  | integer_literal{}
  | short_literal {}
  | char_literal {;}
  | byte_literal {}) {$value = $a.value;};

integral_literal returns[String value]
  : a=(long_literal{}
  | integer_literal {}
  | short_literal {}
  | byte_literal {}){$value = $a.value;};


integer_literal returns[String value]
  : a=INTEGER_LITERAL { $value = $a.text; };

long_literal returns[String value]
  : a=LONG_LITERAL { $value = $.text; };

short_literal returns[String value]
  :  a=SHORT_LITERAL { $value = $.text; };

byte_literal returns[String value]
  :  a=BYTE_LITERAL { $value = $.text;};

float_literal returns[String value]
  :  a=FLOAT_LITERAL { $value = $.text; };

double_literal returns[String value]
  :  a=DOUBLE_LITERAL { $value = $.text; };

char_literal returns[String value]
  :  a=CHAR_LITERAL { $value = $.text; };

string_literal returns [String value]
  :  a=STRING_LITERAL
    { $value = $.text;};

bool_literal
  : BOOL_LITERAL {};

array_literal
  : {}
    ^(I_ENCODED_ARRAY (literal {})*)
    {
    };


annotations
  : {}
    ^(I_ANNOTATIONS (annotation {} )*)
    {
    };


annotation
  : ^(I_ANNOTATION ANNOTATION_VISIBILITY subannotation)
    {
    };

annotation_element
  : ^(I_ANNOTATION_ELEMENT SIMPLE_NAME literal)
    {
    };

subannotation
  :
    ^(I_SUBANNOTATION
        class_type_descriptor
        (annotation_element
        {
        }
        )*
     )
    ;

field_literal
  : ^(I_ENCODED_FIELD fully_qualified_field)
    {
    };

method_literal
  : ^(I_ENCODED_METHOD fully_qualified_method)
    {
    };

enum_literal
  : ^(I_ENCODED_ENUM fully_qualified_field)
    {
    };
