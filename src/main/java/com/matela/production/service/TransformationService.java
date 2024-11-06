package com.matela.production.service;

import com.matela.production.entity.Reste;
import com.matela.production.entity.Transformation;
import com.matela.production.repository.TransformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransformationService {

    @Autowired
    private TransformationRepository transformationRepository;

    public List<Transformation> getAllTransformations() {
        return transformationRepository.findAll();
    }

    public Optional<Transformation> getTransformationById(Long id) {
        return transformationRepository.findById(id);
    }

    public Transformation createTransformation(Transformation transformation) {
        return transformationRepository.save(transformation);
    }

    public Optional<Transformation> updateTransformation(Long id, Transformation transformationDetails) {
        return transformationRepository.findById(id).map(transformation -> {

            transformation.setBlock(transformationDetails.getBlock());
            transformation.setProduit(transformationDetails.getProduit());
            transformation.setQuantite(transformationDetails.getQuantite());
            transformation.setPrixRevient(transformationDetails.getPrixRevient());
            return transformationRepository.save(transformation);
        });
    }

    public void deleteTransformation(Long id) {
        transformationRepository.deleteById(id);
    }

    public List<Transformation> getTransformationByblock(Long id) {
        return transformationRepository.findByBlock_Id(id);
    }

    public Double beneficeGetByTransformation(List<Transformation> transformation) {
        double benefice = 0.0;
        for (Transformation value : transformation) {
            benefice += (value.getProduit().getPrixVente() - value.getPrixRevient()) * value.getQuantite();
        }
        return benefice;
    }

    public Double beneficeGetByTransformationBlock(Long id) {
        List<Transformation> transformations = getTransformationByblock(id);
        return beneficeGetByTransformation(transformations);
    }

    // le calcul est effectuer en volume verifier qu'il y a une transformation
    public boolean verificationReste(List<Transformation> transformation, Double resteForm, Double teta) {
        double resteCalc = 0.0;
        double tetaValue = (transformation.getFirst().getBlock().getVolume() * teta) / 100;
        for (Transformation value : transformation) {
            resteCalc += value.getProduit().getVolume() * value.getQuantite();
        }
        if (resteForm < transformation.getFirst().getBlock().getVolume()) {
            return Math.abs(resteCalc - resteForm) <= tetaValue;
        }
        return false;
    }

    public Double prixRevient(Transformation transformation) {
        double prixRevientBlock = transformation.getBlock().getCoutProduction();
        double volumeBlock = transformation.getBlock().getVolume();
        double volumeReste = transformation.getReste().getVolume();
        return (volumeReste * prixRevientBlock) / volumeBlock;
    }


}
