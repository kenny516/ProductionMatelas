package com.matela.production.service;

import com.matela.production.entity.Reste;
import com.matela.production.repository.ResteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResteService {

    @Autowired
    private ResteRepository resteRepository;

    public List<Reste> getAllRestes() {
        return resteRepository.findAll();
    }

    public Optional<Reste> getResteById(Long id) {
        return resteRepository.findById(id);
    }

    public Reste createReste(Reste reste) {
        return resteRepository.save(reste);
    }

    public Optional<Reste> updateReste(Long id, Reste resteDetails) {
        return resteRepository.findById(id).map(reste ->{
            reste.setLongueur(resteDetails.getLongueur());
            reste.setLargeur(resteDetails.getLargeur());
            reste.setEpaisseur(resteDetails.getEpaisseur());
            reste.setCoutProduction(resteDetails.getCoutProduction());
            return resteRepository.save(reste);
        });
    }

    public void deleteReste(Long id) {
        resteRepository.deleteById(id);
    }
}
