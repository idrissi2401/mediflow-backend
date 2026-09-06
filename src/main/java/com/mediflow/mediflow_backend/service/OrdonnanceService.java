package com.mediflow.mediflow_backend.service;

import com.mediflow.mediflow_backend.entity.Ordonnance;
import com.mediflow.mediflow_backend.repository.OrdonnanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdonnanceService {

    private final OrdonnanceRepository ordonnanceRepository;

    public OrdonnanceService(OrdonnanceRepository ordonnanceRepository) {
        this.ordonnanceRepository = ordonnanceRepository;
    }

    public List<Ordonnance> getAllOrdonnances() {
        return ordonnanceRepository.findAll();
    }

    public Optional<Ordonnance> getOrdonnanceById(Long id) {
        return ordonnanceRepository.findById(id);
    }

    public Ordonnance saveOrdonnance(Ordonnance ordonnance) {
        return ordonnanceRepository.save(ordonnance);
    }

    public Ordonnance updateOrdonnance(Ordonnance ordonnance) {
        return ordonnanceRepository.save(ordonnance);
    }
}