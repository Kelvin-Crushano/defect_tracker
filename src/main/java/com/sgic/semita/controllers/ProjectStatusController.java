package com.sgic.semita.controllers;

import com.sgic.semita.dtos.ProjectStatusDto;
import com.sgic.semita.entities.ProjectStatus;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.ProjectStatusService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.PROJECT_STATUS)
public class ProjectStatusController {

    @Autowired
    private ProjectStatusService projectStatusService;

    // Create
    @PostMapping
    public ResponseEntity<ResponseWrapper<?>> createProjectStatus(@Valid @RequestBody ProjectStatusDto projectStatusDto) {
        ProjectStatus createdProjectStatus = projectStatusService.createProjectStatus(projectStatusDto);
        if (createdProjectStatus != null) {

            return ResponseEntity.status(201).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULL,
                    null
            ));

        } return ResponseEntity.status(201).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.INTERNAL_SERVER_ERROR.getCode(),
                ValidationMessages.INTERNAL_SERVER_ERROR,
                null
        ));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProjectStatusDto>>> getAllProjectStatus(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<ProjectStatusDto> projectStatusList = projectStatusService.getAllProjectStatus(pageable);

        if (projectStatusList != null && !projectStatusList.isEmpty()) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    projectStatusList));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.INTERNAL_SERVER_ERROR.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null));
        }
    }

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ProjectStatus>> updateProjectStatus(
            @PathVariable Long id,
            @RequestBody ProjectStatusDto projectStatusDto) {
        ProjectStatus updatedProjectStatus = projectStatusService.updateProjectStatus(id, projectStatusDto);

        if (updatedProjectStatus != null) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.UPDATE_SUCCESS,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.INTERNAL_SERVER_ERROR.getCode(),
                    RestApiResponseStatusCodes.INTERNAL_SERVER_ERROR.getMessage(),
                    null
            ));
        }
    }

    // Delete
    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<?>> deleteProjectStatus(@PathVariable Long id) {
        boolean deleted = projectStatusService.deleteProjectStatus(id);
        if (deleted) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.DELETE_SUCCESS,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.DELETE_FAILED,
                    null
            ));
        }
    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ProjectStatusDto>> getProjectStatusById(@PathVariable("id") Long id) {
        ProjectStatusDto projectStatusDto = projectStatusService.getProjectStatusById(id);

        if (projectStatusDto != null) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    projectStatusDto
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }

}
