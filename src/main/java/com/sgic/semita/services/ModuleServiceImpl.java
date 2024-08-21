package com.sgic.semita.services;

import com.sgic.semita.dtos.ModuleDto;
import com.sgic.semita.entities.Module;
import com.sgic.semita.entities.Project;
import com.sgic.semita.repositories.ModuleRepository;
import com.sgic.semita.repositories.ProjectRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Module createModule(ModuleDto moduleDto) {


        Project project = projectRepository.findById(moduleDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project ID not found: " + moduleDto.getProjectId()));

        Module module = new Module();
        BeanUtils.copyProperties(moduleDto, module);
        module.setProject(project);
        module.setCreatedAt(Instant.now());
        module.setUpdatedAt(Instant.now());

        return moduleRepository.save(module);
    }

    @Override
    public Module updateModule(Long id, ModuleDto moduleDto) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with ID: " + id));


        BeanUtils.copyProperties(moduleDto, module, "id");
        module.setUpdatedAt(Instant.now());
        return moduleRepository.save(module);
    }

    @Override
    public List<Module> getModulesByProjectId(Long projectId) {
        return moduleRepository.findByProjectId(projectId);
    }

    @Override
    public Page<Module> getAllModules(Pageable pageable) {
        return moduleRepository.findAll(pageable);
    }


    public ModuleDto getModuleNameById(Long id) {
        Module module = moduleRepository.findById(id).orElseThrow(() -> new RuntimeException(ValidationMessages.MODULE_NOT_FOUND + id));

        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setId(module.getId());
        moduleDto.setName(module.getName());

        return moduleDto;
    }
    @Override
    public boolean deleteModule(Long id) {
        if(moduleRepository.existsById(id)){
            moduleRepository.deleteById(id);
            return true;
        }
        return false;

    }
}
