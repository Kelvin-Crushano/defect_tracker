package com.sgic.semita.repositories;

import com.sgic.semita.entities.EmailEndPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailEndPointsRepository extends JpaRepository<EmailEndPoints, Long> {

}

