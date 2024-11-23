package com.matela.production.controller;

import com.matela.production.DTO.MachineDTO;
import com.matela.production.entity.Block;
import com.matela.production.service.BlockService;
import com.matela.production.service.TransformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlockController {
    @Autowired
    private BlockService blockService;
    @Autowired
    private TransformationService transformationService;

    @GetMapping("/form-block")
    public String insertionBlock(Model model) {
        model.addAttribute("block", new Block());
        return "block/InsertionBlock";
    }

    @GetMapping("/blocks")
    public String listeBlocks(Model model) {
        model.addAttribute("blocks", blockService.getAllBlocks());
        return "block/liste";
    }

    @PostMapping("save-product")
    public String saveblock(Block block, Model model) {
        blockService.createBlock(block);
        Block[] listeBlock = blockService.getAllBlocks().toArray(new Block[0]);
        model.addAttribute("blocks", listeBlock);
        model.addAttribute("status", "success");
        return "result-status";
    }

    @GetMapping("/update-block-form")
    public String updateFormTransformation(Model model) {
        model.addAttribute("blocks", blockService.getAllBlocks());
        model.addAttribute("blockTs",new Block());

        return "transformation/UpdatePr";
    }
    @PostMapping("/update-block")
    public String updateTransformation(Block block,Model model) {
        double proportion =  blockService.UpdatePR(block);
        List<Block> blockList = blockService.getAllResteBlock(block);
        transformationService.updateRevient(blockList,proportion);
        model.addAttribute("status", "success");
        return "result-status";
    }


    @GetMapping("/dashboard")
    public String dashboardMachine(Model model) {
        List<MachineDTO> machineDTOS = blockService.getMachineCosts();
        model.addAttribute("machines", machineDTOS);
        return "block/dashboard";
    }
    @GetMapping("/dashboard/year")
    public String showDashboard(@RequestParam(value = "year") int year, Model model) {
        List<MachineDTO> machines ;
        if (year==0){
            machines = blockService.getMachineCosts();
        }else{
           machines = blockService.getMachineCosts(year);
        }
        model.addAttribute("machines", machines);
        model.addAttribute("year", year);
        return "block/dashboard";
    }

}
