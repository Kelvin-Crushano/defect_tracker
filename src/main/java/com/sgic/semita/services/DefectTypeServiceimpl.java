package com.sgic.semita.services;

import com.sgic.semita.dtos.DefectTypeDto;
import com.sgic.semita.entities.DefectType;
import com.sgic.semita.repositories.DefectTypeRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefectTypeServiceimpl implements DefectTypeService{

    @Autowired
    private DefectTypeRepository defectTypeRepository;

    @Override
    public DefectType createDefectType(DefectTypeDto defectTypeDto) {
        DefectType defectType = new DefectType();
        defectType.setName(defectTypeDto.getName());
        return defectTypeRepository.save(defectType);
    }

    @Override
    public List<DefectTypeDto> getAllDefectType(Pageable pageable) {
        Page<DefectType> defectTypePage = defectTypeRepository.findAll(pageable);

        if (defectTypePage.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.NO_RECORDS_FOUND);
        }

        return defectTypePage.getContent().stream()
                .map(dt -> new DefectTypeDto(dt.getId(), dt.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public DefectType updateDefectType(Long id, DefectTypeDto defectTypeDto) {
        // Find the existing defect type by ID
        DefectType existingDefectType = defectTypeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ValidationMessages.INVALID_ID));
        existingDefectType.setName(defectTypeDto.getName());
        return defectTypeRepository.save(existingDefectType);
    }

    @Override
    public boolean deleteDefectType(Long id) {
        if (defectTypeRepository.existsById(id)) {
            defectTypeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DefectTypeDto getDefectTypeById(Long id) {
        DefectType defectType = defectTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.INVALID_ID));
        return new DefectTypeDto(defectType.getId(), defectType.getName());
    }



}
