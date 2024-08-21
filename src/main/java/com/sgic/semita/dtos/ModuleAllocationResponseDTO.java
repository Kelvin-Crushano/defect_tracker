package com.sgic.semita.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModuleAllocationResponseDTO {
    private Long userId;
    private String userName;
    private List<SubModuleDto> allocatedSubModules;

}
