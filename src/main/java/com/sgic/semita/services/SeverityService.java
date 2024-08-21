package com.sgic.semita.services;

import com.sgic.semita.dtos.SeverityDto;
import com.sgic.semita.entities.Severity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SeverityService {
    Severity createSeverity(SeverityDto severityDto);
    List<Severity> getAllSeverity(Pageable pageable);
    Severity updateSeverity(Long id, SeverityDto severityDto);
    boolean deleteSeverity(Long id);
    SeverityDto getSeverityById(Long id);  // New method

}
