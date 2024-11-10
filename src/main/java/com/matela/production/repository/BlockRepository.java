package com.matela.production.repository;

import com.matela.production.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query(value = """
            SELECT * FROM block WHERE id not in (
                SELECT block_mere FROM block WHERE block_mere is not NULL
                );""",nativeQuery = true)
    List<Block> getValidBlock();

    @Query(value = "WITH RECURSIVE descendants AS (" +
            "    SELECT * " +
            "    FROM block " +
            "    WHERE id = :id" +
            "    UNION ALL " +
            "    SELECT b.* " +
            "    FROM block b " +
            "    INNER JOIN descendants d ON b.block_mere = d.id" +
            ") " +
            "SELECT * " +
            "FROM descendants " +
            "ORDER BY date_production DESC " +
            "LIMIT 1", nativeQuery = true)
    Block getLastFIlle(@Param("id") Long id);

    @Query(value = "WITH RECURSIVE descendants AS (" +
            "    SELECT *, 1 AS level " +
            "    FROM block " +
            "    WHERE id = :id" +
            "    UNION ALL " +
            "    SELECT b.*, d.level + 1 " +
            "    FROM block b " +
            "    INNER JOIN descendants d ON b.block_mere = d.id" +
            ") " +
            "SELECT * " +
            "FROM descendants " +
            "WHERE level = 2 " +  // Sélectionner le deuxième enfant
            "ORDER BY date_production ASC " +
            "LIMIT 1", nativeQuery = true)
    Block getFirstFIlle(@Param("id") Long id);


    @Query(value = "WITH RECURSIVE descendants AS (" +
            "    SELECT * " +
            "    FROM block " +
            "    WHERE id = :id" +
            "    UNION ALL " +
            "    SELECT b.* " +
            "    FROM block b " +
            "    INNER JOIN descendants d ON b.block_mere = d.id" +
            ") " +
            "SELECT * " +
            "FROM descendants", nativeQuery = true)
    List<Block> getAllDescendants(@Param("id") Long id);

    @Query(value = "WITH RECURSIVE ancestors AS (" +
            "    SELECT * " +
            "    FROM block " +
            "    WHERE id = :id" +
            "    UNION ALL " +
            "    SELECT b.* " +
            "    FROM block b " +
            "    INNER JOIN ancestors a ON a.block_mere = b.id" +
            ") " +
            "SELECT * " +
            "FROM ancestors " +
            "ORDER BY date_production ASC " +
            "LIMIT 1", nativeQuery = true)
    Block getFirstParent(@Param("id") Long id);




}
