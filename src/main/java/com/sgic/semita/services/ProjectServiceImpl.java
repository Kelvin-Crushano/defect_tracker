package com.sgic.semita.services;

import com.sgic.semita.dtos.ProjectDto;
import com.sgic.semita.dtos.ProjectKLOCDto;
import com.sgic.semita.dtos.ProjectResponseDto;
import com.sgic.semita.entities.Project;
import com.sgic.semita.entities.ProjectStatus;
import com.sgic.semita.entities.ProjectType;
import com.sgic.semita.repositories.ProjectRepository;
import com.sgic.semita.repositories.ProjectStatusRepository;
import com.sgic.semita.repositories.ProjectTypeRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectStatusRepository projectStatusRepository;

    @Autowired
    private ProjectTypeRepository projectTypeRepository;

    @Override
    public Project createProject(ProjectDto projectDto) {

        Project project = new Project();
        project.setCreatedAt(Instant.now());
        project.setUpdatedAt(Instant.now());

        // Copy properties from DTO to entity
        BeanUtils.copyProperties(projectDto, project);
        // Fetch ProjectStatus from the repository
        ProjectStatus projectStatus = projectStatusRepository.findById(projectDto.getProjectStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Project Status ID: " + projectDto.getProjectStatusId()));
        project.setProjectStatus(projectStatus);
        // Fetch ProjectType from the repository and set it on the project
        ProjectType projectType = projectTypeRepository.findById(projectDto.getProjectTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Project Type ID: " + projectDto.getProjectTypeId()));
        project.setProjectType(projectType);
        // Save the project to the repository
        return projectRepository.save(project);
    }

    @Override
    public ProjectResponseDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));
        ProjectResponseDto responseProjectDto = new ProjectResponseDto();
        BeanUtils.copyProperties(project, responseProjectDto);
        responseProjectDto.setProjectTypeName(project.getProjectType().getName());
        responseProjectDto.setProjectStatusName(project.getProjectStatus().getName());
        return responseProjectDto;
    }


    @Override
    public Project updateProject(Long id, ProjectDto projectDto) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));

        BeanUtils.copyProperties(projectDto, existingProject, "id", "projectStatusId", "projectTypeId");

        // Fetch ProjectStatus and set it on the existing project
        ProjectStatus projectStatus = projectStatusRepository.findById(projectDto.getProjectStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Project Status ID: " + projectDto.getProjectStatusId()));
        existingProject.setProjectStatus(projectStatus);

        // Fetch ProjectType and set it on the existing project
        ProjectType projectType = projectTypeRepository.findById(projectDto.getProjectTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Project Type ID: " + projectDto.getProjectTypeId()));
        existingProject.setProjectType(projectType);

        // Save and return the updated project
        return projectRepository.save(existingProject);
    }


    @Override
    public List<ProjectResponseDto> getAllProjects(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findAll(pageable);

        if (projectPage.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.NO_RECORDS_FOUND);
        }

        return projectPage.getContent().stream()
                .map(project -> {
                    ProjectResponseDto projectDto = new ProjectResponseDto();
                    BeanUtils.copyProperties(project, projectDto);
                    projectDto.setProjectStatusName(project.getProjectStatus().getName());
                    projectDto.setProjectTypeName(project.getProjectType().getName());
                    return projectDto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<ProjectResponseDto> searchProjects(String name, String projectStatusName, LocalDate startDate, LocalDate endDate, String prefix, String projectType, Pageable pageable) {
        List<Project> projects = projectRepository.findAll(); // Start with all projects

        if (name != null && !name.trim().isEmpty()) {
            projects = projects.stream()
                    .filter(p -> p.getName().contains(name.trim()))
                    .collect(Collectors.toList());
        }
        if (projectStatusName != null && !projectStatusName.trim().isEmpty()) {
            projects = projects.stream()
                    .filter(p -> p.getProjectStatus().getName().contains(projectStatusName.trim()))
                    .collect(Collectors.toList());
        }
        if (startDate != null) {
            projects = projects.stream()
                    .filter(p -> !p.getStartDate().isBefore(startDate))
                    .collect(Collectors.toList());
        }
        if (endDate != null) {
            projects = projects.stream()
                    .filter(p -> !p.getEndDate().isAfter(endDate))
                    .collect(Collectors.toList());
        }
        if (prefix != null && !prefix.trim().isEmpty()) {
            projects = projects.stream()
                    .filter(p -> p.getPrefix().contains(prefix.trim()))
                    .collect(Collectors.toList());
        }
        if (projectType != null && !projectType.trim().isEmpty()) {
            projects = projects.stream()
                    .filter(p -> p.getProjectType().getName().contains(projectType.trim()))
                    .collect(Collectors.toList());
        }

        return projects.stream()
                .map(this::mapToProjectResponseDto)
                .collect(Collectors.toList());
    }

    private ProjectResponseDto mapToProjectResponseDto(Project project) {
        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
        BeanUtils.copyProperties(project, projectResponseDto);
        projectResponseDto.setProjectStatusName(project.getProjectStatus().getName());
        projectResponseDto.setProjectTypeName(project.getProjectType().getName());
        return projectResponseDto;
    }

    @Override
    public ProjectKLOCDto getProjectKLOC(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
        ProjectKLOCDto projectKLOCDto = new ProjectKLOCDto();
        projectKLOCDto.setKLOC( project.getKLOC());
        return projectKLOCDto;
    }

    @Override
    public void updateProjectKLOC(Long projectId, ProjectKLOCDto projectKLOCDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));

        project.setKLOC(projectKLOCDto.getKLOC());
        projectRepository.save(project);
    }

    @Override
    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }


}

