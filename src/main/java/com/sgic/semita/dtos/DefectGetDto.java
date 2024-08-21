package com.sgic.semita.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefectGetDto {

    private String formattedDefectId;
    private String stepToReCreate;
    private String comments;
    private String defectTypeName;
    private String defectStatusColorCode;
    private String defectStatusName;
    private String priorityColorCode;
    private String priorityStatusName;
    private String severityColorCode;
    private String severityStatusName;
    private String releaseName;
    private String subModuleName;
    private String moduleName;
    private String assignToName;
    private String assignByName;

}
