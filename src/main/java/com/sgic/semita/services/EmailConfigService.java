package com.sgic.semita.services;

import com.sgic.semita.dtos.EmailConfigDTO;
import com.sgic.semita.entities.EmailConfiguration;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmailConfigService {
    EmailConfiguration CreateEmailConfig(EmailConfigDTO emailConfigDTO);


    boolean deleteEmailConfig(long id);
    EmailConfiguration updateEmailConfig(EmailConfigDTO emailConfigDTO);


    List<EmailConfigDTO> getPaginatedEntities(Pageable pageable);
    EmailConfigDTO getEmailConfigById(Long id);  // New method


}
