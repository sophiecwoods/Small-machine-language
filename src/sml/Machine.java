package sml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the machine, the context in which programs run.
 * <p>
 * An instance contains 32 registers and methods to access and change them.
 *
 * @author KLM
 */
public final class Machine {

    // The labels in the SML program, in the order in which they appear (are defined) in the program
    private Labels labels;

    // The SML program, consisting of prog.size() instructions, each of class Instruction (or one of its subclasses)
    private List<Instruction> prog;

    // The registers of the SML machine
    private Registers registers;

    // The program counter; it contains the index (in prog) of the next instruction to be executed.
    private int pc;

    {
        labels = new Labels();
        prog = new ArrayList<>();
        pc = 0;
    }

    /**
     * String representation of the program under execution.
     *
     * @return pretty formatted version of the code.
     */
    @Override
    public String toString() {
        var s = new StringBuffer();
        for (var i = 0; i != getProg().size(); i++) {
            s.append(getProg().get(i)).append("\n");
        }
        return s.toString();
    }

    /**
     * Execute the program in prog, beginning at instruction 0.
     * Precondition: the program and its labels have been store properly.
     */
    public void execute() {
        setPc(0);
        setRegisters(new Registers());
        while (getPc() < getProg().size()) {
            Instruction ins = getProg().get(getPc());
            setPc(getPc() + 1);
            ins.execute(this); // so convoluted
        }
    }

    public Labels getLabels() {
        return this.labels;
    }

    public void setLabels(Labels labs) {
        this.labels = labs;
    }

    public List<Instruction> getProg() {
        return this.prog;
    }

    public void setProg(List<Instruction> prg) {
        this.prog = prg;
    }

    public Registers getRegisters() {
        return this.registers;
    }

    public void setRegisters(Registers regs) {
        this.registers = regs;
    }

    public int getPc() {
        return this.pc;
    }

    public void setPc(int programCounter) {
        this.pc = programCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Machine)) {
            return false;
        }
        final Machine other = (Machine) o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object thislabels = this.labels;
        final Object otherlabels = other.labels;
        if (!Objects.equals(thislabels, otherlabels)) {
            return false;
        }
        final Object thisprog = this.prog;
        final Object otherprog = other.prog;
        if (!Objects.equals(thisprog, otherprog)) {
            return false;
        }
        final Object thisregisters = this.registers;
        final Object otherregisters = other.registers;
        if (!Objects.equals(thisregisters, otherregisters)) {
            return false;
        }
        return this.pc == other.pc;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object labs = this.labels;
        result = result * PRIME + (labs == null ? 0 : labs.hashCode());
        final Object prg = this.prog;
        result = result * PRIME + (prg == null ? 0 : prg.hashCode());
        final Object regs = this.registers;
        result = result * PRIME + (regs == null ? 0 : regs.hashCode());
        result = result * PRIME + this.pc;
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Machine;
    }
}
