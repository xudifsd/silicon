package vm2;

import java.util.Map;

public class StaticFieldsArea {
    /**
     * key:    fullQualifiedStaticFieldName
     * value:  field
     */
    public Map<String, Object> staticFields;


    public Object getStaticField(String fieldName){
        // TODO
        return null;
    }

    public void setStaticField(String fieldName, Object field){
        this.staticFields.put(fieldName, field);
    }
}
