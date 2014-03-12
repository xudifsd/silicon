package vm2;

public class ClazzLoader {
    VM vm;
    public ClazzLoader(VM vm){
        this.vm = vm;
    }

    public void loadClazz(String clazzName){
        if(vm.clazzArea.isLoaded(clazzName)){
            return;
        }


        ast.classs.Class clazz = null; // TODO
        initClazzArea(clazz);
        initMethodArea(clazz);
        initStaticFields(clazz);
        initInstanceFieldsCopy(clazz);
    }

    private void getASTClass(String clazzName){

    }

    private void initClazzArea(ast.classs.Class clazz){
    }

    private void initMethodArea(ast.classs.Class clazz){
    }

    private void initStaticFields(ast.classs.Class clazz){

    }

    private void initInstanceFieldsCopy(ast.classs.Class clazz){

    }
}
