package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.ActiveCourierExistsException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.repositories.ports.CourierRepository;
import com.github.haseoo.courier.repositories.ports.EmployeeRepository;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.services.ports.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static com.github.haseoo.courier.testutlis.ModelMapperConfig.ModelMapperConfig;
import static com.github.haseoo.courier.testutlis.constants.Constants.*;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class CourierServiceTest {
    @Mock
    private CourierRepository courierRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private UserService userService;
    @Spy
    private ModelMapper modelMapper = ModelMapperConfig();

    @InjectMocks
    private CourierServiceImpl sut;

    @Test
    void should_return_list_of_couriers() {
        //given
        when(courierRepository.getList()).thenReturn(getCourierModelList());
        //when & then
        Assertions.assertThat(sut.getList())
                .hasSize(EXPECTED_LIST_SIZE_TWO_ELEMENTS)
                .hasAtLeastOneElementOfType(CourierData.class);
    }

    @Test
    void should_return_courier_by_id() {
        //given
        CourierModel courierModel = getCourierModel();
        when(courierRepository.getById(FIRST_ID)).thenReturn(Optional.of(courierModel));
        //when
        CourierData c = sut.getById(FIRST_ID);
        //then
        verify(courierRepository).getById(FIRST_ID);
    }

    @Test
    void should_throw_employee_not_found_exception() {
        //given
        when(courierRepository.getById(any())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.getById(FIRST_ID)).isExactlyInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    void should_add_courier() {
        //given
        when(courierRepository.saveAndFlush(any())).thenReturn(getCourierModel());
        EmployeeAddOperationData in = getCourierOperationData();
        ArgumentCaptor<CourierModel> argument = ArgumentCaptor.forClass(CourierModel.class);
        //when
        sut.add(in);
        //then
        verify(courierRepository).saveAndFlush(argument.capture());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(in.getName());
        Assertions.assertThat(argument.getValue().getSurname()).isEqualTo(in.getSurname());
        Assertions.assertThat(argument.getValue().getPesel()).isEqualTo(in.getPesel());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getPassword()).isEqualTo(in.getPassword());
    }

    @Test
    void should_throw_exception_when_add_two_couriers_with_same_pesels() {
        //given
        EmployeeAddOperationData in = getCourierOperationData();
        when(employeeRepository.findActiveByPesel(any())).thenReturn(getEmployeeModelList());
        //when
        Assertions.assertThatThrownBy(() -> sut.add(in)).isExactlyInstanceOf(ActiveCourierExistsException.class);
    }

/*TODO edit tests
    @Test
    void should_edit_courier() {
        //given
        CourierModel courierModel = getCourierModel();
        courierModel.setId(FIRST_ID);
        when(courierRepository.getById(any())).thenReturn(Optional.of(courierModel));
        when(courierRepository.saveAndFlush(any())).thenReturn(getCourierModel());
        CourierEditOperationData in = getCourierOperationData();
        ArgumentCaptor<CourierModel> argument = ArgumentCaptor.forClass(CourierModel.class);
        //when
        sut.edit(FIRST_ID, in);
        //then
        verify(courierRepository).saveAndFlush(argument.capture());
        Assertions.assertThat(argument.getValue().getId()).isEqualTo(FIRST_ID);
    }

    @Test
    void should_throw_exception_when_edit_courier_that_does_not_exits() {
        //given
        when(courierRepository.getById(any())).thenReturn(Optional.empty());
        CourierEditOperationData in = getCourierOperationData();
        //when & then
        Assertions.assertThatThrownBy(() -> sut.edit(FIRST_ID, in)).isExactlyInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    void should_throw_exception_when_invalid_pesel_format() {
        //given & when & then
        Assertions.assertThatThrownBy(() -> sut.add(CourierAddOperationData.builder().pesel(INVALID_PESEL_FORMAT).build()))
                .isExactlyInstanceOf(InvalidPeselFormatException.class);
    }

    @Test
    void should_throw_exception_when_invalid_pesel() {
        //given & when & then
        Assertions.assertThatThrownBy(() -> sut.add(CourierAddOperationData.builder().pesel(INVALID_PESEL).build()))
                .isExactlyInstanceOf(InvalidPeselException.class);
    }*/
}