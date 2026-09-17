package com.mediflow.mediflow_backend.service;

import com.mediflow.mediflow_backend.entity.RendezVous;
import com.mediflow.mediflow_backend.repository.RendezVousRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;

    public RendezVousService(
            RendezVousRepository rendezVousRepository) {

        this.rendezVousRepository =
                rendezVousRepository;
    }


    // =========================
    // RÉCUPÉRER TOUS LES RDV
    // =========================

    public List<RendezVous> getAllRendezVous() {

        return rendezVousRepository.findAll();

    }


    // =========================
    // RÉCUPÉRER UN RDV
    // =========================

    public Optional<RendezVous> getRendezVousById(
            Long id) {

        return rendezVousRepository.findById(id);

    }


    // =========================
    // RDV D'UN MÉDECIN
    // =========================

    public List<RendezVous> getRendezVousMedecin(
            Long medecinId,
            LocalDateTime debut,
            LocalDateTime fin) {

        return rendezVousRepository
                .findByMedecinIdAndDateHeureBetweenOrderByDateHeure(
                        medecinId,
                        debut,
                        fin
                );
    }


    // =========================
    // RDV D'UNE PERSONNE ACCUEIL
    // =========================

    public List<RendezVous> getRendezVousAccueil(
            Long accueilId,
            LocalDateTime debut,
            LocalDateTime fin) {

        return rendezVousRepository
                .findByAccueilIdAndDateHeureBetweenOrderByDateHeure(
                        accueilId,
                        debut,
                        fin
                );
    }


    // =========================
    // CRÉER UN RENDEZ-VOUS
    // =========================

    public RendezVous saveRendezVous(
            RendezVous rendezVous) {

        verifierRendezVous(rendezVous);

        return rendezVousRepository.save(
                rendezVous
        );
    }


    // =========================
    // MODIFIER UN RENDEZ-VOUS
    // =========================

    public RendezVous updateRendezVous(
            RendezVous rendezVous) {

        return rendezVousRepository.save(
                rendezVous
        );
    }


    // =========================
    // VÉRIFICATIONS
    // =========================

    private void verifierRendezVous(
            RendezVous rendezVous) {


        // -------------------------
        // DATE OBLIGATOIRE
        // -------------------------

        if (rendezVous.getDateHeure() == null) {

            throw new IllegalArgumentException(
                    "La date et l'heure sont obligatoires"
            );
        }


        // -------------------------
        // PATIENT OBLIGATOIRE
        // -------------------------

        if (
                rendezVous.getPatient() == null ||
                        rendezVous.getPatient().getId() == null
        ) {

            throw new IllegalArgumentException(
                    "Le patient est obligatoire"
            );
        }


        // -------------------------
        // MÉDECIN OU ACCUEIL
        // -------------------------

        boolean aMedecin =
                rendezVous.getMedecin() != null;

        boolean aAccueil =
                rendezVous.getAccueil() != null;


        if (!aMedecin && !aAccueil) {

            throw new IllegalArgumentException(
                    "Le rendez-vous doit être associé à un médecin ou à une personne de l'accueil"
            );
        }


        if (aMedecin && aAccueil) {

            throw new IllegalArgumentException(
                    "Un rendez-vous ne peut pas être médical et administratif en même temps"
            );
        }


        // -------------------------
        // CRÉNEAUX DE 30 MINUTES
        // -------------------------

        int minute =
                rendezVous
                        .getDateHeure()
                        .getMinute();

        if (minute != 0 && minute != 30) {

            throw new IllegalArgumentException(
                    "Les rendez-vous doivent commencer à :00 ou :30"
            );
        }


        // -------------------------
        // HORAIRES DU CABINET
        // -------------------------

        LocalTime heure =
                rendezVous
                        .getDateHeure()
                        .toLocalTime();

        LocalTime ouverture =
                LocalTime.of(9, 0);

        LocalTime fermeture =
                LocalTime.of(18, 0);


        if (
                heure.isBefore(ouverture) ||
                        heure.isAfter(fermeture)
        ) {

            throw new IllegalArgumentException(
                    "Le rendez-vous doit être compris entre 09h00 et 18h00"
            );
        }


        // -------------------------
        // PAUSE MIDI
        // 12H00 -> 13H00
        // -------------------------

        LocalTime debutPause =
                LocalTime.of(12, 0);

        LocalTime finPause =
                LocalTime.of(13, 0);


        if (
                !heure.isBefore(debutPause) &&
                        heure.isBefore(finPause)
        ) {

            throw new IllegalArgumentException(
                    "Aucun rendez-vous ne peut être pris entre 12h00 et 13h00"
            );
        }


        // -------------------------
        // CONFLIT PATIENT
        // -------------------------

        boolean patientOccupe =
                rendezVousRepository
                        .existsByPatientIdAndDateHeureAndAnnuleFalse(
                                rendezVous
                                        .getPatient()
                                        .getId(),
                                rendezVous
                                        .getDateHeure()
                        );


        if (patientOccupe) {

            throw new IllegalArgumentException(
                    "Ce patient a déjà un rendez-vous sur ce créneau"
            );
        }


        // -------------------------
        // CONFLIT MÉDECIN
        // -------------------------

        if (aMedecin) {

            boolean creneauOccupe =
                    rendezVousRepository
                            .existsByMedecinIdAndDateHeureAndAnnuleFalse(
                                    rendezVous
                                            .getMedecin()
                                            .getId(),
                                    rendezVous
                                            .getDateHeure()
                            );


            if (creneauOccupe) {

                throw new IllegalArgumentException(
                        "Ce médecin a déjà un rendez-vous sur ce créneau"
                );
            }
        }


        // -------------------------
        // CONFLIT ACCUEIL
        // -------------------------

        if (aAccueil) {

            boolean creneauOccupe =
                    rendezVousRepository
                            .existsByAccueilIdAndDateHeureAndAnnuleFalse(
                                    rendezVous
                                            .getAccueil()
                                            .getId(),
                                    rendezVous
                                            .getDateHeure()
                            );


            if (creneauOccupe) {

                throw new IllegalArgumentException(
                        "Cette personne de l'accueil a déjà un rendez-vous sur ce créneau"
                );
            }
        }

    }

}