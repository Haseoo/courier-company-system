package com.github.haseoo.courier.controllers.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.haseoo.courier.commandsdata.parcels.ParcelChangeStateMultipleCommandData;
import com.github.haseoo.courier.commandsdata.parcels.ParcelPickupCommandData;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.services.ports.CourierService;
import com.github.haseoo.courier.services.ports.ParcelStateService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCourierData;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin1", roles = "ADMIN")
@Tag(INTEGRATION_TEST)
public class CourierControllerTest {
    @MockBean
    private CourierService courierService;
    @MockBean
    private ParcelStateService parcelStateService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_200_and_list() throws Exception {
        //given
        when(courierService.getList()).thenReturn(Arrays.asList(getCourierData()));
        //when & then
        mockMvc.perform(get("/api/employee/courier"))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_200_and_ask_for_logistician_with_id() throws Exception {
        //given
        long id = 1L;
        when((courierService.getById(anyLong()))).thenReturn(getCourierData());
        //when
        mockMvc.perform(get("/api/employee/courier/" + id))
                .andExpect(status().isOk());
        //then
        verify(courierService).getById(id);
    }

    @Test
    void should_return_404_when_logistician_does_not_exist() throws Exception {
        //given
        long id = 1L;
        when(courierService.getById(anyLong())).thenThrow(new EmployeeNotFoundException(id));
        //when & then
        mockMvc.perform(get("/api/employee/courier/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_assign_parcels() throws Exception {
        //given
        final long id = 1L;
        ParcelChangeStateMultipleCommandData in = new ParcelChangeStateMultipleCommandData(Arrays.asList(1L, 2L, 3L));
        when(parcelStateService.assignParcelsToCourier(anyLong(), any())).thenReturn(getCourierData());
        //when
        mockMvc.perform(post("/api/employee/courier/" + id + "/parcelAssign").contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        //then
        verify(parcelStateService).assignParcelsToCourier(id, in.getParcelsIds());
    }

    @Test
    void should_pick_up_parcels() throws Exception {
//given
        final long id = 1L;
        ParcelPickupCommandData in = new ParcelPickupCommandData(2L, true);
        when(parcelStateService.setAsPickedByCourier(anyLong(), anyLong(), anyBoolean())).thenReturn(getCourierData());
        //when
        mockMvc.perform(post("/api/employee/courier/" + id + "/parcelPickup").contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        //then
        verify(parcelStateService).setAsPickedByCourier(id, in.getParcelsId(), in.isWasPaid());
    }
}
