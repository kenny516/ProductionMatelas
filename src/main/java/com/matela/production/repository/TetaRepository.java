package com.matela.production.repository;

import com.matela.production.entity.Teta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TetaRepository extends JpaRepository<Teta, Long> {
    Teta findByValue(Double value);

}
