/* package com.example.Onskeskyen;

import com.example.Onskeskyen.models.Bruger;
import com.example.Onskeskyen.repositorys.BrugerRepository;
import com.example.Onskeskyen.services.BrugerService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BrugerServiceSimpleTests {

    @Test
    void opretBruger_opretterOgGemmerBrugerKorrekt() {
        // Lav repository og service
        BrugerRepository brugerRepository = new BrugerRepository();
        BrugerService brugerService = new BrugerService(brugerRepository);

        // Data til test
        String navn = "Jens";
        String email = "anna@mail.com";
        String kodeord = "123456789";

        // Kald metoden
        brugerService.opretBruger(navn, email, kodeord);

        // Hent alle brugere og find den nye
        List<Bruger> alleBrugere = brugerService.getAllBrugere();
        Bruger gemtBruger = alleBrugere.stream()
                .filter(b -> b.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        assertNotNull(gemtBruger);
        assertEquals(navn, gemtBruger.getNavn());
        assertEquals(email, gemtBruger.getEmail());
        assertEquals(kodeord, gemtBruger.getKodeord());
        assertNotNull(gemtBruger.getOprettetDato());
    }

    @Test
    void findBruger_returnererBrugerHvisDenFindes() {
        BrugerRepository brugerRepository = new BrugerRepository();
        BrugerService brugerService = new BrugerService(brugerRepository);

        // Opret bruger
        Bruger bruger = new Bruger(0, "Jens", "anna@mail.com", "123456789", LocalDateTime.now());
        brugerRepository.save(bruger);

        Optional<Bruger> resultat = brugerService.findBruger(bruger.getBrugerId());

        assertTrue(resultat.isPresent());
        assertEquals("Jens", resultat.get().getNavn());
        assertEquals("anna@mail.com", resultat.get().getEmail());
        assertEquals("123456789", resultat.get().getKodeord());
    }

    @Test
    void findBruger_returnererTomOptionalHvisBrugerIkkeFindes() {
        BrugerRepository brugerRepository = new BrugerRepository();
        BrugerService brugerService = new BrugerService(brugerRepository);

        Optional<Bruger> resultat = brugerService.findBruger(99999);
        assertTrue(resultat.isEmpty());
    }
}

 */