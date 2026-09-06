package com.mediflow.mediflow_backend.repository;

import com.mediflow.mediflow_backend.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}