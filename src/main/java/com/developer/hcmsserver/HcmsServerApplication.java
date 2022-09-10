package com.developer.hcmsserver;

import com.developer.hcmsserver.utils.AppProperties;
import com.developer.hcmsserver.utils.SpringApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Main Application Class
 * By-Default
 * */

@SpringBootApplication
public class HcmsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HcmsServerApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    @Bean(name="AppProperties")
    public AppProperties getAppProperties() {
        return new AppProperties();
    }
}
