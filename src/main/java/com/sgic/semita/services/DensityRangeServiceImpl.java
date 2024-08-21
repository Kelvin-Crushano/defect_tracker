package com.sgic.semita.services;


import com.sgic.semita.dtos.DensityRangeDto;
import com.sgic.semita.entities.DensityRange;
import com.sgic.semita.entities.Project;
import com.sgic.semita.enums.DensityLevel;
import com.sgic.semita.repositories.DensityRangeRepository;
import com.sgic.semita.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DensityRangeServiceImpl implements DensityRangeService {


    private static final List<DensityLevel> VALID_LEVELS = Arrays.asList(DensityLevel.values());
    @Autowired
    private DensityRangeRepository densityRangeRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<DensityRange> getDensityRangeByProjectId(Long projectId) {
        return densityRangeRepository.findAllByProjectId(projectId);
    }

    @Override
    @Transactional
    public List<DensityRange> createOrUpdateDensityRanges(Long projectId, List<DensityRangeDto> densityRangeDtoList) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        List<DensityRange> updatedDensityRanges = densityRangeDtoList.stream().map(dto -> {
            DensityRange densityRange;
            if (dto.getId() != null) {
                densityRange = densityRangeRepository.findById(dto.getId()).orElse(new DensityRange());
            } else {
                densityRange = new DensityRange();
            }
            BeanUtils.copyProperties(dto, densityRange);
            densityRange.setProject(project);
            return densityRange;
        }).collect(Collectors.toList());

        return densityRangeRepository.saveAll(updatedDensityRanges);
    }
}
