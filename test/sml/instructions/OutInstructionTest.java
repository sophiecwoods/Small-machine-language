package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import sml.Instruction;
import sml.Machine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OutInstructionTest {
    private Machine m;
    private ArrayList<Instruction> instructions;
    private PrintStream standardOut = System.out;
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        m = new Machine();
        instructions = new ArrayList<>();
        System.setOut(new PrintStream(outputStream));

        // test print out contents of register initialised to 0
        Instruction i1 = new OutInstruction("L1", 0);

        // set up for i3
        Instruction i2 = new LinInstruction("L2", 15, 100);
        // test print out contents of register set to 100
        Instruction i3 = new OutInstruction("L3", 15);

        instructions.addAll(Arrays.asList(i1, i2, i3));
        m.setProg(instructions);
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
        m = null;
        instructions = null;
    }

    @Test
    void execute() {
        String output = "0\n15";
        m.execute();
        assertEquals(output, outputStream.toString().trim());
    }

    @Test
    void testToString() {
        String s = "L1: out 0\n" +
                "L2: lin 15 100\n" +
                "L3: out 15\n";
        assertEquals(s, m.toString());
    }
}