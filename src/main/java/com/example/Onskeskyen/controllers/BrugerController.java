package com.example.Onskeskyen.controllers;

import com.example.Onskeskyen.services.BrugerService;
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
}
