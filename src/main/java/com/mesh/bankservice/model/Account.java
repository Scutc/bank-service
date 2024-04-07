package com.mesh.bankservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "account", schema = "bank_service")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
    @SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @Column(name = "initial_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal initialBalance;

    @Column(name = "max_balance", nullable = false)
    private Boolean maxBalance = false;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;
}
