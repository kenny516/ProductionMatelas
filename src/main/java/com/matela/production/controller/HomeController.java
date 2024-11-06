package com.matela.production.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // Adding attributes to the Model to pass data to Thymeleaf
        model.addAttribute("title", "Welcome to Thymeleaf!");
        model.addAttribute("message", "This is a Spring Boot Thymeleaf example.");

        // Return the name of the Thymeleaf template to render
        return "home"; // Thymeleaf will look for a file named "home.html" in src/main/resources/templates
    }
}
