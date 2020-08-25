package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INCORRECT_PARCEL_PIN_EXCEPTION;

public class IncorrectParcelException extends BusinessLogicException {
    public IncorrectParcelException() {
        super(INCORRECT_PARCEL_PIN_EXCEPTION);
    }
}
