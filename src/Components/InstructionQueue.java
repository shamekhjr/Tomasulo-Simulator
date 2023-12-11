package Components;

import java.util.*;

public class InstructionQueue {
    private ArrayList<Instruction> instructions;
    private int currentIndex;
    private HashMap<String, Integer> labels;

    public InstructionQueue() {
        instructions = new ArrayList<>();
        currentIndex = -1;
        labels = new HashMap<>();
    }

    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
        if (instruction.getLabel() != null) {
            labels.put(instruction.getLabel(), instructions.size() - 1);
        }
        if (currentIndex == -1) {
            currentIndex = 0;
        }
    }

    public boolean isEmpty() {
        return instructions.isEmpty();
    }

    public int size() {
        return instructions.size();
    }

    public Instruction getInstructionNoInc() {
        return currentIndex <= instructions.size() - 1 ?instructions.get(currentIndex) : null;
    }

    public void incrementIndex() {
        currentIndex++;
    }

    public Instruction getInstruction() {
        return instructions.get(currentIndex++);
    }

    public HashMap<String, Integer> getLabels() {
        return labels;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void returnToLabel(String label) {
        currentIndex = labels.get(label);
    }

    public void modifyInstruction(int index, Instruction instruction) {
        instructions.set(index, instruction);
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public void print() {
        for (Instruction instruction : instructions) {
            System.out.println(instruction);
        }
    }

    public String forGUI() {
        String s = "Instruction \t Issue \t Exec \t Write \n";
        for (Instruction instruction : instructions) {
            s += instruction.forGui() + "\n";
        }
        return s;
    }
}
