package com.mesh.bankservice.controller.mapper;

import com.mesh.bankservice.model.UsersPage;
import com.mesh.bankservice.model.dto.UsersDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(target = "pageContent", source = "users")
    UsersDto fromUsersPage(UsersPage usersPage);
}
