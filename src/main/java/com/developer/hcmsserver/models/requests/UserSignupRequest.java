package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequest {
    private String name;
    private String email;
    private String password;
    private String role;

    public boolean isEmpty() {
        return name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty();
    }
}
