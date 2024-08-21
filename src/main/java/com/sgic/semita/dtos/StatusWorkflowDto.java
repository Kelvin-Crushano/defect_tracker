package com.sgic.semita.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusWorkflowDto {

    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long projectId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long fromStatusId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long toStatusId;
    private String transitionName;
    private String fromStatusName;
    private String toStatusName;
}
