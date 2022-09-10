package com.developer.hcmsserver.utils;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864000000;// 10 days (in Milliseconds)
    public static final long PASSWORD_EXPIRATION_TIME = 3600000;// 1 hour (in Milliseconds)
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGNUP_URL = "/user/signup";
    public static final String LOGIN_URL = "/user/login";
    public static final String VERIFICATION_EMAIL_URL = "/user/email-verification";
    public static final String PASSWORD_RESET_URL = "/user/password-reset-request";
    public static final String PASSWORD_URL = "/user/password-reset";
    public static final String AUTHORITIES_KEY = "roles";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext
                .getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
