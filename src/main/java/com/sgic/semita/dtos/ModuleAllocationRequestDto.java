package com.sgic.semita.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModuleAllocationRequestDto {
    private Long projectAllocationId;
    private List<Long> subModuleIds;
}
