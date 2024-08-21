package com.sgic.semita.repositories;

import com.sgic.semita.entities.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReleaseRepository extends JpaRepository<Release, Long> {
    Optional<Release> findByName(String name);
    Optional<Release> findByNameAndProjectId(String name, Long projectId);
    Page<Release> findByProjectId(Long projectId, Pageable pageable);
}
