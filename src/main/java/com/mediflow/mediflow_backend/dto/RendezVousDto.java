package com.mediflow.mediflow_backend.dto;

import java.time.LocalDateTime;

public class RendezVousDto {

    private LocalDateTime dateHeure;
    private String motif;
    private boolean annule;

    private ReferenceDto patient;
    private ReferenceDto medecin;
    private ReferenceDto accueil;

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public boolean isAnnule() {
        return annule;
    }

    public void setAnnule(boolean annule) {
        this.annule = annule;
    }

    public ReferenceDto getPatient() {
        return patient;
    }

    public void setPatient(ReferenceDto patient) {
        this.patient = patient;
    }

    public ReferenceDto getMedecin() {
        return medecin;
    }

    public void setMedecin(ReferenceDto medecin) {
        this.medecin = medecin;
    }

    public ReferenceDto getAccueil() {
        return accueil;
    }

    public void setAccueil(ReferenceDto accueil) {
        this.accueil = accueil;
    }
}