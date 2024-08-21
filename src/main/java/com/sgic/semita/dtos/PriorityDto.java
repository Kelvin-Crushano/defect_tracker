package com.sgic.semita.dtos;

import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PriorityDto {
    private Long id;

    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "Colour code must be in the format #RRGGBB")
    @NotEmpty(message = ValidationMessages.COLOR_CODE_NOT_EMPTY)
    private String colorCode;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name Contains only letters. Not insert other characters")
    @NotEmpty(message = ValidationMessages.NAME_NOT_EMPTY)
    private String name;
}
