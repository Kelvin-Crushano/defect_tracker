package com.sgic.semita.controllers;

import com.sgic.semita.dtos.ProjectTypeDto;
import com.sgic.semita.entities.ProjectType;
import com.sgic.semita.services.ProjectTypeService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
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
@RequestMapping(EndpointBundle.PROJECT_TYPE)
public class ProjectTypeController {
    @Autowired
    private ProjectTypeService projectTypeService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<ProjectType>> createProjectType(@Valid @RequestBody ProjectTypeDto projectTypeDto) {
        ProjectType createdProjectType = projectTypeService.createProjectType(projectTypeDto);
        if(createdProjectType!=null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULL,
                    null
            ));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_MODIFIED.getCode(),
                    ValidationMessages.SAVE_FAILED,
                    null
            ));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ProjectTypeDto>>> getAllProjectTypes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<ProjectTypeDto> projectTypesPage = projectTypeService.getAllProjectTypes(pageable);

        if (!projectTypesPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    projectTypesPage
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    ValidationMessages.NO_RECORDS_FOUND,
                    null
            ));
        }
    }

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ProjectType>> updateProjectType(@PathVariable Long id, @Valid @RequestBody ProjectTypeDto projectTypeDto) {
        ProjectType updatedProjectType = projectTypeService.updateProjectType(id, projectTypeDto);
        if(updatedProjectType!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.UPDATE_SUCCESS,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.UPDATE_FAILED,
                    null
            ));
        }
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Void>> deleteProjectType(@PathVariable Long id) {
        boolean deleted = projectTypeService.deleteProjectType(id);
        if(deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.DELETE_SUCCESS,
                    null
            ));
        } else  {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.DELETE_FAILED,
                    null
            ));
        }
    }
    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ProjectTypeDto>>getProjectTypeById(@PathVariable Long id){
        ProjectTypeDto projectTypeDto=projectTypeService.getProjectTypeById(id);
        if(projectTypeDto!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    projectTypeDto
            ));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }
}
