package com.mediflow.mediflow_backend.controller;

import com.mediflow.mediflow_backend.dto.LoginRequest;
import com.mediflow.mediflow_backend.entity.Utilisateur;
import com.mediflow.mediflow_backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        Optional<Utilisateur> utilisateur = authService.login(
                loginRequest.getEmail(),
                loginRequest.getMotDePasse()
        );

        if (utilisateur.isEmpty()) {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }

        return ResponseEntity.ok(utilisateur.get());
    }
}
