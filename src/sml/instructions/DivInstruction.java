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
 * This class represents the Div (divide) instruction from the language.
 *
 * @author Sophie Woods
 */

@Component("divInstruction")
public class DivInstruction extends Instruction {

    private int register;
    private int s1;
    private int s2;

    /*
     * Default constructor checks whether bean injected is AddInstruction and calls mutator to set fields if so
     */
    @Autowired
    public DivInstruction(String label, String fileName, String opcode) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException {
        super(label,opcode);
        if (opcode.equals("div")) setDivInstruction(fileName);
    }

    public DivInstruction(String label, int register, int s1, int s2){
        super(label, "div");
        this.register = register;
        this.s1 = s1;
        this.s2 = s2;
    }

    /*
     * Mutator method to set the field values
     */
    public void setDivInstruction(String fileName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Translator translator = getTranslatorInst(fileName);
        // use reflection to access Translator scanInt method
        Method scanInt = Translator.class.getDeclaredMethod("scanInt");
        scanInt.setAccessible(true);
        this.register = (int) scanInt.invoke(translator);
        this.s1 = (int) scanInt.invoke(translator);
        this.s2 = (int) scanInt.invoke(translator);
    }

    @Override
    public void execute(Machine m) {
        int r1 = m.getRegisters().getRegister(s1);
        int r2 = m.getRegisters().getRegister(s2);
        m.getRegisters().setRegister(register, r1 / r2);
    }

    @Override
    public String toString() { return super.toString() + " " + register + " " + s1 + " " + s2; }
}
