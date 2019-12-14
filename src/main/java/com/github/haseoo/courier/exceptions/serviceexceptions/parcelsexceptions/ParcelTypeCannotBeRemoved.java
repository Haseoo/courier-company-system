package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.PARCEL_TYPE_CANNOT_BE_REMOVED_FORMAT_EXCEPTION;

public class ParcelTypeCannotBeRemoved extends BusinessLogicException {
    public ParcelTypeCannotBeRemoved(String typeName) {
        super(String.format(PARCEL_TYPE_CANNOT_BE_REMOVED_FORMAT_EXCEPTION, typeName));
    }
}
