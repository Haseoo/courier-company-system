package com.github.haseoo.courier.utilities;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.stream.IntStream;

import static com.github.haseoo.courier.utilities.Constants.PARCEL_PIN_LENGTH;
import static com.github.haseoo.courier.utilities.Constants.PIN_CHARACTERS;

@Component
public class PinGenerator {
    private SecureRandom secureRandom;

    public PinGenerator() {
        secureRandom = new SecureRandom();
    }

    public char[] getParcelPin() {
        char[] pin = new char[PARCEL_PIN_LENGTH];
        IntStream.range(0, PARCEL_PIN_LENGTH).forEach(i -> pin[i] = PIN_CHARACTERS.get(secureRandom.nextInt(PIN_CHARACTERS.size())));
        return pin;
    }
}
