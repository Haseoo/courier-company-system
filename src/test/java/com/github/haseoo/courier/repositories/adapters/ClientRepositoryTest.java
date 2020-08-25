package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.ClientCompanyModel;
import com.github.haseoo.courier.models.ClientIndividualModel;
import com.github.haseoo.courier.repositories.jpa.ClientCompanyJPARepository;
import com.github.haseoo.courier.repositories.jpa.ClientIndividualJPARepository;
import com.github.haseoo.courier.repositories.jpa.ClientJPARepository;
import com.github.haseoo.courier.repositories.ports.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCompanyClientModel;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getIndividualClientModel;

@SpringBootTest
@Tag(INTEGRATION_TEST)
class ClientRepositoryTest {
    @Autowired
    private ClientRepository sut;

    @Autowired
    private ClientJPARepository clientJPARepository;
    @Autowired
    private ClientIndividualJPARepository clientIndividualJPARepository;
    @Autowired
    private ClientCompanyJPARepository clientCompanyJPARepository;

    @BeforeEach
    void setup() {
        clientJPARepository.deleteAll();
    }

    @AfterEach
    void tearUp() {
        clientJPARepository.deleteAll();
    }

    @Transactional
    @Test
    void should_return_list_with_clients() {
        //given
        ClientIndividualModel clientIndividualModel = clientIndividualJPARepository.saveAndFlush(getIndividualClientModel());
        ClientCompanyModel clientCompanyModel = clientCompanyJPARepository.saveAndFlush(getCompanyClientModel());
        //when & then
        Assertions.assertThat(sut.getList())
                .contains(clientCompanyModel)
                .contains(clientIndividualModel);
    }

    @Test
    void should_find_company_client_by_id() {
        //given
        ClientCompanyModel clientCompanyModel = clientCompanyJPARepository.saveAndFlush(getCompanyClientModel());
        //when & then
        Assertions.assertThat(sut.getById(clientCompanyModel.getId()))
                .isPresent()
                .containsInstanceOf(ClientCompanyModel.class);
    }

}