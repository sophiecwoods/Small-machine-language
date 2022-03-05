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

class DivInstructionTest {
    private Machine m;
    private ArrayList<Instruction> instructions;

    @BeforeEach
    void setUp() {
        m = new Machine();
        instructions = new ArrayList<>();

        // set up for i2
        Instruction i1 = new LinInstruction("L1", 2, 5);
        // test divide contents of register initialised to 0 by register set to 5
        // and store result in register initialised to 0
        Instruction i2 = new DivInstruction("L2", 0, 1, 2);

        // set up for i5
        Instruction i3 = new LinInstruction("L3", 3, 20);
        Instruction i4 = new LinInstruction("L4", 4, -100);
        // test divide contents of register initialised to 0 by register set to -100
        // and store result in register initialised to 20
        Instruction i5 = new DivInstruction("L5", 3, 5, 4);

        // set up for i8
        Instruction i6 = new LinInstruction("L6", 8, 25);
        Instruction i7 = new LinInstruction("L7", 9, 2);
        // test divide contents of registers set to 25 by register set to 2
        // store result in register initialised to 0
        Instruction i8 = new DivInstruction("L8", 6, 8, 9);

        // set up for i10
        Instruction i9 = new LinInstruction("L9", 31, 7);
        // test divide contents of the same register and store in same register
        Instruction i10 = new DivInstruction("L10", 31, 31, 31);

        // set up for i13
        Instruction i11 = new LinInstruction("L11", 21, 100_000);
        Instruction i12 = new LinInstruction("L12", 27, -800_000);
        // test divide contents of the same register and store in a different register
        Instruction i13 = new DivInstruction("L13", 21, 27, 27);

        // test  divide instruction stored to register followed by divide stored to same register
        // overwrites previous divide
        Instruction i14 = new DivInstruction("L14", 19, 30, 8);
        Instruction i15 = new DivInstruction("L15", 19, 27, 9);

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
            "0, 0", // after i2
            "3, 0", // after i5
            "6,  12", // after i8
            "31, 1", // after i10
            "21, 1", // after i13
            "19, -400_000", // after i15
    })
    void execute(int register, int value) {
        m.execute();
        assertEquals(value, m.getRegisters().getRegister(register));
    }

    @Test
    public void ArithmeticExceptionThrown() {
        // test divide contents of 2 registers both initialised to 0
        // store result in third register also initialised to 0 throws ArithmeticException
        Instruction i16 = new DivInstruction("L16", 10, 11, 12);
        instructions.add(i16);

        Exception exception = assertThrows(ArithmeticException.class, () -> {
            m.execute();
        });

        String expectedMessage = "/ by zero";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equals(expectedMessage));
    }


    @Test
    void testToString() {
        String s = "L1: lin 2 5\n" +
                "L2: div 0 1 2\n" +
                "L3: lin 3 20\n" +
                "L4: lin 4 -100\n" +
                "L5: div 3 5 4\n" +
                "L6: lin 8 25\n" +
                "L7: lin 9 2\n" +
                "L8: div 6 8 9\n" +
                "L9: lin 31 7\n" +
                "L10: div 31 31 31\n" +
                "L11: lin 21 100000\n" +
                "L12: lin 27 -800000\n" +
                "L13: div 21 27 27\n" +
                "L14: div 19 30 8\n" +
                "L15: div 19 27 9\n";

        assertEquals(s, m.toString());
    }
}