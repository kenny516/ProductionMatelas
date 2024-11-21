package com.matela.production.service;

import com.matela.production.DTO.QuantiteActuelleAchatDTO;
import com.matela.production.entity.AchatMatierePremier;
import com.matela.production.repository.AchatMatierePremierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<QuantiteActuelleAchatDTO> findByMatierePremiereCurrentQuantitiesDate(Long idMatierePremiere, LocalDate date, double volume) {
        List<Object[]> results = achatmatierepremierRepository.findByMatierePremiereCurrentQuantitiesBefore(idMatierePremiere, date, volume);

        List<QuantiteActuelleAchatDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            Long idAchat = (Long) result[0];
            Long matierePremiereId = (Long) result[1];
            Double quantite_actuelle = (Double) result[2];
            Double prix_achat = (Double) result[3];
            LocalDate dateAchat = result[4] != null ? ((java.sql.Date) result[4]).toLocalDate() : null; // date_achat

            QuantiteActuelleAchatDTO dto = new QuantiteActuelleAchatDTO(idAchat, matierePremiereId,quantite_actuelle,prix_achat, dateAchat);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<QuantiteActuelleAchatDTO> findByMatierePremiereCurrentQuantitiesDatePerf(LocalDate date) {
        List<Object[]> results = achatmatierepremierRepository.findByMatierePremiereCurrentQuantitiesBeforePerf(date);

        List<QuantiteActuelleAchatDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            Long idAchat = (Long) result[0];
            Long matierePremiereId = (Long) result[1];
            Double quantite_actuelle = (Double) result[2];
            Double prix_achat = (Double) result[3];
            LocalDate dateAchat = result[4] != null ? ((java.sql.Date) result[4]).toLocalDate() : null; // date_achat

            QuantiteActuelleAchatDTO dto = new QuantiteActuelleAchatDTO(idAchat, matierePremiereId,quantite_actuelle,prix_achat, dateAchat);
            dtos.add(dto);
        }
        return dtos;
    }


}
