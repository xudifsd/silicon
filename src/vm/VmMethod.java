package vm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.stm.Instruction.ArrayDataDirective;
import ast.stm.Instruction.PackedSwitchDirective;
import ast.stm.Instruction.SparseSwitchDirective;

public class VmMethod {
	public String name;
	public ast.method.Method astMethod;
	public boolean isSystem;
	public boolean isSystemConstructor;
	public Method systemMethod;
	public Constructor<?> systemConstructor;
	public Map<String, Integer> labelMap;

	public VmMethod(String name, ast.method.Method astMethod) {
		this.name = name;
		this.astMethod = astMethod;
		this.isSystem = false;
		getlabelMap(astMethod);
	}

	public VmMethod(String name, java.lang.reflect.Method systemMethod) {
		super();
		this.name = name;
		this.systemMethod = systemMethod;
		this.isSystem = true;
	}

	public VmMethod(String name, Constructor<?> systemConstructor) {
		super();
		this.name = name;
		this.systemConstructor = systemConstructor;
		this.isSystem = true;
		this.isSystemConstructor = true;
	}

	public void getlabelMap(ast.method.Method astMethod) {
		this.labelMap = new HashMap<String, Integer>();
		List<ast.method.Method.Label> labelList = astMethod.labelList;
		if (labelList.size() == 0)
			return;
		Collections.sort(labelList);
		int LabelIndex = 0;
		int stmIndex = 0;
		ast.method.Method.Label currentLabel = labelList.get(LabelIndex++);
		int targetAddr = Integer.parseInt(currentLabel.add);
		int currentAddr = 0;
		String opCode;
		for (stmIndex = 0; stmIndex < astMethod.statements.size(); stmIndex++) {
			ast.stm.T stm = astMethod.statements.get(stmIndex);
			if (stm instanceof PackedSwitchDirective
					|| stm instanceof SparseSwitchDirective
					|| stm instanceof ArrayDataDirective) {
				break;
			}
			while (targetAddr == currentAddr && LabelIndex < labelList.size()) {
				this.labelMap.put(currentLabel.lab, stmIndex);
				if (LabelIndex < labelList.size()) {
					currentLabel = labelList.get(LabelIndex++);
					targetAddr = Integer.parseInt(currentLabel.add);
				}
			}
			try {
				opCode = (String) stm.getClass().getField("op").get(stm);
				currentAddr += Source.instLen.get(opCode);
			} catch (IllegalArgumentException | IllegalAccessException
					| NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}
		for (; stmIndex < astMethod.statements.size(); stmIndex++) {
			ast.stm.T stm = astMethod.statements.get(stmIndex);
			if (!(stm instanceof ast.stm.Instruction.Nop)) {
				this.labelMap.put(currentLabel.lab, stmIndex);
				if (LabelIndex < labelList.size()) {
					currentLabel = labelList.get(LabelIndex++);
					targetAddr = Integer.parseInt(currentLabel.add);
				}
			}
		}
	}
}
