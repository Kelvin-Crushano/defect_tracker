package com.sgic.semita.services;

import com.sgic.semita.dtos.SeverityDto;
import com.sgic.semita.entities.Severity;
import com.sgic.semita.repositories.SeverityRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeverityServiceImpl implements SeverityService {

    @Autowired
    public SeverityRepository severityRepository;



    @Override
    public Severity createSeverity(SeverityDto severityDto) {
        Severity severity = new Severity();
        BeanUtils.copyProperties(severityDto, severity);

        return severityRepository.save(severity);
    }


    @Override
    public List<Severity> getAllSeverity(Pageable pageable) {
        Page<Severity> severityPage = severityRepository.findAll(pageable);
        return severityPage.getContent();
    }




    @Override
    public Severity updateSeverity(Long id, SeverityDto severityDto) {
        Severity updatedSeverity = severityRepository.findById(id).orElseThrow(() -> new RuntimeException("Severity not found with ID: " + id));
        BeanUtils.copyProperties(severityDto, updatedSeverity, "id");
        return severityRepository.save(updatedSeverity);
    }

    @Override
    public boolean deleteSeverity(Long id) {
        if (severityRepository.existsById(id)) {
            severityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public SeverityDto getSeverityById(Long id) {
        Severity severity = severityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.INVALID_ID));

        SeverityDto severityDto = new SeverityDto();
        BeanUtils.copyProperties(severity, severityDto);
        return severityDto;
    }


}
