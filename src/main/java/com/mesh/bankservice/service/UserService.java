package com.mesh.bankservice.service;

import com.mesh.bankservice.model.User;

public interface UserService {
    User getByEmail(String email);
}
