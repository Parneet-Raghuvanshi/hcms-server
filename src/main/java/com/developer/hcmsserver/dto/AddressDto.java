package com.developer.hcmsserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressDto {
    private Long id;
    private String publicId;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
}
