package com.developer.hcmsserver.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class GeneralResponse {
    private Boolean hasError;
    private String code;
    private String message;
    private Object data;
}
