package com.sgic.semita.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SubModuleDto {
    private Long id;
    @NotBlank(message = "Name must not be null")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name can contain only A-Z or a-z characters")
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long moduleId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long projectId;
}
