package com.sgic.semita.dtos;

import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter

public class ProjectStatusDto {

    private long id;

    @NotEmpty(message = ValidationMessages.NAME_REQUEST)
    @Pattern(regexp = "^[a-zA-Z]+$", message = ValidationMessages.MISMATCH_INPUT)
    private String name;

    @NotEmpty(message = ValidationMessages.COLOR_CODE_NOT_EMPTY)
    @Pattern(regexp = "^#(?:[0-9a-fA-F]{3}){1,2}$", message = ValidationMessages.MISMATCH_INPUT)
    private String colorCode;


}
