package com.mesh.bankservice.repository;

import java.util.Optional;

import com.mesh.bankservice.model.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {
    Optional<PhoneData> findByPhone(String phone);

    Optional<PhoneData> findByPhoneAndUserId(String phone, Long user_id);
}
