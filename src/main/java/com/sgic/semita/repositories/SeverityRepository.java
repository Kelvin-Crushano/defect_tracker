package com.sgic.semita.repositories;

import com.sgic.semita.entities.Severity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeverityRepository extends JpaRepository<Severity, Long> {
    Optional<Severity> findByName(String name);
}
