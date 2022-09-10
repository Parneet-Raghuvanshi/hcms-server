package com.developer.hcmsserver.models.responses;

import lombok.Getter;
import lombok.Setter;

/**
 * Simple POJO for User Data Model Response
 * */

@Getter
@Setter
public class UserDataResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
