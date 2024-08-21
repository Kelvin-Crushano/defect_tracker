package com.sgic.semita.services;

import com.sgic.semita.dtos.SingleStatusWorkflowDto;
import com.sgic.semita.dtos.StatusWorkflowDto;
import com.sgic.semita.entities.DefectStatus;
import com.sgic.semita.entities.Project;
import com.sgic.semita.entities.StatusWorkflow;
import com.sgic.semita.repositories.DefectStatusRepository;
import com.sgic.semita.repositories.ProjectRepository;
import com.sgic.semita.repositories.StatusWorkflowRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StatusWorkflowServiceImpl implements StatusWorkflowService {

    @Autowired
    private StatusWorkflowRepository statusWorkflowRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DefectStatusRepository statusRepository;

    @Override
    public List<StatusWorkflow> createOrUpdateTransitions(List<StatusWorkflow> bulkWorkflows) {
        if (bulkWorkflows.isEmpty()) {
            throw new RuntimeException(ValidationMessages.WORKFLOW_EMPTY);
        }
        Long projectId = bulkWorkflows.get(0).getProject().getId();
        projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(ValidationMessages.PROJECT_NOT_FOUND));
        List<DefectStatus> statuses = statusRepository.findAll();
        Map<Long, DefectStatus> statusMap = statuses.stream()
                .collect(Collectors.toMap(DefectStatus::getId, status -> status));
        bulkWorkflows.forEach(workflow -> {
            Long fromStatusId = workflow.getFromStatus().getId();
            Long toStatusId = workflow.getToStatus().getId();
            if (!statusMap.containsKey(fromStatusId)) {
                throw new RuntimeException("From Status with ID " + fromStatusId + " not found.");
            }
            if (!statusMap.containsKey(toStatusId)) {
                throw new RuntimeException("To Status with ID " + toStatusId + " not found.");
            }
            if (fromStatusId.equals(toStatusId)) {
                throw new RuntimeException(ValidationMessages.INVALID_TRANSITION_SAME_STATUS);
            }
            if (workflow.getId() != null) {
                workflow.setUpdatedAt(Instant.now());
                Optional<StatusWorkflow> existingWorkflow = statusWorkflowRepository.findById(workflow.getId());
                workflow.setCreatedAt(existingWorkflow.map(StatusWorkflow::getCreatedAt).orElse(Instant.now()));
            } else {
                workflow.setCreatedAt(Instant.now());
                workflow.setUpdatedAt(Instant.now());
            }
        });
        statusWorkflowRepository.deleteByProjectId(projectId);
        return statusWorkflowRepository.saveAll(bulkWorkflows);
    }




    @Override
    public SingleStatusWorkflowDto getStatusWorkflowByProjectIdAndStatusId(Long projectId, Long statusId) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException(ValidationMessages.PROJECT_NOT_FOUND + projectId));
        List<StatusWorkflow> statusWorkflows = statusWorkflowRepository.findByProjectIdAndFromStatusId(projectId, statusId);
        if (statusWorkflows.isEmpty()) {
            throw new RuntimeException(ValidationMessages.NO_TRANSITION);
        }
        SingleStatusWorkflowDto responseDto = new SingleStatusWorkflowDto();
        StatusWorkflow workflow = statusWorkflows.get(0);
        responseDto.setFromStatusId(workflow.getFromStatus().getId());
        responseDto.setFromStatusName(workflow.getFromStatus().getName());
        responseDto.setColorCode(workflow.getFromStatus().getColorCode());
        responseDto.setRole(workflow.getFromStatus().getRole().getName());
        List<Map<String, Object>> toStatuses = statusWorkflows.stream()
                .map(sw -> {
                    Map<String, Object> toStatusMap = new HashMap<>();
                    toStatusMap.put("toStatusId", sw.getToStatus().getId());
                    toStatusMap.put("toStatusName", sw.getToStatus().getName());
                    toStatusMap.put("toStatusColorCode", sw.getToStatus().getColorCode());
                    return toStatusMap;
                }).collect(Collectors.toList());

        responseDto.setToStatuses(toStatuses);

        return responseDto;
    }


    @Override
    public List<StatusWorkflowDto> getAllStatusWorkflowsByProjectId(Long projectId, Pageable pageable) {
        Page<StatusWorkflow> statusWorkflowPage = statusWorkflowRepository.findByProjectId(projectId, pageable);
        if (statusWorkflowPage.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.NO_RECORDS_FOUND);
        }
        return statusWorkflowPage.getContent().stream()
                .map(statusWorkflow -> {
                    StatusWorkflowDto dto = new StatusWorkflowDto();
                    dto.setId(statusWorkflow.getId());
                    dto.setTransitionName(statusWorkflow.getTransitionName());
                    dto.setFromStatusName(statusWorkflow.getFromStatus().getName());
                    dto.setToStatusName(statusWorkflow.getToStatus().getName());
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public void deleteAllStatusWorkflowsByProjectId(Long projectId) {
        if (!statusWorkflowRepository.existsByProjectId(projectId)) {
            throw new ResourceNotFoundException(ValidationMessages.PROJECT_NOT_FOUND + " with ID: " + projectId);
        }
        statusWorkflowRepository.deleteByProjectId(projectId);
    }
}
