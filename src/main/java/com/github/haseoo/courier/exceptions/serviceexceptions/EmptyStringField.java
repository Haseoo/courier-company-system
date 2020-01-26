package com.github.haseoo.courier.exceptions.serviceexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.EMPTY_STRING_FIELD_FORMAT_EXCEPTION;

public class EmptyStringField  extends BusinessLogicException {
    public EmptyStringField(String fieldName) {
        super(String.format(EMPTY_STRING_FIELD_FORMAT_EXCEPTION, fieldName));
    }
}
