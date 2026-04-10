package com.example.Onskeskyen.repositorys;

import com.example.Onskeskyen.models.Bruger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BrugerRepository {

    private List<Bruger> brugere = new ArrayList<>();
    private int nextId = 1;

    //Gem bruger
    public Bruger save(Bruger bruger){
        if(bruger.getBrugerId() == 0){
            bruger.setBrugerId(nextId++);
            brugere.add(bruger);
        } else {
            //opdater eksisterende bruger
            for (int i = 0; i < brugere.size(); i++){
                if(brugere.get(i).getBrugerId() == bruger.getBrugerId()) {
                    brugere.set(i, bruger);
                    break;
                }
            }
        }
        return bruger;
    }

    //Find bruger via Id
    public Optional<Bruger> findById(int brugerId){
        return brugere.stream()
                .filter(b -> b.getBrugerId() == brugerId)
                .findFirst();
    }

    //Find bruger via email
    public Optional<Bruger> findByEmail(String email){
        return brugere.stream()
                .filter(b -> b.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}