package com.mediflow.mediflow_backend.service;

import com.mediflow.mediflow_backend.entity.Utilisateur;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "mediflow-secret-key-2026-mediflow-secret-key";

    private static final long EXPIRATION = 1000 * 60 * 60;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(Utilisateur utilisateur) {

        return Jwts.builder()
                .subject(utilisateur.getEmail())
                .claim("role", utilisateur.getRole().name())
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + EXPIRATION)
                )
                .signWith(getKey())
                .compact();
    }

    public String extractEmail(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}