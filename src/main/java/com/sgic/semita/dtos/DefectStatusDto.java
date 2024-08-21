package com.sgic.semita.dtos;

import com.sgic.semita.entities.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.List;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefectStatusDto {

    private Long id;

    @NotBlank(message = "Status name must not be null")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name can only contain letters and spaces")
    private String name;

    @NotBlank(message = "Color code must not be null")
    @Pattern(regexp = "^#(?:[0-9a-fA-F]{3}){1,2}$", message = "Color code must be a valid string")
    private String colorCode;

    @NotNull(message = "Role ID must not be null")
    private Long roleId;

}
