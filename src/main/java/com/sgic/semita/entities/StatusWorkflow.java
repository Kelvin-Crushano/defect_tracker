package com.sgic.semita.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "from_defect_status_id", "to_defect_status_id"}))
public class StatusWorkflow extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_defect_status_id")
    private DefectStatus fromStatus;

    @ManyToOne
    @JoinColumn(name = "to_defect_status_id")
    private DefectStatus toStatus;

    private String transitionName;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    // Getters and Setters
}
