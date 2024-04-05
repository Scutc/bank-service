package com.mesh.bankservice.exception;

import lombok.Getter;

@Getter
public class BankServiceException extends RuntimeException {
    private final Error error;
    private final String[] params;

    public BankServiceException(Error error, String... params) {
        super(error.getCode());
        this.error = error;
        this.params = params;
    }

    public BankServiceException(Error error, Throwable cause, String... params) {
        super(error.getCode(), cause);
        this.error = error;
        this.params = params;
    }

    @Override
    public String getMessage() {
        return error.getCode() + " " + String.format(error.getDescription(), (Object[]) params);
    }
}
