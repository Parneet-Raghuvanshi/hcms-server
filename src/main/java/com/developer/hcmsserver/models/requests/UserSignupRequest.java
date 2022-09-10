package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

/**
 * Simple POJO for User Signup Request
 * */

@Getter
@Setter
public class UserSignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

    public boolean isEmpty() {
        return firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty();
    }
}
