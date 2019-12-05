package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.LogisticianModel;
import com.github.haseoo.courier.models.MagazineModel;
import com.github.haseoo.courier.repositories.jpa.LogisticianJPARepository;
import com.github.haseoo.courier.repositories.jpa.MagazineJPARepository;
import com.github.haseoo.courier.repositories.ports.LogisticianRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.github.haseoo.courier.testutlis.constants.Constants.EXPECTED_LIST_ONE_ELEMENT_SIZE;
import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.MagazineDataGenerator.getMagazineModel;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getLogisticianModel;

@SpringBootTest
@Tag(INTEGRATION_TEST)
class LogisticianRepositoryTest {
    @Autowired
    private LogisticianRepository sut;
    @Autowired
    private LogisticianJPARepository logisticianJPARepository;
    @Autowired
    private MagazineJPARepository magazineJPARepository;


    @BeforeEach
    void setUp() {
        logisticianJPARepository.deleteAll();
        magazineJPARepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        logisticianJPARepository.deleteAll();
        magazineJPARepository.deleteAll();
    }

    @Test
    void should_add_logistician() {
        //given
        LogisticianModel in = getLogisticianModel();
        //when
        LogisticianModel out = sut.saveAndFlush(in);
        //then
        Assertions.assertThat(in).isEqualTo(out);
    }

    @Test
    void should_return_list_with_one_logistician() {
        //given
        LogisticianModel logisticianModel = logisticianJPARepository.saveAndFlush(getLogisticianModel());
        //when & then
        Assertions.assertThat(sut.getList()).hasSize(EXPECTED_LIST_ONE_ELEMENT_SIZE).contains(logisticianModel);
    }

    @Test
    void should_find_logistician_by_id() {
        //given
        LogisticianModel logisticianModel = logisticianJPARepository.saveAndFlush(getLogisticianModel());
        //when & then
        Assertions.assertThat(sut.getById(logisticianModel.getId())).isPresent();
    }

    @Test
    @Transactional
    void should_add_logistician_to_magazine() {
        //given
        MagazineModel magazineModel = magazineJPARepository.saveAndFlush(getMagazineModel());
        LogisticianModel logisticianModel = getLogisticianModel();
        logisticianModel.setMagazine(magazineModel);
        //when
        LogisticianModel out = sut.saveAndFlush(logisticianModel);
        //then
        Assertions.assertThat(out.getMagazine()).extracting(MagazineModel::getId).isEqualTo(magazineModel.getId());
    }
}