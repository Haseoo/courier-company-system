package com.github.haseoo.courier.exceptions.serviceexceptions;

import com.github.haseoo.courier.exceptions.BusinessLogicException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.ADDRESS_DOES_NOT_EXIST_EXCEPTION_FORMAT;

public class AddressDoesNotExist extends BusinessLogicException {
    public AddressDoesNotExist(Long id) {
        super(String.format(ADDRESS_DOES_NOT_EXIST_EXCEPTION_FORMAT, id));
    }
}