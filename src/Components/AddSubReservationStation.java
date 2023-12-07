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

    //get the size of the reservation station
    public int getSize() {
        return size;
    }

    //check if the reservation station is full
    public boolean isFull() {
        return numOfUsedStations == size;
    }

    //check if the reservation station is empty
    public boolean isEmpty() {
        return numOfUsedStations == 0;
    }

    //TODO: update reservation station used slots
}
