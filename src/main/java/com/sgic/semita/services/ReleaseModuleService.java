package com.sgic.semita.services;

import com.sgic.semita.dtos.ReleaseModuleDto;
import com.sgic.semita.dtos.UserDto;
import com.sgic.semita.entities.ReleaseModule;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReleaseModuleService {
    ReleaseModule createReleaseModule(ReleaseModuleDto releaseModuleDto);
    List<ReleaseModuleDto> getAllReleaseModule(Pageable pageable);
    ReleaseModule updateReleaseModule(Long id, ReleaseModuleDto releaseModuleDto);
    boolean deleteReleaseModule(Long id);

}
