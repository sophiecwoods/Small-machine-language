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
 * This class represents the Lin (store) instruction from the language.
 *
 * @author Sophie Woods
 */

@Component("linInstruction")
public class LinInstruction extends Instruction {

    private int register;
    private int s1;

    /*
     * Default constructor checks whether bean injected is AddInstruction and calls mutator to set fields if so
     */
    @Autowired
    public LinInstruction(String label, String fileName, String opcode) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException {
        super(label, opcode);
        if (opcode.equals("lin")) setLinInstruction(fileName);
    }

    public LinInstruction(String label, int register, int s1){
        super(label, "lin");
        this.register = register;
        this.s1 = s1;
    }

    /*
     * Mutator method to set the field values
     */
    public void setLinInstruction(String fileName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Translator translator = getTranslatorInst(fileName);
        // use reflection to access Translator scanInt method
        Method scanInt = Translator.class.getDeclaredMethod("scanInt");
        scanInt.setAccessible(true);
        this.register = (int) scanInt.invoke(translator);
        this.s1 = (int) scanInt.invoke(translator);
    }

    @Override
    public void execute(Machine m) {
        m.getRegisters().setRegister(register, s1);
    }

    @Override
    public String toString() {
        return super.toString() + " " + register + " " + s1;
    }
}
