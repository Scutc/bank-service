package com.mesh.bankservice.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BankServiceExceptionHandler {
    @ExceptionHandler(BankServiceException.class)
    ResponseEntity<ErrorInfo> handleBankServiceException(BankServiceException exception) {
        HttpStatus responseStatus = exception.getError().getHttpStatus();
        ErrorInfo errorInfo = new ErrorInfo(exception.getMessage(), exception.getError().getCode());
        return new ResponseEntity<>(errorInfo, responseStatus);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ErrorInfo> handleBadCredentialException(Exception exception) {
        HttpStatus responseStatus = HttpStatus.FORBIDDEN;
        ErrorInfo errorInfo = new ErrorInfo(exception.getMessage(), null);
        return new ResponseEntity<>(errorInfo, responseStatus);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorInfo> handleBaseException(Exception exception) {
        HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorInfo errorInfo = new ErrorInfo(exception.getMessage(), null);
        return new ResponseEntity<>(errorInfo, responseStatus);
    }
}
