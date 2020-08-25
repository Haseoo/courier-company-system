package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.InvalidPeselException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.InvalidPeselFormatException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.ActiveCourierExistsException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.models.CourierModel;
import com.github.haseoo.courier.models.EmployeeModel;
import com.github.haseoo.courier.repositories.ports.CourierRepository;
import com.github.haseoo.courier.repositories.ports.EmployeeRepository;
import com.github.haseoo.courier.servicedata.users.employees.CourierData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeEditOperationData;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.github.haseoo.courier.testutlis.ModelMapperConfig.ModelMapperConfig;
import static com.github.haseoo.courier.testutlis.constants.Constants.*;
import static com.github.haseoo.courier.testutlis.constants.UsersConstants.INVALID_PESEL;
import static com.github.haseoo.courier.testutlis.constants.UsersConstants.INVALID_PESEL_FORMAT;
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
    @Mock
    private PasswordEncoder passwordEncoder;
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
        CourierData out = sut.getById(FIRST_ID);
        //then
        Assertions.assertThat(out.getId()).isEqualTo(courierModel.getId());
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
        EmployeeAddOperationData in = getEmployeeOperationData();
        when(passwordEncoder.encode(any())).thenReturn(String.valueOf(in.getPassword()));
        ArgumentCaptor<CourierModel> argument = ArgumentCaptor.forClass(CourierModel.class);
        //when
        CourierData out = sut.add(in);
        //then
        verify(courierRepository).saveAndFlush(argument.capture());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(in.getName());
        Assertions.assertThat(argument.getValue().getSurname()).isEqualTo(in.getSurname());
        Assertions.assertThat(argument.getValue().getPesel()).isEqualTo(in.getPesel());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getPassword()).isEqualTo(in.getPassword());

        Assertions.assertThat(out.getName()).isEqualTo(in.getName());
        Assertions.assertThat(out.getSurname()).isEqualTo(in.getSurname());
        Assertions.assertThat(out.getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(out.getPesel()).isEqualTo(in.getPesel());
        Assertions.assertThat(out.getActive()).isTrue();
    }


    @Test
    void should_throw_exception_when_add_two_couriers_with_same_pesels() {
        //given
        EmployeeAddOperationData in = getEmployeeOperationData();
        when(employeeRepository.findActiveByPesel(any())).thenReturn(getEmployeeModelList());
        //when
        Assertions.assertThatThrownBy(() -> sut.add(in)).isExactlyInstanceOf(ActiveCourierExistsException.class);
    }

    @Test
    void should_edit_courier() {
        //given
        final String newName = "newName";
        final String newSurname = "newSurname";
        CourierModel courierModel = getCourierModel();
        courierModel.setId(FIRST_ID);
        when(courierRepository.getById(any())).thenReturn(Optional.of(courierModel));
        when(courierRepository.saveAndFlush(any())).thenReturn(getCourierModel());
        EmployeeEditOperationData in = EmployeeEditOperationData.builder()
                .name(newName)
                .surname(newSurname)
                .build();
        ArgumentCaptor<CourierModel> argument = ArgumentCaptor.forClass(CourierModel.class);
        //when
        sut.edit(FIRST_ID, in);
        //then
        verify(courierRepository).saveAndFlush(argument.capture());
        Assertions.assertThat(argument.getValue().getId()).isEqualTo(FIRST_ID);
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(newName);
        Assertions.assertThat(argument.getValue().getSurname()).isEqualTo(newSurname);
    }


    @Test
    void should_throw_exception_when_edit_courier_that_does_not_exits() {
        //given
        when(courierRepository.getById(any())).thenReturn(Optional.empty());
        EmployeeEditOperationData in = EmployeeEditOperationData.builder().build();
        //when & then
        Assertions.assertThatThrownBy(() -> sut.edit(FIRST_ID, in)).isExactlyInstanceOf(EmployeeNotFoundException.class);
    }


    @Test
    void should_throw_exception_when_invalid_pesel_format_on_add() {
        //given
        EmployeeAddOperationData in = EmployeeAddOperationData.builder()
                .userName("uName")
                .password(new char[0])
                .active(true)
                .name("")
                .surname("")
                .phoneNumber("")
                .pesel(INVALID_PESEL_FORMAT).build();
        // when & then
        Assertions.assertThatThrownBy(() -> sut.add(in))
                .isExactlyInstanceOf(InvalidPeselFormatException.class);
    }

    @Test
    void should_throw_exception_when_invalid_pesel_on_add() {
        //given
        EmployeeAddOperationData in = EmployeeAddOperationData.builder()
                .userName("uName")
                .password(new char[0])
                .active(true)
                .name("")
                .surname("")
                .phoneNumber("")
                .pesel(INVALID_PESEL).build();
        //when & then
        Assertions.assertThatThrownBy(() -> sut.add(in))
                .isExactlyInstanceOf(InvalidPeselException.class);
    }

    @Test
    void should_throw_exception_when_changing_pesel_that_already_exists() {
        //given
        CourierModel courierModel = getCourierModel();
        List<EmployeeModel> outList = Arrays.asList((EmployeeModel) courierModel);
        when(courierRepository.getById(any())).thenReturn(Optional.of(courierModel));
        when(employeeRepository.findActiveByPesel(any())).thenReturn(outList);
        EmployeeEditOperationData in = EmployeeEditOperationData.builder().pesel(INVALID_PESEL_FORMAT).build();
        //when & then
        Assertions.assertThatThrownBy(() -> sut.edit(FIRST_ID, in)).isExactlyInstanceOf(ActiveCourierExistsException.class);
    }

}