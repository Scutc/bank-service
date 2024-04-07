package com.mesh.bankservice.model.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransferDto {
    private BigDecimal value;
}
