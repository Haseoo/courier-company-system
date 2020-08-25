package com.github.haseoo.courier.testutlis.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UsersConstants {
    public static final String TEST_USER_NAME = "Tuser";
    public static final String TEST_USER_PASSWD = "passwd";
    public static final String TEST_PESEL = "79120476785";
    public static final String TEST_PHONE_NUMBER = "111222333";
    public static final String TEST_NAME = "Test";
    public static final String TEST_SURNAME = "Testovsky";
    public static final String TEST_EMAIL = "test@test.com";
    public static final String TEST_COMPANY_NAME = "TestInc";
    public static final String TEST_NIP = "3818383577";
    public static final String INVALID_PESEL_FORMAT = "7120476785";
    public static final String INVALID_PESEL = "19120476785";
}
