package com.github.haseoo.courier.security;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Constants {
    public static final String RESPONSE_WITH_UNAUTH_ERROR = "Responding with unauthorized error. Message - {}";
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_TOKEN_BEGIN = "Bearer ";
    public static final String homeUrl = "http://localhost:2137/home";

    public static String[] getUnprotectedEndpoints() {
        return new String[]{"/swagger-ui.html",
                "/api/parcelType/offer",
                "/h2-console/**",
                "/api/login",
                "/api/client/individual/register",
                "/api/client/company/register",
                "/offer",
                "/api/parcel/get/**",
                "/api/parcel/**/moveDate",
                "/login"};
    }
}
