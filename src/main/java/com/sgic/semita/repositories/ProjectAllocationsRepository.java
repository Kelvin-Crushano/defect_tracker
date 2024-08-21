package com.sgic.semita.repositories;

import com.sgic.semita.entities.Project;
import com.sgic.semita.entities.ProjectAllocations;
import com.sgic.semita.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectAllocationsRepository extends JpaRepository<ProjectAllocations, Long> {
    Optional<ProjectAllocations> findByUser(User user);

    Optional<ProjectAllocations> findByUserAndProject(User user, Project project);

    List<ProjectAllocations> findByUserId(Long userId);

    Page<ProjectAllocations> findByProjectId(Long projectId, Pageable pageable);

    @Query("SELECT pa.user.id AS userId, SUM(pa.contributions) AS totalContributions " +
            "FROM ProjectAllocations pa " +
            "WHERE pa.user.id IN (SELECT DISTINCT pa2.user.id FROM ProjectAllocations pa2 WHERE pa2.project.id = :projectId) " +
            "GROUP BY pa.user.id")
    List<Object[]> findNonAllocatedUsersWithContributions(@Param("projectId") Long projectId);
}
