package com.sgic.semita.repositories;

import com.sgic.semita.entities.Module;
import com.sgic.semita.entities.Release;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import java.util.List;
public interface ModuleRepository extends JpaRepository<Module, Long> {
    Optional<Module> findByName(String name);
    boolean existsByName(String name);
    List<Module> findByProjectId(Long projectId);
    Optional<Module> findByNameAndProjectId(String name, Long projectId);
}
