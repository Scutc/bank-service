package com.mesh.bankservice.service;

import java.time.LocalDate;
import java.util.Optional;

import com.mesh.bankservice.mapper.UserMapper;
import com.mesh.bankservice.mapper.UsersPageMapper;
import com.mesh.bankservice.model.User;
import com.mesh.bankservice.model.UsersPage;
import com.mesh.bankservice.repository.EmailDataRepository;
import com.mesh.bankservice.repository.UserRepository;
import com.mesh.bankservice.repository.UserSpecification;
import com.mesh.bankservice.repository.enity.EmailDataEntity;
import com.mesh.bankservice.repository.enity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableCaching
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailDataRepository emailDataRepository;
    private final UsersPageMapper usersPageMapper;
    private final UserMapper userMapper;

    @Override
    public User findByEmail(String email) {
        Optional<EmailDataEntity> emailDataEntity = emailDataRepository.findByEmail(email);
        if (emailDataEntity.isPresent()) {
            UserEntity userEntity = emailDataEntity.get().getUser();
            return userMapper.fromUserEntity(userEntity);
        }
        return null;
    }

    @Override
    @Cacheable(value = "users", key = "{#phoneNumber, #email, #name, #dateOfBirth, #pageSize, #pageNumber}")
    public UsersPage findByParams(String phoneNumber, String email, String name, LocalDate dateOfBirth, Integer pageSize,
                                  Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<UserEntity> userEntityPage = userRepository.findAll(UserSpecification.build(phoneNumber, email, name, dateOfBirth), pageable);
        UsersPage usersPage =
            usersPageMapper.fromUserEntityPage(userEntityPage.getContent(), userEntityPage.getNumber(), userEntityPage.getSize(),
                userEntityPage.getTotalPages());
        return usersPage;
    }
}
