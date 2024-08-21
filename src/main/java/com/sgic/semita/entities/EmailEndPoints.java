package com.sgic.semita.entities;

import com.sgic.semita.utils.Utills;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity

public class EmailEndPoints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private boolean enabled;

    @OneToMany(mappedBy = "emailEndPoints", cascade = CascadeType.ALL)
    private List<EmailRecipients> emailRecipients = new ArrayList<>();
}
