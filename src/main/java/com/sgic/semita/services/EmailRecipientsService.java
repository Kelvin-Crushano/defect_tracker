package com.sgic.semita.services;

import com.sgic.semita.dtos.EmailRecipientsDto;
import com.sgic.semita.dtos.UserDto;
import com.sgic.semita.entities.EmailRecipients;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmailRecipientsService {
    List<EmailRecipients> createEmailRecipients(List<EmailRecipientsDto> emailRecipientsDto);
    boolean deleteEmailRecipient(Long id);
    List<EmailRecipientsDto> getEmailRecipientsByEndpointId(Long emailEndpointId, Pageable pageable);
    EmailRecipients updateEmailRecipientStatus(Long id, Boolean status);
    List<UserDto> getUsersNotAssociatedWithEmailEndpoint(Long emailEndpointId);
}
