package com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions;

import com.github.haseoo.courier.exceptions.AuthException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.INCORRECT_USERNAME_OR_SECRET;

public class InvalidLoginOrPassword extends AuthException {
    public InvalidLoginOrPassword() {
        super(INCORRECT_USERNAME_OR_SECRET);
    }
}
