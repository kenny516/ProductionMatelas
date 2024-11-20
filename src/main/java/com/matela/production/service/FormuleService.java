package com.matela.production.service;

import com.matela.production.entity.Formule;
import com.matela.production.repository.FormuleRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FormuleService {

    private final FormuleRepository formuleRepository;

    public FormuleService(FormuleRepository formuleRepository) {
        this.formuleRepository = formuleRepository;
    }

    public List<Formule> findAll() {
        return formuleRepository.findAll();
    }

    @Cacheable("formule")
    public Optional<Formule> findById(Long id) {
        return formuleRepository.findById(id);
    }
    @Cacheable("formulle1")
    public Formule findByFirst() {
        return formuleRepository.findFirst();
    }

    public Formule save(Formule formule) {
        return formuleRepository.save(formule);
    }

    public void deleteById(Long id) {
        formuleRepository.deleteById(id);
    }

    public Optional<Formule> updateNom(Long id, String nom) {
        return formuleRepository.findById(id).map(formule -> {
            formule.setNom(nom);
            return formuleRepository.save(formule);
        });
    }

    public Optional<Formule> updateDescription(Long id, String description) {
        return formuleRepository.findById(id).map(formule -> {
            formule.setDescription(description);
            return formuleRepository.save(formule);
        });
    }
}
