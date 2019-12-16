package com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.USERNAME_IS_TAKEN_EXCEPTION_FORMAT;

public class UsernameIsTaken extends BusinessLogicException {
    public UsernameIsTaken(String username) {
        super(String.format(USERNAME_IS_TAKEN_EXCEPTION_FORMAT, username));
    }
}
