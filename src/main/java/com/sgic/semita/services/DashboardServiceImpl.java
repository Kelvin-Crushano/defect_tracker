package com.sgic.semita.services;
import com.sgic.semita.dtos.DashboardDto;
import com.sgic.semita.dtos.ProjectDto;
import com.sgic.semita.dtos.ProjectKLOCDto;
import com.sgic.semita.entities.Defect;
import com.sgic.semita.entities.Project;
import com.sgic.semita.repositories.DefectRepository;
import com.sgic.semita.repositories.ProjectRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DefectRepository defectRepository;
    @Autowired
    private ProjectService projectService;

    // Get all projects ids and name
    @Transactional(readOnly = true)
    @Override
    public List<ProjectDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(project -> {
                    ProjectDto dto = new ProjectDto();
                    dto.setId(project.getId());
                    dto.setName(project.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Det Dashboard details
    @Transactional(readOnly = true)
    @Override
    public DashboardDto.DashboardData getDashboardDetailsByProjectId(Long projectId) {
        DashboardDto.DashboardData data = new DashboardDto.DashboardData();

        ProjectKLOCDto projectKLOCDto = projectService.getProjectKLOC(projectId);
        int kloc = projectKLOCDto.getKLOC();

        long closedDefects = defectRepository.countDefectsFoundBeforeRelease(projectId);
        long openDefects = defectRepository.countDefectsFoundAfterRelease(projectId);

        List<Object[]> defectStatusCountResults = defectRepository.findDefectStatusCountBySeverityAndStatus(projectId);
        Map<String, DashboardDto.SeverityData> defectStatusCountBySeverityAndStatus = new HashMap<>();
        for (Object[] result : defectStatusCountResults) {
            String severity = (String) result[0];
            String severityColorCode = (String) result[1];
            String status = (String) result[2];
            String statusColorCode = (String) result[3];
            long count = (long) result[4];

            DashboardDto.SeverityData severityData = defectStatusCountBySeverityAndStatus
                    .computeIfAbsent(severity, k -> new DashboardDto.SeverityData());

            severityData.setSeverityColorCode(severityColorCode);

            if (severityData.getStatuses() == null) {
                severityData.setStatuses(new HashMap<>());
            }

            severityData.getStatuses().put(status, new DashboardDto.DefectStatusDetail(count, statusColorCode));
        }

        // Calculate metrics
        double totalDefects = closedDefects + openDefects;
        double defectRemovalEfficiency = totalDefects > 0 ? (double) closedDefects / totalDefects * 100 : 0.0;
        double defectLeakage = totalDefects > 0 ? (double) openDefects / totalDefects * 100 : 0.0;
        double defectDensity = kloc > 0 ? (double) totalDefects / kloc : 0.0;

        data.setProjectId(projectId);
        data.setDefectStatusCountBySeverityAndStatus(defectStatusCountBySeverityAndStatus);
        data.setDefectRemovalEfficiency(defectRemovalEfficiency);
        Double defectSeverityIndex = defectRepository.calculateDefectSeverityIndexPercentage(projectId);
        data.setDefectSeverityIndex(defectSeverityIndex != null ? defectSeverityIndex : 0);
        data.setDefectLeakage(defectLeakage);
        data.setDefectDensity(defectDensity);

        return data;
    }

    


}
