package sml.instructions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Machine;
import sml.Translator;

import static org.junit.jupiter.api.Assertions.*;

class AddInstructionTest {
  private Machine m;
  private Translator t;

  @BeforeEach
  void setUp() {
    m = new Machine();
    //...
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void execute() {
  }

  @Test
  void testToString() {
  }
}