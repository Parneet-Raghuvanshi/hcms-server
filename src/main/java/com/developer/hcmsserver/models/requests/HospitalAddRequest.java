package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalAddRequest {
    private String name;
    private String rating;
    private AddressAddRequest address;

    public boolean isEmpty() {
        return name.isEmpty() || rating.isEmpty() || address.isEmpty();
    }
}
