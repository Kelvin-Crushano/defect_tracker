package com.sgic.semita.repositories;

import com.sgic.semita.entities.DefectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefectStatusRepository extends JpaRepository<DefectStatus, Long> {
    Optional<DefectStatus> findByName(String name);
}
