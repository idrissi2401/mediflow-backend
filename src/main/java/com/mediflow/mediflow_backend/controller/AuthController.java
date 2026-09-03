package com.mediflow.mediflow_backend.controller;

import com.mediflow.mediflow_backend.dto.LoginRequest;
import com.mediflow.mediflow_backend.entity.Utilisateur;
import com.mediflow.mediflow_backend.service.AuthService;
import com.mediflow.mediflow_backend.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        Optional<Utilisateur> utilisateur = authService.login(
                loginRequest.getEmail(),
                loginRequest.getMotDePasse()
        );

        if (utilisateur.isEmpty()) {
            return ResponseEntity.status(401)
                    .body("Email ou mot de passe incorrect");
        }

        String token = jwtService.generateToken(utilisateur.get());

        return ResponseEntity.ok(token);
    }
}