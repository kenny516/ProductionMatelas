package com.matela.production.repository;


import com.matela.production.entity.Reste;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface ResteRepository extends JpaRepository<Reste, Long> {

    Reste findFirstByBlockIdOrderByDateCreationDesc(Long block_id);

}

//@Query(value = "SELECT * FROM reste r WHERE r.block_id = :blockId ORDER BY r.date_creation DESC LIMIT 1", nativeQuery = true)
//Reste findLastResteByBlockId(@Param("blockId") Long blockId);
