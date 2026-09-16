package com.mediflow.mediflow_backend.service;

import com.mediflow.mediflow_backend.entity.Consultation;
import com.mediflow.mediflow_backend.repository.ConsultationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationService {

    private final ConsultationRepository consultationRepository;

    public ConsultationService(
            ConsultationRepository consultationRepository
    ) {
        this.consultationRepository = consultationRepository;
    }


    // =========================
    // TOUTES LES CONSULTATIONS
    // =========================

    public List<Consultation> getAllConsultations() {

        return consultationRepository.findAll();
    }


    // =========================
    // CONSULTATION PAR ID
    // =========================

    public Optional<Consultation> getConsultationById(
            Long id
    ) {

        return consultationRepository.findById(id);
    }


    // =========================
    // CONSULTATION PAR
    // RENDEZ-VOUS
    // =========================

    public Optional<Consultation>
    getConsultationByRendezVousId(
            Long rendezVousId
    ) {

        return consultationRepository
                .findByRendezVousId(
                        rendezVousId
                );
    }


    // =========================
    // CONSULTATIONS PAR PATIENT
    // =========================

    public List<Consultation>
    getConsultationsByPatient(
            Long patientId
    ) {

        return consultationRepository
                .findByRendezVousPatientIdOrderByDateHeureDesc(
                        patientId
                );
    }


    // =========================
    // CRÉER UNE CONSULTATION
    // =========================

    public Consultation saveConsultation(
            Consultation consultation
    ) {

        return consultationRepository
                .save(consultation);
    }


    // =========================
    // MODIFIER UNE CONSULTATION
    // =========================

    public Consultation updateConsultation(
            Consultation consultation
    ) {

        return consultationRepository
                .save(consultation);
    }
}