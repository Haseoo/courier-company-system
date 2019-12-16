package com.github.haseoo.courier.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {
    public static final String MODEL_MAPPER_INITIALIZE_EXCEPTION = "Failed to initialize converters for model mapper";
    public static final String INVALID_EMPLOYEE_INSTANCE = "Invalid employee instance";
    public static final String ACTIVE_COURIER_WITH_PESEL_EXISTS = "Courier employee with status active and same PESEL number already exists";
    public static final String ACTIVE_LOGISTICIAN_WITH_PESEL_EXISTS = "Logistician employee with status active and same PESEL number already exists";
    public static final String EMPLOYEE_WITH_TYPE_NOT_FOUND_STRING_FORMAT = "Employee %s with %s id not found";
    public static final String EMPLOYEE_NOT_FOUND_STRING_FORMAT = "Employee with %s id not found";
    public static final String USER_NOT_FOUND_STRING_FORMAT = "User with %s id not found";
    public static final String INVALID_PESEL_EXCEPTION = "Invalid PESEL";
    public static final String INVALID_PESEL_FORMAT_EXCEPTION = "Incorrect format. Pesel must have 11 digits";
    public static final String PARCEL_TYPE_NOT_FOUND_FORMAT_EXCEPTION = "Parcel type with id %s not found";
    public static final String RECEIVER_INFO_NOT_FOUND_FORMAT_EXCEPTION = "Receiver with %s id not found";
    public static final String PARCEL_TYPE_CANNOT_BE_REMOVED_FORMAT_EXCEPTION = "Parcel type %s cannot be removed because it's assigned to at least one parcel";
    public static final String PARCEL_TYPE_FEE_CANNOT_BE_CHANGED_FORMAT_EXCEPTION = "The fee of %s parcel type cannot be changed due to it has non-paid parcels assigned";
    public static final String INVALID_ENUM_TYPE = "Provided invalid enum type";
    public static final String MAGAZINE_DOES_NOT_EXIST_EXCEPTION_FORMAT = "Magazine with id %s does not exist";
    public static final String ADDRESS_DOES_NOT_EXIST_EXCEPTION_FORMAT = "Address with id %s does not exist";
    public static final String USERNAME_IS_TAKEN_EXCEPTION_FORMAT = "Username %s is already taken";
    public static final String CLIENT_NOT_FOUND_EXCEPTION_FORMAT = "Client %s with %s could not be found";
    public static final String CLIENT_WITH_PESEL_EXIST_EXCEPTION = "Client with this pesel number already exists, please reactive your account instead creating new one";
    public static final String CLIENT_WITH_NIP_EXIST_EXCEPTION = "Client with this nip number already exists, please reactive your account instead creating new one";
}
