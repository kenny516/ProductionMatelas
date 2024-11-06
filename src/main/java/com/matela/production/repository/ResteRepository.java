package com.matela.production.repository;

import com.matela.production.entity.Reste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResteRepository extends JpaRepository<Reste, Long> {
}
