package sml.instructions;

import sml.Instruction;
import sml.Machine;
import sml.Translator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static sml.Translator.getTranslatorInst;

/**
 * This class represents the Out (print) instruction from the language.
 *
 * @author Sophie Woods
 */

public class OutInstruction extends Instruction {

    private int register;

    /*
     * Default constructor
     */
    public OutInstruction(String label, String fileName, String opcode) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException {
        super(label,opcode);
        if (opcode.equals("out")) setOutInstruction(fileName);
    }

    public OutInstruction(String label, int register){
        super(label, "out");
        this.register = register;

    }

    /*
     * Mutator method to set the field values
     */
    public void setOutInstruction(String fileName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Translator translator = getTranslatorInst(fileName);
        // use reflection to access Translator scanInt method
        Method scanInt = Translator.class.getDeclaredMethod("scanInt");
        scanInt.setAccessible(true);
        this.register = (int) scanInt.invoke(translator);
    }

    @Override
    public void execute(Machine m) {
        System.out.println(m.getRegisters().getRegister(register));
    }

    @Override
    public String toString() {
        return super.toString() + " " + register;
    }
}
