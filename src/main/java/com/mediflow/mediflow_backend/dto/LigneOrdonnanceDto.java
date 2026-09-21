package com.mediflow.mediflow_backend.dto;

public class LigneOrdonnanceDto {

    private String medicament;
    private String dosage;
    private String frequence;
    private String duree;
    private ReferenceDto ordonnance;

    public String getMedicament() {
        return medicament;
    }

    public void setMedicament(String medicament) {
        this.medicament = medicament;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequence() {
        return frequence;
    }

    public void setFrequence(String frequence) {
        this.frequence = frequence;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public ReferenceDto getOrdonnance() {
        return ordonnance;
    }

    public void setOrdonnance(ReferenceDto ordonnance) {
        this.ordonnance = ordonnance;
    }
}