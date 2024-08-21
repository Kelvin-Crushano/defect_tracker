package com.sgic.semita.services;

import com.sgic.semita.dtos.DefectDto;
import com.sgic.semita.entities.Defect;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.sgic.semita.dtos.DefectGetDto;

import java.util.List;

public interface DefectService {

    List<DefectGetDto> searchDefects(
            Long projectId,
            String stepToReCreate,
            String defectTypeName,
            String defectStatusName,
            String defectPriorityName,
            String defectSeverityName,
            String releaseName,
            String subModuleName,
            String moduleName,
            String assignBy,
            String assignTo,
            Pageable pageable);

    List<Defect> uploadDefects (Long projectId, MultipartFile file);


    Defect createDefect(DefectDto defectDto);
    Defect updateDefect(Long id, DefectDto defectDto);
    List<DefectGetDto> getAllDefectsByProjectId(Long projectId, Pageable pageable);
    DefectGetDto getDefectById(Long id);
    boolean deleteDefect(Long id);

    String convertToCsv(List<DefectGetDto> defectDtos);
}
