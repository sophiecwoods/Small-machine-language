package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * This class represents the Add instruction from the language.
 *
 * @author Sophie Woods
 */
public class LinInstruction extends Instruction {

    private String label;
    private int register;
    private int x;

    public LinInstruction(String label, int register, int x){
        super(label, "lin");
        this.register = register;
        this.x = x;
    }

    @Override
    public void execute(Machine m) {
        m.getRegisters().setRegister(register, x);
    }

    @Override
    public String toString() {
        return super.toString() + " " + register;
    }
}
