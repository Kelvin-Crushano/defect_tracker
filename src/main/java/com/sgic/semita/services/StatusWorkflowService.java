package com.sgic.semita.services;


import com.sgic.semita.dtos.SingleStatusWorkflowDto;
import com.sgic.semita.dtos.StatusWorkflowDto;
import com.sgic.semita.entities.StatusWorkflow;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StatusWorkflowService {

    List<StatusWorkflow> createOrUpdateTransitions(List<StatusWorkflow> bulkDto);
    SingleStatusWorkflowDto getStatusWorkflowByProjectIdAndStatusId(Long projectId, Long statusId);

    List<StatusWorkflowDto> getAllStatusWorkflowsByProjectId(Long projectId, Pageable pageable);
    void deleteAllStatusWorkflowsByProjectId(Long projectId);
}
