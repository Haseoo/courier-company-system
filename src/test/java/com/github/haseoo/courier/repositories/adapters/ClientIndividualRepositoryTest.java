package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.ClientIndividualModel;
import com.github.haseoo.courier.repositories.jpa.ClientIndividualJPARepository;
import com.github.haseoo.courier.repositories.ports.ClientIndividualRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getIndividualClientModel;

@SpringBootTest
@Tag(INTEGRATION_TEST)
class ClientIndividualRepositoryTest {
    @Autowired
    ClientIndividualRepository sut;

    @Autowired
    ClientIndividualJPARepository clientIndividualJPARepository;

    @BeforeEach
    void setup() {
        clientIndividualJPARepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        clientIndividualJPARepository.deleteAll();
    }

    @Test
    void should_add_client() {
        //given
        ClientIndividualModel in = getIndividualClientModel();
        //when
        ClientIndividualModel out = sut.saveAndFlush(in);
        //then
        Assertions.assertThat(in).isEqualTo(out);
    }
}