package com.matela.production.controller;

import com.matela.production.entity.Block;
import com.matela.production.entity.ProduitDisplay;
import com.matela.production.entity.StockDisplay;
import com.matela.production.entity.TransformationDisplay;
import com.matela.production.service.BlockService;
import com.matela.production.service.StockService;
import com.matela.production.service.TransformationDetailService;
import com.matela.production.service.TransformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StockController {

    @Autowired
    private BlockService blockService;
    @Autowired
    private TransformationService transformationService;
    @Autowired
    private TransformationDetailService transformationDetailService;
    @Autowired
    private StockService stockService;

    @GetMapping("/stock")
    public String displayStock(Model model) {
        List<Block> blockList = blockService.getAllBlocks();
        List<Block> restelist = new ArrayList<>();
        List<TransformationDisplay> transformationDisplays = new ArrayList<>();
        List<List<StockDisplay>> maxBenef = new ArrayList<>();
        List<List<StockDisplay>> minPerte = new ArrayList<>();
        for (Block block : blockList) {
            TransformationDisplay transformationDisplay = transformationService.BeneficeTransformation(block.getId());
            transformationDisplays.add(transformationDisplay);
            Block reste = blockService.getFirstFille(block);
            restelist.add(reste);
            maxBenef.add(stockService.getMaxBenefice(reste));
            minPerte.add(stockService.getMinPerte(reste));
        }
        model.addAttribute("blocks", blockList);
        model.addAttribute("restelist", restelist);
        model.addAttribute("transformationDisplays", transformationDisplays);
        model.addAttribute("maxBenef", maxBenef);
        model.addAttribute("minPerte", minPerte);

        return "stock/stock";
    }

    @GetMapping("/stock_produit")
    public String displayStockproduits(Model model) {
        List<Block> blockList = blockService.getBlockValid();

        List<StockDisplay> stockDisplays = stockService.getStockDisplayForAllBlockValid(blockList);
        StockDisplay maxBenef = stockService.getMaxBenefice(stockDisplays);
        StockDisplay minPerte = stockService.getMinPerte(stockDisplays);
        List<ProduitDisplay> displaysProduit;
        displaysProduit = transformationDetailService.allTransformationGroupByProduit();

        model.addAttribute("produitStock", displaysProduit);
        model.addAttribute("maxBenef", maxBenef);
        model.addAttribute("minPerte", minPerte);
        return "stock/liste";
    }



    @GetMapping("/transformations-liste")
    public String listeProduitTransformer(Model model) {
        List<ProduitDisplay> displays = new ArrayList<>();
        displays = transformationDetailService.allTransformationGroupByProduit();
        model.addAttribute("produitsStock", displays);
        return "stock/liste";
    }
}
