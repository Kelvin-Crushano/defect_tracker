package com.sgic.semita.services;

import com.sgic.semita.dtos.ModuleAllocationRequestDto;
import com.sgic.semita.dtos.ModuleAllocationResponseDTO;
import com.sgic.semita.dtos.ProjectAllocationDetailDto;
import com.sgic.semita.entities.ModuleAllocations;
import com.sgic.semita.entities.ProjectAllocations;

import java.util.List;

public interface ModuleAllocationService {
    List<ProjectAllocationDetailDto> getNonAllocatedUsers(Long projectId);

    List<ModuleAllocationResponseDTO> getAllModuleAllocations(Long projectId, Long userId);


    List<ModuleAllocations> saveOrUpdateModuleAllocation(ModuleAllocationRequestDto moduleAllocationRequestDto);

    boolean deleteModuleAllocation(Long projectAllocationId);

    List<ModuleAllocationResponseDTO> searchModuleAllocations(Long projectId,Long userId, String userName, String subModuleName);


}
