package com.mediflow.mediflow_backend.repository;

import com.mediflow.mediflow_backend.entity.Ordonnance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdonnanceRepository
        extends JpaRepository<Ordonnance, Long> {

    Optional<Ordonnance> findByConsultationId(Long consultationId);
}