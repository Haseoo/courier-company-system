package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.AddressModel;
import com.github.haseoo.courier.repositories.jpa.AddressJPARepository;
import com.github.haseoo.courier.repositories.ports.AddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.haseoo.courier.testutlis.Constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.Constants.PackageDataConstants.EXPECTED_LIST_SIZE;
import static com.github.haseoo.courier.testutlis.generators.AddressDataGenerator.getAddressModel;
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
    void should_return_list_with_one_element() {
        //given
        AddressModel in = getAddressModel();
        addressJPARepository.saveAndFlush(in);
        //when & then
        Assertions.assertThat(sut.getList()).hasSize(EXPECTED_LIST_SIZE);
    }
}