package com.mediflow.mediflow_backend.service;

import com.mediflow.mediflow_backend.entity.Patient;
import com.mediflow.mediflow_backend.entity.RendezVous;
import com.mediflow.mediflow_backend.entity.Utilisateur;
import com.mediflow.mediflow_backend.repository.RendezVousRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RendezVousServiceTest {

    private RendezVousRepository rendezVousRepository;
    private RendezVousService rendezVousService;

    private Patient patient;
    private Utilisateur medecin;

    @BeforeEach
    void setUp() {

        rendezVousRepository =
                Mockito.mock(RendezVousRepository.class);

        rendezVousService =
                new RendezVousService(rendezVousRepository);

        patient = new Patient();
        patient.setId(1L);

        medecin = new Utilisateur();
        medecin.setId(1L);
    }


    // =========================
    // TEST 1 : PAUSE MIDI
    // =========================

    @Test
    void doitRefuserUnRendezVousPendantLaPauseMidi() {

        RendezVous rendezVous =
                creerRendezVous(LocalDateTime.of(
                        2026, 9, 22, 12, 30
                ));

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> rendezVousService
                                .saveRendezVous(rendezVous)
                );

        assertTrue(
                exception.getMessage()
                        .contains("12h00 et 13h00")
        );
    }


    // =========================
    // TEST 2 : HORS HORAIRES
    // =========================

    @Test
    void doitRefuserUnRendezVousAvantOuverture() {

        RendezVous rendezVous =
                creerRendezVous(LocalDateTime.of(
                        2026, 9, 22, 8, 30
                ));

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> rendezVousService
                                .saveRendezVous(rendezVous)
                );

        assertTrue(
                exception.getMessage()
                        .contains("09h00 et 18h00")
        );
    }


    // =========================
    // TEST 3 : CONFLIT PATIENT
    // =========================

    @Test
    void doitRefuserUnPatientDejaOccupe() {

        LocalDateTime date =
                LocalDateTime.of(
                        2026, 9, 22, 10, 0
                );

        when(
                rendezVousRepository
                        .existsByPatientIdAndDateHeureAndAnnuleFalse(
                                1L,
                                date
                        )
        ).thenReturn(true);

        RendezVous rendezVous =
                creerRendezVous(date);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> rendezVousService
                                .saveRendezVous(rendezVous)
                );

        assertTrue(
                exception.getMessage()
                        .contains(
                                "patient a déjà un rendez-vous"
                        )
        );
    }


    // =========================
    // TEST 4 : CONFLIT MÉDECIN
    // =========================

    @Test
    void doitRefuserUnMedecinDejaOccupe() {

        LocalDateTime date =
                LocalDateTime.of(
                        2026, 9, 22, 10, 30
                );

        when(
                rendezVousRepository
                        .existsByPatientIdAndDateHeureAndAnnuleFalse(
                                1L,
                                date
                        )
        ).thenReturn(false);

        when(
                rendezVousRepository
                        .existsByMedecinIdAndDateHeureAndAnnuleFalse(
                                1L,
                                date
                        )
        ).thenReturn(true);

        RendezVous rendezVous =
                creerRendezVous(date);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> rendezVousService
                                .saveRendezVous(rendezVous)
                );

        assertTrue(
                exception.getMessage()
                        .contains(
                                "médecin a déjà un rendez-vous"
                        )
        );
    }


    // =========================
    // TEST 5 : RDV VALIDE
    // =========================

    @Test
    void doitEnregistrerUnRendezVousValide() {

        LocalDateTime date =
                LocalDateTime.of(
                        2026, 9, 22, 14, 0
                );

        RendezVous rendezVous =
                creerRendezVous(date);

        when(
                rendezVousRepository.save(rendezVous)
        ).thenReturn(rendezVous);

        RendezVous resultat =
                rendezVousService
                        .saveRendezVous(rendezVous);

        assertNotNull(resultat);

        verify(
                rendezVousRepository,
                times(1)
        ).save(rendezVous);
    }


    // =========================
    // CRÉATION D'UN RDV DE TEST
    // =========================

    private RendezVous creerRendezVous(
            LocalDateTime date) {

        RendezVous rendezVous =
                new RendezVous();

        rendezVous.setPatient(patient);
        rendezVous.setMedecin(medecin);
        rendezVous.setDateHeure(date);

        return rendezVous;
    }
}