package com.example.Onskeskyen.controllers;

import com.example.Onskeskyen.services.BrugerService;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BrugerController {
  private final BrugerService services = new BrugerService();

  @GetMapping("/")
    public String startside(){
          return "index";
  }
    @GetMapping("/login")
    public String loginside(){
        return "login";
    }
    @GetMapping("/onskeliste")
    public String visOnskeliste(Model model) {
        return "onskeliste";
    }
}
