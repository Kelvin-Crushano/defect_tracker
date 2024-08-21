package com.sgic.semita.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRoleDto {
    private Long id;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Role name must contain only letters")
    private String name;
    @NotNull
    private Long projectId;
}
