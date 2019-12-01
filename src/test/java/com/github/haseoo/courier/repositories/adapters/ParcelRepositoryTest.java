package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.*;
import com.github.haseoo.courier.repositories.jpa.ClientCompanyJPARepository;
import com.github.haseoo.courier.repositories.jpa.CourierJPARepository;
import com.github.haseoo.courier.repositories.jpa.ParcelJPARepository;
import com.github.haseoo.courier.repositories.jpa.ParcelTypeJPARepository;
import com.github.haseoo.courier.repositories.ports.ParcelRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.github.haseoo.courier.testutlis.constants.Constants.*;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelModel;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getTestRecordModel;
import static com.github.haseoo.courier.testutlis.generators.ParcelTypeDataGenerator.getActiveParcelTypeModel;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCompanyClient;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCourierModel;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@Tag(INTEGRATION_TEST)
@TestInstance(PER_CLASS)
class ParcelRepositoryTest {

    @Autowired
    private ParcelRepository sut;
    @Autowired
    private ParcelJPARepository parcelJPARepository;
    @Autowired
    private ParcelTypeJPARepository parcelTypeJPARepository;
    @Autowired
    private ClientCompanyJPARepository clientCompanyJPARepository;
    @Autowired
    private CourierJPARepository courierJPARepository;

    private ClientCompanyModel client;
    private ParcelTypeModel type;
    private CourierModel courier;

    @BeforeAll
    void beforeAll() {
        parcelTypeJPARepository.deleteAll();
        clientCompanyJPARepository.deleteAll();
        courierJPARepository.deleteAll();
        type = parcelTypeJPARepository.saveAndFlush(getActiveParcelTypeModel());
        client = clientCompanyJPARepository.saveAndFlush(getCompanyClient());
        courier = courierJPARepository.saveAndFlush(getCourierModel());
    }

    @AfterAll
    void afterAll() {
        parcelTypeJPARepository.deleteAll();
        clientCompanyJPARepository.deleteAll();
        courierJPARepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        parcelJPARepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        parcelJPARepository.deleteAll();
    }

    @Test
    void should_add_parcel() {
        //given
        ParcelModel in = getParcelModel(type, client);
        //when
        ParcelModel out = sut.saveAndFlush(in);
        //then
        Assertions.assertThat(in).isEqualTo(out);
    }

    @Test
    void should_edit_parcel() {
        //given
        ParcelModel in = parcelJPARepository.saveAndFlush(getParcelModel(type, client));
        in.setDateMoved(true);
        //when
        ParcelModel edited = sut.saveAndFlush(in);
        //then
        Assertions.assertThat(edited.getId()).isEqualTo(in.getId());
        Assertions.assertThat(edited.getDateMoved()).isTrue();
    }

    @Test
    void should_return_list_with_two_elements() {
        //given
        parcelJPARepository.saveAndFlush(getParcelModel(type, client));
        parcelJPARepository.saveAndFlush(getParcelModel(type, client));
        //when & then
        Assertions.assertThat(sut.getList()).hasSize(EXPECTED_LIST_SIZE_TWO_ELEMENTS);
    }

    @Test
    void should_return_parcel_by_id() {
        //given
        ParcelModel in = parcelJPARepository.saveAndFlush(getParcelModel(type, client));
        //when & then
        Assertions.assertThat(sut.getById(in.getId())).isPresent();
    }

    @Test
    @Transactional
    void should_add_state_at_courier() {
        //given
        ParcelModel in = parcelJPARepository.saveAndFlush(getParcelModel(type, client));
        ParcelStateRecord state = getTestRecordModel(in, courier);
        in.getParcelStates().add(state);
        //when
        ParcelModel edited = sut.saveAndFlush(in);
        //then
        Assertions.assertThat(edited.getParcelStates()).hasSize(EXPECTED_LIST_ONE_ELEMENT_SIZE);
    }
}