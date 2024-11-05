package com.matela.production.service;

import com.matela.production.model.Produit;
import com.matela.production.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    public Produit getProduitById(Long id) {
        return produitRepository.findById(id).orElse(null);
    }

    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    public Produit updateProduit(Long id, Produit produitDetails) {
        Produit produit = produitRepository.findById(id).orElse(null);
        if (produit != null) {
            produit.setNom(produitDetails.getNom());
            produit.setLongueur(produitDetails.getLongueur());
            produit.setLargeur(produitDetails.getLargeur());
            produit.setEpaisseur(produitDetails.getEpaisseur());
            produit.setPrixVente(produitDetails.getPrixVente());
            return produitRepository.save(produit);
        }
        return null;
    }

    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }
}
