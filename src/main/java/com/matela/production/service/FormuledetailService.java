package com.matela.production.service;

import com.matela.production.entity.FormuleDetail;
import com.matela.production.repository.FormuleDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FormuledetailService {

    private final FormuleDetailRepository formuledetailRepository;

    public FormuledetailService(FormuleDetailRepository formuledetailRepository) {
        this.formuledetailRepository = formuledetailRepository;
    }

    public List<FormuleDetail> findAll() {
        return formuledetailRepository.findAll();
    }

    public Optional<FormuleDetail> findById(Long id) {
        return formuledetailRepository.findById(id);
    }

    public FormuleDetail save(FormuleDetail formuledetail) {
        return formuledetailRepository.save(formuledetail);
    }

    public void deleteById(Long id) {
        formuledetailRepository.deleteById(id);
    }

    public Optional<FormuleDetail> updateQuantite(Long id, Double quantite) {
        return formuledetailRepository.findById(id).map(detail -> {
            detail.setQuantite(quantite);
            return formuledetailRepository.save(detail);
        });
    }

    public Optional<FormuleDetail> updateUnite(Long id, String unite) {
        return formuledetailRepository.findById(id).map(detail -> {
            detail.setUnite(unite);
            return formuledetailRepository.save(detail);
        });
    }
}
