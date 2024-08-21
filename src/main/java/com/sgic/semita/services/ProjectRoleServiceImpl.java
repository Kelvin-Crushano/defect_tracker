package com.sgic.semita.services;

import com.sgic.semita.dtos.ProjectRoleDto;
import com.sgic.semita.entities.Project;
import com.sgic.semita.entities.ProjectRole;
import com.sgic.semita.repositories.ProjectRepository;
import com.sgic.semita.repositories.ProjectRoleRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectRoleServiceImpl implements ProjectRoleService {

    @Autowired
    private ProjectRoleRepository projectRoleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ProjectRole createProjectRole(ProjectRoleDto projectRoleDto) {

        ProjectRole projectRole = new ProjectRole();
        projectRole.setName(projectRoleDto.getName());
        Project project = projectRepository.findById(projectRoleDto.getProjectId())
                .orElseThrow(()-> new IllegalArgumentException("Project not found with ID: " + projectRoleDto.getProjectId()));
        projectRole.setProject(project);
        return projectRoleRepository.save(projectRole);
    }

    @Override
    public ProjectRole updateProjectRole(Long id, ProjectRoleDto projectRoleDto) {
        Optional<ProjectRole> optionalProjectRole = projectRoleRepository.findById(id);
        if (optionalProjectRole.isPresent()) {
            ProjectRole projectRole = optionalProjectRole.get();
            projectRole.setName(projectRoleDto.getName());

            Project project = projectRepository.findById(projectRoleDto.getProjectId())
                    .orElseThrow(()-> new IllegalArgumentException("Project not found with ID: " + projectRoleDto.getProjectId()));
            projectRole.setProject(project);
            return projectRoleRepository.save(projectRole);
        }
        return null;
    }

    public List<ProjectRoleDto> getAllProjectRoles(Pageable pageable) {

        Page<ProjectRole> projectRolePage = projectRoleRepository.findAll(pageable);

        if (projectRolePage.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.NO_RECORDS_FOUND);
        }

        return projectRolePage.getContent().stream()
                .map(dt -> {
                    ProjectRoleDto projectRoleDto = new ProjectRoleDto();
                    BeanUtils.copyProperties(dt, projectRoleDto);
                    projectRoleDto.setProjectId(dt.getProject().getId());
                    return projectRoleDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteProjectRole(Long id) {
        if(projectRoleRepository.existsById(id))
        {
            projectRoleRepository.deleteById(id);
            return true;
        }

        return false;
    }
}