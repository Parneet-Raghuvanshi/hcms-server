package com.developer.hcmsserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class CommonDto implements Serializable {
    private Long id;
    private String publicId;
    private String age;
    private String contact;
    private String height;
    private String weight;
    private String spo2;
    private String bp;
    private String glucose;
}
