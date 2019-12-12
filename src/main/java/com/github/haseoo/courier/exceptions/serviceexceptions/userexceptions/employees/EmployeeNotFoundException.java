package com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.EMPLOYEE_NOT_FOUND_STRING_FORMAT;

public class EmployeeNotFoundException extends BusinessLogicException {
    public EmployeeNotFoundException(Long id) {
        super(String.format(EMPLOYEE_NOT_FOUND_STRING_FORMAT, id));
    }
}