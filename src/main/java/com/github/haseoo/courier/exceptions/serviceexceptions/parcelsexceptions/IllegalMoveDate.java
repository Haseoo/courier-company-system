package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.ILLEGAL_MOVE_DATE_EXCEPTION;

public class IllegalMoveDate extends BusinessLogicException {
    public IllegalMoveDate() {
        super(ILLEGAL_MOVE_DATE_EXCEPTION);
    }
}
