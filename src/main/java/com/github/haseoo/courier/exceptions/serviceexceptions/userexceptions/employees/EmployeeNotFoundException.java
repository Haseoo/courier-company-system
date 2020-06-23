package com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees;

import com.github.haseoo.courier.enums.EmployeeType;
import com.github.haseoo.courier.exceptions.ResourceNotFoundException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.EMPLOYEE_NOT_FOUND_STRING_FORMAT;
import static com.github.haseoo.courier.exceptions.ExceptionMessages.EMPLOYEE_WITH_TYPE_NOT_FOUND_STRING_FORMAT;

public class EmployeeNotFoundException extends ResourceNotFoundException {
    public EmployeeNotFoundException(Long id, EmployeeType employeeType) {
        super(String.format(EMPLOYEE_WITH_TYPE_NOT_FOUND_STRING_FORMAT, employeeType, id));
    }

    public EmployeeNotFoundException(Long id) {
        super(String.format(EMPLOYEE_NOT_FOUND_STRING_FORMAT, id));
    }
}
