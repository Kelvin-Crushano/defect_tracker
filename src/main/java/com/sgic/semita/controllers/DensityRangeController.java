package com.sgic.semita.controllers;

import com.sgic.semita.dtos.DensityRangeDto;
import com.sgic.semita.entities.DensityRange;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.DensityRangeService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.DENSITY_RANGE)
public class DensityRangeController {

    @Autowired
    private DensityRangeService densityRangeService;

    @PostMapping(EndpointBundle.PROJECT_ID)
    public ResponseEntity<ResponseWrapper<List<DensityRange>>> createDensityRanges(@PathVariable("projectId") Long projectId, @Valid @RequestBody List<DensityRangeDto> densityRangeDtos) {
        List<DensityRange> createdDensityRanges = densityRangeService.createOrUpdateDensityRanges(projectId, densityRangeDtos);
        if (!createdDensityRanges.isEmpty()) {
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

    @GetMapping(EndpointBundle.PROJECT_ID)
    public ResponseEntity<ResponseWrapper<List<DensityRange>>> getDensityRangeById(@PathVariable("projectId") Long projectId) {
        List<DensityRange> densityRange = densityRangeService.getDensityRangeByProjectId(projectId);
        if (densityRange != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    densityRange
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }

    @PutMapping(EndpointBundle.PROJECT_ID)
    public ResponseEntity<ResponseWrapper<List<DensityRange>>> updateDensityRanges(@PathVariable("projectId") Long projectId, @Valid @RequestBody List<DensityRangeDto> densityRangeDtos) {
        List<DensityRange> updatedDensityRanges = densityRangeService.createOrUpdateDensityRanges(projectId, densityRangeDtos);
        if (!updatedDensityRanges.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
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


}




