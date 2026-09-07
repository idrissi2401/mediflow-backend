package com.mediflow.mediflow_backend.service;

import com.mediflow.mediflow_backend.entity.LigneOrdonnance;
import com.mediflow.mediflow_backend.repository.LigneOrdonnanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigneOrdonnanceService {

    private final LigneOrdonnanceRepository ligneOrdonnanceRepository;

    public LigneOrdonnanceService(
            LigneOrdonnanceRepository ligneOrdonnanceRepository) {
        this.ligneOrdonnanceRepository = ligneOrdonnanceRepository;
    }

    public List<LigneOrdonnance> getAllLignes() {
        return ligneOrdonnanceRepository.findAll();
    }

    public Optional<LigneOrdonnance> getLigneById(Long id) {
        return ligneOrdonnanceRepository.findById(id);
    }

    public List<LigneOrdonnance> getLignesByOrdonnance(Long ordonnanceId) {
        return ligneOrdonnanceRepository.findByOrdonnanceId(ordonnanceId);
    }

    public LigneOrdonnance saveLigne(LigneOrdonnance ligneOrdonnance) {
        return ligneOrdonnanceRepository.save(ligneOrdonnance);
    }

    public LigneOrdonnance updateLigne(LigneOrdonnance ligneOrdonnance) {
        return ligneOrdonnanceRepository.save(ligneOrdonnance);
    }
}