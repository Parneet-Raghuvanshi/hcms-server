package com.developer.hcmsserver.dto;

import com.developer.hcmsserver.entity.PatientEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DiseaseDto {
    private Long id;
    private String publicId;
    private String name;
    private String description;
    private String img;
    private String severity;
    private List<PatientEntity> patients;
}
