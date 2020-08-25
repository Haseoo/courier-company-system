package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.PARCEL_PICKUP_FORM_MAGAZINE_PAID_EXCEPTION;

public class PaidPickupFromMagazine extends BusinessLogicException {
    public PaidPickupFromMagazine() {
        super(PARCEL_PICKUP_FORM_MAGAZINE_PAID_EXCEPTION);
    }
}
