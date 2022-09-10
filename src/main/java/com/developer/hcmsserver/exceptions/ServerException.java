package com.developer.hcmsserver.exceptions;

import org.springframework.http.HttpStatus;

public class ServerException extends RuntimeException {

    private final String exception;
    private final HttpStatus status;

    public ServerException(String[] exception,HttpStatus status) {
        super(exception[1]);
        this.exception = exception[0];
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
