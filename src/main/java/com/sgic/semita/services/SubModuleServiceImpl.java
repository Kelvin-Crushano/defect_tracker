package com.sgic.semita.services;

import com.sgic.semita.dtos.SubModuleDto;
import com.sgic.semita.entities.Module;
import com.sgic.semita.entities.SubModule;
import com.sgic.semita.repositories.ModuleRepository;
import com.sgic.semita.repositories.ProjectRepository;
import com.sgic.semita.repositories.SubModuleRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubModuleServiceImpl implements SubModuleService {

    @Autowired
    private SubModuleRepository subModuleRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ProjectRepository projectRepository;
    @Override
    public List<SubModuleDto> getAllSubModulesByProject(Long projectId, Pageable pageable) {
        Page<SubModule> subModulesPage = subModuleRepository.findByModuleProjectId(projectId, pageable);
        if (subModulesPage.isEmpty()) {
            throw new RuntimeException(ValidationMessages.NO_RECORDS_FOUND);
        }
        return subModulesPage.stream().map(subModule -> {
            SubModuleDto subModuleDTO = new SubModuleDto();
            BeanUtils.copyProperties(subModule, subModuleDTO);
            subModuleDTO.setModuleId(subModule.getModule().getId());
            return subModuleDTO;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public boolean deleteSubModule(Long id) {
        if (moduleRepository.existsById(id)){
            moduleRepository.deleteById(id);
            return true;
        }
        return false;
    }
    


    @Override
    public SubModule createSubModule(SubModuleDto subModuleDto) {

        Module module = moduleRepository.findById(subModuleDto.getModuleId())
                .orElseThrow(() -> new RuntimeException("Module ID not found: " + subModuleDto.getModuleId()));

        SubModule subModule = new SubModule();
        BeanUtils.copyProperties(subModuleDto, subModule);
        subModule.setModule(module);
        subModule.setCreatedAt(Instant.now());
        subModule.setUpdatedAt(Instant.now());

        return subModuleRepository.save(subModule);
    }

    @Override
    public SubModule updateSubModule(Long id, SubModuleDto subModuleDto) {
        SubModule subModule = subModuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubModule not found with ID: " + id));



        if(subModuleDto.getModuleId()!=null){
            Module module=moduleRepository.findById(subModuleDto.getModuleId())
                    .orElseThrow(()-> new RuntimeException(("Module not found")));
                    subModule.setModule(module);
        }

        BeanUtils.copyProperties(subModuleDto, subModule, "id");
        subModule.setUpdatedAt(Instant.now());
        return subModuleRepository.save(subModule);
    }

    @Override
    public List<SubModuleDto> getAllSubmodulesByProjectId(Long moduleId, Long projectId, Pageable pageable) {

        if (!moduleRepository.existsById(moduleId)) {
            throw new RuntimeException(ValidationMessages.PROJECT_NOT_FOUND+ moduleId);
        }

        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException(ValidationMessages.PROJECT_NOT_FOUND + projectId);
        }

        Page<SubModule> subModulePage = subModuleRepository.findByModuleIdAndProjectId(projectId, moduleId, pageable);

        return subModulePage.getContent().stream()
                .map(subModule -> new SubModuleDto(
                        subModule.getId(),
                        subModule.getName(),
                        subModule.getModule().getId(),
                        subModule.getModule().getProject().getId()
                ))
                .collect(Collectors.toList());
    }
}
