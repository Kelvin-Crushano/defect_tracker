package com.sgic.semita.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EmailRecipients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "email_endpoint_id")
    private EmailEndPoints emailEndPoints;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean status;
}
