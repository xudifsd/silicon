package vm2;

public class ClazzLoader {
    VM vm;
    public ClazzLoader(VM vm){
        this.vm = vm;
    }

    public void loadClazz(String clazzName){
        initClazzArea(clazzName);
        initMethodArea(clazzName);
        initStaticFields(clazzName);
        initInstanceFieldsCopy(clazzName);
    }

    private void initClazzArea(String clazzName){

    }

    private void initMethodArea(String clazzName){

    }

    private void initStaticFields(String clazzName){

    }

    private void initInstanceFieldsCopy(String clazzName){

    }
}
