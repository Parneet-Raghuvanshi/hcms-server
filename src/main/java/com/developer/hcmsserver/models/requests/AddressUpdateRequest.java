package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressUpdateRequest {
    private String email;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;

    public boolean isEmpty() {
        return email.isEmpty() || city.isEmpty() || country.isEmpty() || streetName.isEmpty() || postalCode.isEmpty();
    }
}
