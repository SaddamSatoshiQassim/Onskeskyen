package com.example.Onskeskyen.models;

import java.time.LocalDateTime;

public class Onskeliste {

    private int onskelisteId;
    private int brugerId;
    private String titel;
    private String beskrivelse;
    private boolean offentlig;
    private String delingslink;
    private LocalDateTime oprettetDato;

    public Onskeliste(int onskelisteId, int brugerId, String titel,
                      String beskrivelse, boolean offentlig,
                      String delingslink, LocalDateTime oprettetDato) {

        this.onskelisteId = onskelisteId;
        this.brugerId = brugerId;
        this.titel = titel;
        this.beskrivelse = beskrivelse;
        this.offentlig = offentlig;
        this.delingslink = delingslink;
        this.oprettetDato = oprettetDato;
    }

    public Onskeliste(int brugerId, String titel, String beskrivelse,
                      boolean offentlig, String delingslink) {

        this.brugerId = brugerId;
        this.titel = titel;
        this.beskrivelse = beskrivelse;
        this.offentlig = offentlig;
        this.delingslink = delingslink;
        this.oprettetDato = LocalDateTime.now();
    }

    public void setOnskelisteId(int onskelisteId) {
        this.onskelisteId = onskelisteId;
    }

    public void setDelingslink(String delingslink) {
        this.delingslink = delingslink;
    }

    public int getOnskelisteId() {
        return onskelisteId;
    }

    public int getBrugerId() {
        return brugerId;
    }

    public String getTitel() {
        return titel;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public boolean isOffentlig() {
        return offentlig;
    }

    public String getDelingslink() {
        return delingslink;
    }

    public LocalDateTime getOprettetDato() {
        return oprettetDato;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public void setOffentlig(boolean offentlig) {
        this.offentlig = offentlig;
    }
}