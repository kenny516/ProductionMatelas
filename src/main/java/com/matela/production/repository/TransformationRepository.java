package com.matela.production.repository;

import com.matela.production.entity.Transformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransformationRepository extends JpaRepository<Transformation, Long> {

    public List<Transformation> findByBlock_Id(Long blockId);



}
