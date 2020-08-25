package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.repositories.ports.EmployeeRepository;
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

import java.util.Arrays;
import java.util.Optional;

import static com.github.haseoo.courier.testutlis.ModelMapperConfig.ModelMapperConfig;
import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCourierModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper = ModelMapperConfig();

    @InjectMocks
    private EmployeeServiceImpl sut;

    @Test
    void should_return_list_with_one_element() {
        //given
        when(employeeRepository.getList()).thenReturn(Arrays.asList(getCourierModel()));
        //when & then
        Assertions.assertThat(sut.getList().size()).isOne();
    }

    @Test
    void should_query_by_id() {
        //given
        final long id = 1L;
        when(employeeRepository.getById(any())).thenReturn(Optional.of(getCourierModel()));
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        //when
        sut.getById(id);
        verify(employeeRepository).getById(argument.capture());
        //then
        Assertions.assertThat(argument.getValue()).isEqualTo(id);
    }

    @Test
    void should_throw_exception_when_employee_does_not_exist() {
        //given
        when(employeeRepository.getById(any())).thenReturn(Optional.empty());
        //when & then
        Assertions.assertThatThrownBy(() -> sut.getById(1L)).isExactlyInstanceOf(EmployeeNotFoundException.class);
    }
}