package com.github.haseoo.courier.security;

import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.Random;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RandomText {

    public static String uniqueUsernamePostfix() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 18;
        Random random = new SecureRandom();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
