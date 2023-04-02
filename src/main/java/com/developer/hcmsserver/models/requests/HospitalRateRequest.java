package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalRateRequest {
    private String publicId;
    private String rating;

    public boolean isEmpty() {
        return publicId.isEmpty() || rating.isEmpty();
    }
}
