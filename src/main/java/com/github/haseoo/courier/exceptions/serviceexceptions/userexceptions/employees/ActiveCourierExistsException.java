package com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.ACTIVE_COURIER_WITH_PESEL_EXISTS;

public class ActiveCourierExistsException extends BusinessLogicException {
    public ActiveCourierExistsException() {
        super(ACTIVE_COURIER_WITH_PESEL_EXISTS);
    }
}
