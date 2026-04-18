package com.example.Onskeskyen.services;

import com.example.Onskeskyen.repositorys.OnskeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OnskeServiceTest {

    @Mock
    private OnskeRepository onskeRepository;

    @InjectMocks
    private OnskeService onskeService;

    @Test
    void opretOnskeTilListe_gemmerØnskeIRrepository() {
        onskeService.opretOnskeTilListe(
                1,
                "PlayStation 5",
                "Spillekonsol",
                "https://example.com/ps5",
                3999.95,
                "https://example.com/ps5.jpg"
        );

        verify(onskeRepository).opretOnskeTilListe(
                1,
                "PlayStation 5",
                "Spillekonsol",
                "https://example.com/ps5",
                3999.95,
                "https://example.com/ps5.jpg"
        );
    }
}