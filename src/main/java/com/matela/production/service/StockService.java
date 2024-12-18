package com.matela.production.service;

import com.matela.production.entity.Block;
import com.matela.production.entity.Produit;
import com.matela.production.entity.Stock;
import com.matela.production.entity.StockDisplay;
import com.matela.production.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private BlockService blockService;
    @Autowired
    private ProduitService produitService;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(Long id) {
        return stockRepository.findById(id);
    }

    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    public Stock getLastValueBlock(Block block){
        return stockRepository.getBlockLastValue(block.getId());
    }

    public List<StockDisplay> getMaxBenefice(Block block) {
        List<StockDisplay> stockDisplays = new ArrayList<>();
        List<Produit> produitList = produitService.getAllProduits();
        double maxBenefice = 0.0;
        int idChoisit = -1;

        for (int i = 0; i < produitList.size(); i++) {
            Produit produit = produitList.get(i);
            int nbProduit = (int) (block.getVolume() / produit.getVolume());
            double volume = produit.getVolume() * nbProduit;
            double prixVente = produit.getPrixVente() * nbProduit;
            double prixRevient = produitService.prixRevientVolume(block.getVolume(), block.getCoutProduction(), volume);

            StockDisplay stockDisplay = new StockDisplay();
            stockDisplay.setNomProduit(produit.getNom());
            stockDisplay.setQuantite(nbProduit);
            stockDisplay.setVolume(volume);
            stockDisplay.setPrixVente(prixVente);
            stockDisplay.setPrixRevient(prixRevient);
            stockDisplay.setEstChoisit(false);

            double currentBenefice = prixVente / volume;
            if (currentBenefice > maxBenefice) {
                maxBenefice = currentBenefice;
                idChoisit = i;
            }
            stockDisplays.add(stockDisplay);
        }
        if (idChoisit >= 0) {
            stockDisplays.get(idChoisit).setEstChoisit(true);
        }
        return stockDisplays;
    }

    public List<StockDisplay> getMinPerte(Block block) {
        List<StockDisplay> stockDisplays = new ArrayList<>();
        List<Produit> produitList = produitService.getAllProduits();
        double volumeUsed = 0.0;
        int idChoisit = -1;

        for (int i = 0; i < produitList.size(); i++) {
            Produit produit = produitList.get(i);
            int nbProduit = (int) (block.getVolume() / produit.getVolume());
            double volume = produit.getVolume() * nbProduit;
            double prixVente = (produit.getPrixVente() * nbProduit);
            double prixRevient = produitService.prixRevientVolume(block.getVolume(), block.getCoutProduction(), volume);

            StockDisplay stockDisplay = new StockDisplay();
            stockDisplay.setNomProduit(produit.getNom());
            stockDisplay.setQuantite(nbProduit);
            stockDisplay.setVolume(volume);
            stockDisplay.setPrixVente(prixVente);
            stockDisplay.setPrixRevient(prixRevient);
            stockDisplay.setEstChoisit(false);

            if (volume > volumeUsed) {
                volumeUsed = volume;
                idChoisit = i;
            }
            stockDisplays.add(stockDisplay);
        }
        if (idChoisit >= 0) {
            stockDisplays.get(idChoisit).setEstChoisit(true);
        }
        return stockDisplays;
    }

    public List<StockDisplay> getStockDisplayByblokc(Block block) {
        List<StockDisplay> stockDisplays = new ArrayList<>();
        List<Produit> produitList = produitService.getAllProduits();
        for (Produit produit : produitList) {
            int nbProduit = (int) (block.getVolume() / produit.getVolume());
            double volume = produit.getVolume() * nbProduit;
            double prixVente = (produit.getPrixVente() * nbProduit);
            double prixRevient = produitService.prixRevientVolume(block.getVolume(), block.getCoutProduction(), volume);
            StockDisplay stockDisplay = new StockDisplay();
            stockDisplay.setNomProduit(produit.getNom());
            stockDisplay.setQuantite(nbProduit);
            stockDisplay.setVolume(volume);
            stockDisplay.setPrixVente(prixVente);
            stockDisplay.setPrixRevient(prixRevient);
            stockDisplay.setEstChoisit(false);
            stockDisplays.add(stockDisplay);
        }
        return stockDisplays;
    }

