package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNameUpdateRequest {
    private String email;
    private String name;

    public boolean isEmpty() {
        return email.isEmpty() || name.isEmpty();
    }
}
