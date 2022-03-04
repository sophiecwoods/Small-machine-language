package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sml.Instruction;
import sml.Machine;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AddInstructionTest {
  private Machine m;
  private ArrayList<Instruction> instructions;

  @BeforeEach
  void setUp() {
    m = new Machine();
    instructions = new ArrayList<>();

    // test add contents of 2 registers both initialised to 0 and store result in third register also initialised to 0
    Instruction i1 = new AddInstruction("L1", 0, 1, 2);

    // set up for i3
    Instruction i2 = new LinInstruction("L2", 10, 100);
    // test add contents of 2 registers both initialised to 0 and store result in third register, set to 100
    Instruction i3 = new AddInstruction("L3", 10, 20, 30);

    // set up for i5
    Instruction i4 = new LinInstruction("L4", 17, -5);
    // test add contents of register initialised to 0 and register set to -5
    // and store result in register initialised to 0
    Instruction i5 = new AddInstruction("L5", 15, 16, 17);

    // set up for i8
    Instruction i6 = new LinInstruction("L6", 11, 100_000);
    Instruction i7 = new LinInstruction("L7", 9, 1);
    // test add contents of registers set to 100_000 and 1 and store result in register initialised to 0
    Instruction i8 = new AddInstruction("L8", 24, 11, 9);

    //set up for i10
    Instruction i9 = new LinInstruction("L9", 31, 90);
    // test add contents of the same register and store in same register
    Instruction i10 = new AddInstruction("L10", 31, 31, 31);

    // test add contents of the same register and store in a different register
    Instruction i11 = new AddInstruction("L11", 5, 11, 11);

    // set up for i13
    Instruction i12 = new LinInstruction("L12", 22, Integer.MAX_VALUE);
    // test add number to Integer.MaxValue results in overflow
    Instruction i13 = new AddInstruction("L13", 8, 22, 9);

    // test  add instruction stored to register followed by add to same register
    // overwrites previous add
    Instruction i14 = new AddInstruction("L14", 12, 30, 31);
    Instruction i15 = new AddInstruction("L15", 12, 17, 17);

    instructions.addAll(Arrays.asList(i1,i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15));
    m.setProg(instructions);
  }

  @AfterEach
  void tearDown() {
    m = null;
    instructions = null;
  }

  @ParameterizedTest
  @CsvSource({
          "0, 0", // after i1
          "10, 0", // after i3
          "15, -5", // after i5
          "24, 100_001", // after i8
          "31, 180", // after i10
          "5, 200_000", // after i11
          "8, -2_147_483_648", // after i13
          "12, -10", // after i15
  })
  void execute(int register, int value) {
    m.execute();
    assertEquals(value, m.getRegisters().getRegister(register));
  }

  @Test
  void testToString() {
    String s = "L1: add 0 1 2\n" +
            "L2: lin 10 100\n" +
            "L3: add 10 20 30\n" +
            "L4: lin 17 -5\n" +
            "L5: add 15 16 17\n" +
            "L6: lin 11 100000\n" +
            "L7: lin 9 1\n" +
            "L8: add 24 11 9\n" +
            "L9: lin 31 90\n" +
            "L10: add 31 31 31\n" +
            "L11: add 5 11 11\n" +
            "L12: lin 22 2147483647\n" +
            "L13: add 8 22 9\n" +
            "L14: add 12 30 31\n" +
            "L15: add 12 17 17\n";

    assertEquals(s, m.toString());
  }
}