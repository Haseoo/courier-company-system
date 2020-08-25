package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.*;
import com.github.haseoo.courier.repositories.jpa.*;
import com.github.haseoo.courier.repositories.ports.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
    @Autowired
    private LogisticianJPARepository logisticianJPARepository;

    @Autowired

    @BeforeEach
    void setUp() {
        userJPARepository.deleteAll();
    }

    @AfterEach
    void cleanUp() {
        userJPARepository.deleteAll();
    }

    @Test
    void should_return_list_of_users() {
        //given
        courierJPARepository.saveAndFlush(getCourierModel());
        clientCompanyJPARepository.saveAndFlush(getCompanyClientModel());
        clientIndividualJPARepository.saveAndFlush(getIndividualClientModel());
        logisticianJPARepository.saveAndFlush(getLogisticianModel());

        //when & then
        Assertions.assertThat(sut.getList())
                .hasAtLeastOneElementOfType(CourierModel.class)
                .hasAtLeastOneElementOfType(LogisticianModel.class)
                .hasAtLeastOneElementOfType(ClientIndividualModel.class)
                .hasAtLeastOneElementOfType(ClientCompanyModel.class);
    }

    @Test
    void should_find_user_by_id() {
        //given
        UserModel userModel = courierJPARepository.saveAndFlush(getCourierModel());
        //when & then
        Assertions.assertThat(sut.getById(userModel.getId())).isPresent();
    }

    @Test
    void should_mark_user_as_inactive() {
        //given
        UserModel userModel = courierJPARepository.saveAndFlush(getCourierModel());
        userModel.setActive(false);
        //when & then
        Assertions.assertThat(sut.saveAndFlush(userModel).getActive()).isFalse();

    }
}