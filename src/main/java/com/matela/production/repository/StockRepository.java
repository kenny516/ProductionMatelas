package com.matela.production.repository;

import com.matela.production.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query(value = "SELECT * FROM stock WHERE block_id = :id order by date_inventaire desc limit 1",nativeQuery = true)
    public Stock getBlockLastValue(@Param("id") Long id);
}
