package Components;

import java.util.*;

public class InstructionQueue {
    private Queue<Instruction> instructions = new LinkedList<>();

    public InstructionQueue() {

    }

    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    public Instruction getInstruction() {
        return instructions.remove();
    }
}
