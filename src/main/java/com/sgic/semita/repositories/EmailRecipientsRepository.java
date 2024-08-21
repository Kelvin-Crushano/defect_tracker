package com.sgic.semita.repositories;

import com.sgic.semita.entities.EmailEndPoints;
import com.sgic.semita.entities.EmailRecipients;
import com.sgic.semita.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRecipientsRepository extends JpaRepository<EmailRecipients, Long> {
    boolean existsByEmailEndPointsAndUser(EmailEndPoints emailEndPoints, User user);
    //  List<EmailRecipients> findByEmailEndPointsId(Long emailEndpointId);
    List<EmailRecipients> findByemailEndPointsId(Long emailEndpointId);
    //  Page<EmailRecipients> findByEmailEndPointsId(Long emailEndpointId, Pageable pageable);
    Page<EmailRecipients> findByEmailEndPointsId(Long emailEndpointId, Pageable pageable);
}
