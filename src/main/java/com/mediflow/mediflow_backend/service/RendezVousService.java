package com.mediflow.mediflow_backend.service;

import com.mediflow.mediflow_backend.entity.RendezVous;
import com.mediflow.mediflow_backend.repository.RendezVousRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;

    public RendezVousService(RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }

    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    public Optional<RendezVous> getRendezVousById(Long id) {
        return rendezVousRepository.findById(id);
    }

    public List<RendezVous> getRendezVousMedecin(
            Long medecinId,
            LocalDateTime debut,
            LocalDateTime fin) {

        return rendezVousRepository
                .findByMedecinIdAndDateHeureBetweenOrderByDateHeure(
                        medecinId,
                        debut,
                        fin
                );
    }

    public RendezVous saveRendezVous(RendezVous rendezVous) {

        boolean creneauOccupe =
                rendezVousRepository.existsByMedecinIdAndDateHeureAndAnnuleFalse(
                        rendezVous.getMedecin().getId(),
                        rendezVous.getDateHeure()
                );

        if (creneauOccupe) {
            throw new IllegalArgumentException("Ce créneau est déjà occupé");
        }

        return rendezVousRepository.save(rendezVous);
    }

    public RendezVous updateRendezVous(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }
}