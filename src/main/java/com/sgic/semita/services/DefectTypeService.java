package com.sgic.semita.services;

import com.sgic.semita.dtos.DefectTypeDto;
import com.sgic.semita.entities.DefectType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DefectTypeService {
    List<DefectTypeDto> getAllDefectType(Pageable pageable);

    DefectType updateDefectType(Long id, DefectTypeDto defectTypeDto);

    boolean deleteDefectType(Long id);

    DefectType createDefectType(DefectTypeDto defectTypeDto);
    DefectTypeDto getDefectTypeById(Long id);

}
