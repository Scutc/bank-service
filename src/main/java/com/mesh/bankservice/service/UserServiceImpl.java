package com.mesh.bankservice.service;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Optional;

import com.mesh.bankservice.model.EmailData;
import com.mesh.bankservice.model.User;
import com.mesh.bankservice.repository.EmailDataRepository;
import com.mesh.bankservice.repository.UserRepository;
import com.mesh.bankservice.repository.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailDataRepository emailDataRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return emailDataRepository.findByEmail(email)
            .map(EmailData::getUser);
    }

    @Override
    public Page<User> findByParams(String phoneNumber, String email, String name, LocalDate dateOfBirth, Integer pageSize,
                                   Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAll(UserSpecification.build(phoneNumber, email, name, dateOfBirth), pageable);
    }
}
