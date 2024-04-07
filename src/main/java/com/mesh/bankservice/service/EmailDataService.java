package com.mesh.bankservice.service;

public interface EmailDataService {
    void addEmail(String email, Long userId);

    void updateEmail(String currentEmail, String newEmail, Long userId);
}
