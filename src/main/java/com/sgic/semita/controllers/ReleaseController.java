package com.sgic.semita.controllers;

import com.sgic.semita.dtos.ReleaseDto;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.ReleaseService;
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
@RequestMapping(EndpointBundle.RELEASE)
public class ReleaseController {

    @Autowired
    private ReleaseService releaseService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<ReleaseDto>> createRelease(@Valid @RequestBody ReleaseDto releaseDto) {
        ReleaseDto createdRelease = releaseService.createRelease(releaseDto);
        if(createdRelease!=null){
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

    @GetMapping(EndpointBundle.RELEASE_PROJECT)
    public ResponseEntity<ResponseWrapper<List<ReleaseDto>>> getAllReleasesByProject(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<ReleaseDto> releases = releaseService.getAllReleasesByProject(projectId, pageable);

        if(!releases.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    releases
            ));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    ValidationMessages.NO_RECORDS_FOUND,
                    null
            ));
        }

    }

    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ReleaseDto>> updateRelease(@PathVariable Long id, @Valid @RequestBody ReleaseDto releaseDto) {
        ReleaseDto updatedRelease = releaseService.updateRelease(id, releaseDto);
        if(updatedRelease!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.UPDATE_SUCCESS,
                    null
            ));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.UPDATE_FAILED,
                    null
            ));
        }
    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<ReleaseDto>> getReleaseById(@PathVariable Long id) {
        ReleaseDto releaseDto = releaseService.getReleaseById(id);
        if (releaseDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    releaseDto
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.NO_RECORDS_FOUND,
                    null
            ));
        }
    }


    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Void>>deleteRelease(@PathVariable Long id){
        boolean deleted=releaseService.deleteRelease(id);
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
