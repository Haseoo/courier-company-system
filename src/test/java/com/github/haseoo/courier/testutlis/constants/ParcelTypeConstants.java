package com.github.haseoo.courier.testutlis.constants;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ParcelTypeConstants {
    public static final String TEST_PARCEL_TYPE_NAME = "Test type";
    public static final String TEST_PARCEL_TYPE_DESCRIPTION = "Test description";
    public static final BigDecimal TEST_PARCEL_TYPE_PRICE = BigDecimal.valueOf(69.99);
    public static final Boolean TEST_PARCEL_TYPE_ACTIVE = true;
    public static final Boolean TEST_PARCEL_TYPE_NOT_ACTIVE = false;
}
