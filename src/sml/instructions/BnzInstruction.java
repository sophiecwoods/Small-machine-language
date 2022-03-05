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
        int r1 = m.getRegisters().getRegister(s1);
        // if contents of s1 is not 0
        // find instruction with label2 and set Pc to index of that instruction in prog
        if (r1 != 0) {
            for (int i = 0; i < m.getProg().size(); i++) {
                String ins = m.getProg().get(i).toString();
                int labelEnd = ins.indexOf(":");
                if (ins.substring(0, labelEnd).equals(label2)) {
                    m.setPc(i);
                    break;
                }
            }
        }
    }

    @Override
    public String toString() { return super.toString() + " " + s1 + " " + label2; }
}
