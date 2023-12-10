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
            gpRegisters[i] = new Register("R" + i, null);
            fpRegisters[i] = new Register("F" + i, null);
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

    public Register getRegister(String name) {
        if (name.charAt(0) == 'R') {
            return gpRegisters[Integer.parseInt(name.substring(1))];
        } else if (name.charAt(0) == 'F') {
            return fpRegisters[Integer.parseInt(name.substring(1))];
        } else {
            return null;
        }
    }

    public void setRegister(String name, Double value) {
        if (name.charAt(0) == 'R') {
            gpRegisters[Integer.parseInt(name.substring(1))].setValue(value);
        } else if (name.charAt(0) == 'F') {
            fpRegisters[Integer.parseInt(name.substring(1))].setValue(value);
        }
    }

    public void setRegisterTag(String name, String q) {
        if (name.charAt(0) == 'R') {
            gpRegisters[Integer.parseInt(name.substring(1))].setQ(q);
        } else if (name.charAt(0) == 'F') {
            fpRegisters[Integer.parseInt(name.substring(1))].setQ(q);
        }
    }

    public void setGpRegisters(Register[] gpRegisters) {
        this.gpRegisters = gpRegisters;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < NUM_OF_GP_REGISTERS; i++) {
            s += "R" + i + " = " + gpRegisters[i].getValue() + " Q: " + gpRegisters[i].getQ() + "\n";
        }
        s+="\n";
        for (int i = 0; i < NUM_OF_FP_REGISTERS; i++) {
            s += "F" + i + " = " + fpRegisters[i].getValue() + " Q: " + fpRegisters[i].getQ() +"\n";
        }
        return s;
    }
}
