package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.ATTEMPT_TO_ADD_PARCEL_OF_INACTIVE_TYPE;

public class ParcelInactiveType extends BusinessLogicException {
    public ParcelInactiveType() {
        super(ATTEMPT_TO_ADD_PARCEL_OF_INACTIVE_TYPE);
    }
}
