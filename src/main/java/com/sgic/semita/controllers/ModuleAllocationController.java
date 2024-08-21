package com.sgic.semita.controllers;

import com.sgic.semita.dtos.ModuleAllocationRequestDto;
import com.sgic.semita.dtos.ModuleAllocationResponseDTO;
import com.sgic.semita.dtos.ProjectAllocationDetailDto;
import com.sgic.semita.entities.ModuleAllocations;
import com.sgic.semita.entities.ProjectAllocations;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.ModuleAllocationService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.MODULE_ALLOCATION)
public class ModuleAllocationController {

    @Autowired
    private ModuleAllocationService moduleAllocationService;


    @GetMapping(EndpointBundle.PROJECT_ID)
    public ResponseEntity<ResponseWrapper<List<ProjectAllocationDetailDto>>> getNonAllocatedUsers(@PathVariable("projectId") Long projectId) {
        List<ProjectAllocationDetailDto> nonAllocatedUsers = moduleAllocationService.getNonAllocatedUsers(projectId);

        if (!nonAllocatedUsers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    nonAllocatedUsers
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }


    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ModuleAllocationResponseDTO>>> getAllModuleAllocations(
            @RequestParam("projectId") Long projectId,
            @RequestParam(value = "userId") Long userId) {

        List<ModuleAllocationResponseDTO> moduleAllocations = moduleAllocationService.getAllModuleAllocations(projectId, userId);

        if (!moduleAllocations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    moduleAllocations
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<List<ModuleAllocations>>> createModuleAllocation(@RequestBody ModuleAllocationRequestDto moduleAllocationRequestDto) {
        List<ModuleAllocations> moduleAllocations = moduleAllocationService.saveOrUpdateModuleAllocation(moduleAllocationRequestDto);

        if (moduleAllocations != null && !moduleAllocations.isEmpty()) {
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


    @PutMapping
    public ResponseEntity<ResponseWrapper<List<ModuleAllocations>>> updateModuleAllocation(@RequestBody ModuleAllocationRequestDto moduleAllocationRequestDto) {
        List<ModuleAllocations> updatedModuleAllocations = moduleAllocationService.saveOrUpdateModuleAllocation(moduleAllocationRequestDto);

        if (updatedModuleAllocations != null && !updatedModuleAllocations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.UPDATE_SUCCESS,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.UPDATE_FAILED,
                    null
            ));
        }
    }



    @DeleteMapping
    public ResponseEntity<ResponseWrapper<Void>> deleteModuleAllocation(@RequestParam("projectAllocationId") Long projectAllocationId) {
       boolean isDeleted= moduleAllocationService.deleteModuleAllocation(projectAllocationId);
        if (isDeleted) {
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

    @GetMapping(EndpointBundle.SEARCH)
    public ResponseEntity<ResponseWrapper<List<ModuleAllocationResponseDTO>>> searchModuleAllocations(
            @RequestParam(value = "projectId") Long projectId,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "subModuleName", required = false) String subModuleName) {

        List<ModuleAllocationResponseDTO> results = moduleAllocationService.searchModuleAllocations(projectId,userId, userName, subModuleName);

        if (!results.isEmpty()) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    results
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.NO_RECORDS_FOUND,
                    null
            ));
        }
    }


}
