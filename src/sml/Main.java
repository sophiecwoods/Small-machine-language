package sml;

import java.nio.charset.Charset;

public class Main {
  /**
   * Initialises the system and executes the program.
   *
   * @param args name of the file containing the program text.
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Incorrect number of arguments - Machine <file> - required");
      System.exit(-1);
    }

    Machine m = new Machine();
    Translator t = new Translator(args[0]);
    t.readAndTranslate(m.getLabels(), m.getProg());

    System.out.println("Here is the program; it has " + m.getProg().size() + " instructions.");
    System.out.println(m);

    System.out.println("Beginning program execution.");
    m.execute();
    System.out.println("Ending program execution.");

    System.out.println("Values of registers at program termination:" + m.getRegisters() + ".");
  }
}
