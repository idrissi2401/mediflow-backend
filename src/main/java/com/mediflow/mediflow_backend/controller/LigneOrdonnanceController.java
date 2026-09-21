package com.mediflow.mediflow_backend.controller;

import com.mediflow.mediflow_backend.dto.LigneOrdonnanceDto;
import com.mediflow.mediflow_backend.dto.ReferenceDto;
import com.mediflow.mediflow_backend.entity.LigneOrdonnance;
import com.mediflow.mediflow_backend.entity.Ordonnance;
import com.mediflow.mediflow_backend.service.LigneOrdonnanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lignes-ordonnance")
public class LigneOrdonnanceController {

    private final LigneOrdonnanceService ligneOrdonnanceService;

    public LigneOrdonnanceController(
            LigneOrdonnanceService ligneOrdonnanceService
    ) {
        this.ligneOrdonnanceService = ligneOrdonnanceService;
    }


    // =========================
    // TOUTES LES LIGNES
    // =========================

    @GetMapping
    public List<LigneOrdonnance> getAllLignes() {
        return ligneOrdonnanceService.getAllLignes();
    }


    // =========================
    // LIGNE PAR ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<?> getLigneById(
            @PathVariable Long id
    ) {

        Optional<LigneOrdonnance> ligne =
                ligneOrdonnanceService.getLigneById(id);

        if (ligne.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ligne.get());
    }


    // =========================
    // LIGNES PAR ORDONNANCE
    // =========================

    @GetMapping("/ordonnance/{ordonnanceId}")
    public List<LigneOrdonnance> getLignesByOrdonnance(
            @PathVariable Long ordonnanceId
    ) {

        return ligneOrdonnanceService
                .getLignesByOrdonnance(ordonnanceId);
    }


    // =========================
    // CRÉER UNE LIGNE
    // =========================

    @PostMapping
    public LigneOrdonnance createLigne(
            @RequestBody LigneOrdonnanceDto ligneDto
    ) {

        LigneOrdonnance ligne =
                convertirDto(ligneDto);

        return ligneOrdonnanceService
                .saveLigne(ligne);
    }


    // =========================
    // MODIFIER UNE LIGNE
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLigne(
            @PathVariable Long id,
            @RequestBody LigneOrdonnanceDto ligneDto
    ) {

        Optional<LigneOrdonnance> ligneOptional =
                ligneOrdonnanceService.getLigneById(id);

        if (ligneOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        LigneOrdonnance ligne =
                ligneOptional.get();

        ligne.setMedicament(
                ligneDto.getMedicament()
        );

        ligne.setDosage(
                ligneDto.getDosage()
        );

        ligne.setFrequence(
                ligneDto.getFrequence()
        );

        ligne.setDuree(
                ligneDto.getDuree()
        );

        ligne.setOrdonnance(
                creerReferenceOrdonnance(
                        ligneDto.getOrdonnance()
                )
        );

        LigneOrdonnance ligneEnregistree =
                ligneOrdonnanceService
                        .updateLigne(ligne);

        return ResponseEntity.ok(
                ligneEnregistree
        );
    }


    // =========================
    // SUPPRIMER UNE LIGNE
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLigne(
            @PathVariable Long id
    ) {

        Optional<LigneOrdonnance> ligneOptional =
                ligneOrdonnanceService.getLigneById(id);

        if (ligneOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ligneOrdonnanceService.deleteLigne(id);

        return ResponseEntity.noContent().build();
    }


    // =========================
    // CONVERSION DTO
    // =========================

    private LigneOrdonnance convertirDto(
            LigneOrdonnanceDto ligneDto
    ) {

        LigneOrdonnance ligne =
                new LigneOrdonnance();

        ligne.setMedicament(
                ligneDto.getMedicament()
        );

        ligne.setDosage(
                ligneDto.getDosage()
        );

        ligne.setFrequence(
                ligneDto.getFrequence()
        );

        ligne.setDuree(
                ligneDto.getDuree()
        );

        ligne.setOrdonnance(
                creerReferenceOrdonnance(
                        ligneDto.getOrdonnance()
                )
        );

        return ligne;
    }


    private Ordonnance creerReferenceOrdonnance(
            ReferenceDto referenceDto
    ) {

        if (referenceDto == null ||
                referenceDto.getId() == null) {
            return null;
        }

        Ordonnance ordonnance =
                new Ordonnance();

        ordonnance.setId(
                referenceDto.getId()
        );

        return ordonnance;
    }
}