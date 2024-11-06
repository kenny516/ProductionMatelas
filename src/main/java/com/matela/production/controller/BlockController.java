package com.matela.production.controller;

import com.matela.production.entity.Block;
import com.matela.production.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlockController {
    @Autowired
    private BlockService blockService;

    @GetMapping("/form-block")
    public String insertionBlock(Model model) {
        model.addAttribute("block",new Block());
        return "InsertionBlock";
    }
    @PostMapping
    public String block(Block block, Model model){
        blockService.createBlock(block);
        return "InsertionBlock";
    }





}
