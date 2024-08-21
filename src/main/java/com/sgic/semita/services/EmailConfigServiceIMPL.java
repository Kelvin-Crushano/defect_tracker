package com.sgic.semita.services;

import com.sgic.semita.dtos.EmailConfigDTO;
import com.sgic.semita.entities.EmailConfiguration;
import com.sgic.semita.repositories.EmailConfigurationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailConfigServiceIMPL implements EmailConfigService {
    @Autowired
    private EmailConfigurationRepository emailConfigRepo;

    @Override
    public EmailConfiguration CreateEmailConfig(EmailConfigDTO emailConfigDTO) {
        EmailConfiguration emailConfiguration = new EmailConfiguration();
        BeanUtils.copyProperties(emailConfigDTO, emailConfiguration, "id", "status");
        return emailConfigRepo.save(emailConfiguration);
    }


    @Transactional
    public EmailConfiguration updateEmailConfig(EmailConfigDTO emailConfigDTO) {
        // Retrieve the entity by ID, handle optional
        EmailConfiguration emailConfiguration = emailConfigRepo.findById(emailConfigDTO.getId())
                .orElseThrow(() -> new RuntimeException("Email Configuration not found"));

        // Check if the status update is valid
        if (!emailConfiguration.isStatus() && emailConfigRepo.countByStatus(true) >= 1) {
            throw new RuntimeException("Only one email configuration can have its status set to true.");
        }

        // Copy properties from DTO to entity, excluding the ID
        BeanUtils.copyProperties(emailConfigDTO, emailConfiguration, "id");

        // Save and return the updated entity
        return emailConfigRepo.save(emailConfiguration);
    }

    @Override
    public boolean deleteEmailConfig(long id) {
        if (emailConfigRepo.existsById(id)) {
            emailConfigRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<EmailConfigDTO> getPaginatedEntities(Pageable pageable) {
        Page<EmailConfiguration> emailConfigurationPage = emailConfigRepo.findAll(pageable);

        return emailConfigurationPage.getContent().stream()
                .map(emailConfiguration -> new EmailConfigDTO(
                        emailConfiguration.getId(),
                        emailConfiguration.getPort(),
                        emailConfiguration.getEmail(),
                        emailConfiguration.getHost(),
                        emailConfiguration.isStatus(),
                        emailConfiguration.getProtocol(),
                        emailConfiguration.getPassword()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public EmailConfigDTO getEmailConfigById(Long id) {
        EmailConfiguration emailConfiguration = emailConfigRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Email Configuration not found with ID: " + id));

        EmailConfigDTO emailConfigDTO = new EmailConfigDTO();
        BeanUtils.copyProperties(emailConfiguration, emailConfigDTO);
        return emailConfigDTO;
    }
}
