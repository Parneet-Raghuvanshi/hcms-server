package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonUpdateMedicalRequest {
    private String email;
    private String spo2;
    private String bp;
    private String glucose;

    public boolean isEmpty() {
        return email.isEmpty() || spo2.isEmpty() || bp.isEmpty() || glucose.isEmpty();
    }
}
