package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertAddRequest {
    private String email;
    private String name;
    private String description;
    private String level;

    public boolean isEmpty() {
        return email.isEmpty() || name.isEmpty() || description.isEmpty() || level.isEmpty();
    }
}
