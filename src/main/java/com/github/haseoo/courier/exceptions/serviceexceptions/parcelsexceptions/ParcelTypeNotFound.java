package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.exceptions.ResourceNotFoundException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.PARCEL_TYPE_NOT_FOUND_FORMAT_EXCEPTION;

public class ParcelTypeNotFound extends ResourceNotFoundException {
    public ParcelTypeNotFound(Long id) {
        super(String.format(PARCEL_TYPE_NOT_FOUND_FORMAT_EXCEPTION, id));
    }
}
