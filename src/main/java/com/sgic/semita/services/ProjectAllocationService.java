package com.sgic.semita.services;

import com.sgic.semita.dtos.*;
import com.sgic.semita.dtos.ProjectAllocationDetailDto;
import com.sgic.semita.dtos.ProjectAllocationDto;
import com.sgic.semita.dtos.ProjectBenchDto;
import com.sgic.semita.entities.ProjectAllocations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectAllocationService {
    Page<ProjectAllocationDetailDto> getAllProjectAllocations(Long projectId, Pageable pageable);

    List<ProjectBenchDto> getBenchResources(Long projectId, Pageable pageable);

    void allocateUsersToProject(List<ProjectAllocationDto> allocations);

    ProjectAllocations updateProjectAllocation(Long id, int contributions, Long projectRoleId);

    List<ProjectAllocationDetailDto> searchProjectAllocations(Integer Contributions, Long userId, String userEmail, String projectRoleName);
    ProjectAllocationGetDto getProjectAllocationById(Long id);
    boolean deleteProjectAllocationsById(Long id);
}
