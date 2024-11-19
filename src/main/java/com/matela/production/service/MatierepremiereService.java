package com.matela.production.service;

import com.matela.production.entity.MatierePremiere;
import com.matela.production.repository.MatierePremiereRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MatierepremiereService {

    private final MatierePremiereRepository matierepremiereRepository;

    public MatierepremiereService(MatierePremiereRepository matierepremiereRepository) {
        this.matierepremiereRepository = matierepremiereRepository;
    }

    public List<MatierePremiere> findAll() {
        return matierepremiereRepository.findAll();
    }

    public Optional<MatierePremiere> findById(Long id) {
        return matierepremiereRepository.findById(id);
    }

    public MatierePremiere save(MatierePremiere matierepremiere) {
        return matierepremiereRepository.save(matierepremiere);
    }

    public void deleteById(Long id) {
        matierepremiereRepository.deleteById(id);
    }

    public Optional<MatierePremiere> updateNom(Long id, String nom) {
        return matierepremiereRepository.findById(id).map(matierePremiere -> {
            matierePremiere.setNom(nom);
            return matierepremiereRepository.save(matierePremiere);
        });
    }

    public Optional<MatierePremiere> updatePrixAchat(Long id, Double  prixAchat) {
        return matierepremiereRepository.findById(id).map(matierePremiere -> {
            matierePremiere.setPrixAchat(prixAchat);
            return matierepremiereRepository.save(matierePremiere);
        });
    }
}
