package com.matela.production.service;

import com.matela.production.model.Reste;
import com.matela.production.repository.ResteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResteService {

    @Autowired
    private ResteRepository resteRepository;

    public List<Reste> getAllRestes() {
        return resteRepository.findAll();
    }

    public Reste getResteById(Long id) {
        return resteRepository.findById(id).orElse(null);
    }

    public Reste createReste(Reste reste) {
        return resteRepository.save(reste);
    }

    public Reste updateReste(Long id, Reste resteDetails) {
        Reste reste = resteRepository.findById(id).orElse(null);
        if (reste != null) {
            reste.setLongueur(resteDetails.getLongueur());
            reste.setLargeur(resteDetails.getLargeur());
            reste.setEpaisseur(resteDetails.getEpaisseur());
            reste.setCoutProduction(resteDetails.getCoutProduction());
            return resteRepository.save(reste);
        }
        return null;
    }

    public void deleteReste(Long id) {
        resteRepository.deleteById(id);
    }
}
