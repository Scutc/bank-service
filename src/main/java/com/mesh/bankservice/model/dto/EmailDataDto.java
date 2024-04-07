package com.mesh.bankservice.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EmailDataDto {
    @Email
    @NotBlank
    private String email;
}
