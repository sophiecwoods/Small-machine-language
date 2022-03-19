package sml;

import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import sml.instructions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

import static java.lang.Class.forName;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author Sophie Woods
 */

public final class Translator {

    private static final String PATH = "";

    // word + line is the part of the current line that's not yet processed
    // word has no whitespace
    // If word and line are not empty, line begins with whitespace
    private final String fileName; // source file of SML code
    private String line = "";

    // create a label field which can be accessed by Instruction classes via the getLabel() method
    private String label;

    // create an opcode field which can be accessed by Instruction classes via the getOpcode() method
    private String opcode;

    // create a Singleton version of Translator
    private static Translator instance = null;

    private Translator(String file) { fileName = PATH + file; }

    public static Translator getTranslatorInst(String file) {
        if (instance == null) {
            instance = new Translator(file);
        }
        return instance;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public boolean readAndTranslate(Labels lab, List<Instruction> prog) throws InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException {
        try (var sc = new Scanner(new File(fileName), "UTF-8")) {
            // Scanner attached to the file chosen by the user
            // The labels of the program being translated
            lab.reset();
            // The program to be created
            prog.clear();

            try {
                line = sc.nextLine();
            } catch (NoSuchElementException ioE) {
                return false;
            }

            // Each iteration processes line and reads the next input line into "line"
            while (line != null) {
                // Store the label in label
                var label = scan();

                if (label.length() > 0) {
                    var ins = getInstruction(label);
                    if (ins != null) {
                        lab.addLabel(label);
                        prog.add(ins);
                    }
                }

                try {
                    line = sc.nextLine();
                } catch (NoSuchElementException ioE) {
                    return false;
                }
            }
        } catch (IOException ioE) {
            System.err.println("File: IO error " + ioE);
            return false;
        }
        return true;
    }

    // The input line should consist of an SML instruction, with its label already removed.
    // Translate line into an instruction with label "label" and return the instruction
    public Instruction getInstruction(String label) throws InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException {

        this.label = label;

        if (line.equals("")) {
            return null;
        }

        this.opcode = scan();

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(SmlConfig.class);
        return (Instruction) context.getBean(opcode + "Instruction");

    }

    /*
     * Return the first word of line and remove it from line. If there is no word, return ""
     */
    private String scan() {
        line = line.trim();
        if (line.length() == 0) {
            return "";
        }

        int i = 0;
        while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
            i = i + 1;
        }
        String word = line.substring(0, i);
        line = line.substring(i);
        return word;
    }

    // Return the first word of line as an integer. If there is any error, return the maximum int
    private int scanInt() {
        String word = scan();
        if (word.length() == 0) {
            return Integer.MAX_VALUE;
        }

        try {
            return Integer.parseInt(word);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }

    // accessor method for Bean creation
    public String getLabel() {
        return this.label;
    }

    // accessor method for Bean creation
    public String getFileName() {
        return this.fileName;
    }

    // accessor method for Bean creation
    public String getOpcode() {
        return this.opcode;
    }
}
