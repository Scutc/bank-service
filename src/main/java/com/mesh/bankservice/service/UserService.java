package com.mesh.bankservice.service;

import java.util.Optional;

import com.mesh.bankservice.model.User;

public interface UserService {
    Optional<User> findByEmail(String email);
}
