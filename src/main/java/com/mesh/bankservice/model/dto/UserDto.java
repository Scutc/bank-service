package com.mesh.bankservice.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private List<String> emails;
    private List<String> phones;
}
