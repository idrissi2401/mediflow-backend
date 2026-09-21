package com.mediflow.mediflow_backend.dto;

import java.time.LocalDateTime;

public class ConsultationDto {

    private LocalDateTime dateHeure;
    private String notes;
    private String diagnostic;
    private ReferenceDto rendezVous;

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public ReferenceDto getRendezVous() {
        return rendezVous;
    }

    public void setRendezVous(ReferenceDto rendezVous) {
        this.rendezVous = rendezVous;
    }
}