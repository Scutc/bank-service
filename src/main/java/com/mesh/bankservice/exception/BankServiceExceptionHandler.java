package com.mesh.bankservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorInfo> handleBaseException(Exception exception) {
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        ErrorInfo errorInfo = new ErrorInfo(exception.getMessage(), null);
        return new ResponseEntity<>(errorInfo, responseStatus);
    }
}
