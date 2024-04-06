package com.mesh.bankservice.controller.mapper;

import javax.validation.constraints.Min;

import java.util.List;

import com.mesh.bankservice.model.User;
import com.mesh.bankservice.model.dto.UserDto;
import com.mesh.bankservice.model.dto.UsersDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PhoneDataDtoMapper.class, EmailDataDtoMapper.class})
public interface UserDtoMapper {
    @Mapping(target = "pageContent", source = "users")
    UsersDto fromPageUser(List<User> users, int pageTotal, int pageSize, int pageNumber);

    UserDto fromUser(User user);
}
