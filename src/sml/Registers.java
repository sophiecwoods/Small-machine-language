package sml;

import java.util.Arrays;

/**
 * This class ....
 * <p>An instance contains 32 registers and methods to access and change them</p>
 *
 * @author ...
 */
public final class Registers {
  private static final int NUMBER_OF_REGISTERS = 32;
  private final int[] registers;

  // Constructor: an instance whose registers are set to 0

  {
    registers = new int[NUMBER_OF_REGISTERS];
  }

  /**
   * Setup the registers for the SML machine.
   */
  public Registers() {
    // set all the register values to zero
    Arrays.fill(registers, 0);
  }

  // Set register i to v.
  // Precondition: 0 <= i <= NUMBER_OF_REGISTERS

  public void setRegister(int i, int v) {
    registers[i] = v;
  }

  public int getRegister(int i) {
    return registers[i];
  }

  private int[] getRegisters(){
    return registers;
  }

//  public void setRegisters(int[] regs) {
//    System.arraycopy(getRegisters(), 0, regs, 0, getRegisters().length);
//  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Registers)) {
      return false;
    }
    final Registers other = (Registers) o;
    if (!other.canEqual(this)) {
      return false;
    }
    return Arrays.equals(getRegisters(), other.getRegisters());
  }

  @Override
  public int hashCode() {
    final int PRIME = 59;
    return PRIME + Arrays.hashCode(getRegisters());
  }

  protected boolean canEqual(Object other) {
    return other instanceof Registers;
  }

  @Override
  public String toString() {
    return " registers=" + Arrays.toString(getRegisters());
  }
}
