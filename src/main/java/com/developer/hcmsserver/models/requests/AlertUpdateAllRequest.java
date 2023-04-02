package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertUpdateAllRequest {
    private String publicId;
    private String name;
    private String description;
    private String level;

    public boolean isEmpty() {
        return publicId.isEmpty() || name.isEmpty() || description.isEmpty() || level.isEmpty();
    }
}
