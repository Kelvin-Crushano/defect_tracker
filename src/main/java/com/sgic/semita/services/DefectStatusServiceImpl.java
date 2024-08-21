package com.sgic.semita.services;

import com.sgic.semita.dtos.DefectStatusDto;
import com.sgic.semita.entities.DefectStatus;
import com.sgic.semita.entities.Role;
import com.sgic.semita.repositories.DefectStatusRepository;
import com.sgic.semita.repositories.RoleRepository;
import com.sgic.semita.utils.ValidationMessages;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefectStatusServiceImpl implements DefectStatusService {

    @Autowired
    private DefectStatusRepository defectStatusRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public DefectStatus createDefectStatus(DefectStatusDto defectStatusDto) {

        DefectStatus defectStatus = new DefectStatus();
//        defectStatus.setName(defectStatusDto.getName());
        BeanUtils.copyProperties(defectStatusDto, defectStatus, "id");
        Role role = roleRepository.findById(defectStatusDto.getRoleId())
                .orElseThrow(() -> new RuntimeException(ValidationMessages.ROLE_NOT_FOUND + ": " + defectStatusDto.getRoleId()));
        defectStatus.setRole(role);

        return defectStatusRepository.save(defectStatus);
    }

    @Override
    public List<DefectStatusDto> getAllDefectStatuses(Pageable pageable) {
        Page<DefectStatus> defectStatusPage = defectStatusRepository.findAll(pageable);
        return defectStatusPage.getContent().stream()
                .map(ds -> new DefectStatusDto(ds.getId(), ds.getName(), ds.getColorCode(), ds.getRole().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public DefectStatus updateDefectStatus(Long id, DefectStatusDto defectStatusDto) {
        DefectStatus defectStatus = defectStatusRepository.findById(id).orElseThrow(()-> new RuntimeException(ValidationMessages.DEFECT_STATUS_NOT_FOUND + ": " + id));
        BeanUtils.copyProperties(defectStatusDto, defectStatus,"id");
        Role role = roleRepository.findById(defectStatusDto.getRoleId())
                .orElseThrow(() -> new RuntimeException(ValidationMessages.ROLE_NOT_FOUND + ": " + defectStatusDto.getRoleId()));
        defectStatus.setRole(role);

        return defectStatusRepository.save(defectStatus);

    }

    @Override
    public boolean deleteDefectStatus(Long id) {
        Optional<DefectStatus> optionalDefectStatus = defectStatusRepository.findById(id);
        if (optionalDefectStatus.isPresent()) {
            defectStatusRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DefectStatusDto getDefectStatusById(Long id) {
        DefectStatus defectStatus = defectStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.DEFECT_STATUS_NOT_FOUND + ": " + id));
        DefectStatusDto defectStatusDto = new DefectStatusDto();
        BeanUtils.copyProperties(defectStatus, defectStatusDto);
        return defectStatusDto;
    }
}

