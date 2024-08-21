package com.sgic.semita.repositories;

import com.sgic.semita.entities.DefectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefectTypeRepository extends JpaRepository<DefectType, Long> {
    Optional<DefectType> findByName(String name);
}
