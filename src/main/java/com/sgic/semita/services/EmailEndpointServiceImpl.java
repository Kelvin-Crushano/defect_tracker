package com.sgic.semita.services;

import com.sgic.semita.dtos.EmailEndpointDto;
import com.sgic.semita.entities.EmailEndPoints;
import com.sgic.semita.repositories.EmailEndPointsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailEndpointServiceImpl implements EmailEndpointService {

    @Autowired
    private EmailEndPointsRepository emailEndpointRepository;


    public List<EmailEndpointDto> getPaginatedEntities(Pageable pageable) {
        Page<EmailEndPoints> emailEndPoints=emailEndpointRepository.findAll(pageable);

        return emailEndPoints.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmailEndPoints updateEmailEndpoint(Long id, EmailEndpointDto emailEndpointDto) {
        EmailEndPoints emailEndPoints = emailEndpointRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Email Endpoint not found"));

        BeanUtils.copyProperties(emailEndpointDto, emailEndPoints, "id");
        return emailEndpointRepository.save(emailEndPoints);
    }

    @Override
    public List<EmailEndPoints> createEmailEndpoints(List<EmailEndpointDto> emailEndpointDtos) {
        List<EmailEndPoints> emailEndpoints = emailEndpointDtos.stream()
                .map(dto -> {
                    EmailEndPoints emailEndpoint = new EmailEndPoints();
                    emailEndpoint.setName(dto.getName());
                    emailEndpoint.setEnabled(false); // Default value
                    return emailEndpoint;
                })
                .collect(Collectors.toList());

        return emailEndpointRepository.saveAll(emailEndpoints);
    }



    private EmailEndpointDto convertToDto(EmailEndPoints emailEndPoints) {
        EmailEndpointDto dto = new EmailEndpointDto();
        BeanUtils.copyProperties(emailEndPoints, dto);
        return dto;
    }
    @Override
    public boolean deleteEmailEndpoint(Long id) {
        if (emailEndpointRepository.existsById(id)) {
            emailEndpointRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
