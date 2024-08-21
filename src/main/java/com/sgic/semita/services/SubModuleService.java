package com.sgic.semita.services;

import com.sgic.semita.dtos.SubModuleDto;
import com.sgic.semita.entities.SubModule;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubModuleService {
    SubModule createSubModule(SubModuleDto subModuleDto);
    SubModule updateSubModule(Long id, SubModuleDto subModuleDto);
    List<SubModuleDto> getAllSubmodulesByProjectId(Long moduleId, Long projectId, Pageable pageable);
    List<SubModuleDto> getAllSubModulesByProject(Long projectId, Pageable pageable);
    boolean deleteSubModule(Long id);
}
