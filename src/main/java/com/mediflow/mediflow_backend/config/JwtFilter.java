package com.mediflow.mediflow_backend.config;

import com.mediflow.mediflow_backend.entity.Utilisateur;
import com.mediflow.mediflow_backend.repository.UtilisateurRepository;
import com.mediflow.mediflow_backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UtilisateurRepository utilisateurRepository;

    public JwtFilter(JwtService jwtService,
                     UtilisateurRepository utilisateurRepository) {
        this.jwtService = jwtService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null &&
                authorizationHeader.startsWith("Bearer ")) {

            String token = authorizationHeader.substring(7);

            try {
                String email = jwtService.extractEmail(token);

                Optional<Utilisateur> utilisateurOptional =
                        utilisateurRepository.findByEmail(email);

                if (utilisateurOptional.isPresent()) {

                    Utilisateur utilisateur = utilisateurOptional.get();

                    if (utilisateur.isActif()) {

                        SimpleGrantedAuthority authority =
                                new SimpleGrantedAuthority(
                                        "ROLE_" + utilisateur.getRole().name()
                                );

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        utilisateur.getEmail(),
                                        null,
                                        List.of(authority)
                                );

                        SecurityContextHolder.getContext()
                                .setAuthentication(authentication);
                    }
                }

            } catch (Exception ignored) {
            }
        }

        filterChain.doFilter(request, response);
    }
}