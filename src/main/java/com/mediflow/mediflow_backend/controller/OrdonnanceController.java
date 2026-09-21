package com.mediflow.mediflow_backend.controller;

import com.mediflow.mediflow_backend.dto.OrdonnanceDto;
import com.mediflow.mediflow_backend.dto.ReferenceDto;
import com.mediflow.mediflow_backend.entity.Consultation;
import com.mediflow.mediflow_backend.entity.Ordonnance;
import com.mediflow.mediflow_backend.service.OrdonnanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordonnances")
public class OrdonnanceController {

    private final OrdonnanceService ordonnanceService;

    public OrdonnanceController(
            OrdonnanceService ordonnanceService
    ) {
        this.ordonnanceService = ordonnanceService;
    }


    // =========================
    // TOUTES LES ORDONNANCES
    // =========================

    @GetMapping
    public List<Ordonnance> getAllOrdonnances() {
        return ordonnanceService.getAllOrdonnances();
    }


    // =========================
    // ORDONNANCE PAR ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrdonnanceById(
            @PathVariable Long id
    ) {

        Optional<Ordonnance> ordonnance =
                ordonnanceService.getOrdonnanceById(id);

        if (ordonnance.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                ordonnance.get()
        );
    }


    // =========================
    // ORDONNANCE PAR CONSULTATION
    // =========================

    @GetMapping("/consultation/{consultationId}")
    public ResponseEntity<?> getOrdonnanceByConsultationId(
            @PathVariable Long consultationId
    ) {

        Optional<Ordonnance> ordonnance =
                ordonnanceService
                        .getOrdonnanceByConsultationId(
                                consultationId
                        );

        if (ordonnance.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                ordonnance.get()
        );
    }


    // =========================
    // CRÉER UNE ORDONNANCE
    // =========================

    @PostMapping
    public Ordonnance createOrdonnance(
            @RequestBody OrdonnanceDto ordonnanceDto
    ) {

        Ordonnance ordonnance =
                convertirDto(ordonnanceDto);

        return ordonnanceService
                .saveOrdonnance(ordonnance);
    }


    // =========================
    // MODIFIER UNE ORDONNANCE
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrdonnance(
            @PathVariable Long id,
            @RequestBody OrdonnanceDto ordonnanceDto
    ) {

        Optional<Ordonnance> ordonnanceOptional =
                ordonnanceService
                        .getOrdonnanceById(id);

        if (ordonnanceOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Ordonnance ordonnance =
                ordonnanceOptional.get();

        ordonnance.setDateHeure(
                ordonnanceDto.getDateHeure()
        );

        ordonnance.setConsultation(
                creerReferenceConsultation(
                        ordonnanceDto.getConsultation()
                )
        );

        Ordonnance ordonnanceEnregistree =
                ordonnanceService
                        .updateOrdonnance(ordonnance);

        return ResponseEntity.ok(
                ordonnanceEnregistree
        );
    }


    // =========================
    // CONVERSION DTO
    // =========================

    private Ordonnance convertirDto(
            OrdonnanceDto ordonnanceDto
    ) {

        Ordonnance ordonnance =
                new Ordonnance();

        ordonnance.setDateHeure(
                ordonnanceDto.getDateHeure()
        );

        ordonnance.setConsultation(
                creerReferenceConsultation(
                        ordonnanceDto.getConsultation()
                )
        );

        return ordonnance;
    }


    private Consultation creerReferenceConsultation(
            ReferenceDto referenceDto
    ) {

        if (referenceDto == null ||
                referenceDto.getId() == null) {
            return null;
        }

        Consultation consultation =
                new Consultation();

        consultation.setId(
                referenceDto.getId()
        );

        return consultation;
    }
}