package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.exceptions.ResourceNotFoundException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.PARCEL_NOT_FOUND_EXCEPTION_FORMAT;

public class ParcelNotFound extends ResourceNotFoundException {
    public ParcelNotFound(Long id) {
        super(String.format(PARCEL_NOT_FOUND_EXCEPTION_FORMAT, id));
    }
}
