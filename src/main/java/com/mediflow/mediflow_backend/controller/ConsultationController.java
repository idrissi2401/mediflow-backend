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

    public ConsultationController(
            ConsultationService consultationService
    ) {
        this.consultationService = consultationService;
    }


    // =========================
    // TOUTES LES CONSULTATIONS
    // =========================

    @GetMapping
    public List<Consultation> getAllConsultations() {

        return consultationService
                .getAllConsultations();
    }


    // =========================
    // CONSULTATION PAR ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<?> getConsultationById(
            @PathVariable Long id
    ) {

        Optional<Consultation> consultation =
                consultationService
                        .getConsultationById(id);

        if (consultation.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(
                consultation.get()
        );
    }


    // =========================
    // CONSULTATION PAR
    // RENDEZ-VOUS
    // =========================

    @GetMapping("/rendez-vous/{rendezVousId}")
    public ResponseEntity<?> getConsultationByRendezVousId(
            @PathVariable Long rendezVousId
    ) {

        Optional<Consultation> consultation =
                consultationService
                        .getConsultationByRendezVousId(
                                rendezVousId
                        );

        if (consultation.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(
                consultation.get()
        );
    }


    // =========================
    // CONSULTATIONS PAR PATIENT
    // =========================

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Consultation>>
    getConsultationsByPatient(
            @PathVariable Long patientId
    ) {

        List<Consultation> consultations =
                consultationService
                        .getConsultationsByPatient(
                                patientId
                        );

        return ResponseEntity.ok(
                consultations
        );
    }


    // =========================
    // CRÉER UNE CONSULTATION
    // =========================

    @PostMapping
    public Consultation createConsultation(
            @RequestBody Consultation consultation
    ) {

        return consultationService
                .saveConsultation(
                        consultation
                );
    }


    // =========================
    // MODIFIER UNE CONSULTATION
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateConsultation(
            @PathVariable Long id,
            @RequestBody Consultation consultationModifiee
    ) {

        Optional<Consultation> consultationOptional =
                consultationService
                        .getConsultationById(id);

        if (consultationOptional.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        Consultation consultation =
                consultationOptional.get();


        consultation.setDateHeure(
                consultationModifiee
                        .getDateHeure()
        );

        consultation.setNotes(
                consultationModifiee
                        .getNotes()
        );

        consultation.setDiagnostic(
                consultationModifiee
                        .getDiagnostic()
        );

        consultation.setRendezVous(
                consultationModifiee
                        .getRendezVous()
        );


        Consultation consultationEnregistree =
                consultationService
                        .updateConsultation(
                                consultation
                        );


        return ResponseEntity.ok(
                consultationEnregistree
        );
    }
}