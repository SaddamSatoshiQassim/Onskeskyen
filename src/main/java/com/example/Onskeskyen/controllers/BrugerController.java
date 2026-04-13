package com.example.Onskeskyen.controllers;

import com.example.Onskeskyen.services.BrugerService;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BrugerController {
  private final BrugerService services = new BrugerService();

  @GetMapping("/")
    public String startside(){
          return "index";
  }

    @PostMapping("/login")
    public String login(@RequestParam String brugernavn, @RequestParam String kodeord, Model model) {
        boolean gyldigLogin = services.godkendLogin(brugernavn, kodeord);
        if (gyldigLogin) {
            return "redirect:/onskeliste";
        } else {
            model.addAttribute("fejl", "Forkert brugernavn eller kodeord");
            return "login";
        }
    }

    @PostMapping("/opret")
    public String opretBruger(@RequestParam String navn,
                              @RequestParam String email,
                              @RequestParam String kodeord) {

        services.opretBruger(navn, email, kodeord);
        return "redirect:/";
    }

    @GetMapping("/onskeliste")
    public String visOnskeliste(Model model) {
        return "onskeliste";
    }

    System.out.println("kj")
}
