package com.mesh.bankservice.service;

import java.util.Optional;

import static com.mesh.bankservice.exception.BankServiceError.EMAIL_ALREADY_EXISTS;
import static com.mesh.bankservice.exception.BankServiceError.EMAIL_NOT_BELONGS_USER;

import com.mesh.bankservice.exception.BankServiceException;
import com.mesh.bankservice.repository.EmailDataRepository;
import com.mesh.bankservice.repository.UserRepository;
import com.mesh.bankservice.repository.enity.EmailDataEntity;
import com.mesh.bankservice.repository.enity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailDataServiceImpl implements EmailDataService {
    private final EmailDataRepository emailDataRepository;
    private final UserRepository userRepository;

    @Override
    public void add(String email, Long userId) {
        Optional<EmailDataEntity> optionalEmailData = emailDataRepository.findByEmail(email);
        if (optionalEmailData.isPresent()) {
            throw new BankServiceException(EMAIL_ALREADY_EXISTS, email);
        }
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        EmailDataEntity emailDataEntityForCreate = EmailDataEntity.builder()
            .email(email)
            .user(userEntity)
            .build();
        emailDataRepository.save(emailDataEntityForCreate);
    }

    @Override
    public void update(String currentEmail, String newEmail, Long userId) {
        Optional<EmailDataEntity> currentOptionalEmailDataEntity = emailDataRepository.findByEmailAndUserId(currentEmail, userId);
        if (currentOptionalEmailDataEntity.isEmpty()) {
            throw new BankServiceException(EMAIL_NOT_BELONGS_USER, currentEmail, String.valueOf(userId));
        }
        Optional<EmailDataEntity> optionalEmailDataEntity = emailDataRepository.findByEmail(newEmail);
        if (optionalEmailDataEntity.isPresent()) {
            throw new BankServiceException(EMAIL_ALREADY_EXISTS, newEmail);
        }
        EmailDataEntity currentEmailDataEntity = currentOptionalEmailDataEntity.get();
        currentEmailDataEntity.setEmail(newEmail);
        emailDataRepository.save(currentEmailDataEntity);
    }

    @Override
    public void delete(String email, Long userId) {
        Optional<EmailDataEntity> currentOptionalEmailDataEntity = emailDataRepository.findByEmailAndUserId(email, userId);
        if (currentOptionalEmailDataEntity.isEmpty()) {
            throw new BankServiceException(EMAIL_NOT_BELONGS_USER, email, String.valueOf(userId));
        }
        Optional<EmailDataEntity> optionalEmailDataEntity = emailDataRepository.findByEmail(email);
        if (optionalEmailDataEntity.isEmpty()) {
            throw new BankServiceException(EMAIL_ALREADY_EXISTS, email);
        }
        emailDataRepository.delete(optionalEmailDataEntity.get());
    }
}
