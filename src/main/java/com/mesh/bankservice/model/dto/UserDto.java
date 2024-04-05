package com.mesh.bankservice.model.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private Date dateOfBirth;
    private List<EmailDataDto> emails;
    private List<PhoneDataDto> phones;
}
