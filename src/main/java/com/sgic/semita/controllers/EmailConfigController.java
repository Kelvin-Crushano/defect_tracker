package com.sgic.semita.controllers;

import com.sgic.semita.dtos.EmailConfigDTO;
import com.sgic.semita.entities.EmailConfiguration;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.EmailConfigService;
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

@CrossOrigin
@RestController
@RequestMapping(EndpointBundle.EMAIL_Config)
public class EmailConfigController {

    @Autowired
    private EmailConfigService emailConfigService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<EmailConfiguration>> save(@Valid @RequestBody EmailConfigDTO emailConfigDTO) {

            EmailConfiguration emailSaved = emailConfigService.CreateEmailConfig(emailConfigDTO);
            if (emailSaved != null) {
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


    @PutMapping
    public ResponseEntity<ResponseWrapper<EmailConfiguration>> update(@Valid @RequestBody EmailConfigDTO emailConfigDTO) {

            EmailConfiguration emailEdit = emailConfigService.updateEmailConfig(emailConfigDTO);
            if (emailEdit != null) {
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



    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Void>> delete(@PathVariable long id) {
            boolean deleted = emailConfigService.deleteEmailConfig(id);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                        ValidationMessages.DELETE_SUCCESS,
                        null
                ));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.NO_CONTENT.getCode(),
                        ValidationMessages.DELETE_FAILED,
                        null
                ));
            }

    }


    @GetMapping
    public ResponseEntity<ResponseWrapper<List<EmailConfigDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<EmailConfigDTO> emailConfigurations = emailConfigService.getPaginatedEntities(pageable);

        if (emailConfigurations != null && !emailConfigurations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    emailConfigurations
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.NO_RECORDS_FOUND,
                    null
            ));
        }
    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<EmailConfigDTO>> getById(@PathVariable Long id) {
        EmailConfigDTO emailConfigDTO = emailConfigService.getEmailConfigById(id);
        if (emailConfigDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    emailConfigDTO
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
