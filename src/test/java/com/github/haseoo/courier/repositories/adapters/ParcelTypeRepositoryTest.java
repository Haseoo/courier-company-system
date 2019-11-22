package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.ParcelTypeModel;
import com.github.haseoo.courier.repositories.jpa.ParcelTypeJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.haseoo.courier.testutlis.constants.Constants.*;
import static com.github.haseoo.courier.testutlis.generators.ParcelTypeDataGenerator.getActiveParcelTypeModel;
import static com.github.haseoo.courier.testutlis.generators.ParcelTypeDataGenerator.getNotActiveParcelTypeModel;

@SpringBootTest
@Tag(INTEGRATION_TEST)
class ParcelTypeRepositoryTest {
    @Autowired
    private ParcelTypeRepositoryImpl sut;
    @Autowired
    private ParcelTypeJPARepository parcelTypeJPARepository;

    @BeforeEach
    void beforeEach() {
        parcelTypeJPARepository.deleteAll();
    }

    @Test
    void should_add_parcel_type() {
        //given
        ParcelTypeModel in = getActiveParcelTypeModel();
        //when
        ParcelTypeModel out = sut.saveAndFlush(in);
        //then
        Assertions.assertThat(out.getActive()).isEqualTo(in.getActive());
        Assertions.assertThat(out.getDescription()).isEqualTo(in.getDescription());
        Assertions.assertThat(out.getName()).isEqualTo(in.getName());
        Assertions.assertThat(out.getPrice()).isEqualTo(in.getPrice());
    }

    @Test
    void should_return_list_with_two_elements() {
        //given
        ParcelTypeModel first = parcelTypeJPARepository.saveAndFlush(getActiveParcelTypeModel());
        ParcelTypeModel second = parcelTypeJPARepository.saveAndFlush(getNotActiveParcelTypeModel());
        //when & then
        Assertions.assertThat(sut.getList()).hasSize(EXPECTED_LIST_SIZE_TWO_ELEMENTS).contains(first).contains(second);
    }

    @Test
    void should_return_element_by_id() {
        //given
        ParcelTypeModel in = parcelTypeJPARepository.saveAndFlush(getActiveParcelTypeModel());
        //when & then
        Assertions.assertThat(sut.getById(in.getId())).isPresent();
    }

    @Test
    void should_not_return_element_by_id() {
        //given & when & then
        Assertions.assertThat(sut.getById(FIRST_ID)).isNotPresent();
    }

    @Test
    void should_return_one_active_type() {
        //given
        ParcelTypeModel active = parcelTypeJPARepository.saveAndFlush(getActiveParcelTypeModel());
        ParcelTypeModel notActive = parcelTypeJPARepository.saveAndFlush(getNotActiveParcelTypeModel());
        //when & then
        Assertions.assertThat(sut.getActiveTypes()).hasSize(EXPECTED_LIST_ONE_ELEMENT_SIZE).contains(active).doesNotContain(notActive);
    }
}