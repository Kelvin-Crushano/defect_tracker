package com.sgic.semita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Defect extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stepToReCreate;
//    @Lob
   private byte[] attachment;
    private String comments;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "defect_type_id")
    private DefectType defectType;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "defect_status_id")
    private DefectStatus defectStatus;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "severity_id")
    private Severity severity;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "release_id")
    private Release release;

    @ManyToOne
    @JoinColumn(name = "sub_module_id")
    private SubModule subModule;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "module_id")
    private Module module;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "assign_to")
    private ProjectAllocations assignTo;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "assign_by")
    private ProjectAllocations assignBy;

}
