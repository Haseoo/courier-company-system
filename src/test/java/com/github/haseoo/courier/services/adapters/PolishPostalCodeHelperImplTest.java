package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.services.ports.PostalCodeHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;

@SpringBootTest
@Tag(INTEGRATION_TEST)
class PolishPostalCodeHelperImplTest {

    @Autowired
    PostalCodeHelper sut;

    @Test
    void should_postal_code_be_in_city() {
        //given & when & then
        try {
            Assertions.assertThat(sut.isPostalCodeInCity("25-555", "kielce")).isTrue();
        } catch (IOException e) {
        }
    }


    @Test
    void should_postal_code_be_valid() {
        //given & when & then
        Assertions.assertThatCode(() -> sut.validatePostalCode("Kielce", "25-555")).doesNotThrowAnyException();
    }

}