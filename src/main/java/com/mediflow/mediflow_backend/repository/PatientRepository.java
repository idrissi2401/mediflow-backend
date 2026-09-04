package com.mediflow.mediflow_backend.repository;

import com.mediflow.mediflow_backend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}