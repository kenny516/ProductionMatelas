package com.matela.production.repository;

import com.matela.production.entity.TransformationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransformationDetailRepository extends JpaRepository<TransformationDetail, Long> {
    // Additional query methods can be added here if needed

    @Query(value = "SELECT * FROM transformation_detail where produit_id = :id",nativeQuery = true)
    public List<TransformationDetail> findTransformationByproduit(Long id);
}
