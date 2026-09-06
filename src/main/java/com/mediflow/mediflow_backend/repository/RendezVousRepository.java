package com.mediflow.mediflow_backend.repository;

import com.mediflow.mediflow_backend.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    boolean existsByMedecinIdAndDateHeureAndAnnuleFalse(
            Long medecinId,
            java.time.LocalDateTime dateHeure
    );
}