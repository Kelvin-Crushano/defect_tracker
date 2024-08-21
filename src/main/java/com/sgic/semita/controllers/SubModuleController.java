package com.sgic.semita.controllers;

import com.sgic.semita.dtos.SubModuleDto;
import com.sgic.semita.entities.SubModule;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.SubModuleService;
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
@RequestMapping(EndpointBundle.SUBMODULE)
public class SubModuleController {

    @Autowired
    private SubModuleService subModuleService;

    @GetMapping(EndpointBundle.SUBMODULE_PROJECT)
    public ResponseEntity<ResponseWrapper<List<SubModuleDto>>> getAllSubModulesByProject(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<SubModuleDto> subModule = subModuleService.getAllSubModulesByProject(projectId, pageable);

        if (subModule != null && !subModule.isEmpty()) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    subModule
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }
    
    @PostMapping
    public ResponseEntity<ResponseWrapper<SubModule>> createSubModule(@Valid @RequestBody SubModuleDto subModuleDto) {
        SubModule createdSubModule = subModuleService.createSubModule(subModuleDto);
        if (createdSubModule != null) {
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

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<SubModule>> updateSubModule( @PathVariable Long id, @Valid @RequestBody SubModuleDto subModuleDto) {
        SubModule updatedSubModule = subModuleService.updateSubModule(id, subModuleDto);
        if (updatedSubModule != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.UPDATE_SUCCESS,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.UPDATE_FAILED,
                    null
            ));
        }
    }

    @GetMapping(EndpointBundle.SUB_MODULE_BY_PROJECT)
    public ResponseEntity<ResponseWrapper<List<SubModuleDto>>> getAllSubmodulesByProjectId(
            @PathVariable("moduleId") Long moduleId,
            @PathVariable("projectId") Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<SubModuleDto> submoduleDto = subModuleService.getAllSubmodulesByProjectId(moduleId, projectId, pageable);

        if (!submoduleDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    submoduleDto
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    ValidationMessages.NO_RECORDS_FOUND,
                    null
            ));
        }
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Void>>deleteSubModule(@PathVariable Long id){
        boolean deleted=subModuleService.deleteSubModule(id);
        if(deleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.DELETE_SUCCESS,
                    null
            ));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    ValidationMessages.DELETE_FAILED,
                    null
            ));
        }
    }
}
