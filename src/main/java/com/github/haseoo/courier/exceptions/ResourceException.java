package com.github.haseoo.courier.exceptions;

import static com.github.haseoo.courier.exceptions.ExceptionMessages.USER_DONT_HAVE_PERMISSION_TO_RESOURCE;

public class ResourceException extends AuthException {
    public ResourceException() {
        super(USER_DONT_HAVE_PERMISSION_TO_RESOURCE);
    }
}
