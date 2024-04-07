package com.mesh.bankservice.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.mesh.bankservice.repository.UserRepository;
import com.mesh.bankservice.repository.enity.AccountEntity;
import com.mesh.bankservice.repository.enity.EmailDataEntity;
import com.mesh.bankservice.repository.enity.PhoneDataEntity;
import com.mesh.bankservice.repository.enity.UserEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
public class UserEntityControllerTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
        "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
//        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        UserEntity user1 = UserEntity.builder()
            .name("TestUser1")
            .dateOfBirth(LocalDate.of(1980, 3, 10))
            .password("password1")
            .build();

        EmailDataEntity email1User1 = EmailDataEntity.builder()
            .email("testuser1@example.com")
            .userEntity(user1)
            .build();

        PhoneDataEntity phone1User1 = PhoneDataEntity.builder()
            .phone("+12345678901")
            .userEntity(user1)
            .build();

        AccountEntity accountUser1 = AccountEntity.builder()
            .balance(new BigDecimal("10000.00"))
            .initialBalance(new BigDecimal("10000.00"))
            .maxBalance(false)
            .userEntity(user1)
            .build();

        user1.setEmails(List.of(email1User1));
        user1.setPhones(List.of(phone1User1));
        user1.setAccountEntity(accountUser1);

        UserEntity user2 = UserEntity.builder()
            .name("TestUser2")
            .dateOfBirth(LocalDate.of(1985, 7, 30))
            .password("password2")
            .build();

        EmailDataEntity email1User2 = EmailDataEntity.builder()
            .email("testuser2@example.com")
            .userEntity(user2)
            .build();

        PhoneDataEntity phone1User2 = PhoneDataEntity.builder()
            .phone("+19876543210")
            .userEntity(user2)
            .build();

        AccountEntity accountUser2 = AccountEntity.builder()
            .balance(new BigDecimal("15000.00"))
            .initialBalance(new BigDecimal("15000.00"))
            .maxBalance(false)
            .userEntity(user2)
            .build();

        user2.setEmails(List.of(email1User2));
        user2.setPhones(List.of(phone1User2));
        user2.setAccountEntity(accountUser2);

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @AfterEach
    void clearData() {
        userRepository.deleteAll();
    }

    @Test
    public void getUsersTest() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                .param("pageSize", "10")
                .param("pageNumber", "0"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pageSize").value(10))
            .andExpect(jsonPath("$.pageNumber").value(0))
            .andExpect(jsonPath("$.pageTotal").value(1))
            .andExpect(jsonPath("$.pageContent[0].name").value("TestUser1"))
            .andExpect(jsonPath("$.pageContent[1].name").value("TestUser2"));
    }
}
