package com.matela.production.repository;

import com.matela.production.entity.TransformationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransformationDetailRepository extends JpaRepository<TransformationDetail, Long> {
    // Additional query methods can be added here if needed
}
