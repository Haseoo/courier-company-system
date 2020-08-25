package com.github.haseoo.courier.testutlis.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Constants {
    public static final String INTEGRATION_TEST = "integration";
    public static final String UNIT_TEST = "unit";
    public static final Integer EXPECTED_LIST_ONE_ELEMENT_SIZE = 1;
    public static final Integer EXPECTED_LIST_SIZE_TWO_ELEMENTS = 2;
    public static final Long FIRST_ID = 1L;
    public static final String TEST_SEND_EMAIL_ADDRESS = "test@test.ts";
}
