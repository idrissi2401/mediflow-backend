package com.mediflow.mediflow_backend.controller;

import com.mediflow.mediflow_backend.entity.LigneOrdonnance;
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
            LigneOrdonnanceService ligneOrdonnanceService) {
        this.ligneOrdonnanceService = ligneOrdonnanceService;
    }

    @GetMapping
    public List<LigneOrdonnance> getAllLignes() {
        return ligneOrdonnanceService.getAllLignes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLigneById(@PathVariable Long id) {

        Optional<LigneOrdonnance> ligne =
                ligneOrdonnanceService.getLigneById(id);

        if (ligne.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ligne.get());
    }

    @PostMapping
    public LigneOrdonnance createLigne(
            @RequestBody LigneOrdonnance ligneOrdonnance) {

        return ligneOrdonnanceService.saveLigne(ligneOrdonnance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLigne(
            @PathVariable Long id,
            @RequestBody LigneOrdonnance ligneModifiee) {

        Optional<LigneOrdonnance> ligneOptional =
                ligneOrdonnanceService.getLigneById(id);

        if (ligneOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        LigneOrdonnance ligne = ligneOptional.get();

        ligne.setMedicament(ligneModifiee.getMedicament());
        ligne.setDosage(ligneModifiee.getDosage());
        ligne.setFrequence(ligneModifiee.getFrequence());
        ligne.setDuree(ligneModifiee.getDuree());
        ligne.setOrdonnance(ligneModifiee.getOrdonnance());

        LigneOrdonnance ligneEnregistree =
                ligneOrdonnanceService.updateLigne(ligne);

        return ResponseEntity.ok(ligneEnregistree);
    }
}