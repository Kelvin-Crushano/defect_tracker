package com.sgic.semita.services;

import com.sgic.semita.dtos.DensityRangeDto;
import com.sgic.semita.entities.DensityRange;

import java.util.List;

public interface DensityRangeService {
    //    List<DensityRange> createDensityRanges(List<DensityRangeDto> densityRangeDtos);
    List<DensityRange> getDensityRangeByProjectId(Long id);

    List<DensityRange> createOrUpdateDensityRanges(Long projectId, List<DensityRangeDto> densityRangeDto);


}