package com.mesh.bankservice.service;

public interface PhoneDataService {
    void add(String phone, Long userId);

    void update(String currentPhone, String newPhone, Long userId);

    void delete(String phone, Long userId);
}
