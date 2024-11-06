package com.matela.production.service;

import com.matela.production.entity.TransformationDetail;
import com.matela.production.repository.TransformationDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransformationDetailService {

    private final TransformationDetailRepository transformationDetailRepository;

    @Autowired
    public TransformationDetailService(TransformationDetailRepository transformationDetailRepository) {
        this.transformationDetailRepository = transformationDetailRepository;
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

    // Method to delete a TransformationDetail by ID
    public void deleteById(Long id) {
        transformationDetailRepository.deleteById(id);
    }

    // You can add more business logic here as needed
}
