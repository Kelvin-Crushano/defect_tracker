package com.sgic.semita.services;

import com.sgic.semita.dtos.ProjectTypeDto;
import com.sgic.semita.entities.ProjectType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectTypeService {
    ProjectType createProjectType(ProjectTypeDto projectTypeDto);
    List<ProjectTypeDto> getAllProjectTypes(Pageable pageable);
    ProjectType updateProjectType(Long id, ProjectTypeDto projectTypeDto);
    boolean deleteProjectType(Long id);
    ProjectTypeDto getProjectTypeById(Long id);
}
