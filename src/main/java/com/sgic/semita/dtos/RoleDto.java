
package com.sgic.semita.dtos;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Role name must be between 2 and 10 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Role name must contain only alphabetic characters")
    @NotEmpty(message = "Role name cannot be empty")
    @Column(unique = true)
    private String name;

}
