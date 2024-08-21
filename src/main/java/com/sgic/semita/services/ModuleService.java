package com.sgic.semita.services;

import com.sgic.semita.dtos.ModuleDto;
import com.sgic.semita.entities.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ModuleService {
    Module createModule(ModuleDto moduleDto);
    Module updateModule(Long id, ModuleDto moduleDto);
    List<Module> getModulesByProjectId(Long projectId);
    Page<Module> getAllModules(Pageable pageable);

    ModuleDto getModuleNameById(Long id);

    boolean deleteModule(Long id);
}
