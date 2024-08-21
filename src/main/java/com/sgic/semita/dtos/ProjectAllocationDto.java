package com.sgic.semita.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjectAllocationDto {
    private Long id;
    private int contributions;
    private Long userId;
    private Long projectId;
    private Long projectRoleId;
}
