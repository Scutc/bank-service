package com.mesh.bankservice.service;

import java.math.BigDecimal;
import java.util.List;

import static com.mesh.bankservice.exception.BankServiceError.BALANCE_IS_NOT_ENOUGH;
import static com.mesh.bankservice.exception.BankServiceError.BALANCE_IS_TOO_BIG;
import static com.mesh.bankservice.exception.BankServiceError.TRANSFER_VALUE_SHOULD_BE_POSITIVE;
import static java.math.BigDecimal.ZERO;

import com.mesh.bankservice.exception.BankServiceException;
import com.mesh.bankservice.repository.AccountRepository;
import com.mesh.bankservice.repository.enity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final String MAX_BALANCE_RATE = "2.07";
    private static final String BALANCE_INCREASE_RATE = "1.10";

    private final AccountRepository accountRepository;

    @Override
    @Scheduled(initialDelayString = "${accountProcessing.initialDelay}", fixedDelayString = "${accountProcessing.fixedDelay}")
    @Transactional
    public void increaseBalances() {
        List<AccountEntity> accountEntities = accountRepository.findByMaxBalanceFalseWithLock();
        for (AccountEntity accountEntity : accountEntities) {
            BigDecimal maxAllowedBalance = accountEntity.getInitialBalance().multiply(new BigDecimal(MAX_BALANCE_RATE));
            BigDecimal increaseRate = new BigDecimal(BALANCE_INCREASE_RATE);
            BigDecimal increasedBalance = accountEntity.getBalance().multiply(increaseRate);
            if (increasedBalance.compareTo(maxAllowedBalance) > 0) {
                accountEntity.setBalance(maxAllowedBalance);
                accountEntity.setMaxBalance(true);
            } else {
                accountEntity.setBalance(increasedBalance);
            }
            accountRepository.save(accountEntity);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void transferMoney(Long userId, Long toUserId, BigDecimal amount) {
        if (amount.compareTo(ZERO) <= 0) {
            throw new BankServiceException(TRANSFER_VALUE_SHOULD_BE_POSITIVE);
        }
        AccountEntity accountFrom = accountRepository.findByUserId(userId);
        AccountEntity accountTo = accountRepository.findByUserId(toUserId);

        BigDecimal newBalanceFrom = accountFrom.getBalance().subtract(amount);
        if (newBalanceFrom.compareTo(ZERO) < 0) {
            throw new BankServiceException(BALANCE_IS_NOT_ENOUGH);
        }
        accountFrom.setMaxBalance(false);
        accountFrom.setBalance(newBalanceFrom);

        BigDecimal maxAllowedBalanceTo = accountTo.getInitialBalance().multiply(new BigDecimal(MAX_BALANCE_RATE));
        BigDecimal newBalanceTo = accountTo.getBalance().add(amount);
        if (newBalanceTo.compareTo(maxAllowedBalanceTo) > 0) {
            throw new BankServiceException(BALANCE_IS_TOO_BIG);
        }
        accountTo.setMaxBalance(newBalanceTo.compareTo(maxAllowedBalanceTo) == 0);
        accountTo.setBalance(newBalanceTo);

        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
    }
}
