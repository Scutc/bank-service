package com.mesh.bankservice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.mesh.bankservice.model.User;
import com.mesh.bankservice.repository.enity.EmailDataEntity;
import com.mesh.bankservice.repository.enity.PhoneDataEntity;
import com.mesh.bankservice.repository.enity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
        @Mapping(source = "userEntity.emails", target = "emails", qualifiedByName = "emailListToStringList"),
        @Mapping(source = "userEntity.phones", target = "phones", qualifiedByName = "phoneListToStringList"),
        @Mapping(source = "userEntity", target = "login", qualifiedByName = "extractLoginEmail")
    })
    User fromUserEntity(UserEntity userEntity);

    @Named("emailListToStringList")
    default List<String> emailListToStringList(List<EmailDataEntity> emails) {
        if (emails == null) {
            return null;
        }
        return emails.stream()
            .map(EmailDataEntity::getEmail)
            .collect(Collectors.toList());
    }

    @Named("phoneListToStringList")
    default List<String> phoneListToStringList(List<PhoneDataEntity> phones) {
        if (phones == null) {
            return null;
        }
        return phones.stream()
            .map(PhoneDataEntity::getPhone)
            .collect(Collectors.toList());
    }

    @Named("extractLoginEmail")
    default String extractLoginEmail(UserEntity userEntity) {
        return userEntity.getEmails().stream()
            .filter(EmailDataEntity::isLoginEmail)
            .findFirst()
            .map(EmailDataEntity::getEmail)
            .orElseThrow(() -> new IllegalStateException("No login email found"));
    }
}
