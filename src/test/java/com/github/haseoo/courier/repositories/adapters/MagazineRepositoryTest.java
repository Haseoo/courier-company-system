package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.MagazineModel;
import com.github.haseoo.courier.repositories.jpa.MagazineJPARepository;
import com.github.haseoo.courier.repositories.ports.MagazineRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.haseoo.courier.testutlis.constants.Constants.EXPECTED_LIST_SIZE_TWO_ELEMENTS;
import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.MagazineDataGenerator.getInactiveMagazineModel;
import static com.github.haseoo.courier.testutlis.generators.MagazineDataGenerator.getMagazineModel;

@SpringBootTest
@Tag(INTEGRATION_TEST)
class MagazineRepositoryTest {
    @Autowired
    MagazineRepository sut;
    @Autowired
    MagazineJPARepository magazineJPARepository;

    @BeforeEach
    void setup() {
        magazineJPARepository.deleteAll();
    }
    @AfterEach
    void cleanUp() {
        magazineJPARepository.deleteAll();
    }

    @Test
    void should_add_magazine() {
        //given
        MagazineModel in = getMagazineModel();
        //when
        MagazineModel out = sut.saveAndFlush(in);
        //then
        Assertions.assertThat(in).isEqualTo(out);
    }

    @Test
    void should_edit_Magazine() {
        //given
        MagazineModel entity = magazineJPARepository.saveAndFlush(getMagazineModel());
        entity.setActive(false);
        //when
        MagazineModel out = sut.saveAndFlush(entity);
        //then
        Assertions.assertThat(out.getActive()).isFalse();
    }

    @Test
    void should_return_list_with_two_element() {
        //given
        magazineJPARepository.saveAndFlush(getMagazineModel());
        magazineJPARepository.saveAndFlush(getMagazineModel());
        //when & then
        Assertions.assertThat(sut.getList()).hasSize(EXPECTED_LIST_SIZE_TWO_ELEMENTS);
    }

    @Test
    void should_return_list_with_active_elements() {
        //given
        magazineJPARepository.saveAndFlush(getMagazineModel());
        magazineJPARepository.saveAndFlush(getMagazineModel());
        magazineJPARepository.saveAndFlush(getInactiveMagazineModel());
        magazineJPARepository.saveAndFlush(getInactiveMagazineModel());
        //when & then
        Assertions.assertThat(sut.getActiveMagazines()).allMatch(MagazineModel::getActive);
    }
}