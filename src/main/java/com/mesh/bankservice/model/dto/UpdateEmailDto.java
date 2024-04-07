package com.mesh.bankservice.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UpdateEmailDto {
    @NotBlank
    private String newEmail;
}
