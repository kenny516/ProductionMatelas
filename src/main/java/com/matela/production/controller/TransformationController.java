package com.matela.production.controller;

import com.matela.production.entity.Transformation;
import com.matela.production.service.BlockService;
import com.matela.production.service.ProduitService;
import com.matela.production.service.TransformationService;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransformationController {
    @Autowired
    private TransformationService transformationService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private BlockService blockService;

    @GetMapping("/form-transformation")
    public String formTransformation(Model model) {
        model.addAttribute("transformation",new Transformation());
        model.addAttribute("blocks",blockService.getAllBlocks());
        model.addAttribute("produits",produitService.getAllProduits());
        return "transformation/insertionTransformation";
    }

//    @PostMapping("/save-transformation")
//    public String saveTransformation(List<>, Model model) {
//
//        return "redirect:/form-transformation";
//    }
}
