package Components;

public class OperandTuple {
    private Double vj, vk;
    private String qj, qk;

    public OperandTuple() {
        vj = null;
        vk = null;
        qj = null;
        qk = null;
    }

    public OperandTuple(double vj, double vk, String qj, String qk) {
        this.vj = vj;
        this.vk = vk;
        this.qj = qj;
        this.qk = qk;
    }

    public Double getVj() {
        return vj;
    }

    public void setVj(Double vj) {
        this.vj = vj;
    }

    public Double getVk() {
        return vk;
    }

    public void setVk(Double vk) {
        this.vk = vk;
    }

    public String getQj() {
        return qj;
    }

    public void setQj(String qj) {
        this.qj = qj;
    }

    public String getQk() {
        return qk;
    }

    public void setQk(String qk) {
        this.qk = qk;
    }
}
