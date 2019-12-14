package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.PARCEL_TYPE_NOT_FOUND_FORMAT_EXCEPTION;

public class ParcelTypeNotFound extends BusinessLogicException {
    public ParcelTypeNotFound(Long id) {
        super(String.format(PARCEL_TYPE_NOT_FOUND_FORMAT_EXCEPTION, id));
    }
}
