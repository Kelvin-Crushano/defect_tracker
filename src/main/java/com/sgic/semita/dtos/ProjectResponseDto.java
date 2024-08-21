package com.sgic.semita.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String prefix;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private int KLOC;
    private String clientName;
    private String address;
    private String contactName;
    private String mobileNo;
    private String email;
    private String projectStatusName;
    private String projectTypeName;


}

