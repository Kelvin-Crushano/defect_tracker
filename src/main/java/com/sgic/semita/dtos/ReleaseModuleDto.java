package com.sgic.semita.dtos;

import com.sgic.semita.entities.Module;
import com.sgic.semita.entities.Release;
import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReleaseModuleDto {

    private Long id;

    @NotNull(message = "ModuleId "+ ValidationMessages.NOT_EMPTY)
    private Long moduleId;

    @NotNull(message = "ReleaseId "+ ValidationMessages.NOT_EMPTY)
    private Long releaseId;
}
