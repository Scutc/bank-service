package com.mesh.bankservice.exception;

import org.springframework.http.HttpStatus;

public interface Error {

    String getCode();

    HttpStatus getHttpStatus();

    String getDescription();
}
