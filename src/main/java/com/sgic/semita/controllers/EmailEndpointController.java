package com.sgic.semita.controllers;

import com.sgic.semita.dtos.EmailEndpointDto;
import com.sgic.semita.entities.EmailEndPoints;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.EmailEndpointService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.EMAIL_ENDPOINTS)
public class EmailEndpointController {

    @Autowired
    private EmailEndpointService emailEndpointService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<List<EmailEndPoints>>> createEmailEndpoints(
            @RequestBody List<EmailEndpointDto> emailEndpointDtos) {

        List<EmailEndPoints> createdEmailEndpoints = emailEndpointService.createEmailEndpoints(emailEndpointDtos);

        if (createdEmailEndpoints != null && !createdEmailEndpoints.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULL,
                    createdEmailEndpoints
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                    ValidationMessages.SAVE_FAILED,
                    null
            ));
        }
    }


    @GetMapping
    public ResponseEntity<ResponseWrapper<List<EmailEndpointDto>>> getAllEmailEndpoints(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable= PageRequest.of(page, size);
        List<EmailEndpointDto> emailEndpoints = emailEndpointService.getPaginatedEntities(pageable);

        if (!emailEndpoints.isEmpty() ) {

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    emailEndpoints
            ));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.RETRIEVED_FAILED,
                    null
            ));
        }
    }




    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<EmailEndpointDto>> updateEmailEndpoint(@PathVariable Long id, @RequestBody EmailEndpointDto emailEndpointDto) {

            EmailEndPoints updatedEmailEndpoint = emailEndpointService.updateEmailEndpoint(id, emailEndpointDto);
            if (updatedEmailEndpoint != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.SUCCESS.getCode(),
                        ValidationMessages.UPDATE_SUCCESS,
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
    public ResponseEntity<ResponseWrapper<Void>> deleteEmailEndpoint(@PathVariable Long id) {
        boolean deleted = emailEndpointService.deleteEmailEndpoint(id);
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
