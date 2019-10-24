package com.github.haseoo.courier.exceptions;

public class BusinessLogicException extends RuntimeException {
    public BusinessLogicException(String message) {
        super(message);
    }

    public BusinessLogicException(Exception cause, String message) {
        this(message);
        initCause(cause);
    }
}
