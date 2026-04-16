package com.example.Onskeskyen.services;

import com.example.Onskeskyen.models.Reservation;
import com.example.Onskeskyen.repositorys.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public boolean opretReservation(int item_id, int bruger_id, int antal) {

        // Tjek om allerede reserveret
        if (tjekOmReserveret(item_id)) {
            return false; // allerede reserveret
        }

        repository.save(new Reservation(item_id, bruger_id, antal));
        return true; // succes
    }

    public void sletReservation(int item_id, int bruger_id) {
        repository.delete(item_id, bruger_id);
    }

    public boolean tjekOmReserveret(int item_id) {
        return !repository.findByItem(item_id).isEmpty();
    }

    public Reservation hentReservation(int itemId) {

        List<Reservation> reservationer = repository.findByItem(itemId);

        if (reservationer.isEmpty()) {
            return null;
        }

        return reservationer.get(0);
    }


}