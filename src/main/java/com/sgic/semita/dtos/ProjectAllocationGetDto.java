package com.sgic.semita.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjectAllocationGetDto {
    private Long userId;
    private String userName;
    private int contributions;
    private String projectRoleName;
}
