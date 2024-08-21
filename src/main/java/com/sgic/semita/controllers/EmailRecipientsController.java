package com.sgic.semita.controllers;

import com.sgic.semita.dtos.EmailRecipientsDto;
import com.sgic.semita.dtos.UserDto;
import com.sgic.semita.entities.EmailRecipients;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.EmailRecipientsService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.EMAIL_RECIPIENTS)
public class EmailRecipientsController {

    @Autowired
    private EmailRecipientsService emailRecipientsService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<List<EmailRecipients>>> createEmailRecipients(@Validated @RequestBody List<EmailRecipientsDto> emailRecipientsDtos) {
        List<EmailRecipients> emailRecipientsList = emailRecipientsService.createEmailRecipients(emailRecipientsDtos);
        if (emailRecipientsList != null && !emailRecipientsList.isEmpty()) {
            return ResponseEntity.ok(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    ValidationMessages.SAVED_SUCCESSFULL,
                    null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    HttpStatus.BAD_REQUEST.value(),
                    ValidationMessages.SAVE_FAILED,
                    null));
        }
    }

// delete
@DeleteMapping(EndpointBundle.ID)
public ResponseEntity<ResponseWrapper<?>> deleteEmailRecipient(@PathVariable Long id) {
    boolean isDeleted = emailRecipientsService.deleteEmailRecipient(id);

    ResponseWrapper<String> response;
    if (isDeleted) {
        return ResponseEntity.ok( new ResponseWrapper<>(
                RestApiResponseStatusCodes.SUCCESS.getCode(),
                ValidationMessages.DELETE_SUCCESS,
                null));
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                ValidationMessages.EMAIL_RECIPIENT_NOT + id,
                null));
    }
}

// Get by Email Endpoints
@GetMapping(EndpointBundle.ENDPOINTS_ID)
public ResponseEntity<ResponseWrapper<List<EmailRecipientsDto>>> getEmailRecipientsByEndpointId(
        @PathVariable Long emailEndpointId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);
    List<EmailRecipientsDto> emailRecipients = emailRecipientsService.getEmailRecipientsByEndpointId(emailEndpointId, pageable);

    if (emailRecipients != null && !emailRecipients.isEmpty()) {
        return ResponseEntity.ok(new ResponseWrapper<>(
                RestApiResponseStatusCodes.SUCCESS.getCode(),
                ValidationMessages.RETRIEVED,
                emailRecipients));
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                ValidationMessages.EMAIL_RECIPIENT_NOT + emailEndpointId,
                null));
    }
}



// Update status
    @PutMapping(EndpointBundle.STATUS)
    public ResponseEntity<ResponseWrapper<EmailRecipients>> updateEmailRecipientStatus(@PathVariable Long id, @RequestParam Boolean status) {
        EmailRecipients response = emailRecipientsService.updateEmailRecipientStatus(id, status);
        if (response != null){
            return ResponseEntity.ok(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.UPDATE_SUCCESS,
                    null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(
                RestApiResponseStatusCodes.INTERNAL_SERVER_ERROR.getCode(),
                ValidationMessages.SAVE_FAILED,
                null));

    }

    // User not allocated
    @GetMapping(EndpointBundle._USER)
    public ResponseEntity<ResponseWrapper<List<UserDto>>> getUsersNotAssociatedWithEmailEndpoint(@PathVariable Long emailEndpointId) {
        List<UserDto> users = emailRecipientsService.getUsersNotAssociatedWithEmailEndpoint(emailEndpointId);
        if (users.isEmpty()) {
            return ResponseEntity.status(404).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    RestApiResponseStatusCodes.NOT_FOUND.getMessage(),
                    null));
        } else {
            ResponseWrapper<List<UserDto>> response = new ResponseWrapper<>(
                    RestApiResponseStatusCodes.SUCCESS.getCode(),
                    ValidationMessages.RETRIEVED,
                    users
            );
            return ResponseEntity.ok(response);
        }
    }


}
