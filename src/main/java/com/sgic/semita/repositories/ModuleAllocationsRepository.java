package com.sgic.semita.repositories;

import com.sgic.semita.entities.ModuleAllocations;
import com.sgic.semita.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleAllocationsRepository extends JpaRepository<ModuleAllocations, Long> {
    @Query("SELECT ma FROM ModuleAllocations ma WHERE ma.projectAllocations.project.id = :projectId AND " +
            "(:userId IS NULL OR ma.projectAllocations.user.id = :userId)")
    List<ModuleAllocations> findModuleAllocationsByProjectAndUser(
            @Param("projectId") Long projectId,
            @Param("userId") Long userId);


    @Query("SELECT ma FROM ModuleAllocations ma WHERE " +
            "(:projectId IS NULL OR ma.projectAllocations.project.id = :projectId) AND " +
            "(:userId IS NULL OR ma.projectAllocations.user.id = :userId) AND " +
            "(:userName IS NULL OR ma.projectAllocations.user.name LIKE %:userName%) AND " +
            "(:subModuleName IS NULL OR ma.subModule.name LIKE %:subModuleName%)")
    List<ModuleAllocations> searchModuleAllocations(@Param("projectId") Long projectId,
                                                    @Param("userId") Long userId,
                                                    @Param("userName") String userName,
                                                    @Param("subModuleName") String subModuleName);



    List<ModuleAllocations> findByProjectAllocationsId(Long projectAllocationsId);

}

