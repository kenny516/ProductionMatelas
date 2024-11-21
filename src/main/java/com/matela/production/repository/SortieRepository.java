package com.matela.production.repository;

import com.matela.production.entity.Sortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SortieRepository extends JpaRepository<Sortie, Long> {

    // Exemple : récupérer toutes les sorties pour un achat spécifique
    List<Sortie> findByAchatMatierePremierId(Long achatMaterielId);
    List<Sortie> findByDateSortie(LocalDate date);
}
