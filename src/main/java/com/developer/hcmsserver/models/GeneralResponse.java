package com.developer.hcmsserver.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * The General and Unique response for every request
 * Contains - status, hasError, message, data;
 * */

@Getter
@Setter
@AllArgsConstructor
public class GeneralResponse {
    private Boolean hasError;
    private String code;
    private String message;
    private Object data;

    // Response for Successful Result
    public GeneralResponse(String message,Object data) {
        this.hasError = false;
        this.code = "SUCCESS";
        this.message = message;
        this.data = data;
    }

    // Response for Exception
    public GeneralResponse(String exception,String message) {
        this.hasError = true;
        this.code = exception;
        this.message = message;
        this.data = null;
    }
}
