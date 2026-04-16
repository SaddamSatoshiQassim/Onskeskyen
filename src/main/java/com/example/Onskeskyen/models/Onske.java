package com.example.Onskeskyen.models;

public class Onske {
    private int onskeId;
    private int brugerId;
    private String navn;
    private String link;
    private double pris;
    private String billede;
    private int reserveretAfBrugerId;
    private boolean booket;

    public Onske(int onskeId, int brugerId, String navn, String link, double pris, String billede, boolean booket) {
        this.onskeId = onskeId;
        this.brugerId = brugerId;
        this.navn = navn;
        this.link = link;
        this.pris = pris;
        this.billede = billede;
        this.booket = booket;
    }

    public int getOnskeId() {
        return onskeId;
    }

    public void setOnskeId(int onskeId) {
        this.onskeId = onskeId;
    }

    public int getBrugerId() {
        return brugerId;
    }

    public void setBrugerId(int brugerId) {
        this.brugerId = brugerId;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getPris() {
        return pris;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }

    public String getBillede() {
        return billede;
    }

    public void setBillede(String billede) {
        this.billede = billede;
    }

    public Integer getReserveretAfBrugerId() {
        return reserveretAfBrugerId;
    }

    public void setReserveretAfBrugerId(Integer reserveretAfBrugerId) {
        this.reserveretAfBrugerId = reserveretAfBrugerId;
    }

    public boolean isBooket() {
        return booket;
    }

    public void setBooket(boolean booket) {
        this.booket = booket;
    }
}