package com.sgic.semita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sgic.semita.enums.ReleaseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "`release`", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "project_id", "application_version"})
})
public class Release extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ReleaseType type;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private boolean patch;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "application_version", nullable = false)
    private String applicationVersion;

    @JsonIgnore
    @OneToMany(mappedBy = "release",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReleaseModule> releaseModule = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "release", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Defect> defects = new HashSet<>();
}
