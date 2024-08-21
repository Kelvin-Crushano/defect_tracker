package com.sgic.semita.controllers;

import com.sgic.semita.dtos.DefectDto;
import com.sgic.semita.entities.Defect;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.DefectService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.sgic.semita.dtos.DefectGetDto;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping(EndpointBundle.DEFECT)
public class DefectController {

    @Autowired
    private DefectService defectService;

    // Search
    @GetMapping(EndpointBundle.SEARCH_DEFECT)
    public ResponseEntity<ResponseWrapper<List<DefectGetDto>>> searchDefects(
            @RequestParam(value = "projectId", required = true) Long projectId,
            @RequestParam(value = EndpointBundle.STEP_TO_RECREATE, required = false) String stepToReCreate,
            @RequestParam(value = EndpointBundle.DEFECT_TYPE_SEARCH, required = false) String defectType,
            @RequestParam(value = EndpointBundle.DEFECT_STATUS_SEARCH, required = false) String defectStatus,
            @RequestParam(value = EndpointBundle.DEFECT_PRIORITY, required = false) String priority,
            @RequestParam(value = EndpointBundle.DEFECT_SEVERITY, required = false) String severity,
            @RequestParam(value = EndpointBundle.RELEASE_NAME, required = false) String release,
            @RequestParam(value = EndpointBundle.SUBMODULE_NAME, required = false) String subModule,
            @RequestParam(value = EndpointBundle.MODULE_NAME, required = false) String module,
            @RequestParam(value = EndpointBundle.ASSIGN_BY, required = false) String assignBy,
            @RequestParam(value = EndpointBundle.ASSIGN_TO, required = false) String assignTo,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (projectId == null) {
            ResponseWrapper<List<DefectGetDto>> errorWrapper = new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    RestApiResponseStatusCodes.BAD_REQUEST.getMessage(),
                    null
            );
            return new ResponseEntity<>(errorWrapper, HttpStatus.BAD_REQUEST);
        }

        List<DefectGetDto> defects = defectService.searchDefects(
                projectId, stepToReCreate, defectType, defectStatus,
                priority, severity, release, subModule,
                module, assignBy,assignTo, pageable
        );

        if (defects.isEmpty()) {
            ResponseWrapper<List<DefectGetDto>> responseWrapper = new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    ValidationMessages.DEFECT_NOT_FOUND,
                    defects
            );
            return new ResponseEntity<>(responseWrapper, HttpStatus.NOT_FOUND);
        }

        ResponseWrapper<List<DefectGetDto>> responseWrapper = new ResponseWrapper<>(
                HttpStatus.OK.value(),
                ValidationMessages.DEFECT_FOUND,
                defects
        );

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

    //upload
    @PostMapping(EndpointBundle.UPLOAD)
    public ResponseEntity<ResponseWrapper<List<Defect>>> uploadDefects(
            @RequestParam("file") MultipartFile file,
            @RequestParam("projectId") Long projectId) {
        List<Defect> defects = defectService.uploadDefects(projectId, file);

        if (defects == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            ValidationMessages.INTERNAL_SERVER_ERROR,
                            null));
        } else if (defects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            ValidationMessages.NO_DEFECT_IN_CSV,
                            null));
        } else {
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(),
                    ValidationMessages.SAVED_SUCCESSFULL,
                    null));
        }
    }


    // Download
    @GetMapping(EndpointBundle.EXPORT)
    public ResponseEntity<byte[]> exportDefectsToCsv(
            @RequestParam(value = "projectId", required = true) Long projectId,
            @RequestParam(value = EndpointBundle.STEP_TO_RECREATE, required = false) String stepToReCreate,
            @RequestParam(value = EndpointBundle.DEFECT_TYPE, required = false) String defectType,
            @RequestParam(value = EndpointBundle.DEFECT_STATUS, required = false) String defectStatus,
            @RequestParam(value = EndpointBundle.DEFECT_PRIORITY, required = false) String priority,
            @RequestParam(value = EndpointBundle.DEFECT_SEVERITY, required = false) String severity,
            @RequestParam(value = EndpointBundle.RELEASE_NAME, required = false) String release,
            @RequestParam(value = EndpointBundle.SUBMODULE_NAME, required = false) String subModule,
            @RequestParam(value = EndpointBundle.MODULE_NAME, required = false) String module,
            @RequestParam(value = EndpointBundle.ASSIGN_BY, required = false) String assignBy,
            @RequestParam(value = EndpointBundle.ASSIGN_TO, required = false) String assignTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        List<DefectGetDto> defects = defectService.searchDefects(
                projectId, stepToReCreate, defectType, defectStatus,
                priority, severity, release, subModule,
                module, assignBy,assignTo, pageable
        );

        if (defects == null || defects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String csvContent = defectService.convertToCsv(defects);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "defects.csv");

        return new ResponseEntity<>(csvContent.getBytes(), headers, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<ResponseWrapper<Defect>>createDefect(@Valid @RequestBody DefectDto defectDto){
        Defect createDefect=defectService.createDefect(defectDto);

        if(createDefect!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULL,
                    null
            ));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    ValidationMessages.SAVE_FAILED,
                    null
            ));
        }
    }

    @GetMapping(EndpointBundle._PROJECT_ID)
    public ResponseEntity<ResponseWrapper<List<DefectGetDto>>> getDefectsByProjectId(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<DefectGetDto> defects = defectService.getAllDefectsByProjectId(projectId,pageable);

        if (defects != null) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    defects
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<DefectGetDto>> getDefectById(@PathVariable("id") Long id) {
        DefectGetDto defectGetDto = defectService.getDefectById(id);
        if (defectGetDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    defectGetDto
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
    public ResponseEntity<ResponseWrapper<Defect>> updateDefect(@PathVariable("id") Long id, @Valid @RequestBody DefectDto defectDto) {
        Defect updateDefect=defectService.updateDefect(id,defectDto);
        if (updateDefect != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULL,
                    null
            ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    ValidationMessages.SAVE_FAILED,
                    null
            ));
        }
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Void>>deleteDefect(@PathVariable Long id) {
        boolean deleted = defectService.deleteDefect(id);
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
