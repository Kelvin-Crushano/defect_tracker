package com.sgic.semita.repositories;

import com.sgic.semita.entities.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
    Optional<ProjectStatus> findByName(String name);

}
