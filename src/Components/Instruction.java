package Components;

import Enums.Operation;

public class Instruction {
    private boolean isFPop;
    private boolean isMEMop;
    private int latency;
    private Operation operation;
    private String sourceOperand;
    private String destinationOperand;
    private String targetOperand;
    private int effectiveAddress;
    private double immediateValue;
    private String label; //law el instruction odamha label 3ashan nerga3 lel label da law 3ayz a3ml branch
    private String jumpLabel;
    private String instructionString;

    private int issueCycle;
    private int executionStartCycle;
    private int executionEndCycle;
    private int publishCycle; //these will be initially set to -ve values until updated

    //new constructor with the new variables
    public Instruction(boolean isFPop, boolean isMEMop, int latency, Operation operation, String sourceOperand, String destinationOperand, String targetOperand,int immediateValue, int effectiveAddress, String label, String jumpLabel, String instructionString) {
        this.isFPop = isFPop;
        this.isMEMop = isMEMop;
        this.latency = latency;
        this.operation = operation;
        this.sourceOperand = sourceOperand;
        this.destinationOperand = destinationOperand;
        this.targetOperand = targetOperand;
        this.effectiveAddress = effectiveAddress;
        this.immediateValue = immediateValue;
        this.label = label;
        this.jumpLabel = jumpLabel;
        this.issueCycle = -1;
        this.executionStartCycle = -1;
        this.executionEndCycle = -1;
        this.publishCycle = -1;
        this.instructionString = instructionString;

    }

    public boolean isFPop() {
        return isFPop;
    }

    public boolean isMEMop() {
        return isMEMop;
    }

    public int getLatency() {
        return latency;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getSourceOperand() {
        return sourceOperand;
    }

    public String getDestinationOperand() {
        return destinationOperand;
    }

    public String getTargetOperand() {
        return targetOperand;
    }

    public int getEffectiveAddress() {
        return effectiveAddress;
    }

    public String getLabel() {
        return label;
    }

    public int getIssueCycle() {
        return issueCycle;
    }

    public double getImmediateValue() {
        return immediateValue;
    }

    public String getJumpLabel() {
        return jumpLabel;
    }


    public void setIssueCycle(int issueCycle) {
        this.issueCycle = issueCycle;
    }

    public int getExecutionStartCycle() {
        return executionStartCycle;
    }

    public void setExecutionStartCycle(int executionStartCycle) {
        this.executionStartCycle = executionStartCycle;
    }

    public int getExecutionEndCycle() {
        return executionEndCycle;
    }

    public void setExecutionEndCycle(int executionEndCycle) {
        this.executionEndCycle = executionEndCycle;
    }

    public int getPublishCycle() {
        return publishCycle;
    }

    public void setPublishCycle(int publishCycle) {
        this.publishCycle = publishCycle;
    }

    public String getInstructionString() {
        return instructionString;
    }

    public String toString() {
        return "Instruction{" +
                "isFPop=" + isFPop +
                ", isMEMop=" + isMEMop +
                ", latency=" + latency +
                ", operation=" + operation +
                ", sourceOperand='" + sourceOperand + '\'' +
                ", destinationOperand='" + destinationOperand + '\'' +
                ", targetOperand='" + targetOperand + '\'' +
                ", effectiveAddress=" + effectiveAddress +
                ", immediateValue=" + immediateValue +
                ", label='" + label + '\'' +
                ", jumpLabel=" + jumpLabel + '\'' +
                ", issueCycle=" + issueCycle +
                ", executionStartCycle=" + executionStartCycle +
                ", executionEndCycle=" + executionEndCycle +
                ", publishCycle=" + publishCycle +
                '}';
    }

}
