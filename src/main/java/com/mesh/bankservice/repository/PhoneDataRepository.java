package com.mesh.bankservice.repository;

import java.util.Optional;

import com.mesh.bankservice.repository.enity.PhoneDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneDataRepository extends JpaRepository<PhoneDataEntity, Long> {
    Optional<PhoneDataEntity> findByPhone(String phone);

    Optional<PhoneDataEntity> findByPhoneAndUserId(String phone, Long userId);
}
