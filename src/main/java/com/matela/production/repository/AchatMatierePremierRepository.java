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

    @Query(value = "select  * from achatmatierepremier order by date_achat",nativeQuery = true)
    public List<AchatMatierePremier> getAllAsc();


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
    //lay having alagna fogn

    @Query(value = """
            SELECT id_achat, matiere_premiere_id, quantite_actuelle,prix_achat,date_achat
            FROM vue_quantite_actuelle_achat
            WHERE date_achat <= :date
            """, nativeQuery = true)
    List<Object[]> findByMatierePremiereCurrentQuantitiesBeforePerf(
            @Param("date") LocalDate date
    );

    @Query(value = """
            SELECT id, matiere_premiere_id, quantite,prix_achat,date_achat
            FROM achatmatierepremier
            WHERE date_achat <= :date
            GROUP BY id, matiere_premiere_id, date_achat, quantite, prix_achat
            """, nativeQuery = true)
    List<AchatMatierePremier> findByDate(
            @Param("date") LocalDate date
    );
    @Query(value = """
            SELECT id_achat, matiere_premiere_id, quantite_actuelle,prix_achat,date_achat
            FROM vue_quantite_actuelle_achat
            """, nativeQuery = true)
    List<Object[]> findByJiaby();


    @Query(value = """
            SELECT a.id                                        AS id_achat,
                   a.matiere_premiere_id,
                   (a.quantite - COALESCE(SUM(s.quantite), 0)) AS quantite_actuelle,
                   a.prix_achat,
                   a.date_achat
            FROM achatMatierePremier a
                     LEFT JOIN sortie s
                               ON a.id = s.id_achatMateriel
            WHERE s.date_sortie <= :date
            GROUP BY a.id, a.matiere_premiere_id, a.prix_achat, a.date_achat, a.quantite
            ORDER BY a.date_achat
            """, nativeQuery = true)
    List<Object[]> etatStock(
            @Param("date") LocalDate date
    );

}
