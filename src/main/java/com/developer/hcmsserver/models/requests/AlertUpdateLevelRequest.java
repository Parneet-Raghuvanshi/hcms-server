package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertUpdateLevelRequest {
    private String publicId;
    private String level;

    public boolean isEmpty() {
        return publicId.isEmpty() || level.isEmpty();
    }
}

