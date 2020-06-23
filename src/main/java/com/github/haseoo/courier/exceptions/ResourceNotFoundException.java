package com.github.haseoo.courier.exceptions;

public class ResourceNotFoundException extends BusinessLogicException{
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Exception cause, String message) {
        super(cause, message);
    }
}
