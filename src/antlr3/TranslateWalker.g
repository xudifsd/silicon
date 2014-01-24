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

}

smali_file returns [ast.classs.Class clazz]
  : ^(I_CLASS_DEF header methods fields annotations)

  {
  	  $clazz = new ast.classs.Class();
  	  $clazz.FullyQualifiedName = $header.className;
  	  $clazz.superName = $header.superName;
  	  $clazz.source = $header.source;
  	  $clazz.accessList = $header.accessList;
      $clazz.implementsList = $header.implementsList;
  	  $clazz.methods = $methods.methodList;
      $clazz.fieldList = $fields.fieldList;
      $clazz.annotationList = $annotations.annotationList;
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
    $implementsList = $implements_list.implementsList;
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


implements_spec returns [String className]

  : ^(I_IMPLEMENTS a=class_type_descriptor)
  {
  	$className = $a.className;
  };

implements_list returns [List<String> implementsList]
@init {
 $implementsList = new ArrayList<String>();
}
  : (implements_spec {$implementsList.add($implements_spec.className);} )*
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


fields returns[List<ast.classs.Class.Field> fieldList]
@init
{
  fieldList = new ArrayList<ast.classs.Class.Field>();
}
  : ^(I_FIELDS
      (a= field
      {
        $fieldList.add($a.fieldd);
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

field returns[ast.classs.Class.Field fieldd]
  :^(I_FIELD a=SIMPLE_NAME b=access_list ^(I_FIELD_TYPE c=nonvoid_type_descriptor) d=field_initial_value annotations?)
  {
        $fieldd = new ast.classs.Class.Field($a.text,$b.accessList,$c.type_desc,$d.elementLiteral,$annotations.annotationList);
  };


field_initial_value returns [ast.annotation.Annotation.ElementLiteral elementLiteral]
  : ^(I_FIELD_INITIAL_VALUE a=literal) {$elementLiteral = $a.elementLiteral;}
  | /*epsilon*/;

literal returns[ast.annotation.Annotation.ElementLiteral elementLiteral,String value,String type,Object object]
@init{
  $elementLiteral = new ast.annotation.Annotation.ElementLiteral();
}
  : integer_literal { 
  $value = $integer_literal.value;
  $object = $integer_literal.value;
  $elementLiteral.element.add($integer_literal.value);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$integer_literal.type,null);$type = $integer_literal.type;}

  | long_literal { 
  $value = $long_literal.value;
  $object = $long_literal.value;
  $elementLiteral.element.add($long_literal.value);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$long_literal.type,null);$type = $long_literal.type;}

  | short_literal {
  $value = $short_literal.value;
  $object = $short_literal.value;
  $elementLiteral.element.add($short_literal.value);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$short_literal.type,null);$type = $short_literal.type;}

  | byte_literal { 
  $value = $byte_literal.value;
  $object = $byte_literal.value;
  $elementLiteral.element.add($byte_literal.value);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$byte_literal.type,null);$type = $byte_literal.type;}

  | float_literal { 
  $value = $float_literal.value;
  $object = $float_literal.value;
  $elementLiteral.element.add($float_literal.value);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$float_literal.type,null);$type = $float_literal.type;}


  | double_literal { 
  $value = $double_literal.value;
  $object = $double_literal.value;
  $elementLiteral.element.add($double_literal.value);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$double_literal.type,null);$type = $double_literal.type;}

  | char_literal { 
  $value = $char_literal.value;
  $object = $char_literal.value;
  $elementLiteral.element.add($char_literal.value);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$char_literal.type,null);$type = $char_literal.type;}

  | string_literal { 
  $value = $string_literal.value;
  $object = $string_literal.value;
  $elementLiteral.element.add($string_literal.value);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$string_literal.type,null);$type = $string_literal.type;}

  | bool_literal { 
  $value = $bool_literal.value;
  $object = $bool_literal.value;
  $elementLiteral.element.add($bool_literal.value);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$bool_literal.type,null);$type = $bool_literal.type;}

  | NULL_LITERAL { 
  $value = $NULL_LITERAL.text;
  $object = $NULL_LITERAL.text;
  $elementLiteral.element.add($NULL_LITERAL.text);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,"null",null);$type = "null";}

  | type_descriptor { 
  $value = $type_descriptor.value;
  $object = $type_descriptor.value;
  $elementLiteral.element.add($type_descriptor.value);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$type_descriptor.type,null);$type = $type_descriptor.type;}
  

  | array_literal { 
  $value = $array_literal.value;
  $object = $array_literal.value;
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($array_literal.element,"array",$array_literal.arrayLiteralType);$type = "array";}
  
 
  | subannotation {
  $value = $subannotation.value;
  $object = $subannotation.subAnno;
  $elementLiteral.element.add($subannotation.subAnno);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$subannotation.type,null);$type = $subannotation.type;}

  | field_literal { 
  $value = $field_literal.value;
  $object = $field_literal.value;
  $elementLiteral.element.add($field_literal.value);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$field_literal.type,null);$type = $field_literal.type;}

  | method_literal { 
  $value = $method_literal.value;
  $object = $method_literal.value;
  $elementLiteral.element.add($method_literal.value);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$method_literal.type,null);$type = $method_literal.type;}

  | enum_literal { 
  $value = $enum_literal.value;
  $object = $enum_literal.value;
  $elementLiteral.element.add($enum_literal.value);
  $elementLiteral =  new ast.annotation.Annotation.ElementLiteral($elementLiteral.element,$enum_literal.type,null);$type = $enum_literal.type;};

//everything but string
fixed_size_literal returns [String value]
  :integer_literal {$value = $integer_literal.value;}
  |long_literal {$value = $long_literal.value;}
  |short_literal {$value = $short_literal.value;}
  |byte_literal {$value = $byte_literal.value;}
  |float_literal {$value = $float_literal.value;}
  |double_literal {$value = $double_literal.value;}
  |char_literal {$value = $char_literal.value;}
  |bool_literal {$value = $bool_literal.value;};

//everything but string
  fixed_64bit_literal returns [String value]
  :long_literal {$value = $long_literal.value;}
  |integer_literal {$value = $integer_literal.value;}
  |short_literal {$value = $short_literal.value;}
  |byte_literal {$value = $byte_literal.value;}
  |float_literal {$value = $float_literal.value;}
  |double_literal {$value = $double_literal.value;}
  |char_literal {$value = $char_literal.value;}
  |bool_literal {$value = $bool_literal.value;}
  ;
//everything but string and double
//long is allowed, but it must fit into an int
fixed_32bit_literal returns [String value]
  :integer_literal {$value = $integer_literal.value;}
  |long_literal {$value = $long_literal.value;}
  |short_literal {$value = $short_literal.value;}
  |byte_literal {$value = $byte_literal.value;}
  |float_literal {$value = $float_literal.value;}
  |char_literal {$value = $char_literal.value;}
  |bool_literal {$value = $bool_literal.value;}
  ;

array_elements returns[List<String> elementList]
@init
{
  $elementList = new ArrayList<String>();
}
  : {}
    ^(I_ARRAY_ELEMENTS
      (a=fixed_size_literal
      {
          $elementList.add($a.value);
      })*);

packed_switch_target_count returns[String txt]
  : a=I_PACKED_SWITCH_TARGET_COUNT {$txt = $a.text;};

packed_switch_targets  returns[String count,List<String> labList]
@init
{
  $labList = new ArrayList<String>();
}
  :
    ^(I_PACKED_SWITCH_TARGETS
      a=packed_switch_target_count
      {
        $count = $a.txt;
      }

      (b=offset_or_label
      {
        $labList.add($b.offorlab);
      })*
    );

sparse_switch_target_count returns[String txt]
  : a=I_SPARSE_SWITCH_TARGET_COUNT {$txt = $a.text;};

sparse_switch_keys returns[List<String> keyList]
@init
{
  keyList = new ArrayList<String>();
}
  : ^(I_SPARSE_SWITCH_KEYS
      (a=fixed_32bit_literal
      {
        $keyList.add($a.value);
      })*
    );


sparse_switch_targets returns[List<String> labList]
@init
{
  labList = new ArrayList<String>();
}
  : ^(I_SPARSE_SWITCH_TARGETS
      (a=offset_or_label
      {
        $labList.add($a.offorlab);
      })*
    );

