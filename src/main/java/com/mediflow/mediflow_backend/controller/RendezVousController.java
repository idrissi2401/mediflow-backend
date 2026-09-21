package com.mediflow.mediflow_backend.controller;

import com.mediflow.mediflow_backend.dto.RendezVousDto;
import com.mediflow.mediflow_backend.entity.Patient;
import com.mediflow.mediflow_backend.entity.RendezVous;
import com.mediflow.mediflow_backend.entity.Utilisateur;
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

    public RendezVousController(
            RendezVousService rendezVousService) {

        this.rendezVousService = rendezVousService;
    }


    // =========================
    // RÉCUPÉRER TOUS LES RDV
    // =========================

    @GetMapping
    public List<RendezVous> getAllRendezVous() {

        return rendezVousService.getAllRendezVous();
    }


    // =========================
    // RÉCUPÉRER UN RDV
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<?> getRendezVousById(
            @PathVariable Long id) {

        Optional<RendezVous> rendezVous =
                rendezVousService.getRendezVousById(id);

        if (rendezVous.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(
                rendezVous.get()
        );
    }


    // =========================
    // RDV D'UN MÉDECIN
    // =========================

    @GetMapping("/medecin/{medecinId}")
    public List<RendezVous> getRendezVousMedecin(
            @PathVariable Long medecinId,
            @RequestParam LocalDateTime debut,
            @RequestParam LocalDateTime fin) {

        return rendezVousService
                .getRendezVousMedecin(
                        medecinId,
                        debut,
                        fin
                );
    }


    // =========================
    // CRÉER UN RDV
    // =========================

    @PostMapping
    public ResponseEntity<?> createRendezVous(
            @RequestBody RendezVousDto rendezVousDto) {

        try {

            RendezVous rendezVous =
                    convertirDto(rendezVousDto);

            RendezVous rendezVousEnregistre =
                    rendezVousService
                            .saveRendezVous(rendezVous);

            return ResponseEntity.ok(
                    rendezVousEnregistre
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(409)
                    .body(e.getMessage());
        }
    }


    // =========================
    // MODIFIER UN RDV
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRendezVous(
            @PathVariable Long id,
            @RequestBody RendezVousDto rendezVousDto) {

        Optional<RendezVous> rendezVousOptional =
                rendezVousService
                        .getRendezVousById(id);

        if (rendezVousOptional.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        RendezVous rendezVous =
                rendezVousOptional.get();

        rendezVous.setDateHeure(
                rendezVousDto.getDateHeure()
        );

        rendezVous.setMotif(
                rendezVousDto.getMotif()
        );

        rendezVous.setAnnule(
                rendezVousDto.isAnnule()
        );

        appliquerReferences(
                rendezVous,
                rendezVousDto
        );

        RendezVous rendezVousEnregistre =
                rendezVousService
                        .updateRendezVous(rendezVous);

        return ResponseEntity.ok(
                rendezVousEnregistre
        );
    }


    // =========================
    // CONVERSION DTO -> ENTITÉ
    // =========================

    private RendezVous convertirDto(
            RendezVousDto dto) {

        RendezVous rendezVous =
                new RendezVous();

        rendezVous.setDateHeure(
                dto.getDateHeure()
        );

        rendezVous.setMotif(
                dto.getMotif()
        );

        rendezVous.setAnnule(
                dto.isAnnule()
        );

        appliquerReferences(
                rendezVous,
                dto
        );

        return rendezVous;
    }


    // =========================
    // ASSOCIATIONS
    // =========================

    private void appliquerReferences(
            RendezVous rendezVous,
            RendezVousDto dto) {

        if (dto.getPatient() != null) {

            Patient patient =
                    new Patient();

            patient.setId(
                    dto.getPatient().getId()
            );

            rendezVous.setPatient(
                    patient
            );

        } else {

            rendezVous.setPatient(null);
        }


        if (dto.getMedecin() != null) {

            Utilisateur medecin =
                    new Utilisateur();

            medecin.setId(
                    dto.getMedecin().getId()
            );

            rendezVous.setMedecin(
                    medecin
            );

        } else {

            rendezVous.setMedecin(null);
        }


        if (dto.getAccueil() != null) {

            Utilisateur accueil =
                    new Utilisateur();

            accueil.setId(
                    dto.getAccueil().getId()
            );

            rendezVous.setAccueil(
                    accueil
            );

        } else {

            rendezVous.setAccueil(null);
        }
    }
}