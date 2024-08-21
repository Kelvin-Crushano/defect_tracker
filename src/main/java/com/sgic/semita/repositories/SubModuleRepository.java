package com.sgic.semita.repositories;

import com.sgic.semita.entities.Release;
import com.sgic.semita.entities.SubModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;
@Repository
public interface SubModuleRepository extends JpaRepository<SubModule, Long> {
    Optional<SubModule> findByName(String name);

    Page<SubModule> findByModuleProjectId(Long projectId, Pageable pageable);
    boolean existsByName(String name);
    List<SubModule> findByModuleId(Long moduleId);
    Optional<SubModule> findByNameAndModuleId(String name, Long projectId);

    @Query("SELECT sm FROM SubModule sm JOIN sm.module m JOIN m.project p WHERE p.id = :projectId AND m.id = :moduleId")
    Page<SubModule> findByModuleIdAndProjectId(@Param("moduleId") Long moduleId, @Param("projectId") Long projectId, Pageable pageable);


}
