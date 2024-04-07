package com.mesh.bankservice.service;

import java.time.LocalDate;
import java.util.Optional;

import com.mesh.bankservice.model.User;
import com.mesh.bankservice.model.UsersPage;
import org.springframework.data.domain.Page;

public interface UserService {
    User findByEmail(String email);

    UsersPage findByParams(String phoneNumber, String email, String name, LocalDate dateOfBirth,
                           Integer pageSize, Integer pageNumber);
}
