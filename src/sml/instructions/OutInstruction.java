package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * This class represents the Out (print) instruction from the language.
 *
 * @author Sophie Woods
 */
public class OutInstruction extends Instruction {

    private String label;
    private int s1;


    public OutInstruction(String label, int s1){
        super(label, "out");
        this.s1 = s1;

    }

    @Override
    public void execute(Machine m) {
    }

    @Override
    public String toString() {
        return null;
    }
}
