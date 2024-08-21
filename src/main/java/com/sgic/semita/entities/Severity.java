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
public class Severity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String colorCode;
    @Column(nullable = true)
    private int value;

    @JsonIgnore
    @OneToMany(mappedBy = "severity")
    private Set<Defect> defects = new HashSet<>();
}
