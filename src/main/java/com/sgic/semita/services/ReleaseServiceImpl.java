package com.sgic.semita.services;

import com.sgic.semita.dtos.ReleaseDto;
import com.sgic.semita.entities.Project;
import com.sgic.semita.entities.Release;
import com.sgic.semita.repositories.ProjectRepository;
import com.sgic.semita.repositories.ReleaseRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ReleaseDto createRelease(ReleaseDto releaseDto) {
        Project project = projectRepository.findById(releaseDto.getProjectId())
                .orElseThrow(() -> new RuntimeException(ValidationMessages.PROJECT_NOT_FOUND));

        Release release = new Release();
        BeanUtils.copyProperties(releaseDto, release);
        release.setProject(project);
        release.setCreatedAt(Instant.now());
        release.setUpdatedAt(Instant.now());
        Release savedRelease = releaseRepository.save(release);
        releaseDto.setId(savedRelease.getId());
        return releaseDto;
    }

    @Override
    public List<ReleaseDto> getAllReleasesByProject(Long projectId, Pageable pageable) {
        Page<Release> releasePage = releaseRepository.findByProjectId(projectId, pageable);

        // Check if the project exists
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException(ValidationMessages.PROJECT_NOT_FOUND + " with ID: " + projectId);
        }

        if (releasePage.isEmpty()) {
            throw new RuntimeException(ValidationMessages.NO_RECORDS_FOUND);
        }
        return releasePage.getContent().stream()
                .map(release -> {
                    ReleaseDto releaseDto = new ReleaseDto();
                    BeanUtils.copyProperties(release, releaseDto);
                    releaseDto.setProjectId(release.getProject().getId());
                    return releaseDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ReleaseDto updateRelease(Long id, ReleaseDto releaseDto) {
        Release existingRelease = releaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ValidationMessages.RELEASE_NOT_FOUND + " with ID: " + id));


        BeanUtils.copyProperties(releaseDto, existingRelease, "id");
        Project project = projectRepository.findById(releaseDto.getProjectId())
                .orElseThrow(() -> new RuntimeException(ValidationMessages.PROJECT_NOT_FOUND));

        existingRelease.setProject(project);
        existingRelease.setUpdatedAt(Instant.now());
        releaseRepository.save(existingRelease);
        return releaseDto;
    }

    @Override
    public ReleaseDto getReleaseById(Long id) {
        Release release = releaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ValidationMessages.RELEASE_NOT_FOUND + " with ID: " + id));

        ReleaseDto releaseDto = new ReleaseDto();
        BeanUtils.copyProperties(release, releaseDto);
        releaseDto.setProjectId(release.getProject().getId());
        return releaseDto;
    }

    @Override
    public boolean deleteRelease(Long id) {
        if(releaseRepository.existsById(id)){
            releaseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
