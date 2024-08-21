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
public class SubModule extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

   
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "module_id")
    private Module module;

    @JsonIgnore
    @OneToMany(mappedBy = "subModule",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Defect> defects = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "subModule",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ModuleAllocations> moduleAllocations = new HashSet<>();
}
