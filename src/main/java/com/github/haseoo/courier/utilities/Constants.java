package com.github.haseoo.courier.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.unmodifiableList;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String REFLECTION_PREFIX = "com.github.haseoo.courier.configuration.converters";
    public static final String PESEL_REGEX = "[0-9]";
    public static final Integer TEN_CUT = 10;
    public static final Integer PESEL_LENGTH = 11;
    public static final List<Integer> peselWeights = unmodifiableList(Arrays.asList(1, 3, 7, 9, 1, 3, 7, 9, 1, 3));
    public static final String ADMIN_USERNAME = "admin";
    public static final Long MAX_AGE_SECS = 3600L;
    public static final String EXCEPTION_TRACK_DELIMITER = "\n";
    public static final String EMAIL_REGEX_EXPRESSION = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static final Integer PARCEL_PIN_LENGTH = 4;
    public static final List<Character> PIN_CHARACTERS = unmodifiableList(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
    public static final BigDecimal PRIORITY_MULTILAYER = BigDecimal.valueOf(0.1);
    public static final String POSTAL_CODE_SERVICE_HTTP_METHOD_NAME = "GET";
    public static final String POSTAL_CODE_SERVICE_HTTP_METHOD_HEADER_FILED_NAME = "Accept";
    public static final String POSTAL_CODE_SERVICE_HTTP_METHOD_MEDIA_TYPE = "application/json";

    public static String[] corsAllowedMethods() {
        return new String[]{"HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE"};
    }
}
