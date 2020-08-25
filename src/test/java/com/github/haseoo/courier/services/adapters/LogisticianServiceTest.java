package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.InvalidPeselException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.InvalidPeselFormatException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.ActiveCourierExistsException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.models.EmployeeModel;
import com.github.haseoo.courier.models.LogisticianModel;
import com.github.haseoo.courier.repositories.ports.EmployeeRepository;
import com.github.haseoo.courier.repositories.ports.LogisticianRepository;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeEditOperationData;
import com.github.haseoo.courier.servicedata.users.employees.LogisticianData;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static com.github.haseoo.courier.testutlis.ModelMapperConfig.ModelMapperConfig;
import static com.github.haseoo.courier.testutlis.constants.Constants.FIRST_ID;
import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.constants.UsersConstants.INVALID_PESEL;
import static com.github.haseoo.courier.testutlis.constants.UsersConstants.INVALID_PESEL_FORMAT;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class LogisticianServiceTest {
    @Mock
    private LogisticianRepository logisticianRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Spy
    private ModelMapper modelMapper = ModelMapperConfig();

    @InjectMocks
    private LogisticianServiceImpl sut;

    @Test
    void should_return_list() {
        //given
        when(logisticianRepository.getList()).thenReturn(Arrays.asList(getLogisticianModel()));
        //when & then
        Assertions.assertThat(sut.getList())
                .hasSize(1);
    }

    @Test
    void should_query_by_id() {
        //given
        final long id = 1L;
        LogisticianModel logisticianModel = getLogisticianModel();
        when(logisticianRepository.getById(any())).thenReturn(Optional.of(logisticianModel));
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        //when
        sut.getById(id);
        //then
        verify(logisticianRepository).getById(argument.capture());
        Assertions.assertThat(argument.getValue()).isEqualTo(id);
    }

    @Test
    void should_throw_employee_not_found_exception() {
        //given
        when(logisticianRepository.getById(any())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.getById(FIRST_ID)).isExactlyInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    void should_add_logistician() {
        //given
        when(logisticianRepository.saveAndFlush(any())).thenReturn(getLogisticianModel());
        EmployeeAddOperationData in = getEmployeeOperationData();
        when(passwordEncoder.encode(any())).thenReturn(String.valueOf(in.getPassword()));
        ArgumentCaptor<LogisticianModel> argument = ArgumentCaptor.forClass(LogisticianModel.class);
        //when
        LogisticianData out = sut.add(in);
        //then
        verify(logisticianRepository).saveAndFlush(argument.capture());
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
    void should_throw_exception_when_add_two_employees_with_same_pesels() {
        //given
        EmployeeAddOperationData in = getEmployeeOperationData();
        when(employeeRepository.findActiveByPesel(any())).thenReturn(getEmployeeModelList());
        //when
        Assertions.assertThatThrownBy(() -> sut.add(in)).isExactlyInstanceOf(ActiveCourierExistsException.class);
    }

    @Test
    void should_edit_logistician() {
        //given
        final String newName = "newName";
        final String newSurname = "newSurname";
        LogisticianModel logisticianModel = getLogisticianModel();
        logisticianModel.setId(FIRST_ID);
        when(logisticianRepository.getById(any())).thenReturn(Optional.of(logisticianModel));
        when(logisticianRepository.saveAndFlush(any())).thenReturn(getLogisticianModel());
        EmployeeEditOperationData in = EmployeeEditOperationData.builder()
                .name(newName)
                .surname(newSurname)
                .build();
        ArgumentCaptor<LogisticianModel> argument = ArgumentCaptor.forClass(LogisticianModel.class);
        //when
        sut.edit(FIRST_ID, in);
        //then
        verify(logisticianRepository).saveAndFlush(argument.capture());
        Assertions.assertThat(argument.getValue().getId()).isEqualTo(FIRST_ID);
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(newName);
        Assertions.assertThat(argument.getValue().getSurname()).isEqualTo(newSurname);
    }


    @Test
    void should_throw_exception_when_edit_logistician_that_does_not_exits() {
        //given
        when(logisticianRepository.getById(any())).thenReturn(Optional.empty());
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
        LogisticianModel logisticianModel = getLogisticianModel();
        List<EmployeeModel> outList = Arrays.asList((EmployeeModel) logisticianModel);
        when(logisticianRepository.getById(any())).thenReturn(Optional.of(logisticianModel));
        when(employeeRepository.findActiveByPesel(any())).thenReturn(outList);
        EmployeeEditOperationData in = EmployeeEditOperationData.builder().pesel(INVALID_PESEL_FORMAT).build();
        //when & then
        Assertions.assertThatThrownBy(() -> sut.edit(FIRST_ID, in)).isExactlyInstanceOf(ActiveCourierExistsException.class);
    }

    @Test
    void should_consume_all_logisticians() {
        //given
        List<Object> countList = new ArrayList<>();
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        when(logisticianRepository.getById(anyLong())).thenReturn(Optional.of(getLogisticianModel()));
        //when
        sut.consumeAllById(ids, countList::addAll);
        //then
        Assertions.assertThat(countList).hasSameSizeAs(ids);
    }

    @Test
    void should_trow_exception_when_trying_to_consume_not_existent_logistician() {
        //given
        Consumer<List<LogisticianModel>> consumer = e -> {
        };
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        when(logisticianRepository.getById(anyLong())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.consumeAllById(ids, consumer)).isExactlyInstanceOf(EmployeeNotFoundException.class);
    }

}