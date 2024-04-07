package com.mesh.bankservice.repository;

import java.util.List;

import com.mesh.bankservice.repository.enity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    @Query(value = "SELECT * FROM bank_service.account WHERE max_balance = FALSE FOR UPDATE SKIP LOCKED", nativeQuery = true)
    List<AccountEntity> findByMaxBalanceFalseWithLock();

    AccountEntity findByUserId(@Param("userId") Long userId);
}
