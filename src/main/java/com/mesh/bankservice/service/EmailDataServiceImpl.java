package com.mesh.bankservice.service;

import java.util.Optional;

import static com.mesh.bankservice.exception.BankServiceError.EMAIL_ALREADY_EXISTS;
import static com.mesh.bankservice.exception.BankServiceError.EMAIL_NOT_BELONGS_USER;

import com.mesh.bankservice.exception.BankServiceError;
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
    public void add(String email, Long userId) {
        Optional<EmailData> optionalEmailData = emailDataRepository.findByEmail(email);
        if (optionalEmailData.isPresent()) {
            throw new BankServiceException(EMAIL_ALREADY_EXISTS, email);
        }
        User user = userRepository.findById(userId).orElseThrow();
        EmailData emailDataForCreate = EmailData.builder()
            .email(email)
            .user(user)
            .build();
        emailDataRepository.save(emailDataForCreate);
    }

    @Override
    public void update(String currentEmail, String newEmail, Long userId) {
        Optional<EmailData> currentOptionalEmailData = emailDataRepository.findByEmailAndUserId(currentEmail, userId);
        if (currentOptionalEmailData.isEmpty()) {
            throw new BankServiceException(EMAIL_NOT_BELONGS_USER, currentEmail, String.valueOf(userId));
        }
        Optional<EmailData> optionalEmailData = emailDataRepository.findByEmail(newEmail);
        if (optionalEmailData.isPresent()) {
            throw new BankServiceException(EMAIL_ALREADY_EXISTS, newEmail);
        }
        EmailData currentEmailData = currentOptionalEmailData.get();
        currentEmailData.setEmail(newEmail);
        emailDataRepository.save(currentEmailData);
    }

    @Override
    public void delete(String email, Long userId) {
        Optional<EmailData> currentOptionalEmailData = emailDataRepository.findByEmailAndUserId(email, userId);
        if (currentOptionalEmailData.isEmpty()) {
            throw new BankServiceException(EMAIL_NOT_BELONGS_USER, email, String.valueOf(userId));
        }
        Optional<EmailData> optionalEmailData = emailDataRepository.findByEmail(email);
        if (optionalEmailData.isEmpty()) {
            throw new BankServiceException(EMAIL_ALREADY_EXISTS, email);
        }
        emailDataRepository.delete(optionalEmailData.get());
    }
}
