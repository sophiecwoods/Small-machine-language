package sml.instructions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sml.Instruction;
import sml.Machine;
import sml.Translator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static sml.Translator.getTranslatorInst;

/**
 * This class represents the BNZ (branch if not zero) instruction from the language.
 *
 * @author Sophie Woods
 */

@Component("bnzInstruction")
public class BnzInstruction extends Instruction {

    private int register;
    private String label2;

    /*
     * Default constructor checks whether bean injected is AddInstruction and calls mutator to set fields if so
     */
    @Autowired
    public BnzInstruction(String label, String fileName, String opcode) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException {
        super(label,opcode);
        if (opcode.equals("bnz"))setBnzInstruction(fileName);
    }

    public BnzInstruction(String label, int register, String label2){
        super(label, "bnz");
        this.register = register;
        this.label2 = label2;
    }

    /*
     * Mutator method to set the field values
     */
    public void setBnzInstruction(String fileName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Translator translator = getTranslatorInst(fileName);
        // use reflection to access Translator scanInt and scan methods
        Method scanInt = Translator.class.getDeclaredMethod("scanInt");
        scanInt.setAccessible(true);
        Method scan = Translator.class.getDeclaredMethod("scan");
        scan.setAccessible(true);
        this.register = (int) scanInt.invoke(translator);
        this.label2 = (String) scan.invoke(translator);
    }

    @Override
    public void execute(Machine m) {
        int r1 = m.getRegisters().getRegister(register);
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
    public String toString() { return super.toString() + " " + register + " " + label2; }
}
