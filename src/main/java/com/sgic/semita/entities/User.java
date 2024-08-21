package com.sgic.semita.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User extends DateAudit {
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<EmailRecipients> emailRecipients = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ProjectAllocations> projectAllocations = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles = new ArrayList<>();

}
