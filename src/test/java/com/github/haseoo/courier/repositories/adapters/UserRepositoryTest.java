package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.ClientCompanyModel;
import com.github.haseoo.courier.models.ClientIndividualModel;
import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.repositories.jpa.ClientCompanyJPARepository;
import com.github.haseoo.courier.repositories.jpa.ClientIndividualJPARepository;
import com.github.haseoo.courier.repositories.jpa.CourierJPARepository;
import com.github.haseoo.courier.repositories.jpa.UserJPARepository;
import com.github.haseoo.courier.repositories.ports.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.*;

@SpringBootTest
@Tag(INTEGRATION_TEST)
class UserRepositoryTest {

    @Autowired
    private UserRepository sut;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private CourierJPARepository courierJPARepository;
    @Autowired
    private ClientCompanyJPARepository clientCompanyJPARepository;
    @Autowired
    private ClientIndividualJPARepository clientIndividualJPARepository;

    @BeforeEach
    void setUp() {
        userJPARepository.deleteAll();
    }

    @Test
    void getList() {
        //given
        courierJPARepository.saveAndFlush(getCourierModel());
        clientCompanyJPARepository.saveAndFlush(getCompanyClient());
        clientIndividualJPARepository.saveAndFlush(getIndividualClient());

        //when & then
        Assertions.assertThat(sut.getList())
                .hasAtLeastOneElementOfType(CourierModel.class)
                .hasAtLeastOneElementOfType(ClientIndividualModel.class)
                .hasAtLeastOneElementOfType(ClientCompanyModel.class);
        //TODO dodać resztę użytkowników jak będą gotowi
    }
}