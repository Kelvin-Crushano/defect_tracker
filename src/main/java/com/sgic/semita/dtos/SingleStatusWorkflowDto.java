package com.sgic.semita.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SingleStatusWorkflowDto {
    private Long fromStatusId;
    private String fromStatusName;
    private String colorCode;
    private String role;
    private List<Map<String, Object>> toStatuses;
}
