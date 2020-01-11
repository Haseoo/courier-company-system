package com.github.haseoo.courier.services.ports;

import java.io.IOException;

public interface PostalCodeHelper {
    boolean isPostalCodeInCity(String postalCode, String city) throws IOException;

    void validatePostalCode(String city, String postalCode);
}
