package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.models.ParcelStateRecord;
import com.github.haseoo.courier.repositories.jpa.CourierJPARepository;
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
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getTestRecordModel;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCourierModel;

@SpringBootTest
@Tag(INTEGRATION_TEST)
class CourierRepositoryTest extends TestsWithParcels {
    @Autowired
    private CourierRepositoryImpl sut;
    @Autowired
    private CourierJPARepository courierJPARepository;


    @BeforeEach
    void setup() {
        courierJPARepository.deleteAll();
        super.setup();
    }

    @AfterEach
    void tearDown() {
        courierJPARepository.deleteAll();
        super.tearDown();
    }

    @Test
    void should_add_courier() {
        //given
        CourierModel in = getCourierModel();
        //when
        CourierModel out = sut.saveAndFlush(in);
        //then
        Assertions.assertThat(in).isEqualTo(out);
    }

    @Test
    void should_find_courier_by_Id() {
        //given
        CourierModel courier = courierJPARepository.saveAndFlush(getCourierModel());
        //when & then
        Assertions.assertThat(sut.getById(courier.getId())).isPresent();
    }

    @Transactional
    @Test
    void should_return_list_with_one_courier() {
        //given
        CourierModel courier = courierJPARepository.saveAndFlush(getCourierModel());
        //when & then
        Assertions.assertThat(sut.getList()).hasSize(EXPECTED_LIST_ONE_ELEMENT_SIZE).contains(courier);
    }

    @Transactional
    @Test
    void should_assign_parcel_to_courier() {
        //given
        CourierModel courier = courierJPARepository.saveAndFlush(getCourierModel());
        ParcelStateRecord state = getTestRecordModel(parcelModel, courier);
        //when
        courier.getParcelStates().add(state);
        CourierModel edited = sut.saveAndFlush(courier);
        //then
        Assertions.assertThat(edited.getParcelStates()).hasSize(EXPECTED_LIST_ONE_ELEMENT_SIZE);
        //a courier has a state record with him assigned to
        Assertions.assertThat(edited.getParcelStates())
                .first().extracting(stateRecord -> stateRecord.getCourier().getId())
                .isEqualTo(edited.getId());
        //a courier has a state record with the parcel that is assigned to him
        Assertions.assertThat(edited.getParcelStates())
                .first().extracting(stateRecord -> stateRecord.getParcel().getId())
                .isEqualTo(parcelModel.getId());
    }
}