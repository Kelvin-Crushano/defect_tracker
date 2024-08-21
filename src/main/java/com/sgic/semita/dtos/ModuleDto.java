package com.sgic.semita.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
public class ModuleDto {
    private Long id;

    @NotBlank(message = "Name must not be null")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name can contain only A-Z or a-z characters")
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long projectId;
}
