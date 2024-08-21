package com.sgic.semita.dtos;

import lombok.Getter;
import lombok.Setter;
import com.sgic.semita.entities.*;
import com.sgic.semita.entities.Module;
import com.sgic.semita.utils.ValidationMessages;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
// @Setter
// @Getter
// public class DefectDto {
//     private Long id;
//     private String stepToReCreate;
//     private byte[] attachment;
//     private String comments;

//     private Long defectTypeId;
//     private Long defectStatusId;
//     private Long priorityId;
//     private Long severityId;
//     private Long releaseId;
//     private Long subModuleId;
//     private Long moduleId;
//     private Long assignToId;
//     private Long assignById;


@Getter
@Setter
public class DefectDto {

    private Long id;
    @NotEmpty(message = "step To ReCreate "+ValidationMessages.NOT_EMPTY)
    private String stepToReCreate;
    //private byte[] attachment;
    private String comments;
    @NotNull(message = "Defect Type Id "+ValidationMessages.NOT_EMPTY)
    private Long defect_type_id;
    @NotNull(message = "Defect Status Id "+ValidationMessages.NOT_EMPTY)
    private Long defect_status_id;
    @NotNull(message = "Priority Id "+ValidationMessages.NOT_EMPTY)
    private Long priority_id;
    @NotNull(message = "Severity Id "+ValidationMessages.NOT_EMPTY)
    private Long severity_id;
    @NotNull(message = "Release Id "+ValidationMessages.NOT_EMPTY)
    private Long release_id;
    @NotNull(message = "Sub Module Id "+ValidationMessages.NOT_EMPTY)
    private Long sub_module_id;
    @NotNull(message = "Module Id "+ValidationMessages.NOT_EMPTY)
    private Long module_id;
    @NotNull(message = "Assign to Id "+ValidationMessages.NOT_EMPTY)
    private Long assign_to;
    @NotNull(message = "Assign By Id "+ValidationMessages.NOT_EMPTY)
    private Long assign_by;

}
