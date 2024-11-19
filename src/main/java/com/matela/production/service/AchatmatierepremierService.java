package com.matela.production.service;

import com.matela.production.entity.AchatMatierePremier;
import com.matela.production.repository.AchatMatierePremierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AchatmatierepremierService {

    private final AchatMatierePremierRepository achatmatierepremierRepository;

    public AchatmatierepremierService(AchatMatierePremierRepository achatmatierepremierRepository) {
        this.achatmatierepremierRepository = achatmatierepremierRepository;
    }

    public List<AchatMatierePremier> findAll() {
        return achatmatierepremierRepository.findAll();
    }

    public Optional<AchatMatierePremier> findById(Long id) {
        return achatmatierepremierRepository.findById(id);
    }

    public AchatMatierePremier save(AchatMatierePremier achatmatierepremier) {
        return achatmatierepremierRepository.save(achatmatierepremier);
    }

    public void deleteById(Long id) {
        achatmatierepremierRepository.deleteById(id);
    }

    public Optional<AchatMatierePremier> updateDateAchat(Long id, LocalDate dateAchat) {
        return achatmatierepremierRepository.findById(id).map(achat -> {
            achat.setDateAchat(dateAchat);
            return achatmatierepremierRepository.save(achat);
        });
    }
}
