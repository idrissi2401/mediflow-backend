package com.mediflow.mediflow_backend.repository;

import com.mediflow.mediflow_backend.entity.LigneOrdonnance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneOrdonnanceRepository extends JpaRepository<LigneOrdonnance, Long> {

    List<LigneOrdonnance> findByOrdonnanceId(Long ordonnanceId);
}