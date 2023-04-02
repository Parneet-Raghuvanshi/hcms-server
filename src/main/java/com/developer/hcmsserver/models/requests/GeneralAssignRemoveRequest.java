package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralAssignRemoveRequest {
    private String email;
    private String publicId;

    public boolean isEmpty() {
        return email.isEmpty() || publicId.isEmpty();
    }
}
