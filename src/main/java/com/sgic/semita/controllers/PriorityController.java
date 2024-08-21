package com.sgic.semita.controllers;

import com.sgic.semita.dtos.PriorityDto;
import com.sgic.semita.entities.Priority;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.PriorityService;
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
@RequestMapping(EndpointBundle.PRIORITY)
public class PriorityController {
    @Autowired
    private PriorityService priorityService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Priority>> createPriority(@Valid @RequestBody PriorityDto priorityDto){
        Priority createdPriority = priorityService.createPriority(priorityDto);
        if (createdPriority != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULL,
                    null
            ));
        }else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.SAVE_FAILED,
                    null
            ));
        }
    }


    //get all data
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<PriorityDto>>> getAllPriorities(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<PriorityDto> priority = priorityService.getAllPriorities(pageable);

        if (priority != null && !priority.isEmpty()) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    priority
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
    public ResponseEntity<ResponseWrapper<Priority>> updateUser(@PathVariable("id") Long id, @Valid @RequestBody PriorityDto priorityDto) {
        Priority updatedPriority = priorityService.updatePriority(id, priorityDto);
        if (updatedPriority != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULL,
                    null
            ));
        }else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.SAVE_FAILED,
                    null
            ));
        }
    }


    //Delete by id
    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Void>> deletePriority(@PathVariable Long id) {
        boolean deleted = priorityService.deletePriority(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.DELETE_SUCCESS,
                    null
            ));
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.DELETE_FAILED,
                    null
            ));
        }
    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<PriorityDto>> getPriorityById(@PathVariable Long id) {
        PriorityDto priorityDto = priorityService.getPriorityById(id);
        if (priorityDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    priorityDto
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
