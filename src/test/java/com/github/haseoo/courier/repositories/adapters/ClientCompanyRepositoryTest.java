package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.ClientCompanyModel;
import com.github.haseoo.courier.repositories.jpa.ClientCompanyJPARepository;
import com.github.haseoo.courier.repositories.ports.ClientCompanyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCompanyClientModel;

@SpringBootTest
@Tag(INTEGRATION_TEST)
class ClientCompanyRepositoryTest {
    @Autowired
    ClientCompanyRepository sut;

    @Autowired
    ClientCompanyJPARepository clientCompanyJPARepository;

    @BeforeEach
    void setup() {
        clientCompanyJPARepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        clientCompanyJPARepository.deleteAll();
    }

    @Test
    void should_add_client() {
        //given
        ClientCompanyModel in = getCompanyClientModel();
        //when
        ClientCompanyModel out = sut.saveAndFlush(in);
        //then
        Assertions.assertThat(in).isEqualTo(out);
    }
}