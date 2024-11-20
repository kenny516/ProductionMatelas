package com.matela.production.repository;

import com.matela.production.DTO.QuantiteActuelleAchatDTO;
import com.matela.production.entity.AchatMatierePremier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AchatMatierePremierRepository extends JpaRepository<AchatMatierePremier, Long> {
    @Query(value = """
                SELECT * from vue_quantite_actuelle_achat
            """, nativeQuery = true)
    List<QuantiteActuelleAchatDTO> findAllCurrentQuantitiesRaw();

    @Query(value = """
                SELECT * from vue_quantite_actuelle_achat
                where matiere_premiere_id = :matierePremiereId
            """, nativeQuery = true)
    List<QuantiteActuelleAchatDTO> findByMatierePremiereCurrentQuantities(Long matierePremiereId);

    @Query(value = """
                SELECT * 
                FROM vue_quantite_actuelle_achat
                WHERE matiere_premiere_id = :matierePremiereId
                  AND date_achat <= :date
            """, nativeQuery = true)
    List<QuantiteActuelleAchatDTO> findByMatierePremiereCurrentQuantitiesBefore(
            @Param("matierePremiereId") Long matierePremiereId,
            @Param("date") LocalDate date
    );

}
