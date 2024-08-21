package com.sgic.semita.controllers;

import com.sgic.semita.dtos.*;
import com.sgic.semita.entities.ProjectAllocations;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.ProjectAllocationService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.PROJECT_ALLOCATION)
public class ProjectAllocationController {

    @Autowired
    private ProjectAllocationService projectAllocationService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProjectAllocationDetailDto>>> getAllProjectAllocations(@RequestParam Long projectId, @RequestParam(defaultValue = "0") int page,
                                                                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProjectAllocationDetailDto> allocationsPage = projectAllocationService.getAllProjectAllocations(projectId, pageable);
        List<ProjectAllocationDetailDto> allocations = allocationsPage.getContent();
        if (allocations != null && !allocations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    ValidationMessages.RETRIEVED,
                    allocations
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper<>(
                    HttpStatus.NO_CONTENT.value(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }

    @GetMapping(EndpointBundle.BENCH)
    public ResponseEntity<ResponseWrapper<List<ProjectBenchDto>>> getBenchResources(@RequestParam Long projectId, @RequestParam(defaultValue = "0") int page,
                                                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProjectBenchDto> benchResources = projectAllocationService.getBenchResources(projectId, pageable);
        if (benchResources != null && !benchResources.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    ValidationMessages.RETRIEVED,
                    benchResources
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper<>(
                    HttpStatus.NO_CONTENT.value(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }

    @PostMapping(EndpointBundle.ALLOCATE)
    public ResponseEntity<ResponseWrapper<String>> allocateUsersToProject(@RequestBody List<ProjectAllocationDto> allocations) {
        if (allocations == null || allocations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    HttpStatus.BAD_REQUEST.value(),
                    ValidationMessages.ALLOCATE_FAIL,
                    null
            ));
        } else {
            projectAllocationService.allocateUsersToProject(allocations);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
                    HttpStatus.CREATED.value(),
                    ValidationMessages.USER_ALLOCATE,
                    null
            ));
        }
    }

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ProjectAllocations>> updateProjectAllocation(@Valid @PathVariable Long id,
                                                                                       @RequestParam int contributions,
                                                                                       @RequestParam Long projectRoleId) {
        ProjectAllocations updatedAllocation = projectAllocationService.updateProjectAllocation(id, contributions, projectRoleId);

        if (updatedAllocation != null) {
            // If the allocation is successfully updated
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    ValidationMessages.UPDATE_SUCCESS,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    ValidationMessages.UPDATE_FAILED,
                    null
            ));
        }
    }

    @GetMapping(EndpointBundle.SEARCH)
    public ResponseEntity<ResponseWrapper<List<ProjectAllocationDetailDto>>> searchProjectAllocations(
            @RequestParam(required = false) Integer Contributions,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String userEmail,
            @RequestParam(required = false) String projectRoleName) {

        List<ProjectAllocationDetailDto> results = projectAllocationService.searchProjectAllocations(
                Contributions, userId, userEmail, projectRoleName);

        if (results.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    HttpStatus.NO_CONTENT.value(),
                    ValidationMessages.NO_RECORDS_FOUND,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    ValidationMessages.RETRIEVED,
                    results
            ));
        }
    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ProjectAllocationGetDto>> getProjectAllocationById(@PathVariable("id") Long id) {
        ProjectAllocationGetDto projectAllocationGetDto = projectAllocationService.getProjectAllocationById(id);
        if (projectAllocationGetDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    projectAllocationGetDto
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Void>> deleteProjectAllocations(@PathVariable @Valid Long id) {
        boolean isDeleted = projectAllocationService.deleteProjectAllocationsById(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.DELETE_SUCCESS,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.DEFECT_STATUS_NOT_FOUND + ": " + id,
                    null
            ));
        }
    }
}