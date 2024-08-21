package com.sgic.semita.repositories;

import com.sgic.semita.entities.StatusWorkflow;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusWorkflowRepository extends JpaRepository<StatusWorkflow, Long> {
    List<StatusWorkflow> findByProjectIdAndFromStatusId(Long projectId, Long fromStatusId);
    Page<StatusWorkflow> findByProjectId(Long projectId, Pageable pageable);
    @Transactional
    void deleteByProjectId(Long projectId);
    boolean existsByProjectId(Long projectId);
}
