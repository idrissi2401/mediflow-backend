package com.mediflow.mediflow_backend.service;

import com.mediflow.mediflow_backend.entity.Patient;
import com.mediflow.mediflow_backend.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    private static final Pattern TELEPHONE_PATTERN =
            Pattern.compile("^(?:\\+33|0)[1-9]\\d{8}$");

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");


    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    // =========================
    // RÉCUPÉRER LES PATIENTS
    // =========================

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }


    // =========================
    // RÉCUPÉRER UN PATIENT
    // =========================

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }


    // =========================
    // CRÉER UN PATIENT
    // =========================

    public Patient savePatient(Patient patient) {

        verifierPatient(patient);

        return patientRepository.save(patient);
    }


    // =========================
    // MODIFIER UN PATIENT
    // =========================

    public Patient updatePatient(Patient patient) {

        verifierPatient(patient);

        return patientRepository.save(patient);
    }


    // =========================
    // VALIDATIONS
    // =========================

    private void verifierPatient(Patient patient) {

        // Nom

        if (
                patient.getNom() == null ||
                        patient.getNom().trim().isEmpty()
        ) {
            throw new IllegalArgumentException(
                    "Le nom du patient est obligatoire."
            );
        }


        // Prénom

        if (
                patient.getPrenom() == null ||
                        patient.getPrenom().trim().isEmpty()
        ) {
            throw new IllegalArgumentException(
                    "Le prénom du patient est obligatoire."
            );
        }


        // Date de naissance

        if (patient.getDateNaissance() == null) {
            throw new IllegalArgumentException(
                    "La date de naissance est obligatoire."
            );
        }

        if (
                patient.getDateNaissance().isAfter(
                        LocalDate.now()
                )
        ) {
            throw new IllegalArgumentException(
                    "La date de naissance ne peut pas être dans le futur."
            );
        }


        // Téléphone

        if (
                patient.getTelephone() != null &&
                        !patient.getTelephone().trim().isEmpty()
        ) {

            String telephone =
                    patient.getTelephone()
                            .trim()
                            .replaceAll(
                                    "[\\s.-]",
                                    ""
                            );

            if (
                    !TELEPHONE_PATTERN
                            .matcher(telephone)
                            .matches()
            ) {
                throw new IllegalArgumentException(
                        "Le numéro de téléphone est invalide."
                );
            }

            patient.setTelephone(
                    telephone
            );
        }


        // Email

        if (
                patient.getEmail() != null &&
                        !patient.getEmail().trim().isEmpty()
        ) {

            String email =
                    patient.getEmail()
                            .trim()
                            .toLowerCase();

            if (
                    !EMAIL_PATTERN
                            .matcher(email)
                            .matches()
            ) {
                throw new IllegalArgumentException(
                        "L'adresse email est invalide."
                );
            }

            patient.setEmail(
                    email
            );
        }


        // Nettoyage des informations

        patient.setNom(
                patient.getNom().trim()
        );

        patient.setPrenom(
                patient.getPrenom().trim()
        );

        if (patient.getAdresse() != null) {
            patient.setAdresse(
                    patient.getAdresse().trim()
            );
        }

    }

}