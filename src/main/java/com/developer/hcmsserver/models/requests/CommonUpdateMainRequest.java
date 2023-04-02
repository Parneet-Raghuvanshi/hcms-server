package com.developer.hcmsserver.models.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonUpdateMainRequest {
    private String email;
    private String age;
    private String contact;
    private String height;
    private String weight;

    public boolean isEmpty() {
        return email.isEmpty() || age.isEmpty() || contact.isEmpty() || height.isEmpty() || weight.isEmpty();
    }
}
