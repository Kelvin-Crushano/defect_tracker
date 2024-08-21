package com.sgic.semita.controllers;

import com.sgic.semita.dtos.ProjectDto;
import com.sgic.semita.dtos.ProjectKLOCDto;
import com.sgic.semita.dtos.ProjectResponseDto;
import com.sgic.semita.entities.Project;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.ProjectService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(EndpointBundle.PROJECT)
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Project>> createProject(@Valid @RequestBody ProjectDto projectDto) {
        Project createdProject = projectService.createProject(projectDto);
        if (createdProject != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULL,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.SAVE_FAILED,
                    null
            ));
        }
    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ProjectResponseDto>> getProjectById(@PathVariable("id") Long id) {
        ProjectResponseDto project = projectService.getProjectById(id);
        if (project != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    project
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<ProjectResponseDto>>> getAllProjects(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<ProjectResponseDto> projects = projectService.getAllProjects(pageable);
        if (projects != null && !projects.isEmpty()) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    projects
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Project>> updateProject(@PathVariable("id") Long id, @Valid @RequestBody ProjectDto projectDto) {
        Project updatedProject = projectService.updateProject(id, projectDto);
        if (updatedProject != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULL,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.SAVE_FAILED,
                    null
            ));
        }
    }

    @GetMapping(EndpointBundle.SEARCH)
    public ResponseEntity<ResponseWrapper<List<ProjectResponseDto>>> searchProjects(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String projectStatusName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String prefix,
            @RequestParam(required = false) String projectType) {

        // Check if all parameters are empty
        if ((name == null || name.trim().isEmpty()) &&
                (projectStatusName == null || projectStatusName.trim().isEmpty()) &&
                startDate == null &&
                endDate == null &&
                (prefix == null || prefix.trim().isEmpty()) &&
                (projectType == null || projectType.trim().isEmpty())) {

            return ResponseEntity.badRequest().body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    "At least one search parameter must be provided.",
                    null
            ));
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<ProjectResponseDto> projects = projectService.searchProjects(
                name != null && !name.trim().isEmpty() ? name : null,
                projectStatusName != null && !projectStatusName.trim().isEmpty() ? projectStatusName : null,
                startDate,
                endDate,
                prefix != null && !prefix.trim().isEmpty() ? prefix : null,
                projectType != null && !projectType.trim().isEmpty() ? projectType : null,
                pageable);

        if (projects != null && !projects.isEmpty()) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    projects
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }


    @GetMapping(EndpointBundle.PROJECT_KLOC)
    public ResponseEntity<ResponseWrapper<ProjectKLOCDto>> getProjectKLOC(@PathVariable Long projectId) {
        ProjectKLOCDto projectKLOCDto = projectService.getProjectKLOC(projectId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.SUCCESS.getCode(),
                ValidationMessages.SAVED_SUCCESSFULL,
                projectKLOCDto
        ));
    }

    @PutMapping(EndpointBundle.PROJECT_KLOC)
    public ResponseEntity<ResponseWrapper<ProjectKLOCDto>> updateProjectKLOC(@PathVariable Long projectId, @RequestBody ProjectKLOCDto projectKLOCDto) {
        projectService.updateProjectKLOC(projectId, projectKLOCDto);
        ProjectKLOCDto updatedProjectKLOCDto = projectService.getProjectKLOC(projectId);

        if (updatedProjectKLOCDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.UPDATE_SUCCESS,
                    updatedProjectKLOCDto
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.UPDATE_FAILED,
                    null
            ));
        }
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Void>> deleteProject(@PathVariable Long id) {
        boolean deleted = projectService.deleteProject(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
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
}
