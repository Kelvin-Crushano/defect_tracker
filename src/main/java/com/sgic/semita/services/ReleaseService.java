package com.sgic.semita.services;

import com.sgic.semita.dtos.ReleaseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReleaseService {
    ReleaseDto createRelease(ReleaseDto releaseDto);
    List<ReleaseDto> getAllReleasesByProject(Long projectId, Pageable pageable);
    ReleaseDto updateRelease(Long id, ReleaseDto releaseDto);

    ReleaseDto getReleaseById(Long id);

    boolean deleteRelease(Long id);
}
