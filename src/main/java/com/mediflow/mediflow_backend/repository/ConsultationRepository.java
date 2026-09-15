package com.mediflow.mediflow_backend.repository;

import com.mediflow.mediflow_backend.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultationRepository
        extends JpaRepository<Consultation, Long> {

    Optional<Consultation> findByRendezVousId(Long rendezVousId);
}