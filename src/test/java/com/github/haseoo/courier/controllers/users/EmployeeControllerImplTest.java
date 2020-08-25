package com.github.haseoo.courier.controllers.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.haseoo.courier.commandsdata.users.employees.EmployeeAddCommandData;
import com.github.haseoo.courier.commandsdata.users.employees.EmployeeEditCommandData;
import com.github.haseoo.courier.enums.EmployeeType;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeAddOperationData;
import com.github.haseoo.courier.servicedata.users.employees.EmployeeEditOperationData;
import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.services.ports.LogisticianService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCourierData;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getLogisiticianData;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin1", roles = "ADMIN")
@Tag(INTEGRATION_TEST)
public class EmployeeControllerImplTest {
    @MockBean
    private CourierService courierService;

    @MockBean
    private LogisticianService logisticianService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void should_add_courier_and_return_201() throws Exception {
        //given
        EmployeeAddCommandData in = new EmployeeAddCommandData(
                EmployeeType.COURIER,
                "Test",
                "".toCharArray(),
                "TestName",
                "TestSurname",
                "123456789",
                "97022593432"
        );
        when(courierService.add(any())).thenReturn(getCourierData());
        ArgumentCaptor<EmployeeAddOperationData> argument = ArgumentCaptor.forClass(EmployeeAddOperationData.class);
        //when
        mockMvc.perform(post("/api/employee").contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isCreated());
        verify(courierService).add(argument.capture());
        //then
        Assertions.assertThat(argument.getValue().getUserName()).isEqualTo(in.getUserName());
        Assertions.assertThat(argument.getValue().getPassword()).isEqualTo(in.getPassword());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(in.getName());
        Assertions.assertThat(argument.getValue().getSurname()).isEqualTo(in.getSurname());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getPesel()).isEqualTo(in.getPesel());
        Assertions.assertThat(argument.getValue().getActive()).isTrue();
    }

    @Test
    void should_edit_courier_and_return_200() throws Exception {
        //given
        long id = 1L;
        EmployeeEditCommandData in = new EmployeeEditCommandData(
                EmployeeType.COURIER,
                "".toCharArray(),
                "TestName",
                "TestSurname",
                "123456789",
                "97022593432"
        );
        when(courierService.edit(anyLong(), any())).thenReturn(getCourierData());
        ArgumentCaptor<EmployeeEditOperationData> argument = ArgumentCaptor.forClass(EmployeeEditOperationData.class);
        //when
        mockMvc.perform(put("/api/employee/" + id).contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        verify(courierService).edit(eq(id), argument.capture());
        //then
        Assertions.assertThat(argument.getValue().getPassword()).isEqualTo(in.getPassword());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(in.getName());
        Assertions.assertThat(argument.getValue().getSurname()).isEqualTo(in.getSurname());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getPesel()).isEqualTo(in.getPesel());
    }

    @Test
    void should_add_logisitician_and_return_201() throws Exception {
        //given
        EmployeeAddCommandData in = new EmployeeAddCommandData(
                EmployeeType.LOGISTICIAN,
                "Test",
                "".toCharArray(),
                "TestName",
                "TestSurname",
                "123456789",
                "97022593432"
        );
        when(logisticianService.add(any())).thenReturn(getLogisiticianData());
        ArgumentCaptor<EmployeeAddOperationData> argument = ArgumentCaptor.forClass(EmployeeAddOperationData.class);
        //when
        mockMvc.perform(post("/api/employee").contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isCreated());
        verify(logisticianService).add(argument.capture());
        //then
        Assertions.assertThat(argument.getValue().getUserName()).isEqualTo(in.getUserName());
        Assertions.assertThat(argument.getValue().getPassword()).isEqualTo(in.getPassword());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(in.getName());
        Assertions.assertThat(argument.getValue().getSurname()).isEqualTo(in.getSurname());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getPesel()).isEqualTo(in.getPesel());
        Assertions.assertThat(argument.getValue().getActive()).isTrue();
    }

    @Test
    void should_edit_logistician_and_return_200() throws Exception {
        //given
        long id = 1L;
        EmployeeEditCommandData in = new EmployeeEditCommandData(
                EmployeeType.LOGISTICIAN,
                "".toCharArray(),
                "TestName",
                "TestSurname",
                "123456789",
                "97022593432"
        );
        when(logisticianService.edit(anyLong(), any())).thenReturn(getLogisiticianData());
        ArgumentCaptor<EmployeeEditOperationData> argument = ArgumentCaptor.forClass(EmployeeEditOperationData.class);
        //when
        mockMvc.perform(put("/api/employee/" + id).contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        verify(logisticianService).edit(eq(id), argument.capture());
        //then
        Assertions.assertThat(argument.getValue().getPassword()).isEqualTo(in.getPassword());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(in.getName());
        Assertions.assertThat(argument.getValue().getSurname()).isEqualTo(in.getSurname());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getPesel()).isEqualTo(in.getPesel());
    }
}
