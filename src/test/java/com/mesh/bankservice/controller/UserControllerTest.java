package com.mesh.bankservice.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.mesh.bankservice.model.Account;
import com.mesh.bankservice.model.EmailData;
import com.mesh.bankservice.model.PhoneData;
import com.mesh.bankservice.model.User;
import com.mesh.bankservice.repository.UserRepository;
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
public class UserControllerTest {
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

        User user1 = User.builder()
            .name("TestUser1")
            .dateOfBirth(LocalDate.of(1980, 3, 10))
            .password("password1")
            .build();

        EmailData email1User1 = EmailData.builder()
            .email("testuser1@example.com")
            .user(user1)
            .build();

        PhoneData phone1User1 = PhoneData.builder()
            .phone("+12345678901")
            .user(user1)
            .build();

        Account accountUser1 = Account.builder()
            .balance(new BigDecimal("10000.00"))
            .initialBalance(new BigDecimal("10000.00"))
            .maxBalance(false)
            .user(user1)
            .build();

        user1.setEmails(List.of(email1User1));
        user1.setPhones(List.of(phone1User1));
        user1.setAccount(accountUser1);

        User user2 = User.builder()
            .name("TestUser2")
            .dateOfBirth(LocalDate.of(1985, 7, 30))
            .password("password2")
            .build();

        EmailData email1User2 = EmailData.builder()
            .email("testuser2@example.com")
            .user(user2)
            .build();

        PhoneData phone1User2 = PhoneData.builder()
            .phone("+19876543210")
            .user(user2)
            .build();

        Account accountUser2 = Account.builder()
            .balance(new BigDecimal("15000.00"))
            .initialBalance(new BigDecimal("15000.00"))
            .maxBalance(false)
            .user(user2)
            .build();

        user2.setEmails(List.of(email1User2));
        user2.setPhones(List.of(phone1User2));
        user2.setAccount(accountUser2);

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
            // Проверка наличия и данных первого пользователя
            .andExpect(jsonPath("$.pageContent[0].name").value("TestUser1"))
            // Проверка наличия и данных второго пользователя
            .andExpect(jsonPath("$.pageContent[1].name").value("TestUser2"));
    }
}
