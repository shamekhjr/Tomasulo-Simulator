package Components;

import java.util.*;

public class InstructionQueue {
    private Queue<Instruction> instructions = new LinkedList<>();

    public InstructionQueue() {

    }

    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    public boolean isEmpty() {
        return instructions.isEmpty();
    }

    public Instruction peekInstruction() {
        return instructions.peek();
    }

    public Instruction getInstruction() {
        return instructions.remove();
    }
}
