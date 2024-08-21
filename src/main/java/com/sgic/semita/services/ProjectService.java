package com.sgic.semita.services;

import com.sgic.semita.dtos.ProjectDto;
import com.sgic.semita.dtos.ProjectKLOCDto;
import com.sgic.semita.dtos.ProjectResponseDto;
import com.sgic.semita.dtos.UserDto;
import com.sgic.semita.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ProjectService {
    Project createProject(ProjectDto projectDto);

    ProjectResponseDto getProjectById(Long id);


    Project updateProject(Long id, ProjectDto projectDto);


    List<ProjectResponseDto> getAllProjects(Pageable pageable);

    List<ProjectResponseDto> searchProjects(String name, String status, LocalDate startDate, LocalDate endDate, String prefix, String type, Pageable pageable);

    ProjectKLOCDto getProjectKLOC(Long projectId);

        void updateProjectKLOC(Long projectId, ProjectKLOCDto projectKLOCDto);


    boolean deleteProject(Long id);
}
