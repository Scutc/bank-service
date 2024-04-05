package com.mesh.bankservice.service;

import java.util.Optional;

import com.mesh.bankservice.model.User;
import com.mesh.bankservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        System.out.println();
        return userRepository.findByEmail(email);
    }
}
