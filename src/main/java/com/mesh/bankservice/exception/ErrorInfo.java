package com.mesh.bankservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorInfo {
    private String errorMessage;
    private String errorCode;
}
