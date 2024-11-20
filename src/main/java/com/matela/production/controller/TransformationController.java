package com.matela.production.controller;

import com.matela.production.entity.*;
import com.matela.production.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TransformationController {
    @Autowired
    private TransformationService transformationService;
    @Autowired
    private TransformationDetailService transformationDetailService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private BlockService blockService;
    @Autowired
    private StockService stockService;
    @Autowired
    private TetaService tetaService;

    @GetMapping("/form-transformation")
    public String formTransformation(Model model) {
        List<Produit> produitList = produitService.getAllProduits();
        model.addAttribute("blocks", blockService.getBlockValid());
        model.addAttribute("produits", produitList);
        Transformation transformation = new Transformation();
        transformation.setTransformationDetail(new ArrayList<>());
        for (Produit produit : produitList) {
            TransformationDetail transformationDetail = new TransformationDetail();
            transformationDetail.setProduit(produit);
            transformationDetail.setQuantite(0);
            transformation.getTransformationDetail().add(
                    transformationDetail
            );
        }
        model.addAttribute("transformation", transformation);
        return "transformation/insertionTransformation";
    }

    @PostMapping("/save-transformation")
    public String saveTransformation(Transformation transformation, Model model) {
        Block block = blockService.getBlockById(transformation.getBlock().getId());
        transformation.setBlock(block);

        double longuer = transformationService.convertToMeters(transformation.getLongueur());
        double largeur =  transformationService.convertToMeters(transformation.getLargeur());
        double epaisseur =  transformationService.convertToMeters(transformation.getEpaisseur());

        transformation.setReste(new Block());
        transformation.getReste().setLongueur(longuer);
        transformation.getReste().setLargeur(largeur);
        transformation.getReste().setEpaisseur(epaisseur);


        if (transformationService.validateTransformation(transformation)){
            Block reste = new Block();
            reste.setCoutProduction(blockService.coutProduction(block,transformation.getReste().getVolume()));
            reste.setLongueur(longuer);
            reste.setLargeur(largeur);
            reste.setEpaisseur(epaisseur);
            reste.setBlockMere(block);
              //reste.setDateProduction(transformation.getDateTransformation());
            reste = blockService.createBlock(reste);
            transformation.setReste(reste);
            Transformation tr =  transformationService.createTransformation(transformation);
            for (int i = 0; i < transformation.getTransformationDetail().size(); i++) {
                TransformationDetail transformationDetail = transformation.getTransformationDetail().get(i);
                transformationDetail.setTransformation(tr);
                double revient = produitService.prixRevient(block,transformationDetail.getProduit().getVolume()*transformationDetail.getQuantite());
                transformationDetail.setPrixRevient(revient);
                // creation de transforamtion
                transformationDetailService.create(transformationDetail);
            }
            Stock stock = new Stock();
            stock.setBlock(block);
            stock.setLongueur(reste.getLongueur());
            stock.setLargeur(reste.getLargeur());
            stock.setEpaisseur(reste.getEpaisseur());
            stockService.createStock(stock);
            if (reste.getVolume() == 0){
                model.addAttribute("status", "success");
                return "result-status";
            }
            System.out.println("work");
        }else
        {
            System.out.println("not work");
            model.addAttribute("message", "Transformation not valid a teta "+tetaService.getALl().getFirst().getValue()+"% ");
            model.addAttribute("status", "error");
            return "result-status";
        }
        model.addAttribute("status", "success");
        return "result-status";
    }



}
