package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
    // test add contents of 2 registers both initialised to 0 and store result in third register also initialised to 0
    Instruction i1 = new AddInstruction("L1", 0, 1, 2);

    // test add contents of 2 registers both initialised to 0 and store result in third register, set to 100
    Instruction i2 = new AddInstruction("L2", 10, 20, 30);

    ArrayList<Instruction> instructions = new ArrayList<>();
    instructions.add(i1);
    instructions.add(i2);

    m.setProg(instructions);
  }

  @AfterEach
  void tearDown() {
  }

  @ParameterizedTest
  @CsvSource({
          "0, 0",
          "10, 0",
  })
  void execute(int register, int value) {
    m.execute();
    // set register to test i2
    m.getRegisters().setRegister(10, 100);
    m.execute();
    assertEquals(value, m.getRegisters().getRegister(register));
  }

  @Test
  void testToString() {
    String s = "L1: add 0 1 2\n" +
            "L2: add 10 20 30\n";
    assertEquals(s, m.toString());
  }
}