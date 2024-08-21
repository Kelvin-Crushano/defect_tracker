package com.sgic.semita.repositories;

import com.sgic.semita.dtos.DefectDto;
import java.util.Optional;
import com.sgic.semita.entities.Defect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefectRepository extends JpaRepository<Defect, Long> {
    @Query("SELECT d FROM Defect d " +
            "JOIN d.module m " +
            "JOIN m.project p " +
            "WHERE p.id = :projectId")
    List<Defect> findDefectsByProjectId(@Param("projectId") Long projectId);

    // pagination and filtering
    @Query("SELECT d FROM Defect d " +
            "JOIN d.module m " +
            "JOIN m.project p " +
            "WHERE (:projectId IS NULL OR p.id = :projectId) " +
            "AND (:stepToReCreate IS NULL OR d.stepToReCreate LIKE %:stepToReCreate%) " +
            "AND (:defectType IS NULL OR d.defectType.name LIKE %:defectType%) " +
            "AND (:defectStatus IS NULL OR d.defectStatus.name LIKE %:defectStatus%) " +
            "AND (:priority IS NULL OR d.priority.name LIKE %:priority%) " +
            "AND (:severity IS NULL OR d.severity.name LIKE %:severity%) " +
            "AND (:release IS NULL OR d.release.name LIKE %:release%) " +
            "AND (:subModule IS NULL OR d.subModule.name LIKE %:subModule%) " +
            "AND (:module IS NULL OR d.module.name LIKE %:module%) " +
            "AND (:assignBy IS NULL OR d.assignBy.user.name LIKE %:assignBy%)" +
            "AND (:assignTo IS NULL OR d.assignTo.user.name LIKE %:assignTo%)")
   //  "AND (:assignBy IS NULL OR (d.assignBy.user.name LIKE %:assignBy% OR d.assignTo.user.name LIKE %:assignTo%))")
    Page<Defect> findDefectsByCriteria(
            @Param("projectId") Long projectId,
            @Param("stepToReCreate") String stepToReCreate,
            @Param("defectType") String defectType,
            @Param("defectStatus") String defectStatus,
            @Param("priority") String priority,
            @Param("severity") String severity,
            @Param("release") String release,
            @Param("subModule") String subModule,
            @Param("module") String module,
            @Param("assignBy") String assignBy,
            @Param("assignTo") String assignTo,
            Pageable pageable);




    Page<Defect> findByModule_Project_Id(Long projectId, Pageable pageable);

    @Query("SELECT COUNT(d) FROM Defect d " +
            "JOIN d.subModule sm " +
            "JOIN sm.module m " +
            "JOIN m.project p " +
            "WHERE p.id = :projectId AND d.defectStatus.name = 'Closed'")
    long countDefectsFoundBeforeRelease(@Param("projectId") Long projectId);


    @Query("SELECT COUNT(d) FROM Defect d " +
            "JOIN d.subModule sm " +
            "JOIN sm.module m " +
            "JOIN m.project p " +
            "WHERE d.defectStatus.name = 'Open' AND p.id = :projectId")
    long countDefectsFoundAfterRelease(@Param("projectId") Long projectId);


    @Query("SELECT (SUM(d.severityCount * s.value) * 1.0) / (SUM(d.severityCount) * 100) " +
            "FROM (SELECT s.id AS severityId, COUNT(d) AS severityCount " +
            "FROM Defect d " +
            "JOIN d.subModule sm " +
            "JOIN sm.module m " +
            "JOIN m.project p " +
            "JOIN d.severity s " +
            "WHERE p.id = :projectId " +
            "GROUP BY s.id) d " +
            "JOIN Severity s ON d.severityId = s.id")
    Double calculateDefectSeverityIndexPercentage(@Param("projectId") Long projectId);






    @Query("SELECT d.severity.name, d.severity.colorCode, d.defectStatus.name, d.defectStatus.colorCode, COUNT(d) " +
            "FROM Defect d " +
            "JOIN d.subModule sm " +
            "JOIN sm.module m " +
            "JOIN m.project p " +
            "WHERE p.id = :projectId " +
            "GROUP BY d.severity.name, d.severity.colorCode, d.defectStatus.name, d.defectStatus.colorCode")
    List<Object[]> findDefectStatusCountBySeverityAndStatus(@Param("projectId") Long projectId);


}
