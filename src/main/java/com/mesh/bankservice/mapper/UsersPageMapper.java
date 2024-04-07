package com.mesh.bankservice.mapper;

import java.util.List;

import com.mesh.bankservice.model.UsersPage;
import com.mesh.bankservice.repository.enity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UsersPageMapper {
    @Mapping(target = "users", source = "userEntities")
    UsersPage fromUserEntityPage(List<UserEntity> userEntities, int pageNumber, int pageSize, int pageTotal);
}