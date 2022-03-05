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

        // test divide contents of 2 registers both initialised to 0
        // store result in third register also initialised to 0 throws ArithmeticException
        Instruction i1 = new DivInstruction("L1", 0, 1, 2);

        instructions.add(i1);
        m.setProg(instructions);
    }

    @AfterEach
    void tearDown() {
        m = null;
        instructions = null;
    }

    @Test
    void execute() {

    }


    @Test
    public void ArithmeticExceptionThrown() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            m.execute();
        });

        String expectedMessage = "/ by zero";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.equals(expectedMessage));
    }


    @Test
    void testToString() {

    }
}