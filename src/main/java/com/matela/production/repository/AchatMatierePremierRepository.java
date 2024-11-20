package com.matela.production.repository;

import com.matela.production.entity.AchatMatierePremier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AchatMatierePremierRepository extends JpaRepository<AchatMatierePremier, Long> {
    @Query(value = """
            SELECT id_achat, matiere_premiere_id, quantite_actuelle, prix_achat, date_achat
            FROM vue_quantite_actuelle_achat
            """, nativeQuery = true)
    List<Object[]> findAllCurrentQuantitiesRaw();

    @Query(value = """
            SELECT id_achat, matiere_premiere_id, quantite_actuelle, prix_achat, date_achat
            FROM vue_quantite_actuelle_achat
            WHERE matiere_premiere_id = :matierePremiereId
            """, nativeQuery = true)
    List<Object[]> findByMatierePremiereCurrentQuantities(@Param("matierePremiereId") Long matierePremiereId);

    @Query(value = """
            SELECT id_achat, matiere_premiere_id, quantite_actuelle,prix_achat,date_achat
            FROM vue_quantite_actuelle_achat
            WHERE matiere_premiere_id = :matierePremiereId
            AND date_achat <= :date
            GROUP BY id_achat, matiere_premiere_id, date_achat, quantite_actuelle, prix_achat
            HAVING SUM(quantite_actuelle) >= :requiredQuantity
            """, nativeQuery = true)
    List<Object[]> findByMatierePremiereCurrentQuantitiesBefore(
            @Param("matierePremiereId") Long matierePremiereId,
            @Param("date") LocalDate date,
            @Param("requiredQuantity") Double requiredQuantity
    );


}
