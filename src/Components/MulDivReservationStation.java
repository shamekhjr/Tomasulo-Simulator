package Components;

public class MulDivReservationStation {
    private ReservationStationSlot[] mulDivReservationStationSlots;
    private final int size;
    private int numOfUsedStations;

    public MulDivReservationStation() {
        size = 2;
        numOfUsedStations = 0;
        mulDivReservationStationSlots = new ReservationStationSlot[size];
        for (int i = 0; i < 2; i++) {
            mulDivReservationStationSlots[i] = new ReservationStationSlot();
        }

    }

    //constructor that takes size as input
    public MulDivReservationStation(int size) {
        this.size = size;
        numOfUsedStations = 0;
        mulDivReservationStationSlots = new ReservationStationSlot[this.size];
        for (int i = 0; i < size; i++) {
            mulDivReservationStationSlots[i] = new ReservationStationSlot();
        }
    }

    public ReservationStationSlot[] getMulDivReservationStationSlots() {
        return mulDivReservationStationSlots;
    }

    //get an item at a specific index
    public ReservationStationSlot getMulDivReservationStationSlot(int index) {
        return mulDivReservationStationSlots[index];
    }

    //set the whole reservation station
    public void setMulDivReservationStationSlots(ReservationStationSlot[] mulDivReservationStationSlots) {
        this.mulDivReservationStationSlots = mulDivReservationStationSlots;
    }

    //set an item at a specific index
    public void setMulDivReservationStationSlot(int index, ReservationStationSlot mulDivReservationStationSlot) {
        mulDivReservationStationSlots[index] = mulDivReservationStationSlot;
    }

    //add instruction to the first free reservation station then updated usedStations
    public void addInstruction(Instruction instruction, Double vJ, Double vK, String qJ, String qK) {
        for (int i = 0; i < size; i++) {
            if (!mulDivReservationStationSlots[i].isBusy()) {
                mulDivReservationStationSlots[i].setInstruction(instruction);
                mulDivReservationStationSlots[i].setAll("M"+i,true,vJ,vK,qJ,qK,false,false, false);
                updateNumOfUsedStationsM();
                break;
            }
        }
    }
    //remove an instruction from the reservation station then update usedStations
    public void removeInstruction(int index) {
        mulDivReservationStationSlots[index].setInstruction(null);
        mulDivReservationStationSlots[index].setAll("M" + index, false, null, null, null, null, false, false, false);
        updateNumOfUsedStationsM();
    }

    //update the number of used stations
    public void updateNumOfUsedStationsM() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (mulDivReservationStationSlots[i].isBusy()) {
                count++;
            }
        }
        numOfUsedStations = count;
    }

    public boolean hasFreeStations() {
        return numOfUsedStations < size;
    }

    public boolean isEmpty() {
        return numOfUsedStations == 0;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < size; i++) {
            s += mulDivReservationStationSlots[i].toString() + "\n";
        }
        return s;
    }


}
