package com.matela.production.service;

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
}
