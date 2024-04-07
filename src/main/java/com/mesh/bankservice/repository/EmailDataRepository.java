package com.mesh.bankservice.repository;

import java.util.Optional;

import com.mesh.bankservice.repository.enity.EmailDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDataRepository extends JpaRepository<EmailDataEntity, Long> {
    Optional<EmailDataEntity> findByEmail(String email);

    Optional<EmailDataEntity> findByEmailAndUserId(String email, Long userId);
}
