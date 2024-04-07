package com.mesh.bankservice.service;

import java.util.Optional;

import static com.mesh.bankservice.exception.BankServiceError.EMAIL_ALREADY_EXISTS;
import static com.mesh.bankservice.exception.BankServiceError.EMAIL_NOT_BELONGS_USER;
import static com.mesh.bankservice.exception.BankServiceError.PHONE_ALREADY_EXISTS;
import static com.mesh.bankservice.exception.BankServiceError.PHONE_NOT_BELONGS_TO_USER;

import com.mesh.bankservice.exception.BankServiceException;
import com.mesh.bankservice.model.EmailData;
import com.mesh.bankservice.model.PhoneData;
import com.mesh.bankservice.model.User;
import com.mesh.bankservice.repository.PhoneDataRepository;
import com.mesh.bankservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneDataServiceImpl implements PhoneDataService {
    private final PhoneDataRepository phoneDataRepository;
    private final UserRepository userRepository;

    @Override
    public void add(String phone, Long userId) {
        Optional<PhoneData> optionalPhoneData = phoneDataRepository.findByPhone(phone);
        if (optionalPhoneData.isPresent()) {
            throw new BankServiceException(PHONE_ALREADY_EXISTS, phone);
        }
        User user = userRepository.findById(userId).orElseThrow();
        PhoneData phoneDataForCreate = PhoneData.builder()
            .phone(phone)
            .user(user)
            .build();
        phoneDataRepository.save(phoneDataForCreate);
    }

    @Override
    public void update(String currentPhone, String newPhone, Long userId) {
        Optional<PhoneData> currentOptionalPhoneData = phoneDataRepository.findByPhoneAndUserId(currentPhone, userId);
        if (currentOptionalPhoneData.isEmpty()) {
            throw new BankServiceException(PHONE_NOT_BELONGS_TO_USER, currentPhone, String.valueOf(userId));
        }
        Optional<PhoneData> optionalPhoneData = phoneDataRepository.findByPhone(newPhone);
        if (optionalPhoneData.isPresent()) {
            throw new BankServiceException(PHONE_ALREADY_EXISTS, newPhone);
        }
        PhoneData currentPhoneData = currentOptionalPhoneData.get();
        currentPhoneData.setPhone(newPhone);
        phoneDataRepository.save(currentPhoneData);
    }

    @Override
    public void delete(String phone, Long userId) {
        Optional<PhoneData> currentOptionalPhoneData = phoneDataRepository.findByPhoneAndUserId(phone, userId);
        if (currentOptionalPhoneData.isEmpty()) {
            throw new BankServiceException(PHONE_NOT_BELONGS_TO_USER, phone, String.valueOf(userId));
        }
        Optional<PhoneData> optionalPhoneData = phoneDataRepository.findByPhone(phone);
        if (optionalPhoneData.isEmpty()) {
            throw new BankServiceException(PHONE_ALREADY_EXISTS, phone);
        }
        phoneDataRepository.delete(optionalPhoneData.get());
    }
}
