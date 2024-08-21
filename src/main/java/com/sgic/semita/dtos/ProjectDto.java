package com.sgic.semita.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class ProjectDto {

    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotEmpty(message = ValidationMessages.INVALID_NAME)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotEmpty(message = ValidationMessages.INVALID_PREFIX)
    private String prefix;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message = ValidationMessages.START_DATE_NOT_NULL)
    @FutureOrPresent(message = ValidationMessages.INVALID_START_DATE)
    private LocalDate startDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message = ValidationMessages.END_DATE_NOT_NULL)
    @FutureOrPresent(message = ValidationMessages.INVALID_END_DATE)
    private LocalDate endDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotEmpty(message = "Description isn't Empty")
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(message = ValidationMessages.INVALID_KLOC)
    private int KLOC;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotEmpty(message = ValidationMessages.INVALID_CLIENT_NAME)
    private String clientName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotEmpty(message = "Description isn't Empty")
    private String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotEmpty(message = ValidationMessages.INVALID_CONTACT_NAME)
    private String contactName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Pattern(regexp = "^[0-9]+$", message = ValidationMessages.INVALID_MOBILE_NO)
    private String mobileNo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Email(message = ValidationMessages.INVALID_EMAIL)
    @NotEmpty(message = ValidationMessages.INVALID_EMAIL)
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message = ValidationMessages.PROJECT_STATUS_NOT_NULL)
    private Long projectStatusId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message = ValidationMessages.PROJECT_TYPE_NOT_NULL)
    private Long projectTypeId;


}
