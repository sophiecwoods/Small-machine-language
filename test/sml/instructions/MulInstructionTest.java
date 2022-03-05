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

class MulInstructionTest {
    private Machine m;
    private ArrayList<Instruction> instructions;

    @BeforeEach
    void setUp() {
        m = new Machine();
        instructions = new ArrayList<>();

        // test multiply contents of 2 registers both initialised to 0
        // store result in third register also initialised to 0
        Instruction i1 = new MulInstruction("L1", 0, 1, 2);

        // set up for i3
        Instruction i2 = new LinInstruction("L2", 10, 100);
        // test multiply contents of 2 registers both initialised to 0 and store result in third register, set to 100
        Instruction i3 = new MulInstruction("L3", 10, 20, 30);

        // set up for i5
        Instruction i4 = new LinInstruction("L4", 17, -5);
        // test multiply contents of register initialised to 0 and register set to -5
        // and store result in register initialised to 0
        Instruction i5 = new MulInstruction("L5", 15, 16, 17);

        // set up for i8
        Instruction i6 = new LinInstruction("L6", 11, 10);
        Instruction i7 = new LinInstruction("L7", 9, -100);
        // test multiply contents of registers set to 10 and -100 and store result in register initialised to 0
        Instruction i8 = new MulInstruction("L8", 24, 11, 9);

        //set up for i10
        Instruction i9 = new LinInstruction("L9", 31, 7);
        // test multiply contents of the same register and store in same register
        Instruction i10 = new MulInstruction("L10", 31, 31, 31);

        // test multiply contents of the same register and store in a different register
        Instruction i11 = new MulInstruction("L11", 5, 11, 11);

        // set up for i13
        Instruction i12 = new LinInstruction("L12", 22, Integer.MAX_VALUE);
        Instruction i13 = new LinInstruction("L13", 4, 2);
        // test multiply Integer.MaxValue by 2 results in overflow
        Instruction i14 = new MulInstruction("L14", 8, 22, 4);

        // test  multiply instruction stored to register followed by multiply stored to same register
        // overwrites previous multiply
        Instruction i15 = new MulInstruction("L15", 12, 30, 31);
        Instruction i16 = new MulInstruction("L16", 12, 17, 17);

        instructions.addAll(Arrays.asList(i1,i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16));
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
            "15, 0", // after i5
            "24, -1_000", // after i8
            "31, 49", // after i10
            "5, 100", // after i11
            "8, -2", // after i14
            "12, 25", // after i16
    })
    void execute(int register, int value) {
        m.execute();
        assertEquals(value, m.getRegisters().getRegister(register));
    }

    @Test
    void testToString() {
        String s = "L1: mul 0 1 2\n" +
                "L2: lin 10 100\n" +
                "L3: mul 10 20 30\n" +
                "L4: lin 17 -5\n" +
                "L5: mul 15 16 17\n" +
                "L6: lin 11 10\n" +
                "L7: lin 9 -100\n" +
                "L8: mul 24 11 9\n" +
                "L9: lin 31 7\n" +
                "L10: mul 31 31 31\n" +
                "L11: mul 5 11 11\n" +
                "L12: lin 22 2147483647\n" +
                "L13: lin 4 2\n" +
                "L14: mul 8 22 4\n" +
                "L15: mul 12 30 31\n" +
                "L16: mul 12 17 17\n";

        assertEquals(s, m.toString());
    }
}