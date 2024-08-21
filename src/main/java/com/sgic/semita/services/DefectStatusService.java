package com.sgic.semita.services;

import com.sgic.semita.dtos.DefectStatusDto;
import com.sgic.semita.entities.DefectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DefectStatusService {
    DefectStatus createDefectStatus(DefectStatusDto defectStatusDto);
    List<DefectStatusDto> getAllDefectStatuses(Pageable pageable);
    DefectStatus updateDefectStatus(Long id, DefectStatusDto defectStatusDto);
    boolean deleteDefectStatus(Long id);
    DefectStatusDto getDefectStatusById(Long id);
}
