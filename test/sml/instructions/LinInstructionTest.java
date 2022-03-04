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

class LinInstructionTest {
    private Machine m;
    private ArrayList<Instruction> instructions;

    @BeforeEach
    void setUp() {
        m = new Machine();
        instructions = new ArrayList<>();

        // test store int in register 1
        Instruction i1 = new LinInstruction("L1", 1, 1);

        // test store int in register 31
        Instruction i2 = new LinInstruction("L2", 31, -99);

        // test store int in register and then overwrite it
        Instruction i3 = new LinInstruction("L3", 4, 500);
        Instruction i4 = new LinInstruction("L4", 4, 400);

        // test store max int in register
        Instruction i5 = new LinInstruction("L5", 18, Integer.MAX_VALUE);

        // test store min int in register
        Instruction i6 = new LinInstruction("L6", 23, Integer.MIN_VALUE);

        // test store 0 in register
        Instruction i7 = new LinInstruction("L7", 25, 0);


        instructions.addAll(Arrays.asList(i1,i2, i3, i4, i5, i6, i7));
        m.setProg(instructions);
    }

    @AfterEach
    void tearDown() {
        m = null;
        instructions = null;
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1", // after i1
            "31, -99", // after i2
            "4, 400", // after i3
            "18, 2_147_483_647", // after i4
            "23, -2_147_483_648", // after i5 & i6
            "25, 0" // after i7
    })
    void execute(int register, int value) {
        m.execute();
        assertEquals(value, m.getRegisters().getRegister(register));
    }

    @Test
    void testToString() {
        String s = "L1: lin 1 1\n" +
                "L2: lin 31 -99\n" +
                "L3: lin 4 500\n" +
                "L4: lin 4 400\n" +
                "L5: lin 18 2147483647\n" +
                "L6: lin 23 -2147483648\n" +
                "L7: lin 25 0\n";

        assertEquals(s, m.toString());
    }
}