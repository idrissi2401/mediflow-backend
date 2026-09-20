package com.mediflow.mediflow_backend.controller;

import com.mediflow.mediflow_backend.entity.Utilisateur;
import com.mediflow.mediflow_backend.service.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(
            UtilisateurService utilisateurService) {

        this.utilisateurService = utilisateurService;
    }


    // =========================
    // LISTE
    // =========================

    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {

        return utilisateurService.getAllUtilisateurs();
    }


    // =========================
    // UTILISATEUR PAR ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<?> getUtilisateurById(
            @PathVariable Long id) {

        Optional<Utilisateur> utilisateur =
                utilisateurService.getUtilisateurById(id);

        if (utilisateur.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(
                utilisateur.get()
        );
    }


    // =========================
    // CRÉATION
    // =========================

    @PostMapping
    public ResponseEntity<?> createUtilisateur(
            @RequestBody Utilisateur utilisateur) {

        try {

            Utilisateur utilisateurEnregistre =
                    utilisateurService
                            .saveUtilisateur(utilisateur);

            return ResponseEntity.ok(
                    utilisateurEnregistre
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // =========================
    // MODIFICATION
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUtilisateur(
            @PathVariable Long id,
            @RequestBody Utilisateur utilisateurModifie,
            Authentication authentication) {

        Optional<Utilisateur> utilisateurOptional =
                utilisateurService
                        .getUtilisateurById(id);

        if (utilisateurOptional.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        Utilisateur utilisateur =
                utilisateurOptional.get();


        // =========================
        // EMPÊCHER L'AUTO-DÉSACTIVATION
        // =========================

        String emailUtilisateurConnecte =
                authentication.getName();

        if (
                utilisateur.getEmail()
                        .equalsIgnoreCase(emailUtilisateurConnecte)
                        &&
                        !utilisateurModifie.isActif()
        ) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "Vous ne pouvez pas désactiver votre propre compte."
                    );
        }


        // =========================
        // MODIFICATION
        // =========================

        utilisateur.setNom(
                utilisateurModifie.getNom()
        );

        utilisateur.setPrenom(
                utilisateurModifie.getPrenom()
        );

        utilisateur.setEmail(
                utilisateurModifie.getEmail()
        );

        utilisateur.setRole(
                utilisateurModifie.getRole()
        );

        utilisateur.setActif(
                utilisateurModifie.isActif()
        );


        try {

            Utilisateur utilisateurEnregistre =
                    utilisateurService
                            .updateUtilisateur(utilisateur);

            return ResponseEntity.ok(
                    utilisateurEnregistre
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}