method returns [ast.method.Method method]
@init{
	$method = new ast.method.Method(); /* Be careful! It's better to move it here. YKG. */
}
  : ^(I_METHOD
      method_name_and_prototype
      {
         $method.name = $method_name_and_prototype.method_name;
         $method.prototype = $method_name_and_prototype.prototype;
      }
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
      {
        $method.labelList = $labels.labelList;
      }
      packed_switch_declarations
      {
        $method.pSwitchDecList = $packed_switch_declarations.pSwitchDecList;
      }
      sparse_switch_declarations
      {
        $method.sSwitchDecList = $sparse_switch_declarations.sSwitchDecList;
      }
      statements
      {
        $method.statements = $statements.instList;
      }
      catches
      {
        $method.catchList = $catches.catchList;
      }
      parameters
      {
        $method.parameterList = $parameters.parameterList;
      }
      ordered_debug_directives
      {
        $method.debugList = $ordered_debug_directives.debugList;
      }
      annotations
      {
        if($annotations.annotationList !=  null)
          $method.annotationList = $annotations.annotationList;
      }
    )
  {

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

registers_directive returns [String type, String count]
  : {}
    ^(( I_REGISTERS { $type = ".registers"; }
      | I_LOCALS { $type = ".locals"; }
      )
      a=short_integral_literal { $count = $a.value; }
     );

labels returns[List<ast.method.Method.Label> labelList ]
@init
{
  labelList =  new ArrayList<ast.method.Method.Label>();
}
  : ^(I_LABELS (a=label_def {labelList.add($a.label);})*);

label_def returns [ast.method.Method.Label label]
  : ^(I_LABEL a=SIMPLE_NAME b=address)
    {
      $label =  new ast.method.Method.Label($a.text,$b.add);
    };

packed_switch_declarations returns[List<ast.method.Method.PSwitchDec> pSwitchDecList]
@init
{
  pSwitchDecList = new ArrayList<ast.method.Method.PSwitchDec>();
}
  : ^(I_PACKED_SWITCH_DECLARATIONS
     (a=packed_switch_declaration{$pSwitchDecList.add($a.pSwitchDec);})*);

packed_switch_declaration returns[ast.method.Method.PSwitchDec pSwitchDec]
  : ^(I_PACKED_SWITCH_DECLARATION a=address b=offset_or_label_absolute)
    {
      $pSwitchDec = new ast.method.Method.PSwitchDec($a.add,$b.dest);
    };

sparse_switch_declarations returns[List<ast.method.Method.SSwitchDec> sSwitchDecList]
@init
{
  sSwitchDecList = new ArrayList<ast.method.Method.SSwitchDec>();
}
  : ^(I_SPARSE_SWITCH_DECLARATIONS
    (a=sparse_switch_declaration{$sSwitchDecList.add($a.sSwitchDec);})*);

sparse_switch_declaration returns[ast.method.Method.SSwitchDec sSwitchDec]
  : ^(I_SPARSE_SWITCH_DECLARATION a=address b=offset_or_label_absolute)
    {
      $sSwitchDec = new ast.method.Method.SSwitchDec($a.add,$b.dest);
    };

catches returns[List<ast.method.Method.Catch> catchList]
@init
{
  catchList = new ArrayList<ast.method.Method.Catch>();
}
 : ^(I_CATCHES
     (a=catch_directive{catchList.add($a.catchh);})*
     (b=catchall_directive{catchList.add($b.catchh);})*);

catch_directive returns[ast.method.Method.Catch catchh]
  : ^(I_CATCH a=address b=nonvoid_type_descriptor c=offset_or_label_absolute d=offset_or_label_absolute e=offset_or_label_absolute)
    {
      $catchh = new ast.method.Method.Catch($a.add,$b.type_desc,$c.dest,$d.dest,$e.dest);
    };

catchall_directive returns[ast.method.Method.Catch catchh]
  : ^(I_CATCHALL a=address b=offset_or_label_absolute c=offset_or_label_absolute d=offset_or_label_absolute)
    {
      $catchh = new ast.method.Method.Catch($a.add,$b.dest,$c.dest,$d.dest);
    };

address returns[String add]
  : I_ADDRESS
    {
      $add=$I_ADDRESS.text;
    };

parameters returns[List<ast.method.Method.Parameter> parameterList]
@init{
  parameterList = new ArrayList<ast.method.Method.Parameter>();
}
  : ^(I_PARAMETERS (a=parameter
        {
          $parameterList.add($a.para);
        })*
    )
  ;

parameter returns[ast.method.Method.Parameter para]
@init{
  String str =  null;
}
  : ^(I_PARAMETER (string_literal{ str = new String($string_literal.value);}|) b=annotations
    {
        para = new ast.method.Method.Parameter(str,$b.annotationList);
    }
    );

ordered_debug_directives returns[List<ast.method.Method.Debug> debugList]
@init{
  debugList = new ArrayList<ast.method.Method.Debug>();
}
  : ^(I_ORDERED_DEBUG_DIRECTIVES
       ( line
       | a=local{$debugList.add(new ast.method.Method.Debug(ast.method.Method.Debug.Type.LOCAL,$a.local,null,null,$a.addr));}
       | b=end_local{$debugList.add(new ast.method.Method.Debug(ast.method.Method.Debug.Type.ENDLOCAL,null,$b.endLocal,null,$b.addr));}
       | c=restart_local{$debugList.add(new ast.method.Method.Debug(ast.method.Method.Debug.Type.RESTARTLOCAL,null,null,$c.restartLocal,$c.addr));}
       | prologue
       | epilogue
       | source
       )*
     );

line
  : ^(I_LINE integral_literal address)
    {
    };

local returns[ast.method.Method.Debug.Local local,String addr]
  : ^(I_LOCAL a=REGISTER b=SIMPLE_NAME c=nonvoid_type_descriptor d=string_literal? address)
    {
      $local = new ast.method.Method.Debug.Local($a.text,$b.text,$c.type_desc,$d.value);
      $addr = $address.add;
    };

end_local returns[ast.method.Method.Debug.EndLocal endLocal,String addr]
  : ^(I_END_LOCAL a=REGISTER address)
    {
      $endLocal = new ast.method.Method.Debug.EndLocal($a.text);
      $addr = $address.add;
    };

restart_local returns[ast.method.Method.Debug.RestartLocal restartLocal,String addr]
  : ^(I_RESTART_LOCAL a=REGISTER address)
    {
      $restartLocal = new ast.method.Method.Debug.RestartLocal($a.text);
      $addr = $address.add;
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

statements returns [List<ast.stm.T> instList]
@init{
	$instList = new ArrayList<ast.stm.T>();
}
  : ^(I_STATEMENTS (instruction
        {
        	$instList.add($instruction.inst);
        })*);

label_ref returns[String txt]
  : a=SIMPLE_NAME
    {
        $txt = $a.text;
    };

offset returns[String txt]
  : a=OFFSET
    {
        $txt = $a.text;
    };

offset_or_label_absolute returns[String dest]
  : a=offset {$dest = $a.txt;}
  | a=label_ref {$dest = $a.txt;}
  ;

offset_or_label returns [String offorlab]
  : a=offset {$offorlab=$a.txt;}
  | a=label_ref {$offorlab=$a.txt;}
  ;


register_list returns [ArrayList<String> argList]
@init{
	$argList = new ArrayList<String>();
}
  : ^(I_REGISTER_LIST
      (REGISTER
      {
    	$argList.add($REGISTER.text);  	
      })*);

register_range returns [String started ,String ended]
  : ^(I_REGISTER_RANGE (a=REGISTER b=REGISTER?)?)
    {
        if ($a != null) {
          $started = $a.text;
        }
        if ($b != null) {
          $ended =  $b.text;
          }
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

instruction returns [ast.stm.T inst]
  : a=insn_format10t { $inst = $a.inst;}
  | a=insn_format10x {$inst = $a.inst;}
  | a=insn_format11n {$inst = $a.inst;}
  | a=insn_format11x {$inst = $a.inst;}
  | a=insn_format12x {$inst = $a.inst;}
  | a=insn_format20bc {$inst = $a.inst;}
  | a=insn_format20t {$inst = $a.inst;}
  | a=insn_format21c_field {$inst = $a.inst;}
  | a=insn_format21c_string {$inst = $a.inst;}
  | a=insn_format21c_type {$inst = $a.inst;}
  | a=insn_format21h {$inst = $a.inst;}
  | a=insn_format21s {$inst = $a.inst;}
  | a=insn_format21t {$inst = $a.inst;}
  | a=insn_format22b {$inst = $a.inst;}
  | a=insn_format22c_field {$inst = $a.inst;}
  | a=insn_format22c_type {$inst = $a.inst;}
  | a=insn_format22s {$inst = $a.inst;}
  | a=insn_format22t {$inst = $a.inst;}
  | a=insn_format22x {$inst = $a.inst;}
  | a=insn_format23x {$inst = $a.inst;}
  | a=insn_format30t {$inst = $a.inst;}
  | a=insn_format31c {$inst = $a.inst;}
  | a=insn_format31i {$inst = $a.inst;}
  | a=insn_format31t {$inst = $a.inst;}
  | a=insn_format32x {$inst = $a.inst;}
  | a=insn_format35c_method {$inst = $a.inst;}
  | a=insn_format35c_type {$inst = $a.inst;}
  | a=insn_format3rc_method {$inst = $a.inst;}
  | a=insn_format3rc_type {$inst = $a.inst;}
  | a=insn_format41c_type {$inst = $a.inst;}
  | a=insn_format41c_field {$inst = $a.inst;}
  | a=insn_format51l_type {$inst = $a.inst;}
  | a=insn_format52c_type {$inst = $a.inst;}
  | a=insn_format52c_field {$inst = $a.inst;}
  | a=insn_format5rc_method {$inst = $a.inst;}
  | a=insn_format5rc_type {$inst = $a.inst;}
  | a=insn_array_data_directive{$inst = $a.inst;}
  | a=insn_packed_switch_directive { $inst = $a.inst;}
  | a=insn_sparse_switch_directive {$inst = $a.inst;}
  ;



insn_format10t returns [ast.stm.T inst]

  : //e.g. goto endloop:
    {}
    ^(I_STATEMENT_FORMAT10t a=INSTRUCTION_FORMAT10t b=offset_or_label)//offorlab
    {
        switch($a.text)
        {
          //goto Goto
          case "goto": $inst =  new ast.stm.Instruction.Goto($a.text,$b.offorlab);break;
          default: System.err.println("insn_format10t: " + $a.text + " unknown");
        }
    };
insn_format10x returns [ast.stm.T inst]
  : //e.g. return
    ^(I_STATEMENT_FORMAT10x a=INSTRUCTION_FORMAT10x)
    {
    	     switch($a.text)
           {
              // nop Nop
             case "nop" : $inst = new ast.stm.Instruction.Nop($a.text); break;
             //return-void ReturnVoid
             case "return-void": $inst = new ast.stm.Instruction.ReturnVoid($a.text); break;
             default: System.err.println("insn_format10x: " + $a.text + " unknown");
           }
    };
insn_format11n returns [ast.stm.T inst]

  : //e.g. const/4 v0, 5
    ^(I_STATEMENT_FORMAT11n a=INSTRUCTION_FORMAT11n b=REGISTER c=short_integral_literal)
    {
        switch($a.text)
        {
          //const/4 Const4
          case "const/4" : $inst = new ast.stm.Instruction.Const4($a.text,$b.text,$c.value); break;
          default: System.err.println("insn_format11n: " + $a.text + " unknown");
        }
    };
insn_format11x returns [ast.stm.T inst]
  : //e.g. move-result-object v1
    ^(I_STATEMENT_FORMAT11x a=INSTRUCTION_FORMAT11x b=REGISTER)
    {
        switch($a.text)
        {
          //move-result MoveResult
          case "move-result" : $inst =  new ast.stm.Instruction.MoveResult($a.text,$b.text);break;
          //move-result-wide MoveResultWide
          case "move-result-wide": $inst =  new ast.stm.Instruction.MoveResultWide($a.text,$b.text);break;
          //move-result-object  MoveResultObject
          case "move-result-object": $inst =  new ast.stm.Instruction.MoveResultObject($a.text,$b.text);break;
          //move-exception MoveException
          case "move-exception": $inst =  new ast.stm.Instruction.MoveException($a.text,$b.text);break;
          //return Return
          case "return": $inst =  new ast.stm.Instruction.Return($a.text,$b.text);break;
          //return-wide ReturnWide
          case "return-wide": $inst =  new ast.stm.Instruction.ReturnWide($a.text,$b.text);break;
          //return-object ReturnObject
          case "return-object": $inst =  new ast.stm.Instruction.ReturnObject($a.text,$b.text);break;
          //monitor-enter  MonitorEnter
          case "monitor-enter": $inst =  new ast.stm.Instruction.MonitorEnter($a.text,$b.text);break;
          //monitor-exit MonitorExit
          case "monitor-exit": $inst =  new ast.stm.Instruction.MonitorExit($a.text,$b.text);break;
          //throw Throw
          case "throw": $inst =  new ast.stm.Instruction.Throw($a.text,$b.text);break;
          default: System.err.println("insn_format11x: " + $a.text + " unknown");
        }
    };
insn_format12x returns [ast.stm.T inst]

  : //e.g. move v1 v2
      ^(I_STATEMENT_FORMAT12x a=INSTRUCTION_FORMAT12x b=REGISTER c=REGISTER)
      {
        switch($a.text)
        {

            //01 12x move Move
            case "move": $inst =  new ast.stm.Instruction.Move($a.text,$b.text,$c.text);break;
            //04 12x move-wide MoveWide
            case "move-wide": $inst =  new ast.stm.Instruction.MoveWide($a.text,$b.text,$c.text);break;
            //07 12x move-object MoveObject
            case "move-object": $inst =  new ast.stm.Instruction.MoveObject($a.text,$b.text,$c.text);break;
            //21 12x array-length arrayLength
            case "array-length": $inst =  new ast.stm.Instruction.arrayLength($a.text,$b.text,$c.text);break;
            //7b: neg-int NegInt
            case "neg-int": $inst =  new ast.stm.Instruction.NegInt($a.text,$b.text,$c.text);break;
            //7c: not-int NotInt
            case "not-int": $inst =  new ast.stm.Instruction.NotInt($a.text,$b.text,$c.text);break;
            //7d: neg-long NegLong
            case "neg-long": $inst =  new ast.stm.Instruction.NegLong($a.text,$b.text,$c.text);break;
            //7e: not-long  NotLong
            case "not-long": $inst =  new ast.stm.Instruction.NotLong($a.text,$b.text,$c.text);break;
            //7f: neg-float NegFloat
            case "neg-float": $inst =  new ast.stm.Instruction.NegFloat($a.text,$b.text,$c.text);break;
            //80: neg-double NegDouble
            case "neg-double": $inst =  new ast.stm.Instruction.NegDouble($a.text,$b.text,$c.text);break;
            //81: int-to-long IntToLong
            case "int-to-long": $inst =  new ast.stm.Instruction.IntToLong($a.text,$b.text,$c.text);break;
            //82: int-to-float IntToFloat
            case "int-to-float": $inst =  new ast.stm.Instruction.IntToFloat($a.text,$b.text,$c.text);break;
            //83: int-to-double IntToDouble
            case "int-to-double": $inst =  new ast.stm.Instruction.IntToDouble($a.text,$b.text,$c.text);break;
            //84: long-to-int LongToInt
            case "long-to-int": $inst =  new ast.stm.Instruction.LongToInt($a.text,$b.text,$c.text);break;
            //85: long-to-float LongToFloat
            case "long-to-float": $inst =  new ast.stm.Instruction.LongToFloat($a.text,$b.text,$c.text);break;
            //86: long-to-double LongToDouble
            case "long-to-double": $inst =  new ast.stm.Instruction.LongToDouble($a.text,$b.text,$c.text);break;
            //87: float-to-int FloatToInt
            case "float-to-int": $inst =  new ast.stm.Instruction.FloatToInt($a.text,$b.text,$c.text);break;
            //88: float-to-long FloatToLong
            case "float-to-long": $inst =  new ast.stm.Instruction.FloatToLong($a.text,$b.text,$c.text);break;
            //89: float-to-double FloatToDouble
            case "float-to-double": $inst =  new ast.stm.Instruction.FloatToDouble($a.text,$b.text,$c.text);break;
            //8a: double-to-int DoubleToInt
            case "double-to-int": $inst =  new ast.stm.Instruction.DoubleToInt($a.text,$b.text,$c.text);break;
            //8b: double-to-long DoubleToLong
            case "double-to-long": $inst =  new ast.stm.Instruction.DoubleToLong($a.text,$b.text,$c.text);break;
            //8c: double-to-float DoubleToFloat
            case "double-to-float": $inst =  new ast.stm.Instruction.DoubleToFloat($a.text,$b.text,$c.text);break;
            //8d: int-to-byte IntToByte
            case "int-to-byte": $inst =  new ast.stm.Instruction.IntToByte($a.text,$b.text,$c.text);break;
            //8e: int-to-char  IntToChar
            case "int-to-char": $inst =  new ast.stm.Instruction.IntToChar($a.text,$b.text,$c.text);break;
            //8f: int-to-short IntToShort
            case "int-to-short": $inst =  new ast.stm.Instruction.IntToShort($a.text,$b.text,$c.text);break;

            //b0: add-int/2addr AddInt2Addr
            case "add-int/2addr": $inst =  new ast.stm.Instruction.AddInt2Addr($a.text,$b.text,$c.text);break;
            //b1: sub-int/2addr SubInt2Addr
            case "sub-int/2addr": $inst =  new ast.stm.Instruction.SubInt2Addr($a.text,$b.text,$c.text);break;
            //b2: mul-int/2addr MulInt2Addr
            case "mul-int/2addr": $inst =  new ast.stm.Instruction.MulInt2Addr($a.text,$b.text,$c.text);break;
            //b3: div-int/2addr DivInt2Addr
            case "div-int/2addr": $inst =  new ast.stm.Instruction.DivInt2Addr($a.text,$b.text,$c.text);break;
            //b4: rem-int/2addr RemInt2Addr
            case "rem-int/2addr": $inst =  new ast.stm.Instruction.RemInt2Addr($a.text,$b.text,$c.text);break;
            //b5: and-int/2addr AndInt2Addr
            case "and-int/2addr": $inst =  new ast.stm.Instruction.AndInt2Addr($a.text,$b.text,$c.text);break;
            //b6: or-int/2addr OrInt2Addr
            case "or-int/2addr": $inst =  new ast.stm.Instruction.OrInt2Addr($a.text,$b.text,$c.text);break;
            //b7: xor-int/2addr XorInt2Addr
            case "xor-int/2addr": $inst =  new ast.stm.Instruction.XorInt2Addr($a.text,$b.text,$c.text);break;
            //b8: shl-int/2addr ShlInt2Addr
            case "shl-int/2addr": $inst =  new ast.stm.Instruction.ShlInt2Addr($a.text,$b.text,$c.text);break;
            //b9: shr-int/2addr  ShrInt2Addr
            case "shr-int/2addr": $inst =  new ast.stm.Instruction.ShrInt2Addr($a.text,$b.text,$c.text);break;
            //ba: ushr-int/2addr UshrInt2Addr
            case "ushr-int/2addr": $inst =  new ast.stm.Instruction.UshrInt2Addr($a.text,$b.text,$c.text);break;
            //bb: add-long/2addr AddLong2Addr
            case "add-long/2addr": $inst =  new ast.stm.Instruction.AddLong2Addr($a.text,$b.text,$c.text);break;
            //bc: sub-long/2addr SubLong2Addr
            case "sub-long/2addr": $inst =  new ast.stm.Instruction.SubLong2Addr($a.text,$b.text,$c.text);break;
            //bd: mul-long/2addr MulLong2Addr
            case "mul-long/2addr": $inst =  new ast.stm.Instruction.MulLong2Addr($a.text,$b.text,$c.text);break;
            //be: div-long/2addr DivLong2Addr
            case "div-long/2addr": $inst =  new ast.stm.Instruction.DivLong2Addr($a.text,$b.text,$c.text);break;
            //bf: rem-long/2addr RemLong2Addr
            case "rem-long/2addr": $inst =  new ast.stm.Instruction.RemLong2Addr($a.text,$b.text,$c.text);break;
            //c0: and-long/2addr AndLong2Addr
            case "and-long/2addr": $inst =  new ast.stm.Instruction.AndLong2Addr($a.text,$b.text,$c.text);break;
            //c1: or-long/2addr OrLong2Addr
            case "or-long/2addr": $inst =  new ast.stm.Instruction.OrLong2Addr($a.text,$b.text,$c.text);break;
            //c2: xor-long/2addr XorLong2Addr
            case "xor-long/2addr": $inst =  new ast.stm.Instruction.XorLong2Addr($a.text,$b.text,$c.text);break;
            //c3: shl-long/2addr ShlLong2Addr
            case "shl-long/2addr": $inst =  new ast.stm.Instruction.ShlLong2Addr($a.text,$b.text,$c.text);break;
            //c4: shr-long/2addr ShrLong2Addr
            case "shr-long/2addr": $inst =  new ast.stm.Instruction.ShrLong2Addr($a.text,$b.text,$c.text);break;
            //c5: ushr-long/2addr UshrLong2Addr
            case "ushr-long/2addr": $inst =  new ast.stm.Instruction.UshrLong2Addr($a.text,$b.text,$c.text);break;
            //c6: add-float/2addr AddFloat2Addr
            case "add-float/2addr": $inst =  new ast.stm.Instruction.AddFloat2Addr($a.text,$b.text,$c.text);break;
            //c7: sub-float/2addr SubFloat2Addr
            case "sub-float/2addr": $inst =  new ast.stm.Instruction.SubFloat2Addr($a.text,$b.text,$c.text);break;
            //c8: mul-float/2addr MulFloat2Addr
            case "mul-float/2addr": $inst =  new ast.stm.Instruction.SubFloat2Addr($a.text,$b.text,$c.text);break;
            //c9: div-float/2addr DivFloat2Addr
            case "div-float/2addr": $inst =  new ast.stm.Instruction.DivFloat2Addr($a.text,$b.text,$c.text);break;
            //ca: rem-float/2addr RemFloat2Addr
            case "rem-float/2addr": $inst =  new ast.stm.Instruction.RemFloat2Addr($a.text,$b.text,$c.text);break;
            //cb: add-double/2addr AddDouble2Addr
            case "add-double/2addr": $inst =  new ast.stm.Instruction.AddDouble2Addr($a.text,$b.text,$c.text);break;
            //cc: sub-double/2addr SubDouble2Addr
            case "sub-double/2addr": $inst =  new ast.stm.Instruction.SubDouble2Addr($a.text,$b.text,$c.text);break;
            //cd: mul-double/2addr MulDouble2Addr
            case "mul-double/2addr": $inst =  new ast.stm.Instruction.MulDouble2Addr($a.text,$b.text,$c.text);break;
            //ce: div-double/2addr DivDouble2Addr
            case "div-double/2addr": $inst =  new ast.stm.Instruction.DivDouble2Addr($a.text,$b.text,$c.text);break;
            //cf: rem-double/2addr RemDouble2Addr
            case "rem-double/2addr": $inst =  new ast.stm.Instruction.RemDouble2Addr($a.text,$b.text,$c.text);break;
            default: System.err.println("insn_format12x: " + $a.text + " unknown");
        }
      }
      ;
//can not find
insn_format20bc returns [ast.stm.T inst]
  : //e.g. throw-verification-error generic-error, Lsome/class;
    ^(I_STATEMENT_FORMAT20bc INSTRUCTION_FORMAT20bc verification_error_type verification_error_reference)
    {
          System.err.println("insn_format20bc: can not find in dalvik bytecode" );
    };

insn_format20t returns [ast.stm.T inst]
  : //e.g. goto/16 endloop:
    ^(I_STATEMENT_FORMAT20t a=INSTRUCTION_FORMAT20t b=offset_or_label)
    {

        switch($a.text)
        {
           case "goto/16": $inst = new ast.stm.Instruction.Goto16($a.text,$b.offorlab);break;
           default: System.err.println("insn_format20t: " + $a.text + " unknown");
        }
    };

insn_format21c_field returns [ast.stm.T inst]
  : //e.g. sget_object v0, java/lang/System/out LJava/io/PrintStream;
    ^(I_STATEMENT_FORMAT21c_FIELD a=(INSTRUCTION_FORMAT21c_FIELD | INSTRUCTION_FORMAT21c_FIELD_ODEX)
    b=REGISTER c=fully_qualified_field)
    {
        switch($a.text)
        {

          //60: sget Sget
          case"sget" : $inst = new ast.stm.Instruction.Sget($a.text,$b.text,$c.fieldItem);break;
          //61: sget-wide SgetWide
          case"sget-wide": $inst = new ast.stm.Instruction.SgetWide($a.text,$b.text,$c.fieldItem);break;
          //62: sget-object :SgetObject
          case"sget-object" :$inst = new ast.stm.Instruction.SgetObject($a.text,$b.text,$c.fieldItem);break;
          //63: sget-boolean SgetBoolean
          case"sget-boolean" :inst = new ast.stm.Instruction.SgetBoolean($a.text,$b.text,$c.fieldItem);break;
          //64: sget-byte SgetByte
          case"sget-byte" :$inst = new ast.stm.Instruction.SgetByte($a.text,$b.text,$c.fieldItem);break;
          //65: sget-char SgetChar
          case"sget-char" :$inst = new ast.stm.Instruction.SgetChar($a.text,$b.text,$c.fieldItem);break;
          //66: sget-short SgetShort
          case"sget-short" :$inst = new ast.stm.Instruction.SgetShort($a.text,$b.text,$c.fieldItem);break;
          //67: sput Sput
          case"sput" :$inst = new ast.stm.Instruction.Sput($a.text,$b.text,$c.fieldItem);break;
          //68: sput-wide  SputWide
          case"sput-wide" :$inst = new ast.stm.Instruction.SputWide($a.text,$b.text,$c.fieldItem);break;
          //69: sput-object: SputObject
          case"sput-object": $inst = new ast.stm.Instruction.SputObject($a.text,$b.text,$c.fieldItem);break;
          //6a: sput-boolean SputBoolean
          case"sput-boolean": $inst = new ast.stm.Instruction.SputBoolean($a.text,$b.text,$c.fieldItem);break;
          //6b: sput-byte SputByte
          case"sput-byte" :$inst = new ast.stm.Instruction.SputByte($a.text,$b.text,$c.fieldItem);break;
          //6c: sput-char SputChar
          case"sput-char" : $inst = new ast.stm.Instruction.SputChar($a.text,$b.text,$c.fieldItem);break;
          //6d: sput-short SputShort
          case"sput-short": $inst = new ast.stm.Instruction.SputShort($a.text,$b.text,$c.fieldItem);break;
          default: System.err.println("insn_format21c_field: " + $a.text + " unknown");
        }

    };

insn_format21c_string returns [ast.stm.T inst]
  : //e.g. const-string v1, "Hello World!"
    ^(I_STATEMENT_FORMAT21c_STRING a=INSTRUCTION_FORMAT21c_STRING b=REGISTER c=string_literal)
    {

      switch($a.text)
      {
        //1a 21c  const-string ConstString
        case "const-string" : $inst = new ast.stm.Instruction.ConstString($a.text,$b.text,$c.value);break;
        default: System.err.println("insn_format21c_string: " + $a.text + " unknown");
      }

    };

insn_format21c_type returns [ast.stm.T inst]
  : //e.g. const-class v2, org/jf/HelloWorld2/HelloWorld2
    ^(I_STATEMENT_FORMAT21c_TYPE a=INSTRUCTION_FORMAT21c_TYPE b=REGISTER c=reference_type_descriptor)
    {

        switch($a.text)
        {
            //1c 21c  const-class ConstClass
            case "const-class" :$inst = new ast.stm.Instruction.ConstClass($a.text,$b.text,$c.ref_desc); break;
            //1f 21c  check-cast CheckCast
            case "check-cast" :$inst = new ast.stm.Instruction.CheckCast($a.text,$b.text,$c.ref_desc);break;
            //22 21c  new-instance NewInstance
            case "new-instance": $inst = new ast.stm.Instruction.NewInstance($a.text,$b.text,$c.ref_desc);break;
            default: System.err.println("insn_format21c_type: " + $a.text + " unknown");
        }
    };

insn_format21h returns [ast.stm.T inst]
  : //e.g. const/high16 v1, 1234
    ^(I_STATEMENT_FORMAT21h a=INSTRUCTION_FORMAT21h b=REGISTER c=short_integral_literal)
    {
        switch($a.text)
        {
          //15 21h  const/high16 ConstHigh16
          case "const/high16": $inst = new ast.stm.Instruction.ConstHigh16($a.text,$b.text,$c.value);break;
          //19 21h  const-wide/high16 ConstWideHigh16
          case "const-wide/high16": $inst = new ast.stm.Instruction.ConstWideHigh16($a.text,$b.text,$c.value);break;
          default: System.err.println("insn_format21h: " + $a.text + " unknown");
        }
    };

insn_format21s returns [ast.stm.T inst]
  : //e.g. const/16 v1, 1234
    ^(I_STATEMENT_FORMAT21s a=INSTRUCTION_FORMAT21s b=REGISTER c=short_integral_literal)
    {
      switch($a.text)
      {
        //13 21s  const/16 Const16
        case "const/16" : $inst = new ast.stm.Instruction.Const16($a.text,$b.text,$c.value);break;
        //16 21s const-wide/16 ConstWide16
        case "const-wide/16" : $inst = new ast.stm.Instruction.ConstWide16($a.text,$b.text,$c.value);break;
        default: System.err.println("insn_format21s: " + $a.text + " unknown");
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
        case "if-eqz" : $inst = new ast.stm.Instruction.IfEqz($a.text,$b.text,$c.offorlab);break;
        //39: if-nez IfNez
        case "if-nez" : $inst = new ast.stm.Instruction.IfNez($a.text,$b.text,$c.offorlab);break;
        //3a: if-ltz IfLtz
        case "if-ltz" : $inst = new ast.stm.Instruction.IfLtz($a.text,$b.text,$c.offorlab);break;
        //3b: if-gez IfGez
        case "if-gez" : $inst = new ast.stm.Instruction.IfGez($a.text,$b.text,$c.offorlab);break;
        //3c: if-gtz IfGtz
        case "if-gtz" : $inst = new ast.stm.Instruction.IfGtz($a.text,$b.text,$c.offorlab);break;
        //3d: if-lez IfLez
        case "if-lez" : $inst = new ast.stm.Instruction.IfLez($a.text,$b.text,$c.offorlab);break;
        default: System.err.println("insn_format21t: " + $a.text + " unknown");
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
        case "add-int/lit8": $inst = new ast.stm.Instruction.AddIntLit8($a.text,$b.text,$c.text,$d.value);break;
        //d9: rsub-int/lit8 RsubIntLit8
        case "rsub-int/lit8": $inst = new ast.stm.Instruction.RsubIntLit8($a.text,$b.text,$c.text,$d.value);break;
        //da: mul-int/lit8 MulIntLit8
        case "mul-int/lit8": $inst = new ast.stm.Instruction.MulIntLit8($a.text,$b.text,$c.text,$d.value);break;
        //db: div-int/lit8 DivIntLit8
        case "div-int/lit8": $inst = new ast.stm.Instruction.DivIntLit8($a.text,$b.text,$c.text,$d.value);break;
        //dc: rem-int/lit8  RemIntLit8
        case "rem-int/lit8": $inst = new ast.stm.Instruction.RemIntLit8($a.text,$b.text,$c.text,$d.value);break;
        //dd: and-int/lit8 AndIntLit8
        case "and-int/lit8": $inst = new ast.stm.Instruction.AndIntLit8($a.text,$b.text,$c.text,$d.value);break;
        //de: or-int/lit8 OrIntLit8
        case "or-int/lit8": $inst = new ast.stm.Instruction.OrIntLit8($a.text,$b.text,$c.text,$d.value);break;
        //df: xor-int/lit8 XorIntLit8
        case "xor-int/lit8": $inst = new ast.stm.Instruction.XorIntLit8($a.text,$b.text,$c.text,$d.value);break;
        //e0: shl-int/lit8 ShlIntLit8
        case "shl-int/lit8": $inst = new ast.stm.Instruction.ShlIntLit8($a.text,$b.text,$c.text,$d.value);break;
        //e1: shr-int/lit8 ShrIntLit8
        case "shr-int/lit8": $inst = new ast.stm.Instruction.ShrIntLit8($a.text,$b.text,$c.text,$d.value);break;
        //e2: ushr-int/lit8 UshrIntLit8
        case "ushr-int/lit8": $inst = new ast.stm.Instruction.UshrIntLit8($a.text,$b.text,$c.text,$d.value);break;
        default: System.err.println("insn_format22b: " + $a.text + " unknown");
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
          case "iget" : $inst = new ast.stm.Instruction.Iget($a.text,$b.text,$c.text,$d.fieldItem);break;
          //53: iget-wide IgetWide
          case "iget-wide" : $inst = new ast.stm.Instruction.IgetWide($a.text,$b.text,$c.text,$d.fieldItem);break;
          //54: iget-object IgetOjbect
          case "iget-object" : $inst = new ast.stm.Instruction.IgetOjbect($a.text,$b.text,$c.text,$d.fieldItem);break;
          //55: iget-boolean IgetBoolean
          case "iget-boolean" : $inst = new ast.stm.Instruction.IgetBoolean($a.text,$b.text,$c.text,$d.fieldItem);break;
          //56: iget-byte IgetByte
          case "iget-byte" : $inst = new ast.stm.Instruction.IgetByte($a.text,$b.text,$c.text,$d.fieldItem);break;
          //57: iget-char IgetChar
          case "iget-char" : $inst = new ast.stm.Instruction.IgetChar($a.text,$b.text,$c.text,$d.fieldItem);break;
          //58: iget-short IgetShort
          case "iget-short" : $inst = new ast.stm.Instruction.IgetShort($a.text,$b.text,$c.text,$d.fieldItem);break;
          //59: iput Iput
          case "iput" : $inst = new ast.stm.Instruction.Iput($a.text,$b.text,$c.text,$d.fieldItem);break;
          //5a: iput-wide IputWide
          case "iput-wide" : $inst = new ast.stm.Instruction.IputWide($a.text,$b.text,$c.text,$d.fieldItem);break;
          //5b: iput-object IputObject
          case "iput-object" : $inst = new ast.stm.Instruction.IputObject($a.text,$b.text,$c.text,$d.fieldItem);break;
          //5c: iput-boolean IputBoolean
          case "iput-boolean" : $inst = new ast.stm.Instruction.IputBoolean($a.text,$b.text,$c.text,$d.fieldItem);break;
          //5d: iput-byte IputByte
          case "iput-byte" : $inst = new ast.stm.Instruction.IputByte($a.text,$b.text,$c.text,$d.fieldItem);break;
          //5e: iput-char IputChar
          case "iput-char" : $inst = new ast.stm.Instruction.IputChar($a.text,$b.text,$c.text,$d.fieldItem);break;
          //5f: iput-short IputShort
          case "iput-short" : $inst = new ast.stm.Instruction.IputShort($a.text,$b.text,$c.text,$d.fieldItem);break;
          default: System.err.println("insn_format22c_field: " + $a.text + " unknown");
       }
    };

insn_format22c_type returns [ast.stm.T inst]
  : //e.g. instance-of v0, v1, Ljava/lang/String;
    ^(I_STATEMENT_FORMAT22c_TYPE a=INSTRUCTION_FORMAT22c_TYPE b=REGISTER c=REGISTER d=nonvoid_type_descriptor) //type_desc
    {
        switch($a.text)
        {
            //20 22c  instance-of InstanceOf
            case "instance-of" : $inst = new ast.stm.Instruction.InstanceOf($a.text,$b.text,$c.text,$d.type_desc);break;
            //23 22c  new-array NewArray
            case "new-array" : $inst = new ast.stm.Instruction.NewArray($a.text,$b.text,$c.text,$d.type_desc);break;
            default: System.err.println("insn_format22c_type: " + $a.text + " unknown");
        }
    };

insn_format22s returns [ast.stm.T inst]
  : //e.g. add-int/lit16 v0, v1, 12345
    ^(I_STATEMENT_FORMAT22s a=INSTRUCTION_FORMAT22s b=REGISTER c=REGISTER d=short_integral_literal)//value
    {
        switch($a.text)
        {
          //d0..d7 22s  binop/lit16 vA, vB, #+CCCC
          //d0: add-int/lit16 AddIntLit16
          case "add-int/lit16" : $inst = new ast.stm.Instruction.AddIntLit16($a.text,$b.text,$c.text,$d.value);break;
          //d1: rsub-int (reverse subtract) RsubInt
          case "rsub-int" : $inst = new ast.stm.Instruction.RsubInt($a.text,$b.text,$c.text,$d.value);break;
          //d2: mul-int/lit16 MulIntLit16
          case "mul-int/lit16" : $inst = new ast.stm.Instruction.MulIntLit16($a.text,$b.text,$c.text,$d.value);break;
          //d3: div-int/lit16 DivIntLit16
          case "div-int/lit16" : $inst = new ast.stm.Instruction.DivIntLit16($a.text,$b.text,$c.text,$d.value);break;
          //d4: rem-int/lit16 RemIntLit16
          case "rem-int/lit16" : $inst = new ast.stm.Instruction.RemIntLit16($a.text,$b.text,$c.text,$d.value);break;
          //d5: and-int/lit16 AndIntLit16
          case "and-int/lit16" : $inst = new ast.stm.Instruction.AndIntLit16($a.text,$b.text,$c.text,$d.value);break;
          //d6: or-int/lit16 OrIntLit16
          case "or-int/lit16" : $inst = new ast.stm.Instruction.OrIntLit16($a.text,$b.text,$c.text,$d.value);break;
          //d7: xor-int/lit16 XorIntLit16
          case "xor-int/lit16" : $inst = new ast.stm.Instruction.XorIntLit16($a.text,$b.text,$c.text,$d.value);break;
          default: System.err.println("insn_format22s: " + $a.text + " unknown");
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
        case "if-eq" : $inst = new ast.stm.Instruction.IfEq($a.text,$b.text,$c.text,$d.offorlab);break;
        //33: if-ne IfNe
        case "if-ne" : $inst = new ast.stm.Instruction.IfNe($a.text,$b.text,$c.text,$d.offorlab);break;
        //34: if-lt IfLt
        case "if-lt" : $inst = new ast.stm.Instruction.IfLt($a.text,$b.text,$c.text,$d.offorlab);break;
        //35: if-ge IfGe
        case "if-ge" : $inst = new ast.stm.Instruction.IfGe($a.text,$b.text,$c.text,$d.offorlab);break;
        //36: if-gt IfGt
        case "if-gt" : $inst = new ast.stm.Instruction.IfGt($a.text,$b.text,$c.text,$d.offorlab);break;
        //37: if-le IfLe
        case "if-le" : $inst = new ast.stm.Instruction.IfLe($a.text,$b.text,$c.text,$d.offorlab);break;
        default: System.err.println("insn_format22t: " + $a.text + " unknown");
      }
    };

insn_format22x returns [ast.stm.T inst]
  : //e.g. move/from16 v1, v1234
    ^(I_STATEMENT_FORMAT22x a=INSTRUCTION_FORMAT22x b=REGISTER c=REGISTER)
    {
      switch($a.text)
      {
        //02  move/from16 MoveFrom16
        case "move/from16" : $inst = new ast.stm.Instruction.MoveFrom16($a.text,$b.text,$c.text);break;
        //05  move-wide/from16 MoveWideFrom16
        case "move-wide/from16" : $inst = new ast.stm.Instruction.MoveWideFrom16($a.text,$b.text,$c.text);break;
        //08  move-object/from16 MoveOjbectFrom16
        case "move-object/from16" : $inst = new ast.stm.Instruction.MoveOjbectFrom16($a.text,$b.text,$c.text);break;
        default: System.err.println("insn_format22x: " + $a.text + " unknown");
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
          case "cmpl-float" : $inst = new ast.stm.Instruction.CmplFloat($a.text,$b.text,$c.text,$d.text);break;
          //2e: cmpg-float (gt bias) CmpgFloat
          case "cmpg-float" : $inst = new ast.stm.Instruction.CmpgFloat($a.text,$b.text,$c.text,$d.text);break;
          //2f: cmpl-double (lt bias) CmplDouble
          case "cmpl-double" : $inst = new ast.stm.Instruction.CmplDouble($a.text,$b.text,$c.text,$d.text);break;
          //30: cmpg-double (gt bias) Cmpgdouble
          case "cmpg-double" : $inst = new ast.stm.Instruction.Cmpgdouble($a.text,$b.text,$c.text,$d.text);break;
          //31: cmp-long  CmpLong
          case "cmp-long" : $inst = new ast.stm.Instruction.CmpLong($a.text,$b.text,$c.text,$d.text);break;

          //44..51 23x  arrayop vAA, vBB, vCC
         // 44:   aget  Aget
          case "aget" : $inst = new ast.stm.Instruction.Aget($a.text,$b.text,$c.text,$d.text);break;
         // 45:   aget-wide AgetWide
          case "aget-wide" : $inst = new ast.stm.Instruction.AgetWide($a.text,$b.text,$c.text,$d.text);break;
         // 46:   aget-object AgetObject
          case "aget-object" : $inst = new ast.stm.Instruction.AgetObject($a.text,$b.text,$c.text,$d.text);break;
         // 47:   aget-boolean AgetBoolean
          case "aget-boolean" : $inst = new ast.stm.Instruction.AgetBoolean($a.text,$b.text,$c.text,$d.text);break;
         // 48:   aget-byte AgetByte
          case "aget-byte" : $inst = new ast.stm.Instruction.AgetByte($a.text,$b.text,$c.text,$d.text);break;
          //49:   aget-char AgetChar
          case "aget-char" : $inst = new ast.stm.Instruction.AgetChar($a.text,$b.text,$c.text,$d.text);break;
         // 4a:   aget-short AgetShort
          case "aget-short" : $inst = new ast.stm.Instruction.AgetShort($a.text,$b.text,$c.text,$d.text);break;
         // 4b:   aput Aput
          case "aput" : $inst = new ast.stm.Instruction.Aput($a.text,$b.text,$c.text,$d.text);break;
         // 4c:   aput-wide AputWide
          case "aput-wide" : $inst = new ast.stm.Instruction.AputWide($a.text,$b.text,$c.text,$d.text);break;
         // 4d:   aput-object AputObject
          case "aput-object" : $inst = new ast.stm.Instruction.AputObject($a.text,$b.text,$c.text,$d.text);break;
         // 4e:   aput-boolean AputBoolean
          case "aput-boolean" : $inst = new ast.stm.Instruction.AputBoolean($a.text,$b.text,$c.text,$d.text);break;
         // 4f:   aput-byte AputByte
          case "aput-byte" : $inst = new ast.stm.Instruction.AputByte($a.text,$b.text,$c.text,$d.text);break;
         // 50:   aput-char AputChar
          case "aput-char" : $inst = new ast.stm.Instruction.AputChar($a.text,$b.text,$c.text,$d.text);break;
         // 51:   aput-short AputShort
          case "aput-short" : $inst = new ast.stm.Instruction.AputShort($a.text,$b.text,$c.text,$d.text);break;

          //90..af 23x  binop vAA, vBB, vCC
          //90: add-int AddInt
          case "add-int" : $inst = new ast.stm.Instruction.AddInt($a.text,$b.text,$c.text,$d.text);break;
          //91: sub-int  SubInt
          case "sub-int" : $inst = new ast.stm.Instruction.SubInt($a.text,$b.text,$c.text,$d.text);break;
          //92: mul-int MulInt
          case "mul-int" : $inst = new ast.stm.Instruction.MulInt($a.text,$b.text,$c.text,$d.text);break;
          //93: div-int DivInt
          case "div-int" : $inst = new ast.stm.Instruction.DivInt($a.text,$b.text,$c.text,$d.text);break;
          //94: rem-int RemInt
          case "rem-int" : $inst = new ast.stm.Instruction.RemInt($a.text,$b.text,$c.text,$d.text);break;
          //95: and-int AndInt
          case "and-int" : $inst = new ast.stm.Instruction.AndInt($a.text,$b.text,$c.text,$d.text);break;
          //96: or-int OrInt
          case "or-int" : $inst = new ast.stm.Instruction.OrInt($a.text,$b.text,$c.text,$d.text);break;
          //97: xor-int XorInt
          case "xor-int" : $inst = new ast.stm.Instruction.XorInt($a.text,$b.text,$c.text,$d.text);break;
          //98: shl-int ShlInt
          case "shl-int" : $inst = new ast.stm.Instruction.ShlInt($a.text,$b.text,$c.text,$d.text);break;
          //99: shr-int ShrInt
          case "shr-int" : $inst = new ast.stm.Instruction.ShrInt($a.text,$b.text,$c.text,$d.text);break;
          //9a: ushr-int UshrInt
          case "ushr-int" : $inst = new ast.stm.Instruction.UshrInt($a.text,$b.text,$c.text,$d.text);break;
          //9b: add-long AddLong
          case "add-long" : $inst = new ast.stm.Instruction.AddLong($a.text,$b.text,$c.text,$d.text);break;
          //9c: sub-long SubLong
          case "sub-long" : $inst = new ast.stm.Instruction.SubLong($a.text,$b.text,$c.text,$d.text);break;
          //9d: mul-long MulLong
          case "mul-long" : $inst = new ast.stm.Instruction.MulLong($a.text,$b.text,$c.text,$d.text);break;
          //9e: div-long DivLong
          case "div-long" : $inst = new ast.stm.Instruction.DivLong($a.text,$b.text,$c.text,$d.text);break;
          //9f: rem-long RemLong
          case "rem-long" : $inst = new ast.stm.Instruction.RemLong($a.text,$b.text,$c.text,$d.text);break;
          //a0: and-long AndLong
          case "and-long" : $inst = new ast.stm.Instruction.AndLong($a.text,$b.text,$c.text,$d.text);break;
          //a1: or-long OrLong
          case "or-long" : $inst = new ast.stm.Instruction.OrLong($a.text,$b.text,$c.text,$d.text);break;
          //a2: xor-long XorLong
          case "xor-long" : $inst = new ast.stm.Instruction.XorLong($a.text,$b.text,$c.text,$d.text);break;
          //a3: shl-long ShlLong
          case "shl-long" : $inst = new ast.stm.Instruction.ShlLong($a.text,$b.text,$c.text,$d.text);break;
          //a4: shr-long ShrLong
          case "shr-long" : $inst = new ast.stm.Instruction.ShrLong($a.text,$b.text,$c.text,$d.text);break;
          //a5: ushr-long UshrLong
          case "ushr-long" : $inst = new ast.stm.Instruction.UshrLong($a.text,$b.text,$c.text,$d.text);break;
          //a6: add-float AddFloat
          case "add-float" : $inst = new ast.stm.Instruction.AddFloat($a.text,$b.text,$c.text,$d.text);break;
          //a7: sub-float SubFloat
          case "sub-float" : $inst = new ast.stm.Instruction.SubFloat($a.text,$b.text,$c.text,$d.text);break;
          //a8: mul-float MulFloat
          case "mul-float" : $inst = new ast.stm.Instruction.MulFloat($a.text,$b.text,$c.text,$d.text);break;
          //a9: div-float DivFloat
          case "div-float" : $inst = new ast.stm.Instruction.DivFloat($a.text,$b.text,$c.text,$d.text);break;
          //aa: rem-float RemFloat
          case "rem-float" : $inst = new ast.stm.Instruction.RemFloat($a.text,$b.text,$c.text,$d.text);break;
          //ab: add-double AddDouble
          case "add-double" : $inst = new ast.stm.Instruction.AddDouble($a.text,$b.text,$c.text,$d.text);break;
          //ac: sub-double SubDouble
          case "sub-double" : $inst = new ast.stm.Instruction.SubDouble($a.text,$b.text,$c.text,$d.text);break;
          //ad: mul-double MulDouble
          case "mul-double" : $inst = new ast.stm.Instruction.MulDouble($a.text,$b.text,$c.text,$d.text);break;
          //ae: div-double DivDouble
          case "div-double" : $inst = new ast.stm.Instruction.DivDouble($a.text,$b.text,$c.text,$d.text);break;
          //af: rem-double RemDouble
          case "rem-double" : $inst = new ast.stm.Instruction.RemDouble($a.text,$b.text,$c.text,$d.text);break;


          default: System.err.println("insn_format23x: " + $a.text + " unknown");


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
        case "goto/32" :  $inst = new ast.stm.Instruction.Goto32($a.text,$b.offorlab);break;
        default: System.err.println("insn_format30t: " + $a.text + " unknown");
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
          case "const-string/jumbo" : $inst = new ast.stm.Instruction.ConstStringJumbo($a.text,$b.text,$c.value);break;
          default: System.err.println("insn_format31c: " + $a.text + " unknown");
       }
    };

insn_format31i returns [ast.stm.T inst]
  : //e.g. const v0, 123456
    ^(I_STATEMENT_FORMAT31i a=INSTRUCTION_FORMAT31i b=REGISTER c=fixed_32bit_literal)//value
    {
      switch($a.text)
      {
        //14 31i const Const
        case "const" : $inst = new ast.stm.Instruction.Const($a.text,$b.text,$c.value);
        //17 31i const-wide/32 ConstWide32
        case "const-wide/32": $inst = new ast.stm.Instruction.ConstWide32($a.text,$b.text,$c.value);break;
        default: System.err.println("insn_format31i: " + $a.text + " unknown");
      }

    };

insn_format31t returns [ast.stm.T inst]
  : //e.g. fill-array-data v0, ArrayData:
    ^(I_STATEMENT_FORMAT31t a=INSTRUCTION_FORMAT31t b=REGISTER c=offset_or_label)//offorlab
    {
       switch($a.text)
      {
        //26 31t  fill-array-data  FillArrayData
        case "fill-array-data":$inst = new ast.stm.Instruction.FillArrayData($a.text,$b.text,$c.offorlab);break;
        //2b 31t  packed-switch vAA, +BBBBBBBB
        case "packed-switch":$inst = new ast.stm.Instruction.PackedSwitch($a.text,$b.text,$c.offorlab);break;
        //2c 31t  sparse-switch vAA, +BBBBBBBB
        case "sparse-switch":$inst = new ast.stm.Instruction.SparseSwitch($a.text,$b.text,$c.offorlab);break;
        default: System.err.println("insn_format31t: " + $a.text + " unknown");
      }
    };

insn_format32x returns [ast.stm.T inst]
  : //e.g. move/16 v5678, v1234
    ^(I_STATEMENT_FORMAT32x a=INSTRUCTION_FORMAT32x b=REGISTER c=REGISTER)
    {

       switch($a.text)
       {

         //03 32x  move/16 Move16
         case "move/16" : $inst = new ast.stm.Instruction.Move16($a.text,$b.text,$c.text);break;
         //06 32x move-wide/16 MoveWide16
         case "move-wide/16" : $inst = new ast.stm.Instruction.MoveWide16($a.text,$b.text,$c.text);break;
         //09 32x  move-object/16 MoveObject16
         case "move-object/16" : $inst = new ast.stm.Instruction.MoveObject16($a.text,$b.text,$c.text);break;
         default: System.err.println("insn_format32x: " + $a.text + " unknown");

       }

    };


insn_format35c_method returns [ast.stm.T inst]

  : //e.g. invoke-virtual {} java/io/PrintStream/print(Ljava/lang/Stream;)V
    ^(I_STATEMENT_FORMAT35c_METHOD a=INSTRUCTION_FORMAT35c_METHOD b=register_list c=fully_qualified_method) //methodItem
    {
      switch($a.text)
      {
         //6e..72 35c  invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB
        //6e: invoke-virtual InvokeVirtual
        case "invoke-virtual" : $inst = new ast.stm.Instruction.InvokeVirtual($a.text,$b.argList,$c.methodItem);break;
        //6f: invoke-super InvokeSuper
        case "invoke-super" : $inst = new ast.stm.Instruction.InvokeSuper($a.text,$b.argList,$c.methodItem);break;
        //70: invoke-direct InvokeDirect
        case "invoke-direct" : $inst = new ast.stm.Instruction.InvokeDirect($a.text,$b.argList,$c.methodItem);break;
        //71: invoke-static InvokeStatic
        case "invoke-static" : $inst = new ast.stm.Instruction.InvokeStatic($a.text,$b.argList,$c.methodItem);break;
        //72: invoke-interface   InvokeInterface
        case "invoke-interface" : $inst = new ast.stm.Instruction.InvokeInterface($a.text,$b.argList,$c.methodItem);break;
        default: System.err.println("insn_format35c_method: " + $a.text + " unknown");
      }

    };

insn_format35c_type returns [ast.stm.T inst]
  : //e.g. filled-new-array {}, I
    ^(I_STATEMENT_FORMAT35c_TYPE a=INSTRUCTION_FORMAT35c_TYPE b=register_list c=nonvoid_type_descriptor)
    {
      switch($a.text)
      {
        //24 35c  filled-new-array FilledNewArray
        case "filled-new-array" : $inst = new ast.stm.Instruction.FilledNewArray($a.text,$b.argList,$c.type_desc);break;
        default: System.err.println("insn_format35c_type: " + $a.text + " unknown");
      }

    };

insn_format3rc_method returns [ast.stm.T inst]
  : //e.g. invoke-virtual/range {} java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    ^(I_STATEMENT_FORMAT3rc_METHOD a=INSTRUCTION_FORMAT3rc_METHOD b=register_range c=fully_qualified_method)
    {
        switch($a.text)
        {
          //74..78 3rc  invoke-kind/range {vCCCC .. vNNNN}, meth@BBBB
          //74: invoke-virtual/range InvokeVirtualRange
          case "invoke-virtual/range" : $inst = new ast.stm.Instruction.InvokeVirtualRange($a.text,$b.started,$b.ended,$c.methodItem);break;
          //75: invoke-super/range InvokeSuperRange
          case "invoke-super/range" : $inst = new ast.stm.Instruction.InvokeSuperRange($a.text,$b.started,$b.ended,$c.methodItem);break;
          //76: invoke-direct/range InvokeDirectRange
          case "invoke-direct/range" : $inst = new ast.stm.Instruction.InvokeDirectRange($a.text,$b.started,$b.ended,$c.methodItem);break;
          //77: invoke-static/range InvokeStaticRange
          case "invoke-static/range" : $inst = new ast.stm.Instruction.InvokeStaticRange($a.text,$b.started,$b.ended,$c.methodItem);break;
          //78: invoke-interface/range InvokeInterfaceRange
          case "invoke-interface/range" : $inst = new ast.stm.Instruction.InvokeInterfaceRange($a.text,$b.started,$b.ended,$c.methodItem);break;
          default: System.err.println("insn_format3rc_method: " + $a.text + " unknown");
        }

    };

insn_format3rc_type returns [ast.stm.T inst]
  : //e.g. filled-new-array/range {} I
    ^(I_STATEMENT_FORMAT3rc_TYPE a=INSTRUCTION_FORMAT3rc_TYPE b=register_range c=nonvoid_type_descriptor)//type_desc
    {
      switch($a.text)
      {
        //25 3rc filled-new-array/range
        //FilledNewArrayRange
        case "filled-new-array/range" : $inst = new ast.stm.Instruction.FilledNewArrayRange($a.text,$b.started,$b.ended,$c.type_desc);break;
        default: System.err.println("insn_format3rc_type: " + $a.text + " unknown");
      }
    };

//can not find
insn_format41c_type returns [ast.stm.T inst]
  : //e.g. const-class/jumbo v2, org/jf/HelloWorld2/HelloWorld2
    ^(I_STATEMENT_FORMAT41c_TYPE INSTRUCTION_FORMAT41c_TYPE REGISTER reference_type_descriptor)
    {
         System.err.println("insn_format20bc: can not find in dalvik bytecode" );
    };
// can not find
insn_format41c_field returns [ast.stm.T inst]
  : //e.g. sget-object/jumbo v0, Ljava/lang/System;->out:LJava/io/PrintStream;
    ^(I_STATEMENT_FORMAT41c_FIELD INSTRUCTION_FORMAT41c_FIELD REGISTER fully_qualified_field)
    {
         System.err.println("insn_format20bc: can not find in dalvik bytecode" );
    };

insn_format51l_type returns [ast.stm.T inst]
  : //e.g. const-wide v0, 5000000000L
    ^(I_STATEMENT_FORMAT51l a=INSTRUCTION_FORMAT51l b=REGISTER c=fixed_64bit_literal)//value
    {
      switch($a.text)
      {
          //18 51l  const-wide  ConstWide
          case "const-wide" : $inst = new ast.stm.Instruction.ConstWide($a.text,$b.text,$c.value);break;
          default: System.err.println("insn_format51l_type: " + $a.text + " unknown");
      }

    };
// can not find
insn_format52c_type returns [ast.stm.T inst]
  : //e.g. instance-of/jumbo v0, v1, Ljava/lang/String;
    ^(I_STATEMENT_FORMAT52c_TYPE INSTRUCTION_FORMAT52c_TYPE REGISTER REGISTER nonvoid_type_descriptor)
    {
         System.err.println("insn_format52c_type: can not find in dalvik bytecode" );
    };
// can not find
insn_format52c_field returns [ast.stm.T inst]
  : //e.g. iput-object/jumbo v1, v0, Lorg/jf/HelloWorld2/HelloWorld2;->helloWorld:Ljava/lang/String;
    ^(I_STATEMENT_FORMAT52c_FIELD INSTRUCTION_FORMAT52c_FIELD REGISTER REGISTER fully_qualified_field)
    {
         System.err.println("insn_format52c_field: can not find in dalvik bytecode" );
    };
// can not find
insn_format5rc_method returns [ast.stm.T inst]
  : //e.g. invoke-virtual/jumbo {} java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    ^(I_STATEMENT_FORMAT5rc_METHOD INSTRUCTION_FORMAT5rc_METHOD register_range fully_qualified_method)
    {
       System.err.println("insn_format5rc_method: can not find in dalvik bytecode" );
    };
// can not find
insn_format5rc_type returns [ast.stm.T inst]
  : //e.g. filled-new-array/jumbo {} I
    ^(I_STATEMENT_FORMAT5rc_TYPE INSTRUCTION_FORMAT5rc_TYPE register_range nonvoid_type_descriptor)
    {
      System.err.println("insn_format5rc_type: can not find in dalvik bytecode" );
    };

insn_array_data_directive returns [ast.stm.T inst]
  : //e.g. .array-data 4 1000000 .end array-data
    ^(I_STATEMENT_ARRAY_DATA ^(I_ARRAY_ELEMENT_SIZE a=short_integral_literal) b=array_elements)
    {
      $inst = new ast.stm.Instruction.ArrayDataDirective($a.value,$b.elementList);
    };

insn_packed_switch_directive returns [ast.stm.T inst]
  :
    ^(I_STATEMENT_PACKED_SWITCH ^(I_PACKED_SWITCH_START_KEY a=fixed_32bit_literal)
      b=packed_switch_targets)
    {
        $inst = new ast.stm.Instruction.PackedSwitchDirective($a.value,$b.count,$b.labList);
    };

insn_sparse_switch_directive returns [ast.stm.T inst]
  :
    ^(I_STATEMENT_SPARSE_SWITCH a=sparse_switch_target_count b=sparse_switch_keys c=sparse_switch_targets)
    {
        $inst = new ast.stm.Instruction.SparseSwitchDirective($a.txt,$b.keyList,$c.labList);
    };



nonvoid_type_descriptor returns [String type_desc]
  : a=(PRIMITIVE_TYPE
  | CLASS_DESCRIPTOR
  | ARRAY_DESCRIPTOR)
  {
  	 $type_desc = $a.text;
  };


reference_type_descriptor returns [String ref_desc]
  : a=(CLASS_DESCRIPTOR
  | ARRAY_DESCRIPTOR)
  {
  	$ref_desc = $a.text;
  }
  ;

class_type_descriptor returns [String className]
  : CLASS_DESCRIPTOR
  {
  	$className = $CLASS_DESCRIPTOR.text;
  };

type_descriptor returns [String type_desc,String value,String type]
  : VOID_TYPE { $type_desc = "V"; $type = "void"; $value="V"; /* void */ }
  | nonvoid_type_descriptor { $type_desc = $nonvoid_type_descriptor.type_desc; $value =  $nonvoid_type_descriptor.type_desc;$type = "type"; }
  ;

short_integral_literal returns [String value]
  :long_literal{$value = $long_literal.value;}
  |integer_literal{$value = $integer_literal.value;}
  |short_literal {$value = $short_literal.value;}
  |char_literal {$value = $char_literal.value;}
  |byte_literal {$value = $byte_literal.value;}
  ;

integral_literal returns[String value]
  :long_literal{$value = $long_literal.value;}
  |integer_literal {$value = $integer_literal.value;}
  |short_literal {$value = $short_literal.value;}
  |byte_literal {$value = $byte_literal.value;}
  ;


integer_literal returns[String value,String type]
  : a=INTEGER_LITERAL { $value = $a.text; $type =  "integer";};

long_literal returns[String value,String type]
  : a=LONG_LITERAL { $value = $a.text; $type = "long";};

short_literal returns[String value,String type]
  :  a=SHORT_LITERAL { $value = $a.text;$type = "short"; };

byte_literal returns[String value,String type]
  :  a=BYTE_LITERAL { $value = $a.text;$type = "byte";};

float_literal returns[String value,String type]
  :  a=FLOAT_LITERAL { $value = $a.text; $type = "float"; };

double_literal returns[String value,String type]
  :  a=DOUBLE_LITERAL { $value = $a.text; $type = "double";};

char_literal returns[String value,String type]
  :  a=CHAR_LITERAL 
  { 
    $value = $a.text; 
    $type = "char";
  };

string_literal returns [String value,String type]
  :  a=STRING_LITERAL { $value = $a.text;$type = "string"; };

bool_literal returns [String value,String type]
  :  a=BOOL_LITERAL {$value = $a.text;$type = "bool";};

array_literal returns [String value,List<Object> element,List<String> arrayLiteralType]
@init{
  $element = new ArrayList<Object>();
  $arrayLiteralType = new ArrayList<String>();
}  : {}
    ^(I_ENCODED_ARRAY (a=literal {
    $element.add($a.object); 
    $arrayLiteralType.add($a.type);
    if($a.type.equals("array"))
      System.out.println("find array_literal in array_literal in TranslateWalker.g file");
    })*)
    {
    };


annotations returns [List<ast.annotation.Annotation> annotationList]
@init{
  annotationList = new ArrayList<ast.annotation.Annotation>();
}
  : {}
    ^(I_ANNOTATIONS (a=annotation { $annotationList.add($a.anno);} )*)
    {
    };


annotation returns [ast.annotation.Annotation anno]
  : ^(I_ANNOTATION a=ANNOTATION_VISIBILITY b=subannotation)
    {
      $anno  = new ast.annotation.Annotation($a.text,$b.subAnno);
    };

subannotation returns [ast.annotation.Annotation.SubAnnotation subAnno,String type,String value]
@init{
  List<ast.annotation.Annotation.AnnotationElement> elementList  = new ArrayList<ast.annotation.Annotation.AnnotationElement>();
}
  :
    ^(I_SUBANNOTATION
        a=class_type_descriptor {}
        (b=annotation_element
        {
          elementList.add($b.element);
        }
        )*
     )
     {
        $subAnno = new ast.annotation.Annotation.SubAnnotation($a.className,elementList);
        $type = "subannotation";
        $value = "subannotation's value";
     };

annotation_element returns [ast.annotation.Annotation.AnnotationElement element]
  : ^(I_ANNOTATION_ELEMENT a=SIMPLE_NAME b=literal)
    {
      $element = new ast.annotation.Annotation.AnnotationElement($a.text,$b.elementLiteral);
    };

field_literal returns [String value,String type]
  : ^(I_ENCODED_FIELD a=fully_qualified_field)
    {
      $value = $a.fieldItem.toString();
      $type = "field";
    };

method_literal returns [String value,String type]
  : ^(I_ENCODED_METHOD a=fully_qualified_method)
    {
      $value = $a.methodItem.toString();
      //$value = "La/a/a/a/a/a/a/e/a;->a(Ljava/io/OutputStream;)V";
      $type = "method";
    };

enum_literal returns [String value,String type]
  : ^(I_ENCODED_ENUM a=fully_qualified_field)
    {
       $value = $a.fieldItem.toString();
       $type = "enum";
    };
