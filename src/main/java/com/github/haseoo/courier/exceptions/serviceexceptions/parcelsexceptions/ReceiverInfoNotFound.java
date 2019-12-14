package com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.RECEIVER_INFO_NOT_FOUND_FORMAT_EXCEPTION;

public class ReceiverInfoNotFound extends BusinessLogicException {
    public ReceiverInfoNotFound(Long id) {
        super(String.format(RECEIVER_INFO_NOT_FOUND_FORMAT_EXCEPTION, id));
    }
}
