package com.sgic.semita.services;

import com.sgic.semita.dtos.ProjectTypeDto;
import com.sgic.semita.entities.ProjectType;
import com.sgic.semita.repositories.ProjectTypeRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectTypeServiceImpl implements ProjectTypeService {

    @Autowired
    private ProjectTypeRepository projectTypeRepository;

    @Override
    public ProjectType createProjectType(ProjectTypeDto projectTypeDto) {
        ProjectType projectType = new ProjectType();
        projectType.setName(projectTypeDto.getName());
        return projectTypeRepository.save(projectType);
    }

    @Override
    public List<ProjectTypeDto> getAllProjectTypes(Pageable pageable) {
        Page<ProjectType> projectTypePage = projectTypeRepository.findAll(pageable);
        if(projectTypePage.isEmpty()){
            throw new ResourceNotFoundException(ValidationMessages.NO_RECORDS_FOUND);
        }
        return projectTypePage.getContent().stream()
                .map(projectType -> new ProjectTypeDto(projectType.getId(),
                        projectType.getName())).collect(Collectors.toList());
    }

    @Override
    public ProjectType updateProjectType(Long id, ProjectTypeDto projectTypeDto) {
        ProjectType existingProjectType = projectTypeRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException(ValidationMessages.PROJECT_TYPE_NOT_FOUND + " with ID : " + id));
        BeanUtils.copyProperties(projectTypeDto, existingProjectType, "id");
        return projectTypeRepository.save(existingProjectType);
    }

    @Override
    public boolean deleteProjectType(Long id) {
        if(projectTypeRepository.existsById(id)){
            ProjectType deleteProjectType = projectTypeRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(ValidationMessages.PROJECT_TYPE_NOT_FOUND+ " with ID : " +id));
            projectTypeRepository.delete(deleteProjectType);
            return true;
        }
        return  false;
    }

    @Override
    public ProjectTypeDto getProjectTypeById(Long id) {
        ProjectType projectType=projectTypeRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException(ValidationMessages.PROJECT_TYPE_NOT_FOUND+ " with ID : " +id));
        ProjectTypeDto projectTypeDto=new ProjectTypeDto();
        BeanUtils.copyProperties(projectType,projectTypeDto);
        return projectTypeDto;
    }

}
