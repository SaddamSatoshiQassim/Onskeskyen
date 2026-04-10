package com.example.Onskeskyen.services;
import com.example.Onskeskyen.models.Bruger;
import com.example.Onskeskyen.repositorys.BrugerRepository;
import java.time.LocalDateTime;
import java.util.Optional;


//opretBruge (navn, email, kodeord)
//godkendLogin(email, kodeord)
//FindBruger (bruger_id)


public class BrugerService {

    private BrugerRepository brugerRepository = new BrugerRepository();

    public void opretBruger(String navn, String email, String kodeord){
      Bruger bruger = new Bruger(0, navn, email, kodeord, LocalDateTime.now());
        brugerRepository.save(bruger);
    }

    public boolean godkendLogin(String email, String kodeord){
        Optional<Bruger> brugerOpt = brugerRepository.findByEmail(email);

        if (brugerOpt.isPresent()){
            Bruger bruger = brugerOpt.get();
            return bruger.getKodeord().equals(kodeord);
        }
        return false;
    }

    public Optional<Bruger> findBruger(int brugerId){
    return brugerRepository.findById(brugerId);
    }
}
