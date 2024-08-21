package com.sgic.semita.repositories;

import com.sgic.semita.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {




    @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT er.user.id FROM EmailRecipients er WHERE er.emailEndPoints.id = :emailEndpointId)")
    List<User> findUsersNotAssociatedWithEmailEndpoint(@Param("emailEndpointId") Long emailEndpointId);

    Optional<User> findByEmail(String email);
}
