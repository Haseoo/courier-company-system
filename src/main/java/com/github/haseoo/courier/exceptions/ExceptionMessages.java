package com.github.haseoo.courier.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {
    public static final String MODEL_MAPPER_INITIALIZE_EXCEPTION = "Failed to initialize converters for model mapper";
    public static final String INVALID_EMPLOYEE_INSTANCE = "Invalid employee instance";
    public static final String ACTIVE_COURIER_WITH_PESEL_EXISTS = "Courier employee with status active and same PESEL number already exists";
    public static final String ACTIVE_LOGISTICIAN_WITH_PESEL_EXISTS = "Logistician employee with status active and same PESEL number already exists";
    public static final String EMPLOYEE_NOT_FOUND_STRING_FORMAT = "Employee with %s id not found";
    public static final String USER_NOT_FOUND_STRING_FORMAT = "User with %s id not found";
    public static final String INVALID_PESEL_EXCEPTION = "Invalid PESEL";
    public static final String INVALID_PESEL_FORMAT_EXCEPTION = "Incorrect format. Pesel must have 11 digits";
}
