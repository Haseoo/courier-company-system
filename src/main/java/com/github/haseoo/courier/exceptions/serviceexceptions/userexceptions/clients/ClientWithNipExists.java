package com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.CLIENT_WITH_NIP_EXIST_EXCEPTION;

public class ClientWithNipExists extends BusinessLogicException {
    public ClientWithNipExists() {
        super(CLIENT_WITH_NIP_EXIST_EXCEPTION);
    }
}
