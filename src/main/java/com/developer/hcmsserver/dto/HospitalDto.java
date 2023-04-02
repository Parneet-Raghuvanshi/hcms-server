package com.developer.hcmsserver.dto;

import com.developer.hcmsserver.entity.AddressEntity;
import com.developer.hcmsserver.entity.DoctorEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class HospitalDto {
    private Long id;
    private String publicId;
    private String name;
    private String rating;
    private AddressDto address;
    private List<DoctorEntity> doctors;
}
