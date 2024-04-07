package com.mesh.bankservice.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EmailDataDto {
    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
}
