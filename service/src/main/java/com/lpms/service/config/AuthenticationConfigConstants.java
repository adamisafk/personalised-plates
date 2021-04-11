package com.lpms.service.config;

// Class with constants which needs to be referenced multiple times. Saves having to repeat myself.
public class AuthenticationConfigConstants {
    public static final String SECRET = "Greatest_Web_Service_Of_All_Time";
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/customer/register";
}
