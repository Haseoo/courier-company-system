package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.PARCEL_TYPE_FEE_CANNOT_BE_CHANGED_FORMAT_EXCEPTION;

public class ParcelTypeFeeCannotBeChanged extends BusinessLogicException {
    public ParcelTypeFeeCannotBeChanged(String name) {
        super(String.format(PARCEL_TYPE_FEE_CANNOT_BE_CHANGED_FORMAT_EXCEPTION, name));
    }
}
