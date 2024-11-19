package com.matela.production.service;

import com.matela.production.entity.AchatMatierePremierDetail;
import com.matela.production.repository.AchatMatierePremierDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AchatmatierepremierdetailService {

    private final AchatMatierePremierDetailRepository achatmatierepremierdetailRepository;

    public AchatmatierepremierdetailService(AchatMatierePremierDetailRepository achatmatierepremierdetailRepository) {
        this.achatmatierepremierdetailRepository = achatmatierepremierdetailRepository;
    }

    public List<AchatMatierePremierDetail> findAll() {
        return achatmatierepremierdetailRepository.findAll();
    }

    public Optional<AchatMatierePremierDetail> findById(Long id) {
        return achatmatierepremierdetailRepository.findById(id);
    }

    public AchatMatierePremierDetail save(AchatMatierePremierDetail achatmatierepremierdetail) {
        return achatmatierepremierdetailRepository.save(achatmatierepremierdetail);
    }

    public void deleteById(Long id) {
        achatmatierepremierdetailRepository.deleteById(id);
    }

    public Optional<AchatMatierePremierDetail> updateQuantite(Long id, Double quantite) {
        return achatmatierepremierdetailRepository.findById(id).map(detail -> {
            detail.setQuantite(quantite);
            return achatmatierepremierdetailRepository.save(detail);
        });
    }

    public Optional<AchatMatierePremierDetail> updatePrixAchat(Long id, Double prixAchat) {
        return achatmatierepremierdetailRepository.findById(id).map(detail -> {
            detail.setPrixAchat(prixAchat);
            return achatmatierepremierdetailRepository.save(detail);
        });
    }
}
