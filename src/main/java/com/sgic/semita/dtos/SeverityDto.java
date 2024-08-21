package com.sgic.semita.dtos;


import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeverityDto {
    private Long id;
    @Pattern(regexp = "^(Low|Medium|High)$", message = "Please input Value = Low or Medium or High")
    @NotEmpty(message = ValidationMessages.NAME)
    private String name;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Colour code must be in the format #RRGGBB")
    @NotEmpty(message = ValidationMessages.COLOR_CODE)
    private String colorCode;


}
