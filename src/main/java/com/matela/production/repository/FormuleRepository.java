package com.matela.production.repository;

import com.matela.production.entity.Formule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormuleRepository extends JpaRepository<Formule,Long> {
    Formule findFirst();
}
