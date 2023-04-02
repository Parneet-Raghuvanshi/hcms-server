package com.developer.hcmsserver.exceptions.classes;

public class UserServiceException {
    public static final String[] MAIL_NOT_SENT = {"MAIL_NOT_SENT",
            "Some error occurred while sending email, please try again later."};
    public static final String[] RECORD_ALREADY_EXIST = {"RECORD_ALREADY_EXIST",
            "User already found with this email, please try with different email."};
    public static final String[] REQUIRED_FIELD_EMPTY = {"REQUIRED_FIELD_EMPTY",
            "Some of the Required field is empty, please make sure to fill every required detail."};
    public static final String[] WRONG_PASSWORD = {"WRONG_PASSWORD",
            "Please enter correct password."};
    public static final String[] USER_NOT_FOUND = {"USER_NOT_FOUND",
            "User with this email doesn't exists."};
    public static final String[] EMAIL_NOT_VERIFIED = {"EMAIL_NOT_VERIFIED",
            "Please verify your email before login."};
    public static final String[] UNKNOWN_EXCEPTION = {"UNKNOWN_EXCEPTION",
            "Something went wrong, please try again later."};
    public static final String[] RECORD_NOT_FOUND = {"RECORD_NOT_FOUND",
            "This record is not available in database."};
}
