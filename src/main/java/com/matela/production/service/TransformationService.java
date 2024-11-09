package com.matela.production.service;

import com.matela.production.entity.Block;
import com.matela.production.entity.Transformation;
import com.matela.production.entity.TransformationDetail;
import com.matela.production.entity.TransformationDisplay;
import com.matela.production.repository.TransformationDetailRepository;
import com.matela.production.repository.TransformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransformationService {

    @Autowired
    private TransformationRepository transformationRepository;
    @Autowired
    private TransformationDetailRepository transformationDetailRepository;
    @Autowired
    private TransformationDetailService transformationDetailService;

    public List<Transformation> getAllTransformations() {
        return transformationRepository.findAll();
    }

    public Optional<Transformation> getTransformationById(Long id) {
        return transformationRepository.findById(id);
    }

    public Transformation createTransformation(Transformation transformation) {
        return transformationRepository.save(transformation);
    }

//    public Optional<Transformation> updateTransformation(Long id, Transformation transformationDetails) {
//        return transformationRepository.findById(id).map(transformation -> {
//
//            transformation.setBlock(transformationDetails.getBlock());
//            transformation.setProduit(transformationDetails.getProduit());
//            transformation.setQuantite(transformationDetails.getQuantite());
//            transformation.setPrixRevient(transformationDetails.getPrixRevient());
//            return transformationRepository.save(transformation);
//        });
//    }

    public void deleteTransformation(Long id) {
        transformationRepository.deleteById(id);
    }

    public List<Transformation> getTransformationByblock(Long id) {
        return transformationRepository.findByBlock_Id(id);
    }

//    public Double beneficeGetByTransformation(List<Transformation> transformation) {
//        double benefice = 0.0;
//        for (Transformation value : transformation) {
//            benefice += (value.getProduit().getPrixVente() - value.getPrixRevient()) * value.getQuantite();
//        }
//        return benefice;
//    }

//    public Double beneficeGetByTransformationBlock(Long id) {
//        List<Transformation> transformations = getTransformationByblock(id);
//        return beneficeGetByTransformation(transformations);
//    }

    // le calcul est effectuer en volume verifier qu'il y a une transformation
//    public boolean verificationReste(List<Transformation> transformation, Double resteForm, Double teta) {
//        double resteCalc = 0.0;
//        double tetaValue = (transformation.getFirst().getBlock().getVolume() * teta) / 100;
//        for (Transformation value : transformation) {
//            resteCalc += value.getProduit().getVolume() * value.getQuantite();
//        }
//        if (resteForm < transformation.getFirst().getBlock().getVolume()) {
//            return Math.abs(resteCalc - resteForm) <= tetaValue;
//        }
//        return false;
//    }
//
//    public Double prixRevient(Transformation transformation) {
//        double prixRevientBlock = transformation.getBlock().getCoutProduction();
//        double volumeBlock = transformation.getBlock().getVolume();
//        double volumeReste = transformation.getReste().getVolume();
//        return (volumeReste * prixRevientBlock) / volumeBlock;
//    }

    public boolean validateTransformation(Transformation transformation) {
        double blockVolume = transformation.getBlock().getVolume();
        double produitVolume = transformationDetailService.volumeTransformationDetail(transformation.getTransformationDetail());
        if (blockVolume < produitVolume) {
            return false;
        }
        double resteCalc = blockVolume - produitVolume;
        double resteForm = transformation.getReste().getVolume();
        System.out.println("restForm " + resteForm);
        double tetaValue = (blockVolume * 30) / 100;
        System.out.println("resteCalc " + resteCalc);
        System.out.println("resteCalc " + tetaValue);
        if (resteForm < blockVolume) {
            return Math.abs(resteCalc - resteForm) <= tetaValue;
        }
        return false;
    }

    public TransformationDisplay BeneficeTransformation(Long idBlock) {
        List<Transformation> transformations = getTransformationByblock(idBlock);
//        TransformationDisplay transformationDisplay = new TransformationDisplay();
        double prixVente = 0;
        double prixRevient = 0;
        double benefice = 0;
        for (Transformation transformation : transformations) {
            prixRevient += transformation.getBlock().getCoutProduction() -transformation.getReste().getCoutProduction();
            prixVente += transformationDetailService.prixVente(transformation.getTransformationDetail());
            benefice+= prixVente - prixRevient;
        }
        return new TransformationDisplay(prixRevient,prixVente,benefice);
    }

    public void updateRevient(List<Block> blockList,double proportion){
        for (Block block : blockList) {
            List<Transformation> transformations =  getTransformationByblock(block.getId());
            for (Transformation transformation : transformations){
                for (TransformationDetail transformationDetail : transformation.getTransformationDetail()){
                    double prNew = transformationDetail.getPrixRevient() * proportion;
                    transformationDetail.setPrixRevient(prNew);
                    transformationDetailService.update(transformationDetail.getId(),transformationDetail);
                }
            }
        }
    }








}
