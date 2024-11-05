package com.matela.production.repository;

import com.matela.production.model.Transformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransformationRepository extends JpaRepository<Transformation, Long> {
}
