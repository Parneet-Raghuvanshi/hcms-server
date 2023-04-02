package com.developer.hcmsserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserDto implements Serializable {
    private Long id;
    private String publicId;
    private String name;
    private String email;
    private String role;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;
}
