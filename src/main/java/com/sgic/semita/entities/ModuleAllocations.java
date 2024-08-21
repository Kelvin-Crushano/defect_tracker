package com.sgic.semita.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class ModuleAllocations extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_allocation_id")
    private ProjectAllocations projectAllocations;

    @ManyToOne
    @JoinColumn(name = "sub_module_id")
    private SubModule subModule;

}
