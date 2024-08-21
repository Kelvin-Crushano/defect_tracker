package com.sgic.semita.dtos;

import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;

    @NotEmpty(message = ValidationMessages.NAME_NOT_EMPTY)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters")
    private String name;

    @Email(message = ValidationMessages.INVALID_EMAIL)
    @NotEmpty(message = ValidationMessages.EMAIL_NOT_EMPTY)
    private String email;
}
