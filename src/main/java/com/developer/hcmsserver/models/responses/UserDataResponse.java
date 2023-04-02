package com.developer.hcmsserver.models.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDataResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
