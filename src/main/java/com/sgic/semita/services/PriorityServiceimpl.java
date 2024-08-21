package com.sgic.semita.services;

import com.sgic.semita.dtos.PriorityDto;
import com.sgic.semita.entities.Priority;
import com.sgic.semita.repositories.PriorityRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriorityServiceimpl implements PriorityService {
    @Autowired
    private PriorityRepository priorityRepository;


    @Override
    public Priority createPriority(PriorityDto priorityDto) {
        Priority priority = new Priority();
        BeanUtils.copyProperties(priorityDto, priority);
        return priorityRepository.save(priority);
    }

    @Override
    public List<PriorityDto> getAllPriorities(Pageable pageable) {
        Page<Priority> priorityPage = priorityRepository.findAll(pageable);

        if (priorityPage.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.NO_RECORDS_FOUND);
        }

        return priorityPage.getContent().stream()
                .map(dt -> new PriorityDto(dt.getId(), dt.getName(),dt.getColorCode()))
                .collect(Collectors.toList());
    }


    @Override
    public Priority updatePriority(Long id, PriorityDto priorityDto) {
        Priority existingPriority = priorityRepository.findById(id).
                orElseThrow(() ->
                        new RuntimeException("Priority not found with ID: " + id));
        BeanUtils.copyProperties(priorityDto, existingPriority, "id");
        return priorityRepository.save(existingPriority);
    }

    @Override
    public boolean deletePriority(Long id) {
        if (priorityRepository.existsById(id)) {
            priorityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public PriorityDto getPriorityById(Long id) {
        Priority priority = priorityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Priority not found with ID: " + id));

        return new PriorityDto(priority.getId(), priority.getName(), priority.getColorCode());
    }
}
