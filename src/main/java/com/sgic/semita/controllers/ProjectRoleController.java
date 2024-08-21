package com.sgic.semita.controllers;

import com.sgic.semita.dtos.ProjectRoleDto;
import com.sgic.semita.entities.ProjectRole;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.repositories.ProjectRepository;
import com.sgic.semita.services.ProjectRoleService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.PROJECT_ROLE)
public class ProjectRoleController {

    @Autowired
    private ProjectRoleService projectRoleService;


    @PostMapping
    public ResponseEntity<ResponseWrapper<ProjectRole>> createProjectRole(@Valid @RequestBody ProjectRoleDto projectRoleDto) {
        ProjectRole createdProjectRole = projectRoleService.createProjectRole(projectRoleDto);
        if (createdProjectRole != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULL,
                    null
            ));

        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.SAVE_FAILED,
                    null
            ));
        }

    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProjectRoleDto>>> getAllProjectRoles(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10")int pageSize)
     {
         Pageable pageable= PageRequest.of(pageNumber,pageSize);
        List<ProjectRoleDto> projectRoles = projectRoleService.getAllProjectRoles(pageable);
        if (projectRoles != null && !projectRoles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    projectRoles
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ProjectRole>> updateProjectRole(@PathVariable("id") Long id, @Valid @RequestBody ProjectRoleDto projectRoleDto) {
        ProjectRole updatedProjectRole = projectRoleService.updateProjectRole(id, projectRoleDto);
        if (updatedProjectRole != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
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

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Void>> deleteProjectRole(@PathVariable("id") Long id) {
        boolean deleted = projectRoleService.deleteProjectRole(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.DELETE_SUCCESS,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.DELETE_FAILED,
                    null
            ));
        }
    }
}