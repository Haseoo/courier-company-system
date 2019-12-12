package com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INVALID_EMPLOYEE_INSTANCE;

public class InvalidEmployeeInstanceException extends BusinessLogicException {
    public InvalidEmployeeInstanceException() {
        super(INVALID_EMPLOYEE_INSTANCE);
    }
}
