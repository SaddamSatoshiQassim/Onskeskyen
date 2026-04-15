package com.example.Onskeskyen.services;

import com.example.Onskeskyen.models.Onskeliste;
import com.example.Onskeskyen.repositorys.OnskelisteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnskelisteService {

    private final OnskelisteRepository onskelisteRepository;

    public OnskelisteService(OnskelisteRepository onskelisteRepository) {
        this.onskelisteRepository = onskelisteRepository;
    }

    public List<Onskeliste> findByBrugerId(int brugerId) {
        return onskelisteRepository.findByBrugerId(brugerId);
    }

    public void save(Onskeliste liste) {
        onskelisteRepository.save(liste);
    }

    public void updateById(int id, String titel, String beskrivelse, boolean offentlig) {
        onskelisteRepository.updateById(id, titel, beskrivelse, offentlig);
    }

    public void deleteById(int id) {
        onskelisteRepository.deleteById(id);
    }

}