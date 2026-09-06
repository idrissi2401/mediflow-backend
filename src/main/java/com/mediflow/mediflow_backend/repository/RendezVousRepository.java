package com.mediflow.mediflow_backend.repository;

import com.mediflow.mediflow_backend.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    boolean existsByMedecinIdAndDateHeureAndAnnuleFalse(
            Long medecinId,
            LocalDateTime dateHeure
    );

    List<RendezVous> findByMedecinIdAndDateHeureBetweenOrderByDateHeure(
            Long medecinId,
            LocalDateTime debut,
            LocalDateTime fin
    );
}