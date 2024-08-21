package com.sgic.semita.controllers;

import com.sgic.semita.dtos.DefectStatusDto;
import com.sgic.semita.entities.DefectStatus;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.DefectStatusService;
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
@RequestMapping(EndpointBundle.DEFECT_STATUS)
public class DefectStatusController {

    @Autowired
    private DefectStatusService defectStatusService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<DefectStatusDto>> createDefectStatus(@Valid @RequestBody DefectStatusDto defectStatusDto) {
        DefectStatus createdDefectStatus = defectStatusService.createDefectStatus(defectStatusDto);
        if (createdDefectStatus != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
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

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<DefectStatusDto>>> getAllDefectStatuses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<DefectStatusDto> defectStatusPage = defectStatusService.getAllDefectStatuses(pageable);

        if (!defectStatusPage.isEmpty()) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    defectStatusPage
            ));
        } else {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.NO_RECORDS_FOUND,
                    null
            ));
        }
    }


    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<DefectStatusDto>> updateDefectStatus(@PathVariable("id") Long id, @Valid @RequestBody DefectStatusDto defectStatusDto) {
        DefectStatus updatedDefectStatus = defectStatusService.updateDefectStatus(id, defectStatusDto);
        if(updatedDefectStatus!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    ValidationMessages.UPDATE_SUCCESS,
                    null
            ));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    HttpStatus.BAD_REQUEST.value(),
                    ValidationMessages.UPDATE_FAILED,
                    null
            ));
        }
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Void>> deleteDefectStatus(@PathVariable("id") @Valid Long id) {
        boolean isDeleted = defectStatusService.deleteDefectStatus(id);
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
    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<DefectStatusDto>> getDefectStatusDtoById(@PathVariable Long id) {
        DefectStatusDto defectStatusDto = defectStatusService.getDefectStatusById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(
                RestApiResponseStatusCodes.SUCCESS.getCode(),
                ValidationMessages.RETRIEVED,
                defectStatusDto
        ));

    }








}