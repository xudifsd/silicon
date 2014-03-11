package vm;

import java.util.HashMap;

public class Vm
{
  public HashMap<String, VmClass> loadedClasses;
  public CallStack callStack;
  public Object heap;
  
}
