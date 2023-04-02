package com.developer.hcmsserver.dto;

import com.developer.hcmsserver.entity.AddressEntity;
import com.developer.hcmsserver.entity.CommonEntity;
import com.developer.hcmsserver.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DoctorDto {
    private Long id;
    private String publicId;
    private UserEntity user;
    private CommonEntity common;
    private AddressEntity address;
}
