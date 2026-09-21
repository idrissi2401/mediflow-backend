package com.mediflow.mediflow_backend.dto;

import java.time.LocalDateTime;

public class OrdonnanceDto {

    private LocalDateTime dateHeure;
    private ReferenceDto consultation;

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public ReferenceDto getConsultation() {
        return consultation;
    }

    public void setConsultation(ReferenceDto consultation) {
        this.consultation = consultation;
    }
}