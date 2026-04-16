package com.example.Onskeskyen.controllers;

import com.example.Onskeskyen.services.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }
    @PostMapping("/reserver")
    public String reserverOenske(@RequestParam int item_id,
                                 @RequestParam int bruger_id,
                                 @RequestParam int antal,
                                 Model model) {

        boolean success = service.opretReservation(item_id, bruger_id, antal);

        if (!success) {
            model.addAttribute("fejl", "Dette ønske er allerede reserveret");
        }

        return "redirect:/onskeliste";
    }

    @PostMapping("/annuller")
    public String annullerReservation(@RequestParam int item_id, @RequestParam int bruger_id) {

        service.sletReservation(item_id, bruger_id);
        return "redirect:/onskeliste";
    }
}