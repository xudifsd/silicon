package vm2;

import java.util.Map;

public class MethodArea {
    VM vm;
    /**
     * key:    clazzName
     * value:  Map
     *          key:    fullQualifiedMethodName
     *          value:  vm2.Method
     */
    public Map<String, Map<String, Method>> methods;
}
