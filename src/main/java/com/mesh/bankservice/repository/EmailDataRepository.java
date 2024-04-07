package com.mesh.bankservice.repository;

import java.util.Optional;

import com.mesh.bankservice.model.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDataRepository extends JpaRepository<EmailData, Long> {
    Optional<EmailData> findByEmail(String email);

    Optional<EmailData> findByEmailAndUserId(String email, Long user_id);
}
