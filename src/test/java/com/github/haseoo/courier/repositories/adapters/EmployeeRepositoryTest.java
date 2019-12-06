package com.github.haseoo.courier.repositories.adapters;

import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.models.LogisticianModel;
import com.github.haseoo.courier.repositories.jpa.CourierJPARepository;
import com.github.haseoo.courier.repositories.jpa.EmployeeJPARepository;
import com.github.haseoo.courier.repositories.jpa.LogisticianJPARepository;
import com.github.haseoo.courier.repositories.ports.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCourierModel;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getLogisticianModel;

@SpringBootTest
@Tag(INTEGRATION_TEST)
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository sut;

    @Autowired
    private EmployeeJPARepository employeeJPARepository;
    @Autowired
    private LogisticianJPARepository logisticianJPARepository;
    @Autowired
    private CourierJPARepository courierJPARepository;

    @BeforeEach
    void setup() {
        employeeJPARepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        employeeJPARepository.deleteAll();
    }

    @Test
    @Transactional
    void should_return_list_of_employees() {
        //given
        LogisticianModel logisticianModel = logisticianJPARepository.saveAndFlush(getLogisticianModel());
        CourierModel courierModel = courierJPARepository.saveAndFlush(getCourierModel());
        //when & then
        Assertions.assertThat(sut.getList()).contains(logisticianModel).contains(courierModel);
    }

    @Test
    void should_find_active_employee_by_pesel() {
        //given
        LogisticianModel logisticianModel = logisticianJPARepository.saveAndFlush(getLogisticianModel());
        //when & then
        Assertions.assertThat(sut.findActiveByPesel(logisticianModel.getPesel()).size()).isOne();
    }

    @Test
    void should_not_find_inactive_employee_by_pesel() {
        //given
        LogisticianModel logisticianModel = getLogisticianModel();
        logisticianModel.setActive(false);
        logisticianModel = logisticianJPARepository.saveAndFlush(logisticianModel);
        //when & then
        Assertions.assertThat(sut.findActiveByPesel(logisticianModel.getPesel()).size()).isZero();
    }
}