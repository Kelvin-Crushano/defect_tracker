package com.sgic.semita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class DefectStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String colorCode;

    @JsonIgnore
    @OneToMany(mappedBy = "defectStatus")
    private Set<Defect> defects = new HashSet<>();

    @OneToMany(mappedBy = "fromStatus")
    private Set<StatusWorkflow> statusWorkflowsFrom = new HashSet<>();

    @OneToMany(mappedBy = "toStatus")
    private Set<StatusWorkflow> statusWorkflowsTo = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
