package com.mesh.bankservice.service;

import java.time.LocalDate;
import java.util.Optional;

import com.mesh.bankservice.model.User;
import com.mesh.bankservice.repository.UserRepository;
import com.mesh.bankservice.repository.UserSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<User> findUsersByParams(String phoneNumber, String email, String name, LocalDate dateOfBirth, Integer pageSize,
                                        Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAll(UserSpecifications.findByParams(phoneNumber, email, name, dateOfBirth), pageable);
    }
}
