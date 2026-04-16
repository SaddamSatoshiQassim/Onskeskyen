package com.example.Onskeskyen.models;

public class Reservation {

    private int itemId;
    private int brugerId;
    private int antal;

    public Reservation(int itemId, int brugerId, int antal) {
        this.itemId = itemId;
        this.brugerId = brugerId;
        this.antal = antal;
    }

    public String visReservationStatus() {
        return "Reserveret: " + antal;
    }

    public int getItemId() { return itemId; }
    public int getBrugerId() { return brugerId; }
    public int getAntal() { return antal; }
}