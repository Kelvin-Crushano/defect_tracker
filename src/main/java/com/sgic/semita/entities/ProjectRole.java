package com.sgic.semita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class ProjectRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(unique = true)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    @JsonIgnore
    @OneToMany(mappedBy = "projectRole")
    private List<ProjectAllocations> projectAllocations;

}
