package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.ILLEGAL_PARCEL_STATE;

public class IllegalParcelState extends BusinessLogicException {
    public IllegalParcelState() {
        super(ILLEGAL_PARCEL_STATE);
    }
}
