package com.github.haseoo.courier.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.unmodifiableList;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String REFLECTION_PREFIX = "com.github.haseoo.courier.configuration.converters";
    public static final String PESEL_REGEX = "[0-9]";
    public static final Integer TEN_CUT = 10;
    public static final Integer PESEL_LENGTH = 11;
    public static final List<Integer> peselWeights = unmodifiableList(Arrays.asList(1, 3, 7, 9, 1, 3, 7, 9, 1, 3));
}
