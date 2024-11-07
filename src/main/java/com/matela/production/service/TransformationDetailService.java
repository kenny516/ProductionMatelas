package com.matela.production.service;

import com.matela.production.entity.Block;
import com.matela.production.entity.Produit;
import com.matela.production.entity.ProduitDisplay;
import com.matela.production.entity.TransformationDetail;
import com.matela.production.repository.TransformationDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransformationDetailService {

    private final TransformationDetailRepository transformationDetailRepository;
    private final ProduitService produitService;

    @Autowired
    public TransformationDetailService(TransformationDetailRepository transformationDetailRepository, ProduitService produitService) {
        this.transformationDetailRepository = transformationDetailRepository;
        this.produitService = produitService;
    }

    // Method to save a TransformationDetail
    public TransformationDetail create(TransformationDetail transformationDetail) {
        return transformationDetailRepository.save(transformationDetail);
    }

    // Method to find all TransformationDetails
    public List<TransformationDetail> getAll() {
        return transformationDetailRepository.findAll();
    }

    // Method to find a TransformationDetail by ID
    public Optional<TransformationDetail> findById(Long id) {
        return transformationDetailRepository.findById(id);
    }
    public Optional<TransformationDetail> update(Long id,TransformationDetail transformationDetails) {
        return transformationDetailRepository.findById(id).map(transformationDetail -> {
            transformationDetail.setId(transformationDetails.getId());
            transformationDetail.setProduit(transformationDetails.getProduit());
            transformationDetail.setQuantite(transformationDetails.getQuantite());
            transformationDetail.setPrixRevient(transformationDetails.getPrixRevient());
            transformationDetail.setTransformation(transformationDetails.getTransformation());
            return transformationDetailRepository.save(transformationDetail);
        });
    }
    // Method to delete a TransformationDetail by ID
    public void deleteById(Long id) {
        transformationDetailRepository.deleteById(id);
    }

    public double volumeTransformationDetail(List<TransformationDetail> transformationDetails) {
        double volume = 0.0;
        for (TransformationDetail value : transformationDetails) {
            volume += value.getProduit().getVolume() * value.getQuantite();
        }
        return volume;
    }
    public double prixVente(List<TransformationDetail> transformationDetails){
        double prixVente = 0.0;
        for (TransformationDetail value : transformationDetails) {
            prixVente += value.getProduit().getPrixVente() * value.getQuantite();
        }
        return prixVente;
    }

    public List<ProduitDisplay> allTransformationGroupByProduit(){
        List<ProduitDisplay> displays = new ArrayList<>();
        List<Produit> produitList = produitService.getAllProduits();
        for (Produit produit : produitList) {
            ProduitDisplay produitDisplay = new ProduitDisplay();
            List<TransformationDetail> transformationDetails = transformationDetailRepository.findTransformationByproduit(produit.getId());
            double prixRevient=0;
            double quantite = 0;
            // formule moyenne ponderer (somme prixRevient / sommequantite)
            for (TransformationDetail transformationDetail : transformationDetails) {
                prixRevient += transformationDetail.getPrixRevient();
                quantite += transformationDetail.getQuantite();
            }
            produitDisplay.setPrixRevient(prixRevient);
        }
        return displays;
    }
}
