package com.mediflow.mediflow_backend.controller;

import com.mediflow.mediflow_backend.entity.Ordonnance;
import com.mediflow.mediflow_backend.service.OrdonnanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordonnances")
public class OrdonnanceController {

    private final OrdonnanceService ordonnanceService;

    public OrdonnanceController(OrdonnanceService ordonnanceService) {
        this.ordonnanceService = ordonnanceService;
    }

    @GetMapping
    public List<Ordonnance> getAllOrdonnances() {
        return ordonnanceService.getAllOrdonnances();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrdonnanceById(@PathVariable Long id) {

        Optional<Ordonnance> ordonnance =
                ordonnanceService.getOrdonnanceById(id);

        if (ordonnance.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ordonnance.get());
    }

    @PostMapping
    public Ordonnance createOrdonnance(
            @RequestBody Ordonnance ordonnance) {

        return ordonnanceService.saveOrdonnance(ordonnance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrdonnance(
            @PathVariable Long id,
            @RequestBody Ordonnance ordonnanceModifiee) {

        Optional<Ordonnance> ordonnanceOptional =
                ordonnanceService.getOrdonnanceById(id);

        if (ordonnanceOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Ordonnance ordonnance = ordonnanceOptional.get();

        ordonnance.setDateHeure(ordonnanceModifiee.getDateHeure());
        ordonnance.setConsultation(ordonnanceModifiee.getConsultation());

        Ordonnance ordonnanceEnregistree =
                ordonnanceService.updateOrdonnance(ordonnance);

        return ResponseEntity.ok(ordonnanceEnregistree);
    }
}