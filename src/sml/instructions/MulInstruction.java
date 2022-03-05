package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * This class represents the Add instruction from the language.
 *
 * @author Sophie Woods
 */
public class MulInstruction extends Instruction {

    private String label;
    private int register;
    private int s1;
    private int s2;

    public MulInstruction(String label, int register, int s1, int s2){
        super(label, "mul");
        this.register = register;
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public void execute(Machine m) {

    }

    @Override
    public String toString() { return null; }
}
