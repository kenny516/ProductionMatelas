package com.matela.production.repository;

import com.matela.production.entity.Formule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FormuleRepository extends JpaRepository<Formule,Long> {
    @Query(value = "SELECT f FROM Formule f ORDER BY f.id ASC LIMIT 1")
    Formule findFirstFormule();
}
