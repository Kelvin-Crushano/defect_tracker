package com.sgic.semita.repositories;

import com.sgic.semita.entities.EmailConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailConfigurationRepository extends JpaRepository<EmailConfiguration, Long> {
    long countByStatus(boolean status);

}
