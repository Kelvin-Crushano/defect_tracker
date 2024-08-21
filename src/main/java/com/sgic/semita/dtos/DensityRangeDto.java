package com.sgic.semita.dtos;

import com.sgic.semita.enums.DensityLevel;
import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DensityRangeDto {
    private Long id;
    private DensityLevel densityLevel;
    private int start;
    private int end;
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Colour code must be in the format #RRGGBB")
    @NotEmpty(message = ValidationMessages.COLOUR_CODE)
    private String colorCode;
    private Long projectId;
}
