package com.github.haseoo.courier.exceptions;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.MODEL_MAPPER_INITIALIZE_EXCEPTION;

public class ModelMapperInitializeException extends RuntimeException {
    public ModelMapperInitializeException(Throwable cause) {
        super(MODEL_MAPPER_INITIALIZE_EXCEPTION);
        initCause(cause);
    }
}