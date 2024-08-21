package com.sgic.semita.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class ProjectStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String colorCode;

    @OneToMany(mappedBy = "projectStatus")
    private Set<Project> projects = new HashSet<>();
}
