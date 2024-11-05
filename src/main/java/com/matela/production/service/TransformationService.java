package com.matela.production.service;

import com.matela.production.model.Transformation;
import com.matela.production.repository.TransformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransformationService {

    @Autowired
    private TransformationRepository transformationRepository;

    public List<Transformation> getAllTransformations() {
        return transformationRepository.findAll();
    }

    public Transformation getTransformationById(Long id) {
        return transformationRepository.findById(id).orElse(null);
    }

    public Transformation createTransformation(Transformation transformation) {
        return transformationRepository.save(transformation);
    }

    public Transformation updateTransformation(Long id, Transformation transformationDetails) {
        Transformation transformation = transformationRepository.findById(id).orElse(null);
        if (transformation != null) {
            transformation.setBlock(transformationDetails.getBlock());
            transformation.setProduit(transformationDetails.getProduit());
            transformation.setQuantite(transformationDetails.getQuantite());
            transformation.setPrixRevient(transformationDetails.getPrixRevient());
            return transformationRepository.save(transformation);
        }
        return null;
    }

    public void deleteTransformation(Long id) {
        transformationRepository.deleteById(id);
    }
}
