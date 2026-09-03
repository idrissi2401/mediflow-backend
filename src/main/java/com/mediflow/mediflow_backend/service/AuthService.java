package com.mediflow.mediflow_backend.service;

import com.mediflow.mediflow_backend.entity.Utilisateur;
import com.mediflow.mediflow_backend.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UtilisateurRepository utilisateurRepository,
                       PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Utilisateur> login(String email, String motDePasse) {

        Optional<Utilisateur> utilisateurOptional =
                utilisateurRepository.findByEmail(email);

        if (utilisateurOptional.isEmpty()) {
            return Optional.empty();
        }

        Utilisateur utilisateur = utilisateurOptional.get();

        if (!utilisateur.isActif()) {
            return Optional.empty();
        }

        if (!passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
            return Optional.empty();
        }

        return Optional.of(utilisateur);
    }
}