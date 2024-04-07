package com.mesh.bankservice.controller.mapper;

import java.util.List;

import com.mesh.bankservice.model.User;
import com.mesh.bankservice.model.dto.UsersDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PhoneDataDtoMapper.class, EmailDataDtoMapper.class})
public interface UserDtoMapper {
    @Mapping(target = "pageContent", source = "users")
    UsersDto fromUsersPage(int pageNumber, int pageSize, int pageTotal, List<User> users);
}
