package com.mesh.bankservice.service;

import java.math.BigDecimal;

public interface AccountService {
    void increaseBalances();

    void transferMoney(Long userId, Long toUserId, BigDecimal amount);
}
