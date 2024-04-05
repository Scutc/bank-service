package com.mesh.bankservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BankServiceError implements Error {
    USER_NOT_FOUND("ERROR-100", HttpStatus.NOT_FOUND, "User not found");

    private final String code;
    private final HttpStatus httpStatus;
    private final String description;
}
