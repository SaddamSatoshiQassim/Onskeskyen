package com.example.Onskeskyen.controllers;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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

        String delingslink = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/onskelister/delt/{id}")
                .buildAndExpand(liste.getOnskelisteId())
                .toUriString();

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
        if (liste == null) {
            return "redirect:/onskelister";
        }

        if (liste.getBrugerId() != brugerId) {
            return "redirect:/onskelister";
        }

        List<Onske> onsker = onskeService.hentOnskerForListe(id);
        Map<Integer, Reservation> reservationer = new HashMap<>();

        for (Onske onske : onsker) {
            Reservation reservation = reservationService.findByOnskeId(onske.getOnskeId());
            if (reservation != null) {
                reservationer.put(onske.getOnskeId(), reservation);
            }
        }

        model.addAttribute("bruger", bruger);
        model.addAttribute("onskeliste", liste);
        model.addAttribute("onskelisteId", id);
        model.addAttribute("onsker", onsker);
        model.addAttribute("reservationer", reservationer);

        return "onskeliste-detalje";
    }

    @GetMapping("/onskelister/delt/{id}")
    public String visDeltOnskeliste(@PathVariable int id,
                                    HttpSession session,
                                    Model model) {

        Onskeliste liste = onskelisteService.findById(id);

        if (liste == null) {
            return "redirect:/index";
        }

        if (!liste.isOffentlig()) {
            return "redirect:/index";
        }

        List<Onske> onsker = onskeService.hentOnskerForListe(id);
        Map<Integer, Reservation> reservationer = new HashMap<>();

        for (Onske onske : onsker) {
            Reservation reservation = reservationService.findByOnskeId(onske.getOnskeId());
            if (reservation != null) {
                reservationer.put(onske.getOnskeId(), reservation);
            }
        }

        Integer brugerId = (Integer) session.getAttribute("brugerId");
        boolean erLoggetInd = brugerId != null;
        boolean erEjer = erLoggetInd && brugerId == liste.getBrugerId();

        model.addAttribute("onskeliste", liste);
        model.addAttribute("onsker", onsker);
        model.addAttribute("reservationer", reservationer);
        model.addAttribute("erLoggetInd", erLoggetInd);
        model.addAttribute("erEjer", erEjer);
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