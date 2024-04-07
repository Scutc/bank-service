package com.mesh.bankservice.repository.enity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email_data", schema = "bank_service")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_id_seq")
    @SequenceGenerator(name = "email_id_seq", sequenceName = "email_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 200)
    private String email;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "is_login_email", nullable = false)
    private boolean isLoginEmail = false;
}
