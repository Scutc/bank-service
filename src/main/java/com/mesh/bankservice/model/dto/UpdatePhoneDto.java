package com.mesh.bankservice.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UpdatePhoneDto {
    @NotBlank
    private String newPhone;
}
