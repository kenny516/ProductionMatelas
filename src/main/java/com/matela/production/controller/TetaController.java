package com.matela.production.controller;


import com.matela.production.entity.Teta;
import com.matela.production.service.TetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TetaController {

    @Autowired
    TetaService tetaService;

    @GetMapping("/form-teta")
    public String formTeta(Model model) {
        Teta teta = tetaService.getALl().getFirst();
        model.addAttribute("teta",teta);
        return "teta/teta-form";
    }
    @PostMapping("/save-teta")
    public String saveTeta(Teta teta,Model model) {
        tetaService.createTeta(teta);
        model.addAttribute("status", "success");
        model.addAttribute("message", "Teta created successfully");
        return "result-status";
    }
}
