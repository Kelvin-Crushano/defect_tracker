package com.sgic.semita.dtos;

import com.sgic.semita.enums.ReleaseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class ReleaseDto {
    private Long id;

    @NotBlank(message = "Release name must not be null or blank")
    @Pattern(regexp = "^[A-Za-z0-9_\\- .]+$",
            message = "Name can only contain letters, numbers, spaces, underscores, hyphens, and periods")
    private String name;

    @NotNull(message = "Release type must not be null")
    private ReleaseType type;

    @NotNull(message = "Release date must not be null")
    private LocalDate date;

    @NotBlank(message = "Release status must not be null or blank")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name can only contain letters and spaces")
    private String status;

    @NotNull(message = "Patch status must not be null")
    private Boolean patch;

    @NotNull(message = "Project ID must not be null")
    private Long projectId;

    @NotBlank(message = "Application version must not be null or blank")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)*$",
            message = "Application version must follow the format X.X.X")
    private String applicationVersion;
}
