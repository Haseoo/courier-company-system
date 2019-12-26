package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.PARCEL_NOT_PAID_EXCEPTION_FORMAT;

public class ParcelNotPaid extends BusinessLogicException {
    public ParcelNotPaid(Long id) {
        super(String.format(PARCEL_NOT_PAID_EXCEPTION_FORMAT, id));
    }
}
