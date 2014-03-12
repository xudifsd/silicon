package vm2;

import ast.stm.Instruction;

public class Frame {
    Object[] regs;
    Instruction[] code;
    int pc;
}
