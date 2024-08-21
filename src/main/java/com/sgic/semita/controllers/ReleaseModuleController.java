package com.sgic.semita.controllers;

import com.sgic.semita.dtos.ReleaseModuleDto;
import com.sgic.semita.dtos.UserDto;
import com.sgic.semita.entities.ReleaseModule;
import com.sgic.semita.entities.User;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.ReleaseModuleService;
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
@RequestMapping(EndpointBundle.RELEASE_MODULE)
public class ReleaseModuleController {

    @Autowired
    private ReleaseModuleService releaseModuleService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<ReleaseModule>>createReleaseModule(@Valid @RequestBody ReleaseModuleDto releaseModuleDto){
        ReleaseModule createReleaseModule=releaseModuleService.createReleaseModule(releaseModuleDto);

        if (createReleaseModule!=null){
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
    public ResponseEntity<ResponseWrapper<List<ReleaseModuleDto>>> getAllReleaseModule(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<ReleaseModuleDto> getAllReleaseModule = releaseModuleService.getAllReleaseModule(pageable);
        if (getAllReleaseModule != null ) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    getAllReleaseModule
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
    public ResponseEntity<ResponseWrapper<User>> updateReleaseModule(@PathVariable("id") Long id, @Valid @RequestBody ReleaseModuleDto releaseModuleDto) {
        ReleaseModule updateReleaseModule = releaseModuleService.updateReleaseModule(id, releaseModuleDto);
        if (updateReleaseModule != null) {
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
    public ResponseEntity<ResponseWrapper<Void>> deleteReleaseModule(@PathVariable Long id) {
        boolean deleted = releaseModuleService.deleteReleaseModule(id);
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
