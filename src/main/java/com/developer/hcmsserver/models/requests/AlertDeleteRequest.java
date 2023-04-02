package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertDeleteRequest {
    private String email;
    private String publicId;

    public boolean isEmpty() {
        return email.isEmpty() || publicId.isEmpty();
    }
}
