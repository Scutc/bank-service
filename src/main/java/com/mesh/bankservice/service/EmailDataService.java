package com.mesh.bankservice.service;

public interface EmailDataService {
    void add(String email, Long userId);

    void update(String currentEmail, String newEmail, Long userId);

    void delete(String email, Long userId);
}
