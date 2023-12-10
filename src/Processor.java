import Components.*;
import Enums.Operation;

import java.util.*;
import java.util.regex.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Processor {
    String log;
    AddSubReservationStation addSubReservationStation;
    MulDivReservationStation mulDivReservationStation;
    LoadStoreBuffers loadStoreBuffers;
    InstructionQueue instructionQueue;
    Memory memory;
    RegisterFile registerFile;
    Bus bus;
    int Mul_DLatency;
    int Div_DLatency;
    int Add_DLatency;
    int Sub_DLatency;
    int DAddLatency;
    int SubILatency;
    int MemLatency;
    int cycleCounter;
    final int NON_IMMEDIATE = -404404404;
    boolean stall;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("============================================");
        System.out.println("    Welcome to the Tomasulo Simulator!      ");
        System.out.println("============================================");

        Processor processor = getProcessorSetup();

        // parse the code
        processor.codeParser();

        System.out.println("--------------------------------------------");
        System.out.println("    Tomasulo Simulator is now running!      ");
        System.out.println("--------------------------------------------");


        while (!(processor.instructionQueue.getCurrentIndex() >= processor.instructionQueue.size() && processor.addSubReservationStation.isEmpty() && processor.mulDivReservationStation.isEmpty() && processor.loadStoreBuffers.isEmpty())) {
            System.out.println("Cycle " + processor.cycleCounter);

            processor.issue();
            processor.execute();
            processor.writeBack();

            System.out.println("Add/Sub Reservation Station -----------------");
            System.out.println(processor.addSubReservationStation);
            System.out.println("--------------------------------------------");

            System.out.println("Mul/Div Reservation Station -----------------");
            System.out.println(processor.mulDivReservationStation);
            System.out.println("--------------------------------------------");

            System.out.println("Register File ------------------------------");
            System.out.println(processor.registerFile);
            System.out.println("--------------------------------------------");

            System.out.println("Memory -------------------------------------");
            System.out.println(processor.memory);
            System.out.println("--------------------------------------------");

            System.out.println("Load/Store Buffers -------------------------");
            System.out.println(processor.loadStoreBuffers);
            System.out.println("--------------------------------------------");

            System.out.println("Instruction Queue --------------------------");
            processor.instructionQueue.print();
            System.out.println("--------------------------------------------");
            processor.cycleCounter++;
            System.out.println("============================================");
        }

        //processor.instructionQueue.print();

    }

    public Processor() {
        log = "";
        addSubReservationStation = new AddSubReservationStation();
        mulDivReservationStation = new MulDivReservationStation();
        loadStoreBuffers = new LoadStoreBuffers();
        instructionQueue = new InstructionQueue();
        memory = new Memory();
        registerFile = new RegisterFile();
        bus = new Bus();
        Mul_DLatency = 1;
        Div_DLatency = 1;
        Add_DLatency = 1;
        Sub_DLatency = 1;
        DAddLatency = 1;
        SubILatency = 1;
        MemLatency = 1;
        cycleCounter = 1;
        stall = false;
    }

    public Processor(int addSubSize, int mulDivSize, int loadBufferSize, int storeBufferSize, int memorySize, int Mul_DLatency, int Div_DLatency, int Add_DLatency, int Sub_DLatency, int DAddLatency, int SubILatency, int MemLatency) {
        this.log = "";
        this.addSubReservationStation = new AddSubReservationStation(addSubSize);
        this.mulDivReservationStation = new MulDivReservationStation(mulDivSize);
        this.loadStoreBuffers = new LoadStoreBuffers(loadBufferSize, storeBufferSize);
        this.instructionQueue = new InstructionQueue();
        this.memory = new Memory(memorySize);
        this.registerFile = new RegisterFile();
        this.bus = new Bus();
        this.Mul_DLatency = Mul_DLatency;
        this.Div_DLatency = Div_DLatency;
        this.Add_DLatency = Add_DLatency;
        this.Sub_DLatency = Sub_DLatency;
        this.DAddLatency = DAddLatency;
        this.SubILatency = SubILatency;
        this.MemLatency = MemLatency;
        this.cycleCounter = 1;
        this.stall = false;
    }

    public Processor(int addSubSize, int mulDivSize, int loadBufferSize, int storeBufferSize, Memory memory, RegisterFile registerFile, int Mul_DLatency, int Div_DLatency, int Add_DLatency, int Sub_DLatency, int DAddLatency, int SubILatency, int MemLatency) {
        this.log = "";
        this.addSubReservationStation = new AddSubReservationStation(addSubSize);
        this.mulDivReservationStation = new MulDivReservationStation(mulDivSize);
        this.loadStoreBuffers = new LoadStoreBuffers(loadBufferSize, storeBufferSize);
        this.instructionQueue = new InstructionQueue();
        this.memory = memory;
        this.registerFile = registerFile;
        this.bus = new Bus();
        this.Mul_DLatency = Mul_DLatency;
        this.Div_DLatency = Div_DLatency;
        this.Add_DLatency = Add_DLatency;
        this.Sub_DLatency = Sub_DLatency;
        this.DAddLatency = DAddLatency;
        this.SubILatency = SubILatency;
        this.MemLatency = MemLatency;
        this.cycleCounter = 1;
        this.stall = false;
    }

    public Processor(int addSubSize, int mulDivSize, int loadBufferSize, int storeBufferSize, Memory memory, int Mul_DLatency, int Div_DLatency, int Add_DLatency, int Sub_DLatency, int DAddLatency, int SubILatency, int MemLatency) {
        this.log = "";
        this.addSubReservationStation = new AddSubReservationStation(addSubSize);
        this.mulDivReservationStation = new MulDivReservationStation(mulDivSize);
        this.loadStoreBuffers = new LoadStoreBuffers(loadBufferSize, storeBufferSize);
        this.instructionQueue = new InstructionQueue();
        this.memory = memory;
        this.registerFile = new RegisterFile();
        this.bus = new Bus();
        this.Mul_DLatency = Mul_DLatency;
        this.Div_DLatency = Div_DLatency;
        this.Add_DLatency = Add_DLatency;
        this.Sub_DLatency = Sub_DLatency;
        this.DAddLatency = DAddLatency;
        this.SubILatency = SubILatency;
        this.MemLatency = MemLatency;
        this.cycleCounter = 1;
        this.stall = false;
    }

    public Processor(int addSubSize, int mulDivSize, int loadBufferSize, int storeBufferSize, int memorySize, RegisterFile registerFile, int Mul_DLatency, int Div_DLatency, int Add_DLatency, int Sub_DLatency, int DAddLatency, int SubILatency, int MemLatency) {
        this.log = "";
        this.addSubReservationStation = new AddSubReservationStation(addSubSize);
        this.mulDivReservationStation = new MulDivReservationStation(mulDivSize);
        this.loadStoreBuffers = new LoadStoreBuffers(loadBufferSize, storeBufferSize);
        this.instructionQueue = new InstructionQueue();
        this.memory = new Memory(memorySize);
        this.registerFile = registerFile;
        this.bus = new Bus();
        this.Mul_DLatency = Mul_DLatency;
        this.Div_DLatency = Div_DLatency;
        this.Add_DLatency = Add_DLatency;
        this.Sub_DLatency = Sub_DLatency;
        this.DAddLatency = DAddLatency;
        this.SubILatency = SubILatency;
        this.MemLatency = MemLatency;
        this.cycleCounter = 1;
        this.stall = false;
    }

    private OperandTuple readArithOperands(Instruction instruction) {
        OperandTuple operands = new OperandTuple();

        // group similar instructions
        //fpop, dadd 3
        //i src only
        //bnez src only

        if (instruction.isFPop() || instruction.getOperation() == Operation.DADD) {
            // get the operands from the reservation station
            String src1 = instruction.getSourceOperand();
            String src2 = instruction.getTargetOperand();

            Register regSrc1 = registerFile.getRegister(src1);
            Register regSrc2 = registerFile.getRegister(src2);

            if (regSrc1.getQ() == null) { // src1 is ready
                operands.setVj(regSrc1.getValue());
//            } else if (bus.isPopulated() && bus.getTag().equals(regSrc1.getQ())){
//                operands.setVj(bus.getValue());
            } else {
                operands.setQj(regSrc1.getQ());
            }

            if (regSrc2.getQ() == null) { // src2 is ready
                operands.setVk(regSrc2.getValue());
//            } else if (bus.isPopulated() && bus.getTag().equals(regSrc2.getQ())) {
//                operands.setVk(bus.getValue());
            } else {
                operands.setQk(regSrc2.getQ());
            }

        } else if (instruction.getOperation() == Operation.ADDI || instruction.getOperation() == Operation.SUBI) { // immediate instruction
            // get the operands from the reservation station
            String src1 = instruction.getSourceOperand();
            Register regSrc1 = registerFile.getRegister(src1);

            if (regSrc1.getQ() == null) { // src1 is ready
                operands.setVj(regSrc1.getValue());
//            } else if (bus.isPopulated() && bus.getTag().equals(regSrc1.getQ())) {
//                operands.setVj(bus.getValue());
            } else {
                operands.setQj(regSrc1.getQ());
            }

            operands.setVk((double) instruction.getImmediateValue());
        }

        return operands;
    }

    public void issue() {
        // get the instruction
        Instruction instruction = instructionQueue.getInstructionNoInc();

        if (instruction == null) { // no instruction to issue
            return;
        }

        if (stall) { // stalled from branch
            System.out.println("Instruction (" + instruction.getInstructionString() + ") is stalled");
            return;
        }

        System.out.println("Trying to issue Instruction (" + instruction.getInstructionString() + ")");

        // check instruction type
        Operation currOpr = instruction.getOperation();
        boolean isIssued = false;
        ReservationStationSlot assignedSlot = null;
        LoadStoreSlot assignedLoadStoreSlot = null;

        switch (currOpr) {
            // ADD_D, SUB_D, MUL_D, DIV_D, L_D, S_D, BNEZ, DADD, ADDI, SUBI
            case ADD_D, ADDI, SUB_D, SUBI, DADD -> {
                // check if there is an empty slot in the add/sub reservation station
                if (addSubReservationStation.hasFreeStations()) {
                    // add the instruction to the add/sub reservation station
                    OperandTuple operands = readArithOperands(instruction);

                    // add issue cycle to instruction
                    instruction.setIssueCycle(cycleCounter);
                    instruction.setExecutionStartCycle(null);
                    instruction.setExecutionEndCycle(null);
                    instruction.setPublishCycle(null);

                    assignedSlot = addSubReservationStation.addInstruction(instruction, operands.getVj(), operands.getVk(), operands.getQj(), operands.getQk());
                    isIssued = true;
                }
            }

            case MUL_D, DIV_D -> {
                // check if there is an empty slot in the mul/div reservation station
                if (mulDivReservationStation.hasFreeStations()) {
                    OperandTuple operands = readArithOperands(instruction);

                    // add issue cycle to instruction
                    instruction.setIssueCycle(cycleCounter);
                    instruction.setExecutionStartCycle(null);
                    instruction.setExecutionEndCycle(null);
                    instruction.setPublishCycle(null);

                    // add the instruction to the mul/div reservation station
                    assignedSlot = mulDivReservationStation.addInstruction(instruction, operands.getVj(), operands.getVk(), operands.getQj(), operands.getQk());
                    isIssued = true;
                }
            }

            case L_D -> {
                // check if there is an empty slot in the load/store buffers
                if (!loadStoreBuffers.isLoadBufferFull()) {
                    // add issue cycle to instruction
                    instruction.setIssueCycle(cycleCounter);
                    instruction.setExecutionStartCycle(null);
                    instruction.setExecutionEndCycle(null);
                    instruction.setPublishCycle(null);

                    // add the instruction to the load/store buffers
                    assignedLoadStoreSlot = loadStoreBuffers.addLoadInstruction(instruction);
                    isIssued = true;
                }
            }

            case S_D -> {
                // check if there is an empty slot in the load/store buffers
                if (!loadStoreBuffers.isStoreBufferFull()) {
                    // add the instruction to the load/store buffers
                    String src1 = instruction.getSourceOperand();
                    Register regSrc1 = registerFile.getRegister(src1);

                    // add issue cycle to instruction
                    instruction.setIssueCycle(cycleCounter);
                    instruction.setExecutionStartCycle(null);
                    instruction.setExecutionEndCycle(null);
                    instruction.setPublishCycle(null);

                    if (regSrc1.getQ() == null) { // src1 is ready
                        loadStoreBuffers.addStoreInstruction(instruction, regSrc1.getValue(), null);
                    } else if (bus.isPopulated() && bus.getTag().equals(regSrc1.getQ())) {
                        loadStoreBuffers.addStoreInstruction(instruction, bus.getValue(), null);
                    } else {
                        loadStoreBuffers.addStoreInstruction(instruction, null, regSrc1.getQ());
                    }

                    isIssued = true;
                }
            }

            case BNEZ -> {
                // check if there is an empty slot in the add/sub reservation station
                if (addSubReservationStation.hasFreeStations()) {
                    String src1 = instruction.getSourceOperand();
                    Register regSrc1 = registerFile.getRegister(src1);

                    // add issue cycle to instruction
                    instruction.setIssueCycle(cycleCounter);
                    instruction.setExecutionStartCycle(null);
                    instruction.setExecutionEndCycle(null);
                    instruction.setPublishCycle(null);

                    if (regSrc1.getQ() == null) { // src1 is ready
                        addSubReservationStation.addInstruction(instruction, regSrc1.getValue(), null, null, null); //qJ howa el mohem
                    } else if (bus.isPopulated() && bus.getTag().equals(regSrc1.getQ())) {
                        addSubReservationStation.addInstruction(instruction, bus.getValue(), null, null, null); //qK howa el mohem
                    } else {
                        addSubReservationStation.addInstruction(instruction, null, null, regSrc1.getQ(), null); //qJ howa el mohem
                    }

                    isIssued = true;
                    stall = true;
                }
            }
        }

        if (isIssued) {
            instructionQueue.incrementIndex();
            updateRegisterQ(instruction, assignedSlot, assignedLoadStoreSlot);
            System.out.println("Instruction (" + instruction.getInstructionString() + ") is issued");
        } else {
            System.out.println("Instruction (" + instruction.getInstructionString() + ") could not be issued");
        }

    }

    private void updateRegisterQ(Instruction instruction, ReservationStationSlot assignedSlot, LoadStoreSlot assignedLoadStoreSlot) {
        if (assignedSlot != null) {
            String dest = instruction.getDestinationOperand();
            registerFile.getRegister(dest).setQ(assignedSlot.getTag());
        } else if (assignedLoadStoreSlot != null) {
            String dest = instruction.getDestinationOperand();
            registerFile.getRegister(dest).setQ(assignedLoadStoreSlot.getTag());
        }
    }

    public void execute() {
        // check if operands ready

        for(ReservationStationSlot e : addSubReservationStation.getAddSubReservationStationSlots()) {
            executionLoopOperations(e);
        }
        for(ReservationStationSlot e : mulDivReservationStation.getMulDivReservationStationSlots()) {
            executionLoopOperations(e);
        }
        for(LoadStoreSlot e : loadStoreBuffers.getLoadSlots()) {
            executionLoopOperations(e);
        }
        for (LoadStoreSlot e : loadStoreBuffers.getStoreSlots()) {
            executionLoopOperations(e);
        }

    }

    private void executionLoopOperations(LoadStoreSlot e) {
        if(e.isBusy() && e.getInstruction().getIssueCycle() < cycleCounter) {
            if(!e.isReady()) {
                e.updateReady();

                if(e.isReady()) {
                    executeCycle(e);
                } else System.out.println("Instruction (" + e.getInstruction().getInstructionString() + ")  in slot "+ e.getTag()+" is not ready to execute yet, waiting for operands: " + e.getQ());
            }
            else if(e.isReady() && !e.isFinished()) {
                executeCycle(e);
            }
        }

    }

    private void executeCycle(LoadStoreSlot e) {
        if (e.getInstruction().getIssueCycle() < cycleCounter) {
            if (e.getInstruction().getExecutionStartCycle() == null) {
                e.getInstruction().setExecutionStartCycle(cycleCounter);
                //instructionQueue.modifyInstruction(e.getInstruction().getIndex(), e.getInstruction());
            }
            e.decrementTimeLeft();
            if (e.getTimeLeft() == 0) {
                int effectiveAddress = e.getInstruction().getEffectiveAddress();
                if (e.isLoad()) { // no exec in load
                    System.out.println("SETTING RESULT TO " + memory.getMemoryItem(effectiveAddress));
                    e.setResult(memory.getMemoryItem(effectiveAddress));
                    e.getInstruction().setResult(memory.getMemoryItem(effectiveAddress));

                } else {

                    e.setResult(e.getV());
                    e.getInstruction().setResult(e.getV());
                }
                e.setFinished(true);
                e.getInstruction().setExecutionEndCycle(cycleCounter);
                instructionQueue.modifyInstruction(e.getInstruction().getIndex(), e.getInstruction());
                System.out.println("Instruction (" + e.getInstruction().getInstructionString() + ")  in slot " + e.getTag() + " has finished executing");
            } else
                System.out.println("Instruction (" + e.getInstruction().getInstructionString() + ")  in slot " + e.getTag() + " is executing, " + e.getTimeLeft() + " cycles left");
        }
    }

    private void executeCycle(ReservationStationSlot e) {
        if (e.getInstruction().getIssueCycle() < cycleCounter) {
            if (e.getInstruction().getExecutionStartCycle() == null) {
                e.getInstruction().setExecutionStartCycle(cycleCounter);
                //instructionQueue.modifyInstruction(e.getInstruction().getIndex(), e.getInstruction());
            }

            e.decrementTimeLeft();
            if (e.getTimeLeft() == 0) {
                e.setResult(calculate(e));
                e.getInstruction().setResult(calculate(e));
                if (e.getInstruction().getOperation() == Operation.BNEZ) {
                    if(e.getResult() == 1) {
                        instructionQueue.returnToLabel(e.getInstruction().getJumpLabel());
                    }
                    stall = false;
                }
                e.setFinished(true);
                e.getInstruction().setExecutionEndCycle(cycleCounter);
                instructionQueue.modifyInstruction(e.getInstruction().getIndex(), e.getInstruction());
                System.out.println("Instruction (" + e.getInstruction().getInstructionString() + ")  in slot "+ e.getTag()+" has finished executing");
            } else System.out.println("Instruction (" + e.getInstruction().getInstructionString() + ")  in slot "+ e.getTag()+" is executing, "+e.getTimeLeft()+" cycles left");
        }
    }

    private void executionLoopOperations(ReservationStationSlot e) {
        if(e.isBusy() && e.getInstruction().getIssueCycle() < cycleCounter){
            if (!e.isReady()) {
                e.updateReady();

                if(e.isReady()) {
                   executeCycle(e);
                } else {
                    System.out.println("Instruction (" + e.getInstruction().getInstructionString() + ")  in slot "+ e.getTag()+" is not ready to execute yet, waiting for operands: "+e.getqJ()+", "+e.getqK());
                }
            }
            else if(e.isReady() && !e.isFinished()) {
                executeCycle(e);
            }
        }
    }

    private void updateReservationSlotsFromBus() {
        for(ReservationStationSlot e : addSubReservationStation.getAddSubReservationStationSlots()) {
            if(e.isBusy()) {
                if(e.getqJ() != null && e.getqJ().equals(bus.getTag())) {
                    e.setvJ(bus.getValue());
                    e.setqJ(null);
                }
                if(e.getqK() != null && e.getqK().equals(bus.getTag())) {
                    e.setvK(bus.getValue());
                    e.setqK(null);
                }
            }
        }

        for(ReservationStationSlot e : mulDivReservationStation.getMulDivReservationStationSlots()) {
            if(e.isBusy()) {
                if(e.getqJ() != null && e.getqJ().equals(bus.getTag())) {
                    e.setvJ(bus.getValue());
                    e.setqJ(null);
                }
                if(e.getqK() != null && e.getqK().equals(bus.getTag())) {
                    e.setvK(bus.getValue());
                    e.setqK(null);
                }
            }
        }

        for(LoadStoreSlot e : loadStoreBuffers.getLoadSlots()) {
            if(e.isBusy()) {
                if(e.getQ() != null && e.getQ().equals(bus.getTag())) {
                    e.setV(bus.getValue());
                    e.setQ(null);
                }
            }
        }
    }

    private void updateRegisterFileFromBus() {
        if (bus.isPopulated()) {

            //update each register in the register file
            for (Register reg : registerFile.getGpRegisters()) {
                if (reg.getQ() != null && reg.getQ().equals(bus.getTag())) {
                    reg.setValue(bus.getValue());
                    reg.setQ(null);
                }
            }

            for (Register reg : registerFile.getFpRegisters()) {
                if (reg.getQ() != null && reg.getQ().equals(bus.getTag())) {
                    reg.setValue(bus.getValue());
                    reg.setQ(null);
                }
            }

        }
    }


    private Double calculate(ReservationStationSlot entry) {
        switch (entry.getInstruction().getOperation()){
            case DADD, ADDI, ADD_D -> {
                return entry.getvJ() + entry.getvK();
            }
            case SUBI, SUB_D -> {
                return entry.getvJ() - entry.getvK();
            }
            case MUL_D -> {
                return entry.getvJ() * entry.getvK();
            }
            case DIV_D -> {
                if (entry.getvK() == 0) {
                    throw new ArithmeticException("Division by zero, in " + entry.getTag());
                }
                return entry.getvJ() / entry.getvK();
            }
            case BNEZ -> {
                return entry.getvJ() != 0 ? 1.0 : 0.0;
            }
        }
        return 0.0;
    }

    private HashMap<String,Integer> updateINHashMap(HashMap<String, Integer> instructionNeeds, ReservationStationSlot e) {

        if (e.getqJ() != null || e.getqK() != null) { //it hasn't started executing yet
            if (e.getqJ() != null) {
                if (instructionNeeds.containsKey(e.getqJ())) {
                    instructionNeeds.put(e.getqJ(), instructionNeeds.get(e.getqJ()) + 1);
                } else {
                    instructionNeeds.put(e.getqJ(), 1);
                }
            }

            if (e.getqK() != null) {
                if (instructionNeeds.containsKey(e.getqK())) {
                    instructionNeeds.put(e.getqK(), instructionNeeds.get(e.getqK()) + 1);
                } else {
                    instructionNeeds.put(e.getqK(), 1);
                }
            }
        }

        return instructionNeeds;
    }


    public void writeBack() {
        //init counter to see how many want to publish, we choose based on "usefulness"
        //if same usefulness, random/FIFO
        int countFinished = 0;

        //store how many times each label is needed in a HashMap
        HashMap<String, Integer> instructionNeeds = new HashMap<>();
        HashMap<String, Instruction> finishedInstructions = new HashMap<>();
        HashSet<Instruction> readyStores = new HashSet<>();


        for(ReservationStationSlot e : addSubReservationStation.getAddSubReservationStationSlots()) {
            instructionNeeds = updateINHashMap(instructionNeeds, e);
            if(e.isFinished() && !e.isPublished() && e.getInstruction().getExecutionEndCycle() < cycleCounter && e.getInstruction().getOperation() != Operation.BNEZ) {
                countFinished++;
                finishedInstructions.put(e.getTag(), e.getInstruction());
            }

            if (e.isFinished() && !e.isPublished() && e.getInstruction().getExecutionEndCycle() < cycleCounter && e.getInstruction().getOperation() == Operation.BNEZ) {
//                if (e.getResult() == 1) {
//                    instructionQueue.returnToLabel(e.getInstruction().getJumpLabel());
//                }
                e.setPublished(true);
                e.getInstruction().setPublishCycle(cycleCounter);
                instructionQueue.modifyInstruction(e.getInstruction().getIndex(), e.getInstruction());
                System.out.println("Publishing instruction " + e.getInstruction().getInstructionString() + " with tag " + e.getTag() + " and result " + e.getInstruction().getResult());
                addSubReservationStation.removeInstruction(e.getTag());

            }
        }

        for(ReservationStationSlot e : mulDivReservationStation.getMulDivReservationStationSlots()) {
            instructionNeeds = updateINHashMap(instructionNeeds, e);
            if(e.isFinished() && !e.isPublished() && e.getInstruction().getExecutionEndCycle() < cycleCounter) {
                countFinished++;
                finishedInstructions.put(e.getTag(), e.getInstruction());
            }
        }

        for(LoadStoreSlot e : loadStoreBuffers.getLoadSlots()) {
            if(e.isFinished() && !e.isPublished() && e.getInstruction().getExecutionEndCycle() < cycleCounter) {

                countFinished++;
                finishedInstructions.put(e.getTag(), e.getInstruction());
            }
        }

        for (LoadStoreSlot e : loadStoreBuffers.getStoreSlots()) {
            if(e.isFinished() && !e.isPublished() && e.getInstruction().getExecutionEndCycle() < cycleCounter) {
                readyStores.add(e.getInstruction());
            }
        }

        if (countFinished == 1) {
            //key value pair containing the only element in the hashmap
            HashMap.Entry<String, Instruction> finishedInstruction = finishedInstructions.entrySet().iterator().next();

            //put the tag and the value on the bus
            bus.publish(finishedInstruction.getKey(), finishedInstruction.getValue().getResult());
            System.out.println("Publishing instruction " + finishedInstruction.getValue().getInstructionString() + " with tag " + finishedInstruction.getKey() + " and result " + finishedInstruction.getValue().getResult());
            finishedInstruction.getValue().setPublishCycle(cycleCounter);
            instructionQueue.modifyInstruction(finishedInstruction.getValue().getIndex(), finishedInstruction.getValue());

            //remove the instruction from the reservation station
            addSubReservationStation.removeInstruction(finishedInstruction.getKey());
            mulDivReservationStation.removeInstruction(finishedInstruction.getKey());
            loadStoreBuffers.removeLoadInstruction(finishedInstruction.getKey());


        } else if (countFinished > 1) {
            // most useful instruction
            boolean found = false;
            boolean needed = instructionNeeds.size() > 0;

            while (!found && needed) {
                // get the max value in the hashmap and the key
                Map.Entry<String, Integer> maxEntry = instructionNeeds.entrySet().iterator().next();

                for (Map.Entry<String, Integer> entry : instructionNeeds.entrySet()) {
                    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                        maxEntry = entry;
                    }
                }

                String currentTag = maxEntry.getKey();

                instructionNeeds.remove(currentTag);

                for (String tag: finishedInstructions.keySet()) {
                    if (tag.equals(currentTag)) {
                        found = true;
                        //put the tag and the value on the bus
                        bus.publish(tag, finishedInstructions.get(tag).getResult());
                        System.out.println("Publishing instruction " + finishedInstructions.get(tag).getInstructionString()+ " with tag " + tag + " and result " + finishedInstructions.get(tag).getResult());

                        finishedInstructions.get(tag).setPublishCycle(cycleCounter);
                        instructionQueue.modifyInstruction(finishedInstructions.get(tag).getIndex(), finishedInstructions.get(tag));

                        //remove the instruction from the reservation station
                        addSubReservationStation.removeInstruction(tag);
                        mulDivReservationStation.removeInstruction(tag);
                        loadStoreBuffers.removeLoadInstruction(tag);
                        break;
                    }
                }
            }

            // FIFO
            if (!needed) {
                boolean found2 = false;
                for (Instruction instr : instructionQueue.getInstructions()) {
                    for (ReservationStationSlot e : addSubReservationStation.getAddSubReservationStationSlots()) {
                        if (e.getInstruction() != null && e.getInstruction().getInstructionString().equals(instr.getInstructionString())) {
                            //put the tag and the value on the bus
                            bus.publish(instr.getLabel(), instr.getResult());
                            System.out.println("Publishing instruction " + instr.getInstructionString()+ " with tag " + instr.getLabel() + " and result " + instr.getResult());
                            instr.setPublishCycle(cycleCounter);
                            instructionQueue.modifyInstruction(instr.getIndex(), instr);

                            //remove the instruction from the reservation station
                            addSubReservationStation.removeInstruction(e.getTag());
                            found2 = true;
                            break;
                        }
                    }

                    if (!found2) {
                        for (ReservationStationSlot e : mulDivReservationStation.getMulDivReservationStationSlots()) {
                            if (e.getInstruction() != null && e.getInstruction().getInstructionString().equals(instr.getInstructionString())) {
                                //put the tag and the value on the bus
                                bus.publish(instr.getLabel(), instr.getResult());
                                System.out.println("Publishing instruction " + instr.getInstructionString()+ " with tag " + instr.getLabel() + " and result " + instr.getResult());
                                instr.setPublishCycle(cycleCounter);
                                instructionQueue.modifyInstruction(instr.getIndex(), instr);

                                //remove the instruction from the reservation station
                                mulDivReservationStation.removeInstruction(e.getTag());
                                found2 = true;
                                break;
                            }
                        }
                    }

                    if (!found2) {
                        for (LoadStoreSlot e : loadStoreBuffers.getLoadSlots()) {
                            if (e.getInstruction() != null && e.getInstruction().getInstructionString().equals(instr.getInstructionString())) {
                                //put the tag and the value on the bus
                                bus.publish(instr.getLabel(), instr.getResult());
                                System.out.println("Publishing instruction " + instr.getInstructionString()+ " with tag " + instr.getLabel() + " and result " + instr.getResult());
                                instr.setPublishCycle(cycleCounter);
                                instructionQueue.modifyInstruction(instr.getIndex(), instr);

                                //remove the instruction from the reservation station
                                loadStoreBuffers.removeLoadInstruction(e.getTag());
                                found2 = true;
                                break;
                            }
                        }
                    }
                }
            }

        }

        if (readyStores.size() == 1) {
            //key value pair containing the only element in the hashmap
            Instruction instrToStore = readyStores.iterator().next();

            for (LoadStoreSlot e : loadStoreBuffers.getStoreSlots()) {
                if (e.getInstruction() != null && e.getInstruction().getInstructionString().equals(instrToStore.getInstructionString())) {
                    //store in the memory
                    memory.setMemoryItem(instrToStore.getEffectiveAddress(), instrToStore.getResult());
                    instrToStore.setPublishCycle(cycleCounter);
                    System.out.println("Publishing instruction " + instrToStore.getInstructionString()+ " with tag " + e.getTag() + " and result " + instrToStore.getResult());
                    instructionQueue.modifyInstruction(instrToStore.getIndex(), instrToStore);

                    //remove the instruction from the reservation station
                    loadStoreBuffers.removeStoreInstruction(e.getTag());
                    break;
                }
            }

        } else if (readyStores.size() > 1) {

            //FIFO
            //loop on instruction queue and see if the instruction is a store
            //if yes, this is the one i will write to memory

            for (LoadStoreSlot e : loadStoreBuffers.getStoreSlots()) {
                for (Instruction instr : instructionQueue.getInstructions()) {
                    if (instr.getOperation() == Operation.S_D) {
                        if (e.getInstruction() != null && e.getInstruction().getInstructionString().equals(instr.getInstructionString())) {
                            memory.setMemoryItem(instr.getEffectiveAddress(), instr.getResult());
                            instr.setPublishCycle(cycleCounter);
                            System.out.println("Publishing instruction " + instr.getInstructionString()+ " with tag " + e.getTag() + " and result " + instr.getResult());
                            instructionQueue.modifyInstruction(instr.getIndex(), instr);
                            loadStoreBuffers.removeStoreInstruction(e.getTag());
                            break;
                        }
                    }
                }
            }



        }


        // update reservation stations from bus
        updateReservationSlotsFromBus();

        // update register file from bus
        updateRegisterFileFromBus();
    }

    public static Processor getProcessorSetup() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the latencies of each instruction (in cycles) ...");

        System.out.print("Memory Latency: ");
        int MemLatency = scanner.nextInt();

        System.out.print("ADD.D Latency: ");
        int Add_DLatency = scanner.nextInt();

        System.out.print("SUB.D Latency: ");
        int Sub_DLatency = scanner.nextInt();

        System.out.print("MUL.D Latency: ");
        int Mul_DLatency = scanner.nextInt();

        System.out.print("DIV.D Latency: ");
        int Div_DLatency = scanner.nextInt();

        System.out.print("DADD Latency: ");
        int DAddLatency = scanner.nextInt();

        System.out.print("SUBI Latency: ");
        int SubILatency = scanner.nextInt();

        System.out.println("Latencies updated successfully!");

        System.out.println("Enter the size of each component ...");

        System.out.print("Add/Sub Reservation Station: ");
        int addSubReservationStationSize = scanner.nextInt();

        System.out.print("Mul/Div Reservation Station: ");
        int mulDivReservationStationSize = scanner.nextInt();

        System.out.print("Load Buffer: ");
        int loadBuffersSize = scanner.nextInt();

        System.out.print("Store Buffer: ");
        int storeBuffersSize = scanner.nextInt();

        System.out.print("Memory Size: ");
        int memorySize = scanner.nextInt();

        Memory memory = new Memory(memorySize);

        System.out.println("Sizes updated successfully!");

        boolean preLoadMemory = false;
        boolean preLoadRegisterFile = false;

        System.out.println("Do you want to pre-load the memory? (Y/N)");
        String choice = scanner.next();

        if (choice.equalsIgnoreCase("Y")) {
            preLoadMemory = true;
            System.out.println("number of memory locations to pre-load: ");
            int numberOfMemoryLocations = scanner.nextInt();

            for (int i = 0; i < numberOfMemoryLocations; i++) {
                System.out.println("Enter the value of memory location: ");
                int index = scanner.nextInt();

                if (index >= memorySize) {
                    System.out.println("Invalid memory location!");
                    i--;
                    continue;
                }

                System.out.println("Enter the value: ");
                double value = scanner.nextDouble();
                memory.setMemoryItem(index, value);
            }

        }

        System.out.println("Do you want to pre-load the register file? (Y/N)");
        choice = scanner.next();

        RegisterFile registerFile = new RegisterFile();

        if (choice.equalsIgnoreCase("Y")) {
            preLoadRegisterFile = true;
            System.out.println("number of registers to pre-load: ");
            int numberOfregisters = scanner.nextInt();

            for (int i = 0; i < numberOfregisters; i++) {
                System.out.println("Enter the register name (FNN/RNN | N = {0...31}): ");
                String regName = scanner.next();

                if (regName.charAt(0) == 'R' || regName.charAt(0) == 'r') {
                    int regNumber = Integer.parseInt(regName.substring(1));
                    if (regNumber < 0 || regNumber > 31) {
                        System.out.println("Invalid register number!");
                        i--;
                        continue;
                    } else {
                        System.out.println("Enter the value: ");
                        double value = scanner.nextDouble();
                        registerFile.setRegister("R"+regNumber, value);
                    }

                } else if (regName.charAt(0) == 'F' || regName.charAt(0) == 'f') {
                    int regNumber = Integer.parseInt(regName.substring(1));
                    if (regNumber < 0 || regNumber > 31) {
                        System.out.println("Invalid register number!");
                        i--;
                        continue;
                    } else {
                        System.out.println("Enter the value: ");
                        double value = scanner.nextDouble();
                        registerFile.setRegister("F"+regNumber, value);
                    }

                } else {
                    System.out.println("Invalid register name!");
                    i--;
                    continue;
                }

            }

        }

        System.out.println("Setting up the processor ...");

        int totalIterations = 100;

        for (int i = 0; i < totalIterations; i++) {
            printLoadingBar(i, totalIterations);
            Thread.sleep(18); // Adjust the sleep duration to control the speed of the loading animation
        }

        System.out.println("Processor setup complete!");


        if (preLoadMemory && preLoadRegisterFile) {
            return new Processor(addSubReservationStationSize, mulDivReservationStationSize, loadBuffersSize, storeBuffersSize, memory, registerFile, Mul_DLatency, Div_DLatency, Add_DLatency, Sub_DLatency, DAddLatency, SubILatency, MemLatency);
        } else if (preLoadMemory) {
            return new Processor(addSubReservationStationSize, mulDivReservationStationSize, loadBuffersSize, storeBuffersSize, memory, Mul_DLatency, Div_DLatency, Add_DLatency, Sub_DLatency, DAddLatency, SubILatency, MemLatency);
        } else if (preLoadRegisterFile) {
            return new Processor(addSubReservationStationSize, mulDivReservationStationSize, loadBuffersSize, storeBuffersSize, memorySize, registerFile, Mul_DLatency, Div_DLatency, Add_DLatency, Sub_DLatency, DAddLatency, SubILatency, MemLatency);
        } else {
            return new Processor(addSubReservationStationSize, mulDivReservationStationSize, loadBuffersSize, storeBuffersSize, memorySize, Mul_DLatency, Div_DLatency, Add_DLatency, Sub_DLatency, DAddLatency, SubILatency, MemLatency);
        }
    }

    private static String[] splitInstruction(String instruction) {
        String fourToken = "(\\w+(?:\\.\\w)?)\\s+(\\w+)\\s*,\\s*(\\w+)\\s*,\\s*(\\w+)";
        String threeToken = "(\\w+(?:\\.\\w)?)\\s+(\\w+)\\s*,\\s*(\\w+)";
        String labeled = "(\\w+:)\\s*(("+fourToken+")|("+threeToken+"))";

        Pattern pattern4T = Pattern.compile(fourToken);
        Pattern pattern3T = Pattern.compile(threeToken);
        Pattern patternLT = Pattern.compile(labeled);

        Matcher matcher4T = pattern4T.matcher(instruction);
        Matcher matcher3T = pattern3T.matcher(instruction);
        Matcher matcherLT = patternLT.matcher(instruction);

        if (matcher4T.matches()) {
            return new String[]{matcher4T.group(1), matcher4T.group(2), matcher4T.group(3), matcher4T.group(4)};
        } else if (matcher3T.matches()) {
            return new String[]{matcher3T.group(1), matcher3T.group(2), matcher3T.group(3)};
        } else if (matcherLT.matches() && (pattern4T.matcher(matcherLT.group(2))).matches()) {
            String afterLabelText = matcherLT.group(2);
            Matcher afterLabel = pattern4T.matcher(afterLabelText);
            if (afterLabel.matches()) {
                return new String[] {matcherLT.group(1), afterLabel.group(1), afterLabel.group(2), afterLabel.group(3), afterLabel.group(4)};
            }
            return null;
        } else if (matcherLT.matches() && (pattern3T.matcher(matcherLT.group(2))).matches()){
            String afterLabelText = matcherLT.group(2);
            Matcher afterLabel = pattern3T.matcher(afterLabelText);
            if (afterLabel.matches()) {
               return new String[] {matcherLT.group(1), afterLabel.group(1), afterLabel.group(2), afterLabel.group(3)};
            }
            return null;
        } else {
            //System.out.println(matcherLT.group(0));
            return null;
        }
    }

    public void codeParser() throws IOException {
        /** Instructions Format:
         *  1- 4 token format
         *      = INSTR RD, RS, RT
         *      = INSTR_I RD, RS, IMM
         *  2- 3 token format
         *      =  INSTR RD, EFFECTIVE_ADDRESS
         *      =  BNEZ RD, LABEL
         *  3- Labeled Instructions
         *      = LABEL: [4 token format]
         *      = LABEL: [3 token format]
         **/

        try {
            // load text from Code/code.txt
            BufferedReader br = new BufferedReader(new FileReader("Code/code.txt"));
            int instrCounter = 0;

            Instruction currentInstruction;
            String line;
            boolean isFPop;
            boolean isMEMop;
            int latency = 1;
            Operation operation;
            String sourceOperand;
            String destinationOperand;
            String targetOperand;
            int effectiveAddress = -1;
            String label;
            String jumpLabel;
            int immediateValue = NON_IMMEDIATE;

            boolean fourToken = false;
            boolean threeToken = false;
            boolean isImmediate = false;

            while ((line = br.readLine()) != null) {
                String[] tokens = splitInstruction(line);

                isFPop = false;
                isMEMop = false;
                latency = 1;
                operation = null;
                sourceOperand = null;
                destinationOperand = null;
                targetOperand = null;
                effectiveAddress = -1;
                label = null;
                jumpLabel = null;
                immediateValue = NON_IMMEDIATE;

                fourToken = false;
                threeToken = false;
                isImmediate = false;

                if (tokens == null) {
                    System.out.println("Error in line: " + line);
                    throw new Exception("CODE ERROR, Error in line: " + line);
                }
                // check if labeled
                boolean labeled = tokens[0].endsWith(":");

                // check if 4 token format
                if (tokens.length == 4 && !labeled || labeled && tokens.length == 5) {
                    fourToken = true;
                }

                // check if 3 token format
                if (tokens.length == 3 || labeled && tokens.length == 4) {
                    threeToken = true;
                }

                // check if immediate
                if (labeled && tokens[1].toUpperCase().endsWith("I") || !labeled && tokens[0].toUpperCase().endsWith("I")) {
                    isImmediate = true;
                }

                String currInstruction = labeled ? tokens[1] : tokens[0];
                // 0       1          2         3         4
                // lbl     ADD.D      F30,      F21,     F22
                // lbl     L.D        R2,       100
                // ADD.D   F30,       F21,      F22
                // L.D     F30,       100

                switch (currInstruction) {
                    case "L.D" -> {
                        isMEMop = true;
                        latency = MemLatency;
                        operation = Operation.L_D;
                        destinationOperand = labeled ? tokens[2] : tokens[1];
                        effectiveAddress = Integer.parseInt(labeled ? tokens[3] : tokens[2]);
                        label = labeled ? tokens[0].substring(0, tokens[0].length() - 1) : null;
                    }
                    case "S.D" -> {
                        isMEMop = true;
                        latency = MemLatency;
                        operation = Operation.S_D;
                        sourceOperand = labeled ? tokens[2] : tokens[1];
                        effectiveAddress = Integer.parseInt(labeled ? tokens[3] : tokens[2]);
                        label = labeled ? tokens[0].substring(0, tokens[0].length() - 1) : null;
                    }
                    case "ADD.D" -> {
                        isFPop = true;
                        latency = Add_DLatency;
                        operation = Operation.ADD_D;
                        sourceOperand = labeled ? tokens[3] : tokens[2];
                        destinationOperand = labeled ? tokens[2] : tokens[1];
                        targetOperand = labeled ? tokens[4] : tokens[3];
                        label = labeled ? tokens[0].substring(0, tokens[0].length() - 1) : null;
                    }
                    case "SUB.D" -> {
                        isFPop = true;
                        latency = Sub_DLatency;
                        operation = Operation.SUB_D;
                        sourceOperand = labeled ? tokens[3] : tokens[2];
                        destinationOperand = labeled ? tokens[2] : tokens[1];
                        targetOperand = labeled ? tokens[4] : tokens[3];
                        label = labeled ? tokens[0].substring(0, tokens[0].length() - 1) : null;
                    }
                    case "MUL.D" -> {
                        isFPop = true;
                        latency = Mul_DLatency;
                        operation = Operation.MUL_D;
                        sourceOperand = labeled ? tokens[3] : tokens[2];
                        destinationOperand = labeled ? tokens[2] : tokens[1];
                        targetOperand = labeled ? tokens[4] : tokens[3];
                        label = labeled ? tokens[0].substring(0, tokens[0].length() - 1) : null;
                    }
                    case "DIV.D" -> {
                        isFPop = true;
                        latency = Div_DLatency;
                        operation = Operation.DIV_D;
                        sourceOperand = labeled ? tokens[3] : tokens[2];
                        destinationOperand = labeled ? tokens[2] : tokens[1];
                        targetOperand = labeled ? tokens[4] : tokens[3];
                        label = labeled ? tokens[0].substring(0, tokens[0].length() - 1) : null;
                    }
                    // 0    1   2
                    //BNEZ F1, Label
                    case "BNEZ" -> {
                        operation = Operation.BNEZ;
                        sourceOperand = labeled ? tokens[2] : tokens[1];
                        label = labeled ? tokens[0].substring(0, tokens[0].length() - 1) : null;
                        jumpLabel = labeled ? tokens[3] : tokens[2];
                        if (!instructionQueue.getLabels().containsKey(jumpLabel)) {
                            throw new Exception("CODE ERROR, Label: " + jumpLabel + " does not exist");
                        }
                    }
                    //BNEZ, DADD, ADDI, SUBI
                    case "DADD" -> {
                        latency = DAddLatency;
                        operation = Operation.DADD;
                        sourceOperand = labeled ? tokens[3] : tokens[2];
                        destinationOperand = labeled ? tokens[2] : tokens[1];
                        targetOperand = labeled ? tokens[4] : tokens[3];
                        label = labeled ? tokens[0].substring(0, tokens[0].length() - 1) : null;
                    }
                    case "ADDI" -> {
                        operation = Operation.ADDI;
                        sourceOperand = labeled ? tokens[3] : tokens[2];
                        destinationOperand = labeled ? tokens[2] : tokens[1];
                        immediateValue = labeled ? Integer.parseInt(tokens[4]) : Integer.parseInt(tokens[3]);
                        label = labeled ? tokens[0].substring(0, tokens[0].length() - 1) : null;
                    }
                    case "SUBI" -> {
                        latency = SubILatency;
                        operation = Operation.SUBI;
                        sourceOperand = labeled ? tokens[3] : tokens[2];
                        destinationOperand = labeled ? tokens[2] : tokens[1];
                        immediateValue = labeled ? Integer.parseInt(tokens[4]) : Integer.parseInt(tokens[3]);
                        label = labeled ? tokens[0].substring(0, tokens[0].length() - 1) : null;
                    }
                }

                currentInstruction = new Instruction(isFPop, isMEMop, latency, operation, sourceOperand, destinationOperand, targetOperand, immediateValue, effectiveAddress, label, jumpLabel, line, instrCounter++);
                instructionQueue.addInstruction(currentInstruction);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void printLoadingBar(int currentIteration, int totalIterations) {
        int barLength = 20;
        int progress = (int) ((double) currentIteration / totalIterations * barLength);

        System.out.print("\r[");
        for (int i = 0; i < barLength; i++) {
            if (i < progress) {
                System.out.print("=");
            } else {
                System.out.print(" ");
            }
        }
        System.out.print("] ");

        // Display different characters for animation
        switch (currentIteration % 3) {
            case 0 -> System.out.print("/");
            case 1 -> System.out.print("|");
            case 2 -> System.out.print("\\");
        }

        // Clear any characters after the loading bar
        System.out.print("  ");
    }
}
