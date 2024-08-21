package com.sgic.semita.dtos;

import com.sgic.semita.utils.Utills;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailConfigDTO {
    private Long id;

    @Min(value = 1, message = "Port must be between 1 and 65535")
    @Max(value = 65535, message = "Port must be between 1 and 65535")
    @Positive(message = "Port must be a positive number")
    @NotNull(message = "Port cannot be empty")
    private int port;

    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Host cannot be empty")
    @Pattern(regexp = "^[a-zA-Z.]+$", message = "Host must contain only letters and dots")
    private String host;


    private boolean status;

    @NotEmpty(message = "Protocol cannot be empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "protocol must contain only letters")
    private String protocol;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;







    // Setter with validation
    public void setPort(String port) {
        if (Utills.isValidNumber(port)) {
            Integer portNumber = Utills.parseNumber(port);

            // Validate the length of the port number (e.g., at least 4 digits)
            if (Utills.isValidNumberLength(portNumber, 4)) {
                this.port = portNumber;
            } else {
                throw new IllegalArgumentException("Port must be at least 4 digits long.");
            }
        } else {
            throw new IllegalArgumentException("Invalid port format. Port must be a valid number.");
        }
    }

    public void setstatus(String status) {
        if (Utills.isValidBoolean(status)) {
            this.status = Utills.parseBoolean(status);
        } else {
            throw new IllegalArgumentException("Invalid boolean format. Must be 'true', 'false', '0', or '1'.");
        }
    }
}
