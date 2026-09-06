package com.mediflow.mediflow_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lignes_ordonnance")
public class LigneOrdonnance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String medicament;

    @Column(nullable = false)
    private String dosage;

    @Column(nullable = false)
    private String frequence;

    @Column(nullable = false)
    private String duree;

    @ManyToOne
    @JoinColumn(name = "ordonnance_id", nullable = false)
    private Ordonnance ordonnance;

    public LigneOrdonnance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Ordonnance getOrdonnance() {
        return ordonnance;
    }

    public void setOrdonnance(Ordonnance ordonnance) {
        this.ordonnance = ordonnance;
    }
}