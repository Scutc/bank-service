package com.mesh.bankservice.service;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

import com.mesh.bankservice.model.Account;
import com.mesh.bankservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    @Scheduled(initialDelayString = "${accountProcessing.initialDelay}", fixedDelayString = "${accountProcessing.fixedDelay}")
    @Transactional
    public void increaseBalances() {
        List<Account> accounts = accountRepository.findByMaxBalanceFalseWithLock();
        for (Account account: accounts) {
            BigDecimal maxAllowedBalance = account.getInitialBalance().multiply(new BigDecimal("2.07"));
            BigDecimal increaseRate = new BigDecimal("1.10");
            BigDecimal increasedBalance = account.getBalance().multiply(increaseRate);

            if (increasedBalance.compareTo(maxAllowedBalance) > 0) {
                account.setBalance(maxAllowedBalance);
                account.setMaxBalance(true);
            } else {
                account.setBalance(increasedBalance);
            }

            accountRepository.save(account);
        }
    }
}
