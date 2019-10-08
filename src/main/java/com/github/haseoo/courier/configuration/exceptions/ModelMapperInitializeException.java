package com.github.haseoo.courier.configuration.exceptions;

import static com.github.haseoo.courier.configuration.exceptions.ExceptionMessages.MODEL_MAPPER_INITIALIZE_EXCEPTION;

public class ModelMapperInitializeException extends RuntimeException {
    public ModelMapperInitializeException(Throwable cause) {
        super(MODEL_MAPPER_INITIALIZE_EXCEPTION);
        initCause(cause);
    }
}