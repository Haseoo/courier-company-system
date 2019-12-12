package com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INVALID_PESEL_EXCEPTION;

public class InvalidPeselException extends BusinessLogicException {
    public InvalidPeselException() {
        super(INVALID_PESEL_EXCEPTION);
    }
}
