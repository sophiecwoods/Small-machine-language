package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Translator;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AddInstructionTest {
  private Machine m;
  private Translator t;

  @BeforeEach
  void setUp() {
    m = new Machine();
    Instruction i1 = new AddInstruction("L1", 0, 1, 2);
    ArrayList<Instruction> instructions = new ArrayList<>();
    instructions.add(i1);
    m.setProg(instructions);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void execute() {
    m.execute();
    assertTrue(m.getRegisters().getRegister(0) == 3);
  }

  @Test
  void testToString() {
    String s = "L1: add 0 1 2\n";
    assertEquals(m.toString(), s);
  }
}