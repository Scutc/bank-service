package com.mesh.bankservice.model;

import java.util.List;

import lombok.Data;

@Data
public class UsersPage {
    private List<User> users;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer pageTotal;
}
