package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralPublicIdRequest {
    private String publicId;

    public boolean isEmpty() {
        return publicId.isEmpty();
    }
}
