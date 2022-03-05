package sml.instructions;

import org.junit.jupiter.params.provider.ValueSource;
import sml.Instruction;
import sml.Machine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BnzInstructionTest {
    private Machine m;
    private ArrayList<Instruction> instructions;

    @BeforeEach
    void setUp() {
        m = new Machine();
        instructions = new ArrayList<>();

        // test no branch as register 15 is instantiated to 0
        Instruction i1 = new LinInstruction("L1", 0, 2);
        Instruction i2 = new MulInstruction("L2", 0, 0, 0);
        Instruction i3 = new BnzInstruction("L3", 15, "L2");

        // test branch as register 11 is initially 3 and then decremented to 0
        Instruction i4 = new LinInstruction("L4", 10, 2);
        Instruction i5 = new LinInstruction("L5", 11, 3);
        Instruction i6 = new LinInstruction("L6", 12, 1);
        Instruction i7 = new SubInstruction("L7", 11, 11, 12);
        Instruction i8 = new MulInstruction("L8", 10, 10, 10);
        Instruction i9 = new BnzInstruction("L9",11, "L7");

        instructions.addAll(Arrays.asList(i1, i2, i3, i4, i5, i6, i7, i8, i9));
        m.setProg(instructions);

    }

    @AfterEach
    void tearDown() {
        m = null;
        instructions = null;
    }

    @ParameterizedTest
    @CsvSource({
            "0, 4", // test contents of register 0 after execution is 4 (no branch)
            "10, 256" // test contents of register 10 after execution is 256 (branched twice)
    })
    void execute(int register, int regContent) {
        m.execute();
        assertEquals(regContent, m.getRegisters().getRegister(register));
    }

    @Test
    void testToString() {
        String s = "L1: lin 0 2\n" +
                "L2: mul 0 0 0\n" +
                "L3: bnz 15 L2\n" +
                "L4: lin 10 2\n" +
                "L5: lin 11 3\n" +
                "L6: lin 12 1\n" +
                "L7: sub 11 11 12\n" +
                "L8: mul 10 10 10\n" +
                "L9: bnz 11 L7\n";
        assertEquals(s, m.toString());
    }
}