package Components;

import java.util.*;

public class InstructionQueue {
    private ArrayList<Instruction> instructions;
    int currentIndex;
    HashMap<String, Integer> labels;

    public InstructionQueue() {
        instructions = new ArrayList<>();
        currentIndex = -1;
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

    public Instruction getInstruction() {
        return instructions.get(currentIndex++);
    }

    public void returnToLabel(String label) {
        currentIndex = labels.get(label);
    }
}
