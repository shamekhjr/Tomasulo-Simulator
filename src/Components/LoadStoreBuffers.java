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
            loadSlots[i] = new LoadStoreSlot("L"+i, true);
        }
        for (int i = 0; i < storeBufferSize; i++) {
            storeSlots[i] = new LoadStoreSlot("S"+i, false);
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
            loadSlots[i] = new LoadStoreSlot("L"+i, true);
        }
        for (int i = 0; i < storeBufferSize; i++) {
            storeSlots[i] = new LoadStoreSlot("S"+i, false);
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

    public LoadStoreSlot addLoadInstruction(Instruction instruction) {
        for (int i = 0; i < loadBufferSize; i++) {
            if (!loadSlots[i].isBusy()) {
                loadSlots[i].setInstruction(instruction);
                loadSlots[i].setAll("L"+i,true, null,null, true, false, false, instruction.getEffectiveAddress());
                updateNumOfUsedLoadSlots();
                return loadSlots[i];

            }
        }
        return null;
    }

    public void addStoreInstruction(Instruction instruction, Double v, String q) {
        for (int i = 0; i < storeBufferSize; i++) {
            if (!storeSlots[i].isBusy()) {
                storeSlots[i].setInstruction(instruction);
                storeSlots[i].setAll("S"+i,true,v,q,false,false, false, instruction.getEffectiveAddress());
                updateNumOfUsedStoreSlots();
                break;
            }
        }
    }

    //remove an instruction that takes tag as input from the reservation station then update usedStations
    public void removeLoadInstruction(String tag) {
        for (int i = 0; i < loadBufferSize; i++) {
            if (loadSlots[i].isBusy() && loadSlots[i].getTag().equals(tag)) {
                loadSlots[i].setInstruction(null);
                loadSlots[i].setAll(tag, null,false, null,null, false, false, false);
                updateNumOfUsedLoadSlots();
                break;
            }
        }
    }

    public void removeStoreInstruction(String tag) {
        for (int i = 0; i < storeBufferSize; i++) {
            if (storeSlots[i].getTag().equals(tag)) {
                storeSlots[i].setInstruction(null);
                storeSlots[i].setAll(tag,false, null,null, false, false, false, -1);
                updateNumOfUsedStoreSlots();
                break;
            }
        }
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

    public String loadBufferForGui() {
        // columns tag and address
        String s = "Tag\tAddress\n";
        for (int i = 0; i < loadBufferSize; i++) {
            s += loadSlots[i].loadForGUI() + "\n";
        }
        return s;
    }

    public String storeBufferForGui() {
        // columns tag and address
        String s = "Tag\tValue\tAddress\n";
        for (int i = 0; i < storeBufferSize; i++) {
            s += storeSlots[i].storeForGUI() + "\n";
        }
        return s;
    }

}
