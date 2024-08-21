package com.sgic.semita.controllers;

import com.sgic.semita.dtos.SingleStatusWorkflowDto;
import com.sgic.semita.dtos.StatusWorkflowDto;
import com.sgic.semita.entities.StatusWorkflow;
import com.sgic.semita.dtos.StatusWorkflowDto;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.StatusWorkflowService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.STATUS_WORKFLOW)
public class StatusWorkflowController {

    @Autowired
    private StatusWorkflowService statusWorkflowService;


    @PostMapping
    public ResponseEntity<ResponseWrapper<List<StatusWorkflow>>> createOrUpdateTransitions(@RequestBody List<StatusWorkflow> bulkDto) {
        List<StatusWorkflow> result = statusWorkflowService.createOrUpdateTransitions(bulkDto);
        if (result == null || result.isEmpty()) {
            ResponseWrapper<List<StatusWorkflow>> response = new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    RestApiResponseStatusCodes.INTERNAL_SERVER_ERROR.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok( new ResponseWrapper<>(
                HttpStatus.OK.value(),
                ValidationMessages.CREATED_UPDATED,
                null
        ));
    }

    @GetMapping(EndpointBundle.PROJECT_ID_STATUS_ID)
    public ResponseEntity<ResponseWrapper<SingleStatusWorkflowDto>> getStatusWorkflowByProjectIdAndStatusId(
            @PathVariable Long projectId, @PathVariable Long statusId) {

        SingleStatusWorkflowDto workflowDto = statusWorkflowService.getStatusWorkflowByProjectIdAndStatusId(projectId, statusId);

        if (workflowDto != null) {
            ResponseWrapper<SingleStatusWorkflowDto> response = new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    workflowDto
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<SingleStatusWorkflowDto> response = new ResponseWrapper<>(
                  RestApiResponseStatusCodes.INTERNAL_SERVER_ERROR.getCode(),
                    RestApiResponseStatusCodes.INTERNAL_SERVER_ERROR.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  

    @GetMapping(EndpointBundle.GET_BY_PROJECT)
    public ResponseEntity<ResponseWrapper<List<StatusWorkflowDto>>> getAllStatusWorkflowsByProjectId(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<StatusWorkflowDto> statusWorkflowsPage = statusWorkflowService.getAllStatusWorkflowsByProjectId(projectId, pageable);

        if (!statusWorkflowsPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    statusWorkflowsPage
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    ValidationMessages.NO_RECORDS_FOUND,
                    null
            ));
        }
    }

    @DeleteMapping(EndpointBundle.DELETE_ALL_TRANSITIONS_PROJECT_BY_ID)
    public ResponseEntity<ResponseWrapper<Void>> deleteStatusWorkflowsByProjectId(@PathVariable Long projectId) {
        try {
            statusWorkflowService.deleteAllStatusWorkflowsByProjectId(projectId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.DELETE_SUCCESS,
                    null
            ));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ex.getMessage(),
                    null
            ));
        }
    }
}
