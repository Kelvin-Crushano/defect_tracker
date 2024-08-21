package com.sgic.semita.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EmailConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // Ensure email is unique
    private String email;
    private String host;
    private int port;
    private boolean status;
    private String protocol;
    private String password;
}
