package com.matela.production.service;

import com.matela.production.entity.Sortie;
import com.matela.production.repository.SortieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SortieService {

    private final SortieRepository sortieRepository;

    public SortieService(SortieRepository sortieRepository) {
        this.sortieRepository = sortieRepository;
    }

    public Sortie createSortie(Sortie sortie) {
        return sortieRepository.save(sortie);
    }

    public List<Sortie> getAllSorties() {
        return sortieRepository.findAll();
    }

    public Optional<Sortie> getSortieById(Long id) {
        return sortieRepository.findById(id);
    }

    public List<Sortie> getSortiesByAchatMaterielId(Long achatMaterielId) {
        return sortieRepository.findByAchatMatierePremierId(achatMaterielId);
    }

    public void deleteSortie(Long id) {
        sortieRepository.deleteById(id);
    }


}
