package Components;

public class Memory {
    private double[] memory;
    private final int memSize;

    public Memory() {
        memSize = 1024;
        memory = new double[memSize];
        for (int i = 0; i < memSize; i++) {
            memory[i] = 0;
        }
    }

    public Memory(int memSize) {
        this.memSize = memSize;
        memory = new double[this.memSize];
        for (int i = 0; i < this.memSize; i++) {
            memory[i] = 0;
        }
    }

    public double[] getMemory() {
        return memory;
    }

    public double getMemoryItem(int index) {
        return memory[index];
    }

    public void setMemory(double[] memory) {
        this.memory = memory;
    }

    public void setMemoryItem(int index, double value) {
        memory[index] = value;
    }

    public int getMemSize() {
        return memSize;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < memSize; i++) {
            s += "Memory[" + i + "] = " + memory[i] + "\n";
        }
        return s;
    }
}
