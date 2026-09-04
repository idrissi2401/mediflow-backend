package com.mediflow.mediflow_backend.controller;

import com.mediflow.mediflow_backend.entity.Patient;
import com.mediflow.mediflow_backend.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable Long id) {

        Optional<Patient> patient = patientService.getPatientById(id);

        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(patient.get());
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(
            @PathVariable Long id,
            @RequestBody Patient patientModifie) {

        Optional<Patient> patientOptional =
                patientService.getPatientById(id);

        if (patientOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Patient patient = patientOptional.get();

        patient.setNom(patientModifie.getNom());
        patient.setPrenom(patientModifie.getPrenom());
        patient.setDateNaissance(patientModifie.getDateNaissance());
        patient.setTelephone(patientModifie.getTelephone());
        patient.setEmail(patientModifie.getEmail());
        patient.setAdresse(patientModifie.getAdresse());

        Patient patientEnregistre =
                patientService.updatePatient(patient);

        return ResponseEntity.ok(patientEnregistre);
    }
}