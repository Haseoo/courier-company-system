package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INVALID_POSTAL_CODE_FORMAT_EXCEPTION;

public class InvalidPostalCode extends BusinessLogicException {
    public InvalidPostalCode(String postalCode) {
        super(String.format(INVALID_POSTAL_CODE_FORMAT_EXCEPTION, postalCode));
    }
}
