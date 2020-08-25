package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.AddressModel;
import com.github.haseoo.courier.repositories.jpa.AddressJPARepository;
import com.github.haseoo.courier.repositories.ports.AddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.haseoo.courier.testutlis.constants.AddressConstants.NEW_STREET;
import static com.github.haseoo.courier.testutlis.constants.Constants.*;
import static com.github.haseoo.courier.testutlis.generators.AddressDataGenerator.*;

@SpringBootTest
@Tag(INTEGRATION_TEST)
class AddressRepositoryTest {
    @Autowired
    private AddressJPARepository addressJPARepository;
    @Autowired
    private AddressRepository sut;

    @BeforeEach
    void setUp() {
        addressJPARepository.deleteAll();
    }

    @AfterEach
    void cleanUp() {
        addressJPARepository.deleteAll();
    }

    @Test
    void should_add_address() {
        //given
        AddressModel in = getAddressModel();
        //when
        AddressModel out = sut.saveAndFlush(in);
        //then
        Assertions.assertThat(out).isEqualTo(in);
    }

    @Test
    void should_edit_address() {
        //given
        AddressModel address = addressJPARepository.saveAndFlush(getAddressModel());
        //when
        address.setStreet(NEW_STREET);
        AddressModel newAddress = sut.saveAndFlush(address);
        //then
        Assertions.assertThat(newAddress.getId()).isEqualTo(address.getId());
        Assertions.assertThat(newAddress.getStreet()).isEqualTo(address.getStreet());
        Assertions.assertThat(sut.getList().size()).isOne();
    }

    @Test
    void should_return_list_with_one_element() {
        //given
        AddressModel in = getAddressModel();
        addressJPARepository.saveAndFlush(in);
        //when & then
        Assertions.assertThat(sut.getList()).hasSize(EXPECTED_LIST_ONE_ELEMENT_SIZE);
    }

    @Test
    void should_address_exist() {
        //given
        addressJPARepository.saveAndFlush(getAddressModel());
        //when & then
        Assertions.assertThat(sut.addressExist(getExistentQueryData())).isPresent();
    }

    @Test
    void should_address_not_exist() {
        //given
        addressJPARepository.saveAndFlush(getAddressModel());
        //when & then
        Assertions.assertThat(sut.addressExist(getNotExistentQueryData())).isNotPresent();
    }

    @Test
    void should_find_address_by_id() {
        //given
        AddressModel in = addressJPARepository.saveAndFlush(getAddressModel());
        //when & then
        Assertions.assertThat(sut.getById(in.getId())).isPresent();
    }

    @Test
    void should_find_not_address_by_id() {
        //given & when & then
        Assertions.assertThat(sut.getById(FIRST_ID)).isNotPresent();
    }
}