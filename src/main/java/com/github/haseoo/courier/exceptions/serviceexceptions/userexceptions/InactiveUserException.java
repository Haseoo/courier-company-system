package com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.USER_INACTIVE_EXCEPTION_FORMAT;

public class InactiveUserException extends BusinessLogicException {
    public InactiveUserException(String username) {
        super(String.format(USER_INACTIVE_EXCEPTION_FORMAT, username));
    }
}
