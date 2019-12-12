package com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.ACTIVE_LOGISTICIAN_WITH_PESEL_EXISTS;

public class ActiveLogisticianExistsException extends BusinessLogicException {
    public ActiveLogisticianExistsException() {
        super(ACTIVE_LOGISTICIAN_WITH_PESEL_EXISTS);
    }
}
