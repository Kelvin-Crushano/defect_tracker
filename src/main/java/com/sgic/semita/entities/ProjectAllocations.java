package com.sgic.semita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "project_allocations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"project_id", "project_role_id", "user_id"})
})
public class ProjectAllocations extends DateAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Contributions cannot be null")
    private int contributions;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


    @ManyToOne
    @JoinColumn(name = "project_role_id")
    @NotNull(message = "Project_Role cannot be null")
    private ProjectRole projectRole;

    @JsonIgnore
    @OneToMany(mappedBy = "assignTo")
    private List<Defect> assignedToDefects = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "assignBy")
    private List<Defect> assignedByDefects = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "projectAllocations")
    private List<ModuleAllocations> moduleAllocations = new ArrayList<>();
}
