package com.matela.production.repository;

import com.matela.production.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine,Long> {
}
