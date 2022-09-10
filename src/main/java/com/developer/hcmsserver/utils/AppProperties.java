package com.developer.hcmsserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Class for importing application.properties (Private Variables Only)
 * FOR SOME REALLY IMPORTANT SECRETS
 * and storing them to Variables.
 * */

@Component
public class AppProperties {

    @Autowired
    private Environment env;

    public String getTokenSecret() {
        return env.getProperty("TOKEN_SECRET");
    }
}
