# Tomasulo-Simulator
An Implementation of the Tomasulo Algorithm for MIPS64 instructions

Approach:

	Our overarching goal was to design a Tomasulo simulator that is as close to the reality as possible. That is, while taking into account the changes introduced by the project description, namely the fact that the branch decision is computed in the Tomasulo architecture and not in a separate module. 

	To do this, we decided to leverage the object-oriented nature of the Java programming language to separate our code into several classes, each representing a component of the Tomasulo architecture and fitting them with all necessary attributes and methods. Then, the processor class encapsulates all of these components and is responsible for controlling their behavior as well as their contents. 
	
	As for how our code operates, we put a lot of care into sticking with the fundamentals of the Tomasulo algorithm, mainly issuing instructions in order, and starting the execution at least a cycle later, and publishing on the bus at least a cycle after execution is complete. There were, however, some edge cases that we needed to take into consideration and properly handle. 

For instance, we needed to handle the case when several instructions wanted to publish their results on the bus in the same cycle. To address this, developed a custom algorithm that calculates which instruction is the most useful (i.e, whose result is needed by the largest number of other reservation stations) and let this instruction write its result first. In the case of several instructions having the same usefulness, we use FIFO.

As for the case of branches, as the description required, the decision is computed inside of the add/sub reservation station, and since we are supposed to not predict, we stall the pipeline, meaning we do not issue any other instructions until the result of the branch is computed. And other than the aforementioned cases, the code runs as described by the Tomasulo algorithm.

Code structure:
For the code structure and content, there are a total of 14 files: 13 classes and 1 enum. The names for the classes are ReservationStationSlot, AddSubReservationStation, MulDivReservationStation, LoadStoreSlot, LoadStoreBuffer, Bus, Instruction, InstructionQueue, Memory, Register, RegisterFile, OperandTuple, and Processor, and the name for the enum is Operation.
The Operation Enum:
This enum contains the list of possible operations that the processor can perform. Those operations are the following: ADD.D, SUB.D, MUL.D, DIV.D, L.D, S.D, BNEZ, DADD, ADDI, SUBI
The Register Class:
The Register object is the building block of the RegisterFile object. It has three attributes: value, name, and q. The value variable is of type double and it represents the value stored in that specific register. The name represents the name of that register (e.g. F1, F2, R1, R2, etc. ). The q attribute represents the tag for which the Register is to get its values with, meaning that the register is listening for the value associated with the tag from the Bus object. The q attribute is initially null, but if it is assigned to a value then the register is unusable until the register gets the value it is waiting for. The attributes of course come with their getters and setters.
The RegisterFile Class:
The RegisterFile object is basically the register file of the processor. It has four attributes: two static int attributes and two Register array attributes. The static ints are NUM_OF_GP_REGISTERS, which represents the number of General Purpose Registers, and NUM_OF_FP_REGISTERS, which represents the number of Double Precision Floating Point Registers. The two register arrays are gpRegisters (General Purpose) and fpRegisters (Double Precision Floating Point). Each attribute has its set of getters and setters according to convenience.
The Instruction Class:
The Instruction objects are basically the instructions of the program that are to be parsed from a text file, loaded into the InstructionQueue and later executed during the processors run time. Each instruction has a wide array of attributes: isFPop and isMEMop to indicate if it is a floating point operation or a memory operation or neither, the latency to indicate the number of clock cycles it would take to execute this particular instruction, the operation enum to specify the operation that will be executed, the source, target, and destination operands of the instruction, the effective address in case of Load or store operations, immediate value in case of immediate operations, label incase the instruction begins with a label, jump label incase it is a branch instruction and we need to branch to a specific label, the instruction string which indicates the whole instruction, the result of the instruction in case it was an arithmetic instruction, and the issue, execution start, execution end, and publish cycles. All attributes have their getters and setters according to convenience.
The InstructionQueue Class:
The InstructionQueue object is the object that holds all of the instructions of the code that need to be issued to get executed. It has three attributes: the instructions array list that holds all of the instructions of the program, the current index, which indicates the current instruction that needs to be issued, and the labels hashmap, which holds the indices of all of the labeled instruction in the program with the index of the instruction hosting the label as its value, basically label = index pair. All attributes have their getters and setters according to convenience.
The Memory Class:
The Memory object is the memory or the cache of the processor. It has two attributes: final int memory Size, which is the size of the memory set by the user, and an array of double precision numbers representing the values inside the memory. Getters and setters are made to either get/set a specific value inside of the memory or get/set the memory as a whole.

Test cases:

Sample 1:
L.D F6,5
L.D F2,6
MUL.D F0,F2,F4
SUB.D F8,F2,F6
DIV.D F10,F0,F6
ADD.D F6,F8,F2

Sample 2:
ADDI R5,R5,2
LOOP: S.D F6,9
SUBI R5,R5,1
BNEZ R5,LOOP

Sample 3:
L.D F6,5
L.D F2,6
MUL.D F0,F2,F4
SUB.D F8,F2,F6
DIV.D F10,F0,F6
ADD.D F6,F8,F2
ADDI R5,R5,2
LOOP: S.D F6,9
SUBI R5,R5,1
BNEZ R5,LOOP
