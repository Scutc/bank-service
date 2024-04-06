package com.mesh.bankservice.service;

import java.util.Optional;

import static com.mesh.bankservice.exception.BankServiceError.EMAIL_ALREADY_EXISTS;

import com.mesh.bankservice.exception.BankServiceException;
import com.mesh.bankservice.model.EmailData;
import com.mesh.bankservice.model.User;
import com.mesh.bankservice.repository.EmailDataRepository;
import com.mesh.bankservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailDataServiceImpl implements EmailDataService {
    private final EmailDataRepository emailDataRepository;
    private final UserRepository userRepository;

    @Override
    public void addEmail(String email, Long userId) {
        Optional<EmailData> emailData = emailDataRepository.findByEmail(email);
        if (emailData.isPresent()) {
            throw new BankServiceException(EMAIL_ALREADY_EXISTS, email);
        }
        User user = userRepository.findById(userId).orElseThrow();
        EmailData emailDataForCreate = EmailData.builder()
            .email(email)
            .user(user)
            .build();
        emailDataRepository.save(emailDataForCreate);
    }
}
