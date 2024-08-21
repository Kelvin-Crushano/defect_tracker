package com.sgic.semita.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectAllocationDetailDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private int contributions;
    private Long userId;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String projectRoleName;
    private int availability;
}
