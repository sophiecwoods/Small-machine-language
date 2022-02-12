package sml;

/**
 * This class represents an abstract instruction
 *
 * @author ...
 */
public abstract class Instruction {
  private String label;
  private String opcode;

  /**
   * Constructor: an instruction with label l and opcode op
   * (op must be an operation of the language)
   *
   * @param l  label
   * @param op operand
   */
  public Instruction(String l, String op) {
    setLabel(l);
    setOpcode(op);
  }

  public final String getLabel() {
    return label;
  }

  public final void setLabel(String lab) {
    this.label = lab;
  }

  public final String getOpcode() {
    return opcode;
  }

  public final void setOpcode(String op) {
    this.opcode = op;
  }

  public abstract void execute(Machine m);

  // =

  @Override
  public String toString() {
    return getLabel() + ": " + getOpcode();
  }

}
