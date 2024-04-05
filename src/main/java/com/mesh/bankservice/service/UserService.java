package com.mesh.bankservice.service;

import java.time.LocalDate;
import java.util.Optional;

import com.mesh.bankservice.model.User;
import org.springframework.data.domain.Page;

public interface UserService {
    Optional<User> findByEmail(String email);

    Page<User> findUsersByParams(String phoneNumber, String email, String name, LocalDate dateOfBirth,
                                 Integer pageSize, Integer pageNumber);
}
