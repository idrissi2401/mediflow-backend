package com.mediflow.mediflow_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rendez_vous")
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateHeure;

    @Column(nullable = false)
    private String motif;

    @Column(nullable = false)
    private boolean annule = false;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Rendez-vous médical
    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Utilisateur medecin;

    // Rendez-vous administratif
    @ManyToOne
    @JoinColumn(name = "accueil_id")
    private Utilisateur accueil;


    public RendezVous() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


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


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    public Utilisateur getMedecin() {
        return medecin;
    }

    public void setMedecin(Utilisateur medecin) {
        this.medecin = medecin;
    }


    public Utilisateur getAccueil() {
        return accueil;
    }

    public void setAccueil(Utilisateur accueil) {
        this.accueil = accueil;
    }

}