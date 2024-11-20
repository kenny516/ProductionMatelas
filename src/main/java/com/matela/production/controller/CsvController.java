package com.matela.production.controller;

import com.matela.production.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class CsvController {
    @Autowired
    BlockService blockService;


    @GetMapping("/csv")
    public String csvFront(){
        return "csv/ImportCsv";
    }

    @PostMapping("/import-csvBlock")
    public String importCsv(@RequestParam("csvFile") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            return "redirect:/?error";
        }
        try {
            blockService.importBlocksFromCsv(file);
            model.addAttribute("status", "success");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("status", "error");
        }
        return "result-status";
    }

    private void saveCsvData(List<String[]> data) {
        // Logic to save parsed data into the database
    }

    @GetMapping("/generer")
    public String genererDonner(Model model){
        blockService.GenererCSV(1000000,5000,1,3,"D:\\L3\\s5\\MrTahina\\SpongeProject\\ProductionMatelas\\database\\csv\\test.csv");
        model.addAttribute("status", "success");
        return "result-status";
    }

}
