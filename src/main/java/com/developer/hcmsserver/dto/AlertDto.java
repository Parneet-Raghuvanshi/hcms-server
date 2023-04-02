package com.developer.hcmsserver.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlertDto {
    private Long id;
    private String publicId;
    private String name;
    private String level;
    private String description;
}
