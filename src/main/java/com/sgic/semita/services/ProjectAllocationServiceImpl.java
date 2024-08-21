package com.sgic.semita.services;

import com.sgic.semita.dtos.ProjectAllocationDetailDto;
import com.sgic.semita.dtos.ProjectAllocationDto;
import com.sgic.semita.dtos.ProjectAllocationGetDto;
import com.sgic.semita.dtos.ProjectBenchDto;
import com.sgic.semita.entities.*;
import com.sgic.semita.repositories.ProjectAllocationsRepository;
import com.sgic.semita.repositories.ProjectRepository;
import com.sgic.semita.repositories.ProjectRoleRepository;
import com.sgic.semita.repositories.UserRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectAllocationServiceImpl implements ProjectAllocationService {

    @Autowired
    private ProjectAllocationsRepository projectAllocationsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectRoleRepository projectRoleRepository;


    @Override
    public Page<ProjectAllocationDetailDto> getAllProjectAllocations(Long projectId, Pageable pageable) {
        Page<ProjectAllocations> allocationsPage = projectAllocationsRepository.findByProjectId(projectId, pageable);

        if (allocationsPage.isEmpty()) {
            throw new RuntimeException("No project allocations found for project ID: " + projectId);
        }

        List<ProjectAllocationDetailDto> allocationDtos = allocationsPage.getContent().stream()
                .map(this::mapToProjectAllocationDetailDto)
                .collect(Collectors.toList());

        return new PageImpl<>(allocationDtos, pageable, allocationsPage.getTotalElements());
    }

    private ProjectAllocationDetailDto mapToProjectAllocationDetailDto(ProjectAllocations allocation) {
        ProjectAllocationDetailDto dto = new ProjectAllocationDetailDto();
        dto.setId(allocation.getId());
        dto.setContributions(allocation.getContributions());

        User user = userRepository.findById(allocation.getUser().getId()).orElse(null);
        if (user != null) {
            dto.setUserId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
        }
        projectRoleRepository.findById(allocation.getProjectRole().getId()).ifPresent(projectRole -> dto.setProjectRoleName(projectRole.getName()));
        int availability = calculateAvailability(allocation);
        dto.setAvailability(availability);
        return dto;
    }

    private int calculateAvailability(ProjectAllocations allocation) {
        return 100 - allocation.getContributions();
    }

    @Override
    public List<ProjectBenchDto> getBenchResources(Long projectId, Pageable pageable) {
        List<User> allUsers = userRepository.findAll();
        Page<ProjectAllocations> projectAllocations = projectAllocationsRepository.findByProjectId(projectId, pageable);
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new RuntimeException("Project not Found with ID :" + projectId);
        }

        List<Long> allocatedUserIds = projectAllocations.stream()
                .map(pa -> pa.getUser().getId())
                .toList();

        return allUsers.stream()
                .filter(user -> !allocatedUserIds.contains(user.getId()))
                .map(this::mapToProjectbenchDto)
                .collect(Collectors.toList());
    }

    private ProjectBenchDto mapToProjectbenchDto(User user) {
        ProjectBenchDto dto = new ProjectBenchDto();
        BeanUtils.copyProperties(user, dto);
        dto.setAvailability(calculateUserAvailability(user.getId()));
        return dto;
    }

    private int calculateUserAvailability(Long userId) {
        List<ProjectAllocations> allocations = projectAllocationsRepository.findByUserId(userId);
        int totalContributions = 100; // Maximum contribution limit
        int allocatedContributions = allocations.stream()
                .mapToInt(ProjectAllocations::getContributions)
                .sum();

        return totalContributions - allocatedContributions;
    }


    @Override
    public void allocateUsersToProject(List<ProjectAllocationDto> allocations) {
        for (ProjectAllocationDto allocation : allocations) {
            ProjectAllocations projectAllocations = new ProjectAllocations();

            User user = userRepository.findById(allocation.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + allocation.getUserId()));
            Project project = projectRepository.findById(allocation.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found with ID: " + allocation.getProjectId()));
            ProjectRole projectRole = projectRoleRepository.findById(allocation.getProjectRoleId())
                    .orElseThrow(() -> new RuntimeException("Project role not found with ID: " + allocation.getProjectRoleId()));

            projectAllocations.setUser(user);
            projectAllocations.setProject(project);
            projectAllocations.setProjectRole(projectRole);
            projectAllocations.setContributions(allocation.getContributions());

            Instant now = Instant.now();
            projectAllocations.setCreatedAt(now);
            projectAllocations.setUpdatedAt(now);

            projectAllocationsRepository.save(projectAllocations);
        }
    }

    @Override
    public ProjectAllocations updateProjectAllocation(Long id, int contributions, Long projectRoleId) {
        if (contributions > 100) {
            throw new RuntimeException("Contributions cannot exceed 100");
        }

        ProjectAllocations allocation = projectAllocationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Allocation with id " + id + " not found"));
        allocation.setContributions(contributions);

        ProjectRole projectRole = projectRoleRepository.findById(projectRoleId)
                .orElseThrow(() -> new RuntimeException("Project role with id " + projectRoleId + " not found"));
        allocation.setProjectRole(projectRole);

        allocation.setUpdatedAt(Instant.now());
        return projectAllocationsRepository.save(allocation);
    }


    @Override
    public List<ProjectAllocationDetailDto> searchProjectAllocations(Integer Contributions, Long userId, String userEmail, String projectRoleName) {
        List<ProjectAllocations> allocations = projectAllocationsRepository.findAll();

        if (Contributions != null) {
            allocations = allocations.stream()
                    .filter(a -> a.getContributions() == Contributions)
                    .collect(Collectors.toList());
        }
        if (userId != null) {
            allocations = allocations.stream()
                    .filter(a -> a.getUser().getId().equals(userId))
                    .collect(Collectors.toList());
        }
        if (userEmail != null) {
            allocations = allocations.stream()
                    .filter(a -> a.getUser().getEmail().contains(userEmail))
                    .collect(Collectors.toList());
        }
        if (projectRoleName != null) {
            allocations = allocations.stream()
                    .filter(a -> a.getProjectRole().getName().contains(projectRoleName))
                    .collect(Collectors.toList());
        }

        return allocations.stream()
                .map(this::mapToProjectAllocationDetailDto)
                .collect(Collectors.toList());
    }

    public ProjectAllocationGetDto getProjectAllocationById(Long id) {
        ProjectAllocations projectAllocations = projectAllocationsRepository.
                findById(id).orElseThrow(() -> new RuntimeException(ValidationMessages.PROJECT_ALLOCATION_NOT_FOUND + id));

        Long userId = projectAllocations.getUser().getId();
        String userName = projectAllocations.getUser().getName();
        int contributions = projectAllocations.getContributions();
        String projectRoleName = projectAllocations.getProjectRole().getName();

        return new ProjectAllocationGetDto(userId, userName, contributions, projectRoleName);
    }

    public boolean deleteProjectAllocationsById(Long id) {
        if (!projectAllocationsRepository.existsById(id)) {
            throw new RuntimeException(ValidationMessages.PROJECT_ALLOCATION_NOT_FOUND+id);
        }
        Optional<ProjectAllocations> optionalProjectAllocation = projectAllocationsRepository.findById(id);
        if (optionalProjectAllocation.isPresent()) {
            projectAllocationsRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
