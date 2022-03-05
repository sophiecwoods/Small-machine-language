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
        System.out.println(m.getRegisters().getRegister(s1));
    }

    @Override
    public String toString() {
        return super.toString() + " " + s1;
    }
}
