package Components;

public class LoadStoreSlot {

    //el 7agat beta3et el store elle mesh hanestakhdemha fel load
    // hankarar manestakhdemhash fe ba2y el code

    boolean isLoad;
    private Instruction instruction;
    private String tag;
    private int timeLeft;
    private boolean busy;
    private int effectiveAddress;

    private Double v;
    private String q;
    private Double result;
    private boolean ready;
    private boolean finished;
    private boolean published;

    public LoadStoreSlot() {
        instruction = null;
        tag = "";
        timeLeft = 0;
        busy = false;
        v = (double) 0;
        q = "0";
        result = (double) 0;
        ready = false;
        finished = false;
        published = false;
    }

    public LoadStoreSlot(Instruction instruction, String tag, boolean busy, double v, String q) {
        this.instruction = instruction;
        this.tag = tag;
        this.timeLeft = instruction.getLatency();
        this.busy = busy;
        this.effectiveAddress = instruction.getEffectiveAddress();
        this.v = v;
        this.q = q;
        this.result = 0.0;
        this.ready = false;
        this.finished = false;
        this.published = false;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;

    }

    public boolean isLoad() {
        return isLoad;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;

    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;

    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;

    }

    public int getEffectiveAddress() {
        return effectiveAddress;
    }

    public double getV() {
        return v;
    }

    public void setV(int v) {
        this.v = (double) v;

    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;

    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;

    }

    public boolean isReady() {
        return ready;
    }

    public void setReady() {
        if (isLoad) {
            this.ready = true;
        } else {
            if (q.equals("0")) {
                this.ready = true;
            }
        }

    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;

    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;

    }

    public String toString() {
        return "LoadStoreSlot{" + "instruction=" + instruction + ", tag=" + tag + ", timeLeft=" + timeLeft + ", busy=" + busy + ", effectiveAddress=" + effectiveAddress + ", v=" + v + ", q=" + q + ", result=" + result + ", ready=" + ready + ", finished=" + finished + ", published=" + published + '}';
    }

    public void decrementTimeLeft() {
        timeLeft--;
    }

    public void setAll(String tag, boolean busy, Double v, String q, boolean finished, boolean published) {
        this.tag = tag;
        this.timeLeft = instruction.getLatency();
        this.busy = busy;
        this.v = v;
        this.q = q;
        this.finished = finished;
        this.published = published;
    }


}
