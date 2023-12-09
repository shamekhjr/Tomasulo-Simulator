package Components;

public class LoadStoreBuffers {
    private LoadStoreSlot[] loadSlots;
    private LoadStoreSlot[] storeSlots;
    private final int loadBufferSize;
    private final int storeBufferSize;
    private int numOfUsedLoadSlots;
    private int numOfUsedStoreSlots;

    public LoadStoreBuffers() {
        loadBufferSize = 3;
        storeBufferSize = 3;
        numOfUsedLoadSlots = 0;
        numOfUsedStoreSlots = 0;
        loadSlots = new LoadStoreSlot[loadBufferSize];
        storeSlots = new LoadStoreSlot[storeBufferSize];
        for (int i = 0; i < loadBufferSize; i++) {
            loadSlots[i] = new LoadStoreSlot();
        }
        for (int i = 0; i < storeBufferSize; i++) {
            storeSlots[i] = new LoadStoreSlot();
        }
    }

    public LoadStoreBuffers(int loadBufferSize, int storeBufferSize) {
        this.loadBufferSize = loadBufferSize;
        this.storeBufferSize = storeBufferSize;
        numOfUsedLoadSlots = 0;
        numOfUsedStoreSlots = 0;
        loadSlots = new LoadStoreSlot[this.loadBufferSize];
        storeSlots = new LoadStoreSlot[this.storeBufferSize];
        for (int i = 0; i < loadBufferSize; i++) {
            loadSlots[i] = new LoadStoreSlot();
        }
        for (int i = 0; i < storeBufferSize; i++) {
            storeSlots[i] = new LoadStoreSlot();
        }
    }

    public LoadStoreSlot[] getLoadSlots() {
        return loadSlots;
    }

    public LoadStoreSlot[] getStoreSlots() {
        return storeSlots;
    }

    public LoadStoreSlot getLoadSlot(int index) {
        return loadSlots[index];
    }

    public LoadStoreSlot getStoreSlot(int index) {
        return storeSlots[index];
    }

    public void setLoadSlots(LoadStoreSlot[] loadSlots) {
        this.loadSlots = loadSlots;
    }

    public void setStoreSlots(LoadStoreSlot[] storeSlots) {
        this.storeSlots = storeSlots;
    }

    public void setLoadSlot(int index, LoadStoreSlot loadSlot) {
        loadSlots[index] = loadSlot;
    }

    public void setStoreSlot(int index, LoadStoreSlot storeSlot) {
        storeSlots[index] = storeSlot;
    }

    public int getLoadBufferSize() {
        return loadBufferSize;
    }

    public int getStoreBufferSize() {
        return storeBufferSize;
    }

    public int getNumOfUsedLoadSlots() {
        return numOfUsedLoadSlots;
    }

    public int getNumOfUsedStoreSlots() {
        return numOfUsedStoreSlots;
    }

    public void setNumOfUsedLoadSlots(int numOfUsedLoadSlots) {
        this.numOfUsedLoadSlots = numOfUsedLoadSlots;
    }

    public void setNumOfUsedStoreSlots(int numOfUsedStoreSlots) {
        this.numOfUsedStoreSlots = numOfUsedStoreSlots;
    }

    public void updateNumOfUsedLoadSlots() {
        int count = 0;
        for (int i = 0; i < loadBufferSize; i++) {
            if (loadSlots[i].isBusy()) {
                count++;
            }
        }
        numOfUsedLoadSlots = count;
    }

    public void updateNumOfUsedStoreSlots() {
        int count = 0;
        for (int i = 0; i < storeBufferSize; i++) {
            if (storeSlots[i].isBusy()) {
                count++;
            }
        }
        numOfUsedStoreSlots = count;
    }

    public boolean isLoadBufferFull() {
        return numOfUsedLoadSlots == loadBufferSize;
    }

    public boolean isStoreBufferFull() {
        return numOfUsedStoreSlots == storeBufferSize;
    }



    public boolean isLoadBufferEmpty() {
        return numOfUsedLoadSlots == 0;
    }

    public boolean isStoreBufferEmpty() {
        return numOfUsedStoreSlots == 0;
    }

    public void addLoadInstruction(Instruction instruction) {
        for (int i = 0; i < loadBufferSize; i++) {
            if (!loadSlots[i].isBusy()) {
                loadSlots[i].setInstruction(instruction);
                loadSlots[i].setAll("L"+i,true, null,null, false, false);
                updateNumOfUsedLoadSlots();
                break;
            }
        }
    }

    public void addStoreInstruction(Instruction instruction, Double v, String q) {
        for (int i = 0; i < storeBufferSize; i++) {
            if (!storeSlots[i].isBusy()) {
                storeSlots[i].setInstruction(instruction);
                storeSlots[i].setAll("S"+i,true,v,q,false,false);
                updateNumOfUsedStoreSlots();
                break;
            }
        }
    }

    public void removeLoadInstruction(int index, Double v, String q, boolean finished, boolean published) {
        loadSlots[index].setInstruction(null);
        loadSlots[index].setAll("L"+index,false, null,null,finished,published);
        updateNumOfUsedLoadSlots();
    }

    public void removeStoreInstruction(int index, int v, String q, boolean finished, boolean published) {
        storeSlots[index].setInstruction(null);
        storeSlots[index].setAll("S"+index,false, null,null, finished, published);
        updateNumOfUsedStoreSlots();
    }

    public boolean isEmpty() {
        return isLoadBufferEmpty() && isStoreBufferEmpty();
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < loadBufferSize; i++) {
            s += loadSlots[i].toString() + "\n";
        }
        for (int i = 0; i < storeBufferSize; i++) {
            s += storeSlots[i].toString() + "\n";
        }
        return s;
    }

}
