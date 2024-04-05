package com.mesh.bankservice.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final CommonError error;
    private final String[] params;

    public CommonException(CommonError error, String... params) {
        super(error.getCode());
        this.error = error;
        this.params = params;
    }

    public CommonException(CommonError error, Throwable cause, String... params) {
        super(error.getCode(), cause);
        this.error = error;
        this.params = params;
    }

    @Override
    public String getMessage() {
        return error.getCode() + " " + String.format(error.getDescription(), (Object[]) params);
    }
}
