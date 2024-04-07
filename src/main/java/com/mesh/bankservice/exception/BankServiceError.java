package com.mesh.bankservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Getter
@RequiredArgsConstructor
public enum BankServiceError implements Error {
    USER_NOT_FOUND("ERROR-100", HttpStatus.NOT_FOUND, "User not found"),
    EMAIL_ALREADY_EXISTS("ERROR-101", BAD_REQUEST, "Email {%s} already exits"),
    EMAIL_NOT_BELONGS_USER("ERROR-102", FORBIDDEN, "Email {%s} not belongs user id {%s}"),
    EMAIL_IS_NOT_EXISTING("ERROR-102", BAD_REQUEST, "Email {%s} is not existing"),
    PHONE_ALREADY_EXISTS("ERROR-103", BAD_REQUEST, "Phone {%s} already exits"),
    PHONE_NOT_BELONGS_TO_USER("ERROR-104", FORBIDDEN, "Phone {%s} not belongs to user id {%s}"),
    PHONE_IS_NOT_EXISTING("ERROR-105", BAD_REQUEST, "Phone {%s} is not existing"),
    TRANSFER_VALUE_SHOULD_BE_POSITIVE("ERROR-106", BAD_REQUEST, "Transfer value should be positive"),
    BALANCE_IS_NOT_ENOUGH("ERROR-107", BAD_REQUEST, "Not enough funds on the balance"),
    BALANCE_IS_TOO_BIG("ERROR-108", BAD_REQUEST, "Balance after transfer is too big");

    private final String code;
    private final HttpStatus httpStatus;
    private final String description;
}
