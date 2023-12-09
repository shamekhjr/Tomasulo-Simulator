package Components;

public class AddSubReservationStation {
    private ReservationStationSlot[] addSubReservationStationSlots;
    private final int size;
    private int numOfUsedStations;

    public AddSubReservationStation() {
        size = 3;
        numOfUsedStations = 0;
        addSubReservationStationSlots = new ReservationStationSlot[size];
        for (int i = 0; i < 3; i++) {
            addSubReservationStationSlots[i] = new ReservationStationSlot();
        }

    }

    //constructor that takes size as input
    public AddSubReservationStation(int size) {
        this.size = size;
        numOfUsedStations = 0;
        addSubReservationStationSlots = new ReservationStationSlot[this.size];
        for (int i = 0; i < size; i++) {
            addSubReservationStationSlots[i] = new ReservationStationSlot();
        }
    }

    public ReservationStationSlot[] getAddSubReservationStationSlots() {
        return addSubReservationStationSlots;
    }

    //get an item at a specific index
    public ReservationStationSlot getAddSubReservationStationSlot(int index) {
        return addSubReservationStationSlots[index];
    }

    //set the whole reservation station
    public void setAddSubReservationStationSlots(ReservationStationSlot[] addSubReservationStationSlots) {
        this.addSubReservationStationSlots = addSubReservationStationSlots;
    }

    //set an item at a specific index
    public void setAddSubReservationStationSlot(int index, ReservationStationSlot addSubReservationStationSlot) {
        addSubReservationStationSlots[index] = addSubReservationStationSlot;
    }

    //add instruction to the first free reservation station then updated usedStations
    public void addInstruction(Instruction instruction, Double vJ, Double vK, String qJ, String qK) {
        for (int i = 0; i < size; i++) {
            if (!addSubReservationStationSlots[i].isBusy()) {
                addSubReservationStationSlots[i].setInstruction(instruction);
                addSubReservationStationSlots[i].setAll("A"+i,true,vJ,vK,qJ,qK,false,false, false);
                updateNumOfUsedStationsA();
                break;
            }
        }
    }
    //remove an instruction from the reservation station then update usedStations
    public void removeInstruction(int index) {
        addSubReservationStationSlots[index].setInstruction(null);
        addSubReservationStationSlots[index].setAll("A"+index,false, null, null,null,null,false,false, false);
        updateNumOfUsedStationsA();
    }



    //get the size of the reservation station
    public int getSize() {
        return size;
    }

    //check if the reservation station is full
    public boolean isFull() {
        return numOfUsedStations == size;
    }

    //check if the reservation station is empty
    public boolean hasFreeStations() {
        return numOfUsedStations < size;
    }
    public void updateNumOfUsedStationsA() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (addSubReservationStationSlots[i].isBusy()) {
                count++;
            }
        }
        numOfUsedStations = count;
    }

    public boolean isEmpty() {
        return numOfUsedStations == 0;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < size; i++) {
            s += addSubReservationStationSlots[i].toString() + "\n";
        }
        return s;
    }
}
