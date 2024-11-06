package com.matela.production.service;

import com.matela.production.entity.Produit;
import com.matela.production.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    public Optional<Produit> getProduitById(Long id) {
        return produitRepository.findById(id);
    }

    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    public Optional<Produit> updateProduit(Long id, Produit produitDetails) {
        return produitRepository.findById(id).map(produit -> {
            produit.setNom(produitDetails.getNom());
            produit.setLongueur(produitDetails.getLongueur());
            produit.setLargeur(produitDetails.getLargeur());
            produit.setEpaisseur(produitDetails.getEpaisseur());
            produit.setPrixVente(produitDetails.getPrixVente());
            return produitRepository.save(produit);
        });
    }

    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }

    public Double getVolume(Produit produit) {
        return produit.getLongueur() * produit.getLargeur() * produit.getEpaisseur();
    }
}
