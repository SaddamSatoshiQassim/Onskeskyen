package com.example.Onskeskyen.controllers;

import com.example.Onskeskyen.models.Bruger;
import com.example.Onskeskyen.models.Onske;
import com.example.Onskeskyen.models.Onskeliste;
import com.example.Onskeskyen.models.Reservation;
import com.example.Onskeskyen.services.BrugerService;
import com.example.Onskeskyen.services.OnskeService;
import com.example.Onskeskyen.services.OnskelisteService;
import com.example.Onskeskyen.services.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OnskelisteController {

    private final OnskelisteService onskelisteService;
    private final OnskeService onskeService;
    private final BrugerService brugerService;
    private final ReservationService reservationService;

    public OnskelisteController(OnskelisteService onskelisteService,
                                OnskeService onskeService,
                                BrugerService brugerService,
                                ReservationService reservationService) {
        this.onskelisteService = onskelisteService;
        this.onskeService = onskeService;
        this.brugerService = brugerService;
        this.reservationService = reservationService;
    }

    @GetMapping("/onskelister")
    public String visOnskelister(HttpSession session, Model model) {
        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        Bruger bruger = brugerService.findBruger(brugerId);

        if (bruger == null) {
            return "redirect:/login";
        }

        model.addAttribute("bruger", bruger);
        model.addAttribute("onskelister", onskelisteService.findByBrugerId(brugerId));

        return "onskelister";
    }


    @GetMapping("/opret-onskeliste")
    public String visOpretOnskeliste(HttpSession session) {
        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        return "opret-onskeliste";
    }


    @PostMapping("/opret-onskeliste")
    public String opretOnskeliste(@RequestParam String titel,
                                  @RequestParam String beskrivelse,
                                  @RequestParam(defaultValue = "false") boolean offentlig,
                                  HttpSession session) {

        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        String delingslink = "http://localhost:8080/onskelister/" + titel.replace(" ", "-");

        Onskeliste liste = new Onskeliste(
                brugerId,
                titel,
                beskrivelse,
                offentlig,
                delingslink
        );

        onskelisteService.save(liste);

        return "redirect:/onskelister";
    }

    @GetMapping("/onskelister/{id}")
    public String visEnOnskeliste(@PathVariable int id,
                                  HttpSession session,
                                  Model model) {

        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        List<Onske> onsker = onskeService.hentOnskerForListe(id);

        for (Onske o : onsker) {

            Reservation r = reservationService.hentReservation(o.getOnskeId());

            if (r != null) {
                o.setBooket(true);
                o.setReserveretAfBrugerId(r.getBrugerId());
            } else {
                o.setBooket(false);
            }
        }

        model.addAttribute("currentUserId", brugerId);
        model.addAttribute("onskelisteId", id);
        model.addAttribute("onsker", onsker);

        return "onskeliste-detalje";
    }

    // ➕ Tilføj ønske til liste
    @PostMapping("/onskelister/{id}/tilfoej-onske")
    public String tilfoejOnske(@PathVariable int id,
                               @RequestParam String navn,
                               @RequestParam String link,
                               @RequestParam double pris,
                               @RequestParam(required = false) String billede,
                               HttpSession session) {

        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        onskeService.opretOnskeTilListe(id, navn, link, pris, billede);

        return "redirect:/onskelister/" + id;
    }

    // ❌ Slet ønskeliste
    @PostMapping("/slet-onskeliste")
    public String sletOnskeliste(@RequestParam int id, HttpSession session) {
        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        onskelisteService.deleteById(id);

        return "redirect:/onskelister";
    }


    @PostMapping("/onskelister/{listeId}/slet-onske")
    public String sletOnske(@PathVariable int listeId,
                            @RequestParam int onskeId,
                            HttpSession session) {

        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        onskeService.deleteOnskeById(onskeId);

        return "redirect:/onskelister/" + listeId;
    }
}