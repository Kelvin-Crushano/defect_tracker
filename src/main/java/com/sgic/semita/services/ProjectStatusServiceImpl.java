package com.sgic.semita.services;


import com.sgic.semita.dtos.ProjectStatusDto;
import com.sgic.semita.entities.ProjectStatus;
import com.sgic.semita.repositories.ProjectStatusRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProjectStatusServiceImpl implements ProjectStatusService{

    @Autowired
    private ProjectStatusRepository projectStatusRepository;

    @Override
    public ProjectStatus createProjectStatus(ProjectStatusDto projectStatusDto) {
        ProjectStatus projectStatus = new ProjectStatus();
        BeanUtils.copyProperties(projectStatusDto, projectStatus);
        return projectStatusRepository.save(projectStatus);
    }

    // Get all with pagination
    @Override
    @Transactional(readOnly = true)
    public List<ProjectStatusDto> getAllProjectStatus(Pageable pageable) {
        Page<ProjectStatus> projectStatusPage = projectStatusRepository.findAll(pageable);

        if (projectStatusPage.isEmpty()) {
            throw new RuntimeException(ValidationMessages.NO_SUCH_FILE);
        }

        return projectStatusPage.stream()
                .map(ps -> {
                    ProjectStatusDto dto = new ProjectStatusDto();
                    BeanUtils.copyProperties(ps, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }


    // Update
    @Override
    @Transactional
    public ProjectStatus updateProjectStatus(Long id, ProjectStatusDto projectStatusDto) {
        ProjectStatus existingProjectStatus = projectStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ValidationMessages.NO_SUCH_FILE + id));

        existingProjectStatus.setName(projectStatusDto.getName());
        existingProjectStatus.setColorCode(projectStatusDto.getColorCode());

        return projectStatusRepository.save(existingProjectStatus);
    }

    // Delete
    @Override
    public boolean deleteProjectStatus(Long id) {
        if (projectStatusRepository.existsById(id)) {
            projectStatusRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public ProjectStatusDto getProjectStatusById(Long id) {
        ProjectStatus projectStatus = projectStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.INVALID_ID));
        ProjectStatusDto projectStatusDto = new ProjectStatusDto();
        BeanUtils.copyProperties(projectStatus, projectStatusDto);
        return projectStatusDto;
    }

}
