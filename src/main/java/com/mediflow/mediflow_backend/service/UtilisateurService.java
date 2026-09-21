package com.mediflow.mediflow_backend.service;

import com.mediflow.mediflow_backend.entity.Utilisateur;
import com.mediflow.mediflow_backend.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");


    public UtilisateurService(
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder) {

        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }


    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }


    public Optional<Utilisateur> getUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }


    // =========================
    // CRÉATION
    // =========================

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {

        verifierUtilisateur(utilisateur, null);


        // Mot de passe obligatoire à la création

        if (
                utilisateur.getMotDePasse() == null ||
                        utilisateur.getMotDePasse().trim().isEmpty()
        ) {

            throw new IllegalArgumentException(
                    "Le mot de passe est obligatoire."
            );
        }


        // Chiffrement du mot de passe

        utilisateur.setMotDePasse(
                passwordEncoder.encode(
                        utilisateur.getMotDePasse()
                )
        );


        return utilisateurRepository.save(utilisateur);
    }


    // =========================
    // MODIFICATION
    // =========================

    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {

        verifierUtilisateur(
                utilisateur,
                utilisateur.getId()
        );

        /*
         * On ne chiffre pas de nouveau le mot de passe ici.
         * Le mot de passe existant reste inchangé.
         */

        return utilisateurRepository.save(utilisateur);
    }


    // =========================
    // VALIDATIONS
    // =========================

    private void verifierUtilisateur(
            Utilisateur utilisateur,
            Long utilisateurId) {


        // =========================
        // NOM
        // =========================

        if (
                utilisateur.getNom() == null ||
                        utilisateur.getNom().trim().isEmpty()
        ) {

            throw new IllegalArgumentException(
                    "Le nom est obligatoire."
            );
        }


        // =========================
        // PRÉNOM
        // =========================

        if (
                utilisateur.getPrenom() == null ||
                        utilisateur.getPrenom().trim().isEmpty()
        ) {

            throw new IllegalArgumentException(
                    "Le prénom est obligatoire."
            );
        }


        // =========================
        // EMAIL
        // =========================

        if (
                utilisateur.getEmail() == null ||
                        utilisateur.getEmail().trim().isEmpty()
        ) {

            throw new IllegalArgumentException(
                    "L'adresse email est obligatoire."
            );
        }


        String email =
                utilisateur
                        .getEmail()
                        .trim()
                        .toLowerCase();


        if (!EMAIL_PATTERN.matcher(email).matches()) {

            throw new IllegalArgumentException(
                    "L'adresse email est invalide."
            );
        }


        // =========================
        // EMAIL UNIQUE
        // =========================

        Optional<Utilisateur> utilisateurAvecEmail =
                utilisateurRepository.findByEmail(email);


        if (utilisateurAvecEmail.isPresent()) {

            Utilisateur utilisateurExistant =
                    utilisateurAvecEmail.get();


            /*
             * Création :
             * utilisateurId est null.
             *
             * Modification :
             * on accepte l'adresse email si elle appartient
             * déjà à l'utilisateur que l'on modifie.
             */

            if (
                    utilisateurId == null ||
                            !utilisateurExistant
                                    .getId()
                                    .equals(utilisateurId)
            ) {

                throw new IllegalArgumentException(
                        "Cette adresse email est déjà utilisée."
                );
            }
        }


        // =========================
        // RÔLE
        // =========================

        if (utilisateur.getRole() == null) {

            throw new IllegalArgumentException(
                    "Le rôle est obligatoire."
            );
        }


        // =========================
        // NETTOYAGE
        // =========================

        utilisateur.setNom(
                utilisateur.getNom().trim()
        );

        utilisateur.setPrenom(
                utilisateur.getPrenom().trim()
        );

        utilisateur.setEmail(email);
    }
}