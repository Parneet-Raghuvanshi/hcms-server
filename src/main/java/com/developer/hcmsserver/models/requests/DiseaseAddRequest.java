package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiseaseAddRequest {
    private String name;
    private String description;
    private String img;
    private String severity;

    public boolean isEmpty() {
        return name.isEmpty() || description.isEmpty() || img.isEmpty() || severity.isEmpty();
    }
}
