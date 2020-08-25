package com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions;

import com.github.haseoo.courier.exceptions.ResourceNotFoundException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.USER_NOT_FOUND_STRING_FORMAT;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(Long id) {
        super(String.format(USER_NOT_FOUND_STRING_FORMAT, id));
    }
}
