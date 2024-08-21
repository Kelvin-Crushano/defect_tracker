package com.sgic.semita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Project extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_status_id")
    private ProjectStatus projectStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_type_id")
    private ProjectType projectType;

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private List<ProjectAllocations> projectAllocations = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<Module> modules = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<Release> releases = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<DensityRange> densityRanges = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<StatusWorkflow> statusWorkflows = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private List<ProjectRole> projectRoles = new ArrayList<>();
}
