package com.example.Onskeskyen.services;

import com.example.Onskeskyen.models.Bruger;
import com.example.Onskeskyen.repositorys.BrugerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BrugerService {

    private final BrugerRepository brugerRepository;

    public BrugerService(BrugerRepository brugerRepository) {
        this.brugerRepository = brugerRepository;
    }

    public List<Bruger> getAllBrugere() {
        return brugerRepository.findAll();
    }

    public Optional<Bruger> findBruger(int brugerId) {
        return brugerRepository.findById(brugerId);
    }

    public void opretBruger(String navn, String email, String kodeord) {
        Bruger bruger = new Bruger(0, navn, email, kodeord, LocalDateTime.now());
        brugerRepository.save(bruger);
    }

    public void updateBrugerById(int id, String navn, String email, String kodeord) {
        brugerRepository.updateById(id, navn, email, kodeord);
    }

    public void deleteBrugerById(int id) {
        brugerRepository.deleteById(id);
    }

    public boolean godkendLogin(String email, String kodeord) {
        Optional<Bruger> brugerOpt = brugerRepository.findByEmail(email);

        if (brugerOpt.isPresent()) {
            Bruger bruger = brugerOpt.get();
            return bruger.getKodeord().equals(kodeord);
        }
        return false;
    }
}