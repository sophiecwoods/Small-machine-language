package sml;

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
 * @author ...
 */
public final class Translator {

    private static final String PATH = "";

    // word + line is the part of the current line that's not yet processed
    // word has no whitespace
    // If word and line are not empty, line begins with whitespace
    private final String fileName; // source file of SML code
    private String line = "";

    public Translator(String file) {
        fileName = PATH + file;
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
        int s1; // Possible operands of the instruction
        int s2;
        int r;
        String lbl;

        if (line.equals("")) {
            return null;
        }
        var opCode = scan();

       Class<?> c = getInstructionClass(opCode);
       Class[] params = getConstructorParamTypes(c);
       Object[] arguments = getConstructorArguments(c, label);
       // create a new Instruction object with the right constructor based on the parameters and arguments
        // identified via reflection in 3 methods above
       Instruction ins = (Instruction) c.getDeclaredConstructor(params).newInstance(arguments);

       return ins;
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

    /*
     * Find and return the class given by the opCode using reflection. If no class is found return null.
     */
    public Class<?> getInstructionClass(String opCode) {
        // convert the opCode into a String with syntax matching the name of the class
        String className = "sml.instructions." + opCode.substring(0, 1).toUpperCase()
                + opCode.substring(1) + "Instruction";
        Class<?> c = null;
        try {
            c = forName(className);
        } catch (ClassNotFoundException e) {
            System.err.println("Unknown instruction: " + opCode);
        }
        return c;
    }

    /*
     * Return the parameter types of the constructor of the Instruction Class
     */
    public Class[] getConstructorParamTypes(Class<?> c) {
        Constructor<?>[] constructors = c.getConstructors();
        Class<?>[] paramTypes = null;
        for (Constructor<?> con : constructors) {
            paramTypes = con.getParameterTypes();
        }
        return paramTypes;
    }

    /*
     * Return the arguments to pass into the constructor of the Instruction Class
     */
    public Object[] getConstructorArguments(Class<?> c, String label)  {
        Constructor<?>[] constructors = c.getConstructors();
        Object[] arguments = null;

        for (Constructor<?> con : constructors) {
          Parameter[] params = con.getParameters();
          Class<?>[] paramTypes = con.getParameterTypes();
          arguments = new Object[params.length];
          arguments[0] = label;

          for (int i = 1; i < params.length; i++) {
              // check whether argument should be a String or Integer based on parameters
              // use scan and scanInt methods to process arguments accordingly
              if(paramTypes[i] == String.class) {
                  arguments[i] = scan();
              }
              else {
                  arguments[i] = scanInt();
              }
          }
        }
        return arguments;
    }
}