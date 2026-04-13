/*Denne unittest er på om når en bruger opretter sig om det virker og den gemmer data på bruger.
Dette er en af hovedfunktioner for resten af hjemmsiden, derfor er den testværdi


package com.example.Onskeskyen;
import com.example.Onskeskyen.models.Bruger;
import com.example.Onskeskyen.services.BrugerService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BrugerServiceTests {

    private BrugerService brugerService;
    public BrugerServiceTests(BrugerService brugerService){
        this.brugerService=brugerService;
    }

    @Test
    void opretBruger_opretterOgGemmerBrugerKorrekt() {
        // Arrange
        String navn = "Jens";
        String email = "anna@mail.com";
        String kodeord = "1234";

        // Act
        brugerService.opretBruger(navn, email, kodeord);
        Optional<Bruger> gemtBruger = brugerService.findBruger(1);

        // Assert
        assertTrue(gemtBruger.isPresent());

        Bruger bruger = gemtBruger.get();
        assertEquals("Jens", bruger.getNavn());
        assertEquals("anna@mail.com", bruger.getEmail());
        assertEquals("1234", bruger.getKodeord());
    }
}


 */