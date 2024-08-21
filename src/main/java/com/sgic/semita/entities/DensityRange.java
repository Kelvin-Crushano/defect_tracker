package com.sgic.semita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sgic.semita.enums.DensityLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DensityRange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DensityLevel densityLevel;
    private int start;
    private int end;
    private String colorCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}

