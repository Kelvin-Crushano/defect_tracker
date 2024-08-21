package com.sgic.semita.services;

import com.sgic.semita.dtos.ModuleAllocationRequestDto;
import com.sgic.semita.dtos.ModuleAllocationResponseDTO;
import com.sgic.semita.dtos.ProjectAllocationDetailDto;
import com.sgic.semita.dtos.SubModuleDto;
import com.sgic.semita.entities.ModuleAllocations;
import com.sgic.semita.entities.ProjectAllocations;
import com.sgic.semita.entities.SubModule;
import com.sgic.semita.entities.User;
import com.sgic.semita.repositories.*;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModuleAllocationServiceImpl implements ModuleAllocationService {

    @Autowired
    private ModuleAllocationsRepository moduleAllocationsRepository;

    @Autowired
    private ProjectAllocationsRepository projectAllocationsRepository;

    @Autowired
    private SubModuleRepository subModuleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public List<ProjectAllocationDetailDto> getNonAllocatedUsers(Long projectId) {
        List<Object[]> results = projectAllocationsRepository.findNonAllocatedUsersWithContributions(projectId);

        return results.stream()
                .map(result -> {
                    ProjectAllocationDetailDto dto = new ProjectAllocationDetailDto();
                    Long userId = (Long) result[0];
                    int totalContributions = ((Number) result[1]).intValue();

                    // Fetch user details if necessary; assuming a method exists to get user details by ID
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException(ValidationMessages.USER_NOT_FOUND));
                    BeanUtils.copyProperties(user, dto);
                    dto.setUserId(user.getId());
                    dto.setContributions(totalContributions);
                    dto.setAvailability(100 - totalContributions); // Adjust based on your business logic

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ModuleAllocationResponseDTO> getAllModuleAllocations(Long projectId, Long userId) {
        List<ModuleAllocations> moduleAllocationsList = moduleAllocationsRepository.findModuleAllocationsByProjectAndUser(projectId, userId);

        if (moduleAllocationsList.isEmpty()) {
            throw new RuntimeException(ValidationMessages.MODULE_ALLOCATION_NOT_FOUND_PROJECT + projectId);
        }
        ModuleAllocationResponseDTO userAllocationDto = new ModuleAllocationResponseDTO();
        userAllocationDto.setUserId(userId);
        ProjectAllocations projectAllocation = moduleAllocationsList.get(0).getProjectAllocations();
        userAllocationDto.setUserName(projectAllocation.getUser().getName());

        List<SubModuleDto> subModuleDtos = moduleAllocationsList.stream()
                .map(moduleAllocation -> {
                    SubModuleDto subModuleDto = new SubModuleDto();
                    subModuleDto.setId(moduleAllocation.getSubModule().getId());
                    subModuleDto.setName(moduleAllocation.getSubModule().getName());
                    return subModuleDto;
                })
                .collect(Collectors.toList());
        userAllocationDto.setAllocatedSubModules(subModuleDtos);
        return Collections.singletonList(userAllocationDto);
    }



    @Override
    public List<ModuleAllocations> saveOrUpdateModuleAllocation(ModuleAllocationRequestDto moduleAllocationRequestDto) {
        List<ModuleAllocations> existingModuleAllocations = moduleAllocationsRepository.findByProjectAllocationsId(moduleAllocationRequestDto.getProjectAllocationId());

        Set<Long> existingSubModuleIds = existingModuleAllocations.stream()
                .map(allocation -> allocation.getSubModule().getId())
                .collect(Collectors.toSet());

        Set<Long> newSubModuleIds = new HashSet<>(moduleAllocationRequestDto.getSubModuleIds());

        Set<Long> subModuleIdsToAdd = newSubModuleIds.stream()
                .filter(id -> !existingSubModuleIds.contains(id))
                .collect(Collectors.toSet());

        Set<Long> subModuleIdsToRemove = existingSubModuleIds.stream()
                .filter(id -> !newSubModuleIds.contains(id))
                .collect(Collectors.toSet());

        if (!subModuleIdsToRemove.isEmpty()) {
            List<ModuleAllocations> allocationsToRemove = existingModuleAllocations.stream()
                    .filter(allocation -> subModuleIdsToRemove.contains(allocation.getSubModule().getId()))
                    .collect(Collectors.toList());

            moduleAllocationsRepository.deleteAll(allocationsToRemove);
        }

        // Create new ModuleAllocations for submodules to be added
        List<ModuleAllocations> createdAllocations = subModuleIdsToAdd.stream()
                .map(subModuleId -> {
                    ModuleAllocations moduleAllocations = new ModuleAllocations();

                    ProjectAllocations projectAllocation = projectAllocationsRepository.findById(moduleAllocationRequestDto.getProjectAllocationId())
                            .orElseThrow(() -> new RuntimeException(ValidationMessages.PROJECT_ALLOCATION_NOT_FOUND));

                    SubModule subModule = subModuleRepository.findById(subModuleId)
                            .orElseThrow(() -> new RuntimeException(ValidationMessages.SUBMODULE_NOT_FOUND + subModuleId));

                    moduleAllocations.setSubModule(subModule);
                    moduleAllocations.setProjectAllocations(projectAllocation);
                    moduleAllocations.setCreatedAt(Instant.now());
                    moduleAllocations.setUpdatedAt(Instant.now());

                    return moduleAllocationsRepository.save(moduleAllocations);
                })
                .toList();

        // Update existing ModuleAllocations for submodules that need to be updated
        List<ModuleAllocations> updatedAllocations = existingModuleAllocations.stream()
                .filter(allocation -> subModuleIdsToAdd.contains(allocation.getSubModule().getId()))
                .peek(allocation -> {
                    allocation.setUpdatedAt(Instant.now());
                }).toList();

        moduleAllocationsRepository.saveAll(updatedAllocations);

        List<ModuleAllocations> resultAllocations = new ArrayList<>(createdAllocations);
        resultAllocations.addAll(updatedAllocations);

        return resultAllocations;
    }


    @Override
    public boolean deleteModuleAllocation(Long projectAllocationsId) {
        List<ModuleAllocations> moduleAllocations = moduleAllocationsRepository.findByProjectAllocationsId(projectAllocationsId);
        if (moduleAllocations == null || moduleAllocations.isEmpty()) {
            throw new RuntimeException("Project Allocation with ID " + projectAllocationsId + " not found");
        }

        moduleAllocationsRepository.deleteAll(moduleAllocations);
        return true;
    }




    @Override
    public List<ModuleAllocationResponseDTO> searchModuleAllocations(Long projectId, Long userId, String userName, String subModuleName) {
        if (projectId != null && !projectRepository.existsById(projectId)) {
            throw new RuntimeException("Project with ID " + projectId + " not found.");
        }

        List<ModuleAllocations> moduleAllocationsList = moduleAllocationsRepository.searchModuleAllocations(projectId, userId, userName, subModuleName);

        return moduleAllocationsList.stream()
                .map(moduleAllocation -> {
                    ModuleAllocationResponseDTO dto = new ModuleAllocationResponseDTO();
                    dto.setUserId(moduleAllocation.getProjectAllocations().getUser().getId());
                    dto.setUserName(moduleAllocation.getProjectAllocations().getUser().getName());

                    SubModuleDto subModuleDto = new SubModuleDto();
                    subModuleDto.setId(moduleAllocation.getSubModule().getId());
                    subModuleDto.setName(moduleAllocation.getSubModule().getName());

                    dto.setAllocatedSubModules(List.of(subModuleDto));

                    return dto;
                })
                .collect(Collectors.toList());
    }


}
