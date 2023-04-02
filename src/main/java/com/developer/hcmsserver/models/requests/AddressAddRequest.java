package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class AddressAddRequest {
    private String city;
    private String country;
    private String streetName;
    private String postalCode;

    public boolean isEmpty() {
        return city.isEmpty() || country.isEmpty() || streetName.isEmpty() || postalCode.isEmpty();
    }
}
