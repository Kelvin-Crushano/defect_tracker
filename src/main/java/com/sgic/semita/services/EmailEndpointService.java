package com.sgic.semita.services;

import com.sgic.semita.dtos.EmailEndpointDto;
import com.sgic.semita.entities.EmailEndPoints;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmailEndpointService {
    List<EmailEndpointDto> getPaginatedEntities(Pageable pageable);
    EmailEndPoints updateEmailEndpoint(Long id, EmailEndpointDto emailEndpointDto);
    //EmailEndPoints createEmailEndpoint(EmailEndpointDto emailEndpointDto);
    List<EmailEndPoints> createEmailEndpoints(List<EmailEndpointDto> emailEndpointDtos);
    boolean deleteEmailEndpoint(Long id);  // New method


}
