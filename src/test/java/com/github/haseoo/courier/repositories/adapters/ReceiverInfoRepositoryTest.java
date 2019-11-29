package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.ReceiverInfoModel;
import com.github.haseoo.courier.repositories.jpa.ReceiverInfoJPARepository;
import com.github.haseoo.courier.repositories.ports.ReceiverInfoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.haseoo.courier.testutlis.constants.Constants.EXPECTED_LIST_ONE_ELEMENT_SIZE;
import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.ReceiverInfoDataGenerator.*;

@SpringBootTest
@Tag(INTEGRATION_TEST)
class ReceiverInfoRepositoryTest {
    @Autowired
    private ReceiverInfoJPARepository receiverInfoJPARepository;
    @Autowired
    private ReceiverInfoRepository sut;

    @BeforeEach
    void setUp() {
        receiverInfoJPARepository.deleteAll();
    }
    @AfterEach
    void cleanUp() {
        receiverInfoJPARepository.deleteAll();
    }

    @Test
    void should_add_receiver_info() {
        //given
        ReceiverInfoModel in = getReceiverInfoModel();
        //when
        ReceiverInfoModel out = sut.saveAndFlush(in);
        //then
        Assertions.assertThat(out).isEqualTo(in);
    }

    @Test
    void should_return_receiver_info_list_with_one_element() {
        //given
        receiverInfoJPARepository.saveAndFlush(getReceiverInfoModel());
        //when & then
        Assertions.assertThat(sut.getList()).hasSize(EXPECTED_LIST_ONE_ELEMENT_SIZE);
    }

    @Test
    void should_return_receiver_info_by_id() {
        //given
        Long id = receiverInfoJPARepository.saveAndFlush(getReceiverInfoModel()).getId();
        //when & then
        Assertions.assertThat(sut.getById(id)).isPresent();
    }

    @Test
    void should_receiver_info_exist() {
        //given
        receiverInfoJPARepository.saveAndFlush(getReceiverInfoModel());
        //when & then
        Assertions.assertThat(sut.receiverInfoExists(getExistentQueryData())).isTrue();
    }

    @Test
    void should_not_receiver_info_exist() {
        //given
        receiverInfoJPARepository.saveAndFlush(getReceiverInfoModel());
        //when & then
        Assertions.assertThat(sut.receiverInfoExists(getNotExistentQueryData())).isFalse();
    }

}
