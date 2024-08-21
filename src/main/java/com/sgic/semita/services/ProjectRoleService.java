package com.sgic.semita.services;

import com.sgic.semita.dtos.DefectTypeDto;
import com.sgic.semita.dtos.ProjectRoleDto;
import com.sgic.semita.entities.ProjectRole;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectRoleService {

    ProjectRole createProjectRole(ProjectRoleDto projectRoleDto);
    ProjectRole updateProjectRole(Long id, ProjectRoleDto projectRoleDto);
    List<ProjectRoleDto> getAllProjectRoles(Pageable pageable);
    boolean deleteProjectRole(Long id);


}