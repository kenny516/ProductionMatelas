package com.matela.production.controller;

import com.matela.production.entity.Block;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        // Adding attributes to the Model to pass data to Thymeleaf
        model.addAttribute("title", "Welcome to Thymeleaf!");
        model.addAttribute("message", "This is a Spring Boot Thymeleaf example.");

        // Return the name of the Thymeleaf template to render
        return "home"; // Thymeleaf will look for a file named "home.html" in src/main/resources/templates
    }
    @GetMapping("/products")
    public String products(Model model) {
        List<String> products = List.of("Product A", "Product B", "Product C");
        model.addAttribute("products", products);
        return "products";
    }

    @PostMapping("/block")
    public String block(@ModelAttribute Block block, Model model){

        return "InsertionBlock";
    }
}
