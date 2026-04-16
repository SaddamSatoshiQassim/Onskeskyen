package com.example.Onskeskyen.controllers;

import com.example.Onskeskyen.models.Bruger;
import com.example.Onskeskyen.services.BrugerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BrugerController {

    private final BrugerService services;

    public BrugerController(BrugerService services) {
        this.services = services;
    }

    @GetMapping({"/", "/index"})
    public String startside() {
        return "index";
    }

    @GetMapping("/login")
    public String visLogin() {
        return "login";
    }

    @GetMapping("/om-os")
    public String visOmOs() {
        return "om-os";
    }

    @GetMapping("/kontakt")
    public String visKontakt() {
        return "kontakt";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String kodeord,
                        Model model,
                        HttpSession session) {

        boolean gyldigLogin = services.godkendLogin(email, kodeord);

        if (gyldigLogin) {
            Bruger bruger = services.findBrugerByEmail(email);

            if (bruger != null) {
                session.setAttribute("brugerId", bruger.getBrugerId());
                session.setAttribute("loggetIndEmail", bruger.getEmail());
                session.setAttribute("loggetIndNavn", bruger.getNavn());
                return "redirect:/onskeliste";
            } else {
                model.addAttribute("fejl", "Brugeren blev ikke fundet");
                return "login";
            }
        } else {
            model.addAttribute("fejl", "Forkert email eller adgangskode");
            return "login";
        }
    }

    @PostMapping("/opret")
    public String opretBruger(@RequestParam String navn,
                              @RequestParam String email,
                              @RequestParam String kodeord,
                              HttpSession session,
                              Model model) {

        services.opretBruger(navn, email, kodeord);

        Bruger bruger = services.findBrugerByEmail(email);

        if (bruger != null) {
            session.setAttribute("brugerId", bruger.getBrugerId());
            session.setAttribute("loggetIndEmail", bruger.getEmail());
            session.setAttribute("loggetIndNavn", bruger.getNavn());
            return "redirect:/onskeliste";
        } else {
            model.addAttribute("fejl", "Brugeren kunne ikke oprettes");
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }
}