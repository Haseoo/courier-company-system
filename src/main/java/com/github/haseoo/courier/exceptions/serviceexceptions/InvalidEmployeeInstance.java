package com.github.haseoo.courier.exceptions.serviceexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INVALID_EMPLOYEE_INSTANCE;

public class InvalidEmployeeInstance extends BusinessLogicException {
    public InvalidEmployeeInstance() {
        super(INVALID_EMPLOYEE_INSTANCE);
    }
}
