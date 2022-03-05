package sml.instructions;

import sml.Instruction;
import sml.Machine;

/**
 * This class represents the BNZ (branch if not zero) instruction from the language.
 *
 * @author Sophie Woods
 */
public class BnzInstruction extends Instruction {

    private String label1;
    private int s1;
    private String label2;

    public BnzInstruction(String label1, int s1, String label2){
        super(label1, "bnz");
        this.s1 = s1;
        this.label2 = label2;
    }

    @Override
    public void execute(Machine m) {

    }

    @Override
    public String toString() { return null; }
}
