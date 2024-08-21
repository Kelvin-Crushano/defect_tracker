package com.sgic.semita.services;

import com.sgic.semita.dtos.DashboardDto;
import com.sgic.semita.dtos.ProjectDto;

import java.util.List;

public interface DashboardService {
    DashboardDto.DashboardData getDashboardDetailsByProjectId(Long projectId);
    List<ProjectDto> getAllProjects();
}
