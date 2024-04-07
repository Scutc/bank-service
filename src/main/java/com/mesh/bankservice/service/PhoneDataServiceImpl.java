package com.mesh.bankservice.service;

import java.util.Optional;

import com.mesh.bankservice.exception.BankServiceException;
import com.mesh.bankservice.repository.PhoneDataRepository;
import com.mesh.bankservice.repository.UserRepository;
import com.mesh.bankservice.repository.enity.PhoneDataEntity;
import com.mesh.bankservice.repository.enity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mesh.bankservice.exception.BankServiceError.PHONE_ALREADY_EXISTS;
import static com.mesh.bankservice.exception.BankServiceError.PHONE_NOT_BELONGS_TO_USER;

@Service
@RequiredArgsConstructor
public class PhoneDataServiceImpl implements PhoneDataService {
    private final PhoneDataRepository phoneDataRepository;
    private final UserRepository userRepository;

    @Override
    public void add(String phone, Long userId) {
        Optional<PhoneDataEntity> optionalPhoneData = phoneDataRepository.findByPhone(phone);
        if (optionalPhoneData.isPresent()) {
            throw new BankServiceException(PHONE_ALREADY_EXISTS, phone);
        }
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        PhoneDataEntity phoneDataForCreate = PhoneDataEntity.builder()
            .phone(phone)
            .user(userEntity)
            .build();
        phoneDataRepository.save(phoneDataForCreate);
    }

    @Override
    public void update(String currentPhone, String newPhone, Long userId) {
        Optional<PhoneDataEntity> currentOptionalPhoneData = phoneDataRepository.findByPhoneAndUserId(currentPhone, userId);
        if (currentOptionalPhoneData.isEmpty()) {
            throw new BankServiceException(PHONE_NOT_BELONGS_TO_USER, currentPhone, String.valueOf(userId));
        }
        Optional<PhoneDataEntity> optionalPhoneData = phoneDataRepository.findByPhone(newPhone);
        if (optionalPhoneData.isPresent()) {
            throw new BankServiceException(PHONE_ALREADY_EXISTS, newPhone);
        }
        PhoneDataEntity currentPhoneDataEntity = currentOptionalPhoneData.get();
        currentPhoneDataEntity.setPhone(newPhone);
        phoneDataRepository.save(currentPhoneDataEntity);
    }

    @Override
    public void delete(String phone, Long userId) {
        Optional<PhoneDataEntity> currentOptionalPhoneData = phoneDataRepository.findByPhoneAndUserId(phone, userId);
        if (currentOptionalPhoneData.isEmpty()) {
            throw new BankServiceException(PHONE_NOT_BELONGS_TO_USER, phone, String.valueOf(userId));
        }
        Optional<PhoneDataEntity> optionalPhoneData = phoneDataRepository.findByPhone(phone);
        if (optionalPhoneData.isEmpty()) {
            throw new BankServiceException(PHONE_ALREADY_EXISTS, phone);
        }
        phoneDataRepository.delete(optionalPhoneData.get());
    }
}
