package com.matela.production.controller;

import com.matela.production.entity.Produit;
import com.matela.production.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping("/form-prduit")
    public String insertionBlock(Model model) {
        model.addAttribute("produit", new Produit());
        return "produit/InsertionProduit";
    }

    @GetMapping("/produits")
    public String listeBlocks(Model model) {
        model.addAttribute("produits", produitService.getAllProduits());
        return "produit/liste";
    }


}
