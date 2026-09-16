package com.mediflow.mediflow_backend.repository;

import com.mediflow.mediflow_backend.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConsultationRepository
        extends JpaRepository<Consultation, Long> {


    // Consultation liée à un rendez-vous
    Optional<Consultation> findByRendezVousId(
            Long rendezVousId
    );


    // Historique des consultations d'un patient
    List<Consultation>
    findByRendezVousPatientIdOrderByDateHeureDesc(
            Long patientId
    );
}