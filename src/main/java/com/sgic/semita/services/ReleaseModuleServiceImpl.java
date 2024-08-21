package com.sgic.semita.services;

import com.sgic.semita.dtos.ReleaseModuleDto;
import com.sgic.semita.dtos.UserDto;
import com.sgic.semita.entities.Defect;
import com.sgic.semita.entities.ReleaseModule;
import com.sgic.semita.entities.User;
import com.sgic.semita.repositories.ModuleRepository;
import com.sgic.semita.repositories.ReleaseModuleRepository;
import com.sgic.semita.repositories.ReleaseRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReleaseModuleServiceImpl implements ReleaseModuleService{

    @Autowired
    private ReleaseModuleRepository releaseModuleRepository;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private ModuleRepository moduleRepository;


    @Override
    public ReleaseModule createReleaseModule(ReleaseModuleDto releaseModuleDto) {
        ReleaseModule releaseModule=new ReleaseModule();
        validateForeignKey(releaseModule, releaseModuleDto);
        return releaseModuleRepository.save(releaseModule);
    }

    @Override
    public List<ReleaseModuleDto> getAllReleaseModule(Pageable pageable) {
        Page<ReleaseModule> releaseModulesPage = releaseModuleRepository.findAll(pageable);

        if (releaseModulesPage.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.NO_RECORDS_FOUND);
        }

        return releaseModulesPage.getContent().stream()
                .map(releaseModule -> {
                    ReleaseModuleDto releaseModuleDto=new ReleaseModuleDto();
                    BeanUtils.copyProperties(releaseModule, releaseModuleDto);
                    setRelatedEntitiesDto(releaseModule, releaseModuleDto);
                    return releaseModuleDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ReleaseModule updateReleaseModule(Long id, ReleaseModuleDto releaseModuleDto) {
        ReleaseModule releaseModule = releaseModuleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ValidationMessages.INVALID_ID));

        BeanUtils.copyProperties(releaseModuleDto, releaseModule, "id");
        validateForeignKey(releaseModule, releaseModuleDto);
        return releaseModuleRepository.save(releaseModule);
    }

    @Override
    public boolean deleteReleaseModule(Long id) {
        if (releaseModuleRepository.existsById(id)) {
            releaseModuleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validateForeignKey(ReleaseModule releaseModule, ReleaseModuleDto releaseModuleDto) {
        releaseModule.setRelease(releaseRepository.findById(releaseModuleDto.getReleaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Release Id " + releaseModuleDto.getReleaseId() + " not found")));
        releaseModule.setModule(moduleRepository.findById(releaseModuleDto.getModuleId())
                .orElseThrow(() -> new ResourceNotFoundException("Module Id " + releaseModuleDto.getModuleId() + " not found")));
    }

    private void setRelatedEntitiesDto(ReleaseModule releaseModule, ReleaseModuleDto releaseModuleDto) {
        releaseModuleDto.setReleaseId(releaseModule.getRelease().getId());
        releaseModuleDto.setModuleId(releaseModule.getModule().getId());
    }


}
