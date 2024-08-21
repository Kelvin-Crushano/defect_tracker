package com.sgic.semita.services;

import com.sgic.semita.dtos.PriorityDto;
import com.sgic.semita.entities.Priority;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PriorityService {
    Priority createPriority(PriorityDto priorityDto);
    List<PriorityDto> getAllPriorities(Pageable pageable);
    Priority updatePriority(Long id, PriorityDto priorityDto);
    boolean deletePriority(Long id);
    PriorityDto getPriorityById(Long id);  // New method

}
