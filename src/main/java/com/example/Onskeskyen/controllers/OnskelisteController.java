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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/opret-onskeliste")
    public String opretOnskeliste(@RequestParam String titel,
                                  @RequestParam(required = false) String beskrivelse,
                                  @RequestParam(defaultValue = "false") boolean offentlig,
                                  HttpSession session) {

        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        Onskeliste liste = new Onskeliste(
                brugerId,
                titel,
                beskrivelse,
                offentlig,
                ""
        );

        onskelisteService.save(liste);

        // 🔥 HER ER DET VIGTIGE
        String delingslink = "http://localhost:8083/onskelister/delt/" + liste.getOnskelisteId();

        onskelisteService.updateDelingslinkById(liste.getOnskelisteId(), delingslink);
        liste.setDelingslink(delingslink);

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

        Bruger bruger = brugerService.findBruger(brugerId);
        if (bruger == null) {
            return "redirect:/login";
        }

        Onskeliste liste = onskelisteService.findById(id);
        if (liste == null || liste.getBrugerId() != brugerId) {
            return "redirect:/onskelister";
        }

        List<Onske> onsker = onskeService.hentOnskerForListe(id);

        model.addAttribute("bruger", bruger);
        model.addAttribute("onskeliste", liste);
        model.addAttribute("onsker", onsker);

        return "onskeliste-detalje";
    }

    @GetMapping("/onskelister/delt/{id}")
    public String visDeltOnskeliste(@PathVariable int id,
                                    HttpSession session,
                                    Model model) {

        Onskeliste liste = onskelisteService.findById(id);

        if (liste == null || !liste.isOffentlig()) {
            return "redirect:/index";
        }

        List<Onske> onsker = onskeService.hentOnskerForListe(id);

        Integer brugerId = (Integer) session.getAttribute("brugerId");

        model.addAttribute("onskeliste", liste);
        model.addAttribute("onsker", onsker);
        model.addAttribute("brugerId", brugerId);

        return "onskeliste-delt";
    }

    @PostMapping("/onskelister/{id}/tilfoej-onske")
    public String tilfoejOnske(@PathVariable int id,
                               @RequestParam String navn,
                               @RequestParam(required = false) String beskrivelse,
                               @RequestParam String link,
                               @RequestParam double pris,
                               @RequestParam(required = false) String billede,
                               HttpSession session) {

        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        Onskeliste liste = onskelisteService.findById(id);
        if (liste == null || liste.getBrugerId() != brugerId) {
            return "redirect:/onskelister";
        }

        onskeService.opretOnskeTilListe(id, navn, beskrivelse, link, pris, billede);

        return "redirect:/onskelister/" + id;
    }

    @PostMapping("/slet-onskeliste")
    public String sletOnskeliste(@RequestParam int id, HttpSession session) {
        Integer brugerId = (Integer) session.getAttribute("brugerId");

        if (brugerId == null) {
            return "redirect:/login";
        }

        Onskeliste liste = onskelisteService.findById(id);
        if (liste == null || liste.getBrugerId() != brugerId) {
            return "redirect:/onskelister";
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

        Onskeliste liste = onskelisteService.findById(listeId);
        if (liste == null || liste.getBrugerId() != brugerId) {
            return "redirect:/onskelister";
        }

        onskeService.deleteOnskeById(onskeId);

        return "redirect:/onskelister/" + listeId;
    }
}