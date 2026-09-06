package com.mediflow.mediflow_backend.controller;

import com.mediflow.mediflow_backend.entity.RendezVous;
import com.mediflow.mediflow_backend.service.RendezVousService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rendez-vous")
public class RendezVousController {

    private final RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @GetMapping
    public List<RendezVous> getAllRendezVous() {
        return rendezVousService.getAllRendezVous();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRendezVousById(@PathVariable Long id) {

        Optional<RendezVous> rendezVous =
                rendezVousService.getRendezVousById(id);

        if (rendezVous.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rendezVous.get());
    }

    @GetMapping("/medecin/{medecinId}")
    public List<RendezVous> getRendezVousMedecin(
            @PathVariable Long medecinId,
            @RequestParam LocalDateTime debut,
            @RequestParam LocalDateTime fin) {

        return rendezVousService.getRendezVousMedecin(
                medecinId,
                debut,
                fin
        );
    }

    @PostMapping
    public ResponseEntity<?> createRendezVous(
            @RequestBody RendezVous rendezVous) {

        try {
            RendezVous rendezVousEnregistre =
                    rendezVousService.saveRendezVous(rendezVous);

            return ResponseEntity.ok(rendezVousEnregistre);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(409)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRendezVous(
            @PathVariable Long id,
            @RequestBody RendezVous rendezVousModifie) {

        Optional<RendezVous> rendezVousOptional =
                rendezVousService.getRendezVousById(id);

        if (rendezVousOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RendezVous rendezVous = rendezVousOptional.get();

        rendezVous.setDateHeure(rendezVousModifie.getDateHeure());
        rendezVous.setMotif(rendezVousModifie.getMotif());
        rendezVous.setAnnule(rendezVousModifie.isAnnule());
        rendezVous.setPatient(rendezVousModifie.getPatient());
        rendezVous.setMedecin(rendezVousModifie.getMedecin());

        RendezVous rendezVousEnregistre =
                rendezVousService.updateRendezVous(rendezVous);

        return ResponseEntity.ok(rendezVousEnregistre);
    }
}