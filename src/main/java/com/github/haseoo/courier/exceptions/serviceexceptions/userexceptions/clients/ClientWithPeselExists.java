package com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.CLIENT_WITH_PESEL_EXIST_EXCEPTION;

public class ClientWithPeselExists extends BusinessLogicException {
    public ClientWithPeselExists() {
        super(CLIENT_WITH_PESEL_EXIST_EXCEPTION);
    }
}
