package com.github.haseoo.courier.exceptions.serviceexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INVALID_EMAIL_ADDRESS_EXCEPTION_FORMAT;

public class InvalidEmailAddress extends BusinessLogicException {
    public InvalidEmailAddress(String emailAddress) {
        super(String.format(INVALID_EMAIL_ADDRESS_EXCEPTION_FORMAT, emailAddress));
    }
}
