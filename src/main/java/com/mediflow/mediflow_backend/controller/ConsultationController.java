package com.mediflow.mediflow_backend.controller;

import com.mediflow.mediflow_backend.entity.Consultation;
import com.mediflow.mediflow_backend.service.ConsultationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping
    public List<Consultation> getAllConsultations() {
        return consultationService.getAllConsultations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConsultationById(@PathVariable Long id) {

        Optional<Consultation> consultation =
                consultationService.getConsultationById(id);

        if (consultation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(consultation.get());
    }

    @PostMapping
    public Consultation createConsultation(
            @RequestBody Consultation consultation) {

        return consultationService.saveConsultation(consultation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateConsultation(
            @PathVariable Long id,
            @RequestBody Consultation consultationModifiee) {

        Optional<Consultation> consultationOptional =
                consultationService.getConsultationById(id);

        if (consultationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Consultation consultation = consultationOptional.get();

        consultation.setDateHeure(consultationModifiee.getDateHeure());
        consultation.setNotes(consultationModifiee.getNotes());
        consultation.setDiagnostic(consultationModifiee.getDiagnostic());
        consultation.setRendezVous(consultationModifiee.getRendezVous());

        Consultation consultationEnregistree =
                consultationService.updateConsultation(consultation);

        return ResponseEntity.ok(consultationEnregistree);
    }
}