package com.mesh.bankservice.service;

import static com.mesh.bankservice.exception.BankServiceError.USER_NOT_FOUND;

import com.mesh.bankservice.exception.CommonException;
import com.mesh.bankservice.model.User;
import com.mesh.bankservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CommonException(USER_NOT_FOUND);
        }
        return user;
    }
}
