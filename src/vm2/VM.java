package vm2;

import java.util.Map;
import java.util.Stack;

public class VM {

    /**
     * key:    clazzName
     * value:  superClazzName
     */
    public Map<String, String> clazzArea;

    /**
     * key:    fullQualifiedMethodName
     * value:  vm2.Method
     */
    public Map<String, Method> methodArea;

    /**
     * key:    fullQualifiedStaticFieldName
     * value:  field
     */
    public Map<String, Object> staticFieldsArea;


    /**
     *  key:    clazzName
     *  value:  instanceFieldsMap
     *            key:    fieldName
     *            value:  field
     */
    public Map<String, Map<String, Object>> instanceFieldsCopyArea;

    Object returnValue;         /* hen xian ran --comment by boss */
    Stack<Frame> callstack;     /* you know it  --comment by boss */
    String mainClazzName;       /* bu jie shi   --comment by boss */

    /**
     * code:   simulate x86's CS register
     * pc:     simulate x86's IP register,
     *         '-1' indicate the program finished,
     *         'code[pc]' is the current instruction.
     */
    Object[] regs;
    ast.stm.T[] code;
    int pc;

    Interpreter interpreter;

    public VM(){
        // init
    }

    public void setMainClazzName(String mainClazzName){
        this.mainClazzName = mainClazzName;

        // pc = -1;
        // invoke-static mainClazz->main.
    }

    public void run(){
        while(pc != -1){
            code[pc].accept(interpreter);
        }
    }
}
