package com.github.haseoo.courier.exceptions.serviceexceptions;

import com.github.haseoo.courier.exceptions.ResourceNotFoundException;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.ANY_MAGAZINE_DOES_NOT_EXIST_EXCEPTION;
import static com.github.haseoo.courier.exceptions.ExceptionMessages.MAGAZINE_DOES_NOT_EXIST_EXCEPTION_FORMAT;

public class MagazineDoesNotExist extends ResourceNotFoundException {
    public MagazineDoesNotExist(Long id) {
        super(String.format(MAGAZINE_DOES_NOT_EXIST_EXCEPTION_FORMAT, id));
    }

    public MagazineDoesNotExist() {
        super(ANY_MAGAZINE_DOES_NOT_EXIST_EXCEPTION);
    }
}
