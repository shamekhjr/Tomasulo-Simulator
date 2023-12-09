import Components.*;
import Enums.Operation;
import java.util.Scanner;
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
            processor.cycleCounter++;
            System.out.println("============================================");
        }

        processor.instructionQueue.print();

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
    }

    public Processor(int addSubSize, int mulDivSize, int loadBufferSize, int storeBufferSize, int memorySize, int Mul_DLatency, int Div_DLatency, int Add_DLatency, int Sub_DLatency, int DAddLatency, int SubILatency, int MemLatency) {
        log = "";
        addSubReservationStation = new AddSubReservationStation(addSubSize);
        mulDivReservationStation = new MulDivReservationStation(mulDivSize);
        loadStoreBuffers = new LoadStoreBuffers(loadBufferSize, storeBufferSize);
        instructionQueue = new InstructionQueue();
        memory = new Memory(memorySize);
        registerFile = new RegisterFile();
        bus = new Bus();
        this.Mul_DLatency = Mul_DLatency;
        this.Div_DLatency = Div_DLatency;
        this.Add_DLatency = Add_DLatency;
        this.Sub_DLatency = Sub_DLatency;
        this.DAddLatency = DAddLatency;
        this.SubILatency = SubILatency;
        this.MemLatency = MemLatency;
        this.cycleCounter = 1;
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
            } else if (bus.isPopulated() && bus.getTag().equals(regSrc1.getQ())){
                operands.setVj(bus.getValue());
            } else {
                operands.setQj(regSrc1.getQ());
            }

            if (regSrc2.getQ() == null) { // src2 is ready
                operands.setVk(regSrc2.getValue());
            } else if (bus.isPopulated() && bus.getTag().equals(regSrc2.getQ())) {
                operands.setVk(bus.getValue());
            } else {
                operands.setQk(regSrc2.getQ());
            }

        } else if (instruction.getImmediateValue() != NON_IMMEDIATE) { // immediate instruction
            // get the operands from the reservation station
            String src1 = instruction.getSourceOperand();
            Register regSrc1 = registerFile.getRegister(src1);

            if (regSrc1.getQ() == null) { // src1 is ready
                operands.setVj(regSrc1.getValue());
            } else if (bus.isPopulated() && bus.getTag().equals(regSrc1.getQ())) {
                operands.setVj(bus.getValue());
            } else {
                operands.setQj(regSrc1.getQ());
            }

            operands.setVk(instruction.getImmediateValue());
        }

        return operands;
    }

    public void issue() {
        // get the instruction
        Instruction instruction = instructionQueue.getInstructionNoInc();

        if (instruction == null) { // no instruction to issue
            return;
        }

        // check instruction type
        Operation currOpr = instruction.getOperation();
        boolean isIssued = false;
        switch (currOpr) {
            // ADD_D, SUB_D, MUL_D, DIV_D, L_D, S_D, BNEZ, DADD, ADDI, SUBI
            case ADD_D, ADDI, SUB_D, SUBI, DADD -> {
                // check if there is an empty slot in the add/sub reservation station
                if (addSubReservationStation.hasFreeStations()) {
                    // add the instruction to the add/sub reservation station
                    OperandTuple operands = readArithOperands(instruction);

                    // add issue cycle to instruction
                    instruction.setIssueCycle(cycleCounter);

                    addSubReservationStation.addInstruction(instruction, operands.getVj(), operands.getVk(), operands.getQj(), operands.getQk());
                    isIssued = true;
                }
            }

            case MUL_D, DIV_D -> {
                // check if there is an empty slot in the mul/div reservation station
                if (mulDivReservationStation.hasFreeStations()) {
                    OperandTuple operands = readArithOperands(instruction);

                    // add issue cycle to instruction
                    instruction.setIssueCycle(cycleCounter);

                    // add the instruction to the mul/div reservation station
                    mulDivReservationStation.addInstruction(instruction, operands.getVj(), operands.getVk(), operands.getQj(), operands.getQk());
                    isIssued = true;
                }
            }

            case L_D -> {
                // check if there is an empty slot in the load/store buffers
                if (!loadStoreBuffers.isLoadBufferFull()) {
                    // add issue cycle to instruction
                    instruction.setIssueCycle(cycleCounter);

                    // add the instruction to the load/store buffers
                    loadStoreBuffers.addLoadInstruction(instruction);
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

                    if (regSrc1.getQ() == null) { // src1 is ready
                        loadStoreBuffers.addStoreInstruction(instruction, regSrc1.getValue(), null);
                    } else if (bus.isPopulated() && bus.getTag().equals(regSrc1.getQ())) {
                        loadStoreBuffers.addStoreInstruction(instruction, bus.getValue(), null);
                    } else {
                        loadStoreBuffers.addStoreInstruction(instruction, -1, regSrc1.getQ());
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

                    if (regSrc1.getQ() == null) { // src1 is ready
                        addSubReservationStation.addInstruction(instruction, regSrc1.getValue(), (double) 0, null, null);
                    } else if (bus.isPopulated() && bus.getTag().equals(regSrc1.getQ())) {
                        addSubReservationStation.addInstruction(instruction, bus.getValue(), 0.0, null, null);
                    } else {
                        addSubReservationStation.addInstruction(instruction, (double) -1, (double) 0, regSrc1.getQ(), null);
                    }

                    isIssued = true;
                }
            }
        }

        if (isIssued) {
            instructionQueue.incrementIndex();
            System.out.println("Instruction (" + instruction.getInstructionString() + ") is issued");
        } else {
            System.out.println("Instruction (" + instruction.getInstructionString() + ") could not be issued");
        }

    }

    public void execute() {
        // check if operands ready
        for(ReservationStationSlot e : addSubReservationStation.getAddSubReservationStationSlots()) {
            if(e.isBusy()){
                if(!e.isReady()){
                    e.setReady();
                    if(e.isReady()) e.getInstruction().setExecutionStartCycle(cycleCounter);
                }
                if(e.isReady() && !e.isFinished()) {
                    e.decrementTimeLeft();
                    if(e.getTimeLeft() == 0) {
                        // TODO Evaluation OF Results
                        e.setFinished(true);
                        e.getInstruction().setExecutionEndCycle(cycleCounter);
                        e.getInstruction().setPublishCycle(cycleCounter + 1);
                    }
                }
            }
        }
        // decrement the cycles left for each instruction in the reservation stations and edit publishCycle in instruction

        // calculate result if operands ready
    }


    public void writeBack() {

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

        System.out.println("Sizes updated successfully!");

        System.out.println("Setting up the processor ...");

        int totalIterations = 100;

        for (int i = 0; i < totalIterations; i++) {
            printLoadingBar(i, totalIterations);
            Thread.sleep(18); // Adjust the sleep duration to control the speed of the loading animation
        }

        System.out.println("Processor setup complete!");



        return new Processor(addSubReservationStationSize, mulDivReservationStationSize, loadBuffersSize, storeBuffersSize, memorySize, Mul_DLatency, Div_DLatency, Add_DLatency, Sub_DLatency, DAddLatency, SubILatency, MemLatency);
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
         *      =  INSTR RD, OFFSET(RS)
         *      =  BNEZ RD, LABEL
         *  3- Labeled Instructions
         *      = LABEL: [4 token format]
         *      = LABEL: [3 token format]
         **/

        try {
            // load text from Code/code.txt
            BufferedReader br = new BufferedReader(new FileReader("Code/code.txt"));

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
                currentInstruction = new Instruction(isFPop, isMEMop, latency, operation, sourceOperand, destinationOperand, targetOperand, immediateValue, effectiveAddress, label, jumpLabel, line);
                instructionQueue.addInstruction(currentInstruction);
                //
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
