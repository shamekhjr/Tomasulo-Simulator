package Components;

public class RegisterFile {
    static final int NUM_OF_GP_REGISTERS = 32;
    static final int NUM_OF_FP_REGISTERS = 32;
    private Register[] gpRegisters;
    private Register[] fpRegisters;

    public RegisterFile() {
        gpRegisters = new Register[NUM_OF_GP_REGISTERS];
        fpRegisters = new Register[NUM_OF_FP_REGISTERS];
        for (int i = 0; i < NUM_OF_FP_REGISTERS; i++) {
            gpRegisters[i] = new Register("R" + i, "0");
            fpRegisters[i] = new Register("F" + i, "0");
        }
    }

    public RegisterFile(Register[] gpRegisters, Register[] fpRegisters) {
        this.gpRegisters = gpRegisters;
        this.fpRegisters = fpRegisters;
    }

    public Register[] getGpRegisters() {
        return gpRegisters;
    }

    public Register[] getFpRegisters() {
        return fpRegisters;
    }

    public Register getGpRegister(int index) {
        return gpRegisters[index];
    }

    public Register getFpRegister(int index) {
        return fpRegisters[index];
    }

    public void setGpRegisters(Register[] gpRegisters) {
        this.gpRegisters = gpRegisters;
    }
}
