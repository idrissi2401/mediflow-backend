package com.mediflow.mediflow_backend.repository;

import com.mediflow.mediflow_backend.entity.Ordonnance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdonnanceRepository extends JpaRepository<Ordonnance, Long> {
}