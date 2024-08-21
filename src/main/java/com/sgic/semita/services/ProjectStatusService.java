package com.sgic.semita.services;

import com.sgic.semita.dtos.ProjectStatusDto;
import com.sgic.semita.entities.ProjectStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectStatusService{

    ProjectStatus createProjectStatus(ProjectStatusDto projectStatusDto);
    List<ProjectStatusDto> getAllProjectStatus(Pageable pageable);
  //  List<ProjectStatusDto> getAllProjectStatus(int pageNumber, int pageSize);
  ProjectStatus updateProjectStatus(Long id, ProjectStatusDto projectStatusDto);
    boolean deleteProjectStatus(Long id);
    ProjectStatusDto getProjectStatusById(Long id);


}
