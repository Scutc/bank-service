package com.mesh.bankservice.service;

import java.math.BigDecimal;

import static com.mesh.bankservice.exception.BankServiceError.BALANCE_IS_NOT_ENOUGH;
import static com.mesh.bankservice.exception.BankServiceError.BALANCE_IS_TOO_BIG;
import static com.mesh.bankservice.exception.BankServiceError.TRANSFER_VALUE_SHOULD_BE_POSITIVE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mesh.bankservice.exception.BankServiceException;
import com.mesh.bankservice.repository.AccountRepository;
import com.mesh.bankservice.repository.enity.AccountEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountEntityServiceImplTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void successTransferMoney() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal transferAmount = new BigDecimal("100");

        AccountEntity fromAccount = AccountEntity.builder()
            .balance(new BigDecimal(1000))
            .initialBalance(new BigDecimal(1000))
            .maxBalance(false)
            .build();

        AccountEntity toAccount = AccountEntity.builder()
            .balance(new BigDecimal(1000))
            .initialBalance(new BigDecimal(1000))
            .maxBalance(false)
            .build();

        when(accountRepository.findByUserId(fromUserId)).thenReturn(fromAccount);
        when(accountRepository.findByUserId(toUserId)).thenReturn(toAccount);


        accountService.transferMoney(fromUserId, toUserId, transferAmount);

        assertThat(fromAccount.getBalance()).isEqualByComparingTo(new BigDecimal("900"));
        assertThat(toAccount.getBalance()).isEqualByComparingTo(new BigDecimal("1100"));
        assertThat(fromAccount.getMaxBalance()).isFalse();
        assertThat(toAccount.getMaxBalance()).isFalse();

        verify(accountRepository).save(fromAccount);
        verify(accountRepository).save(toAccount);
    }

    @Test
    public void transferMoneyWithNegativeAmount() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal amount = new BigDecimal("-100");

        assertThatThrownBy(() -> accountService.transferMoney(fromUserId, toUserId, amount))
            .isInstanceOf(BankServiceException.class)
            .hasMessageContaining(TRANSFER_VALUE_SHOULD_BE_POSITIVE.getDescription());
    }

    @Test
    public void transferMoneyWithNotEnoughBalance() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal transferAmount = new BigDecimal("1000");

        AccountEntity fromAccount = new AccountEntity();
        fromAccount.setBalance(new BigDecimal("500")); // Недостаточно средств

        when(accountRepository.findByUserId(fromUserId)).thenReturn(fromAccount);

        assertThatThrownBy(() -> accountService.transferMoney(fromUserId, toUserId, transferAmount))
            .isInstanceOf(BankServiceException.class)
            .hasMessageContaining(BALANCE_IS_NOT_ENOUGH.getDescription());
    }

    @Test
    public void transferMoneyExceedsMaxAllowedBalance() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        BigDecimal transferAmount = new BigDecimal("100");

        AccountEntity fromAccount = new AccountEntity();
        AccountEntity toAccount = new AccountEntity();
        fromAccount.setBalance(new BigDecimal("1000"));
        toAccount.setBalance(new BigDecimal("2000")); // Текущий баланс получателя
        toAccount.setInitialBalance(new BigDecimal("1000")); // Начальный баланс получателя

        when(accountRepository.findByUserId(fromUserId)).thenReturn(fromAccount);
        when(accountRepository.findByUserId(toUserId)).thenReturn(toAccount);

        assertThatThrownBy(() -> accountService.transferMoney(fromUserId, toUserId, transferAmount))
            .isInstanceOf(BankServiceException.class)
            .hasMessageContaining(BALANCE_IS_TOO_BIG.getDescription());
    }
}
