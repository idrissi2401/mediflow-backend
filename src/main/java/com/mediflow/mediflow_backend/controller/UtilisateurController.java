package com.mediflow.mediflow_backend.controller;

import com.mediflow.mediflow_backend.entity.Utilisateur;
import com.mediflow.mediflow_backend.service.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUtilisateurById(@PathVariable Long id) {

        Optional<Utilisateur> utilisateur =
                utilisateurService.getUtilisateurById(id);

        if (utilisateur.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(utilisateur.get());
    }

    @PostMapping
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.saveUtilisateur(utilisateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUtilisateur(
            @PathVariable Long id,
            @RequestBody Utilisateur utilisateurModifie) {

        Optional<Utilisateur> utilisateurOptional =
                utilisateurService.getUtilisateurById(id);

        if (utilisateurOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Utilisateur utilisateur = utilisateurOptional.get();

        utilisateur.setNom(utilisateurModifie.getNom());
        utilisateur.setPrenom(utilisateurModifie.getPrenom());
        utilisateur.setEmail(utilisateurModifie.getEmail());
        utilisateur.setRole(utilisateurModifie.getRole());
        utilisateur.setActif(utilisateurModifie.isActif());

        Utilisateur utilisateurEnregistre =
                utilisateurService.updateUtilisateur(utilisateur);

        return ResponseEntity.ok(utilisateurEnregistre);
    }
}