package com.sgic.semita.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "release_module", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"release_id", "module_id"})
})
public class ReleaseModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Module_id")
    private Module module;

    @ManyToOne
    @JoinColumn(name = "Release_id")
    private Release release;
}