package com.mediflow.mediflow_backend.repository;

import com.mediflow.mediflow_backend.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    // =========================
    // RENDEZ-VOUS MÉDECIN
    // =========================

    boolean existsByMedecinIdAndDateHeureAndAnnuleFalse(
            Long medecinId,
            LocalDateTime dateHeure
    );

    List<RendezVous> findByMedecinIdAndDateHeureBetweenOrderByDateHeure(
            Long medecinId,
            LocalDateTime debut,
            LocalDateTime fin
    );


    // =========================
    // RENDEZ-VOUS ACCUEIL
    // =========================

    boolean existsByAccueilIdAndDateHeureAndAnnuleFalse(
            Long accueilId,
            LocalDateTime dateHeure
    );

    List<RendezVous> findByAccueilIdAndDateHeureBetweenOrderByDateHeure(
            Long accueilId,
            LocalDateTime debut,
            LocalDateTime fin
    );


    // =========================
    // RENDEZ-VOUS PATIENT
    // =========================

    boolean existsByPatientIdAndDateHeureAndAnnuleFalse(
            Long patientId,
            LocalDateTime dateHeure
    );

}