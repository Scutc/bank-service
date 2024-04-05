package com.mesh.bankservice.exception;

import org.springframework.http.HttpStatus;

public interface CommonError {

    String getCode();

    HttpStatus getHttpStatus();

    String getDescription();
}
