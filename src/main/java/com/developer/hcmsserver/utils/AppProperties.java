package com.developer.hcmsserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Autowired
    private Environment env;

    public String getProductionUrl() {
        return env.getProperty("PROD_URL");
    }

    public String getTokenSecret() {
        return env.getProperty("TOKEN_SECRET");
    }
}
