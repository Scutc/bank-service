package com.mesh.bankservice.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class UsersDto {
    private List<UserDto> pageContent;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer pageTotal;
}