//////////////////////////////
    public List<StockDisplay> getStockDisplayForAllBlockValid(List<Block> blockList) {
        List<StockDisplay> stockDisplays = new ArrayList<>();
        List<Produit> produitList = produitService.getAllProduits();

        for (Produit produit : produitList) {
            int nbProduit = 0;
            double volume = 0;
            double prixVente = 0;
            double prixRevient = 0;
            for (Block block : blockList) {
                int nbProduitActu = (int) (block.getVolume() / produit.getVolume());
                double volumeActu = produit.getVolume() * nbProduitActu;
                nbProduit += nbProduitActu;
                volume += produit.getVolume() * nbProduitActu;
                prixVente += (produit.getPrixVente() * nbProduitActu);
                prixRevient += produitService.prixRevientVolume(block.getVolume(), block.getCoutProduction(), volumeActu);
            }
            StockDisplay stockDisplay = new StockDisplay();
            stockDisplay.setNomProduit(produit.getNom());
            stockDisplay.setQuantite(nbProduit);
            stockDisplay.setVolume(volume);
            stockDisplay.setPrixVente(prixVente);
            stockDisplay.setPrixRevient(prixRevient);
            stockDisplay.setEstChoisit(false);
            stockDisplays.add(stockDisplay);
        }
        return stockDisplays;
    }

    public List<StockDisplay> setMinPerte(List<StockDisplay> listsStockDisplays){
        double volumeUsed = 0.0;
        int idChoisit = -1;
        for (int i = 0; i < listsStockDisplays.size(); i++) {
            listsStockDisplays.get(i).setEstChoisit(false);
            if (listsStockDisplays.get(i).getVolume() > volumeUsed) {
                volumeUsed = listsStockDisplays.get(i).getVolume();
                idChoisit = i;
            }
        }
        if (idChoisit >= 0) {
            listsStockDisplays.get(idChoisit).setEstChoisit(true);
        }
        return listsStockDisplays;
    }
    public List<StockDisplay> setMaxBenefice(List<StockDisplay> listsStockDisplays){
        double maxBenefice = 0.0;
        int idChoisit = -1;
        for (int i = 0; i < listsStockDisplays.size(); i++) {
            listsStockDisplays.get(i).setEstChoisit(false);
            double currentBenefice = listsStockDisplays.get(i).getPrixVente() / listsStockDisplays.get(i).getVolume();
            if (currentBenefice > maxBenefice) {
                maxBenefice = currentBenefice;
                idChoisit = i;
            }
        }
        if (idChoisit >= 0) {
            listsStockDisplays.get(idChoisit).setEstChoisit(true);
        }
        return listsStockDisplays;
    }
    public StockDisplay getMinPerte(List<StockDisplay> listsStockDisplays){
        double volumeUsed = 0.0;
        int idChoisit = -1;
        for (int i = 0; i < listsStockDisplays.size(); i++) {
            listsStockDisplays.get(i).setEstChoisit(false);
            if (listsStockDisplays.get(i).getVolume() > volumeUsed) {
                volumeUsed = listsStockDisplays.get(i).getVolume();
                idChoisit = i;
            }
        }
        if (idChoisit >= 0) {
            listsStockDisplays.get(idChoisit).setEstChoisit(true);
        }
        return listsStockDisplays.get(idChoisit);
    }
    public StockDisplay getMaxBenefice(List<StockDisplay> listsStockDisplays){
        double maxBenefice = 0.0;
        int idChoisit = -1;
        for (int i = 0; i < listsStockDisplays.size(); i++) {
            listsStockDisplays.get(i).setEstChoisit(false);
            double currentBenefice = listsStockDisplays.get(i).getPrixVente() / listsStockDisplays.get(i).getVolume();
            if (currentBenefice > maxBenefice) {
                maxBenefice = currentBenefice;
                idChoisit = i;
            }
        }
        if (idChoisit >= 0) {
            listsStockDisplays.get(idChoisit).setEstChoisit(true);
        }
        return listsStockDisplays.get(idChoisit);
    }








}
