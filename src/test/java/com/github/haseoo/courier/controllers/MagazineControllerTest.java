package com.github.haseoo.courier.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.haseoo.courier.commandsdata.parcels.ParcelChangeStateMultipleCommandData;
import com.github.haseoo.courier.commandsdata.places.*;
import com.github.haseoo.courier.enums.MagazineParcelFilterType;
import com.github.haseoo.courier.exceptions.serviceexceptions.MagazineDoesNotExist;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
import com.github.haseoo.courier.servicedata.places.MagazineAddOperationData;
import com.github.haseoo.courier.servicedata.places.MagazineEditOperationData;
import com.github.haseoo.courier.services.ports.ClientCompanyService;
import com.github.haseoo.courier.services.ports.MagazineService;
import com.github.haseoo.courier.services.ports.ParcelStateService;
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

import java.util.Arrays;
import java.util.Collections;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.MagazineDataGenerator.getMagazineData;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelInMagazineData;
import static com.github.haseoo.courier.testutlis.generators.UserControllerTestDataGenerator.getClientCompanyData;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin1", roles = "ADMIN")
@Tag(INTEGRATION_TEST)
public class MagazineControllerTest {
    @MockBean
    private MagazineService magazineService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private ParcelStateService parcelStateService;

    @MockBean
    private ClientCompanyService clientCompanyService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void should_query_for_all_magazines_when_admin_is_logged() throws Exception {
        //given
        when(userDetailsService.isCurrentUserAdmin()).thenReturn(true);
        when(magazineService.getList()).thenReturn(Collections.singletonList(getMagazineData()));
        //when
        mockMvc.perform(get("/api/magazine"))
                .andExpect(status().isOk());
        //then
        verify(magazineService).getList();
        verify(magazineService, never()).getActiveList();
    }

    @Test
    void should_query_for_active_magazines_when_admin_is_not_logged() throws Exception {
        //given
        when(userDetailsService.isCurrentUserAdmin()).thenReturn(false);
        when(magazineService.getList()).thenReturn(Collections.singletonList(getMagazineData()));
        //when
        mockMvc.perform(get("/api/magazine"))
                .andExpect(status().isOk());
        //then
        verify(magazineService).getActiveList();
        verify(magazineService, never()).getList();
    }

    @Test
    void should_return_200_and_magazine_by_id() throws Exception {
        //given
        final long id = 1L;
        when(magazineService.getById(anyLong())).thenReturn(getMagazineData());
        //when
        mockMvc.perform(get("/api/magazine/" + id))
                .andExpect(status().isOk());
        //then
        verify(magazineService).getById(id);
    }

    @Test
    void should_return_404_when_magazine_does_not_exist() throws Exception {
        //given
        final long id = 1L;
        when(magazineService.getById(anyLong())).thenThrow(new MagazineDoesNotExist(id));
        //given & then
        mockMvc.perform(get("/api/magazine/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_add_magazine_and_return_201() throws Exception {
        //given
        AddressCommandData ain = new AddressCommandData(
                "testCity",
                "TestStreet",
                "TT-TTT",
                "T1",
                "T1"
        );
        MagazineAddCommandData in = new MagazineAddCommandData(ain);
        ArgumentCaptor<MagazineAddOperationData> argument = ArgumentCaptor.forClass(MagazineAddOperationData.class);
        when(magazineService.add(any())).thenReturn(getMagazineData());
        //when
        mockMvc.perform(post("/api/magazine").contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isCreated());
        verify(magazineService).add(argument.capture());
        //then
        Assertions.assertThat(argument.getValue().getAddress().getCity()).isEqualTo(ain.getCity());
        Assertions.assertThat(argument.getValue().getAddress().getStreet()).isEqualTo(ain.getStreet());
        Assertions.assertThat(argument.getValue().getAddress().getPostalCode()).isEqualTo(ain.getPostalCode());
        Assertions.assertThat(argument.getValue().getAddress().getBuildingNumber()).isEqualTo(ain.getBuildingNumber());
        Assertions.assertThat(argument.getValue().getAddress().getFlatNumber()).isEqualTo(ain.getFlatNumber());
    }

    @Test
    void should_edit_magazine_and_return_200() throws Exception {
        //given
        final long id = 1L;
        AddressCommandData ain = new AddressCommandData(
                "testCity",
                "TestStreet",
                "TT-TTT",
                "T1",
                "T1"
        );
        MagazineEditCommandData in = new MagazineEditCommandData(ain, false);
        ArgumentCaptor<MagazineEditOperationData> argument = ArgumentCaptor.forClass(MagazineEditOperationData.class);
        when(magazineService.edit(anyLong(), any())).thenReturn(getMagazineData());
        //when
        mockMvc.perform(put("/api/magazine/" + id).contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        //then
        verify(magazineService).edit(eq(id), argument.capture());
        Assertions.assertThat(argument.getValue().getAddress().getCity()).isEqualTo(ain.getCity());
        Assertions.assertThat(argument.getValue().getAddress().getStreet()).isEqualTo(ain.getStreet());
        Assertions.assertThat(argument.getValue().getAddress().getPostalCode()).isEqualTo(ain.getPostalCode());
        Assertions.assertThat(argument.getValue().getAddress().getBuildingNumber()).isEqualTo(ain.getBuildingNumber());
        Assertions.assertThat(argument.getValue().getAddress().getFlatNumber()).isEqualTo(ain.getFlatNumber());
        Assertions.assertThat(argument.getValue().getActive()).isFalse();
    }

    @Test
    void should_add_logistician_and_return_200() throws Exception {
        //given
        final long id = 1L;
        final MagazineAddLogisiticiansCommandData in = new MagazineAddLogisiticiansCommandData(Arrays.asList(1L, 2L, 3L));
        when(magazineService.addLogisitcians(anyLong(), anyList())).thenReturn(getMagazineData());
        //when
        mockMvc.perform(put("/api/magazine/" + id + "/logisticians").contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        //then
        verify(magazineService).addLogisitcians(eq(id), eq(in.getLogisiticiansIds()));
    }

    @Test
    void should_add_parcels_and_return_200() throws Exception {
        //given
        final long id = 1L;
        final ParcelChangeStateMultipleCommandData in = new ParcelChangeStateMultipleCommandData(Arrays.asList(1L, 2L, 3L));
        when(parcelStateService.addParcelsToMagazine(anyLong(), anyList())).thenReturn(getMagazineData());
        //when
        mockMvc.perform(post("/api/magazine/" + id + "/parcels/add").contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        //then
        verify(parcelStateService).addParcelsToMagazine(eq(id), eq(in.getParcelsIds()));
    }

    @Test
    void should_return_200_and_ask_for_parcels_in_magazine() throws Exception {
        //given
        final long id = 1L;
        when(parcelStateService.addParcelsToMagazine(anyLong(), anyList())).thenReturn(getMagazineData());
        when(magazineService.getAssignedAtSenderParcels(anyLong())).thenReturn(Arrays.asList(getParcelInMagazineData(),
                getParcelInMagazineData()));
        MagazineParcelFilerCommandData in = new MagazineParcelFilerCommandData(MagazineParcelFilterType.ASSIGNED_TO_MAGAZINE);
        when(clientCompanyService.getById(anyLong())).thenReturn(getClientCompanyData());
        //when
        mockMvc.perform(post("/api/magazine/" + id + "/parcels").contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        //then
        verify(magazineService).getAssignedAtSenderParcels(id);
    }

    @Test
    void should_return_200_and_ask_for_parcels_to_return() throws Exception {
        //given
        final long id = 1L;
        when(parcelStateService.addParcelsToMagazine(anyLong(), anyList())).thenReturn(getMagazineData());
        when(magazineService.getAssignedAtSenderParcels(anyLong())).thenReturn(Arrays.asList(getParcelInMagazineData(),
                getParcelInMagazineData()));
        MagazineParcelFilerCommandData in = new MagazineParcelFilerCommandData(MagazineParcelFilterType.TO_RETURN);
        when(clientCompanyService.getById(anyLong())).thenReturn(getClientCompanyData());
        //when
        mockMvc.perform(post("/api/magazine/" + id + "/parcels").contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        //then
        verify(magazineService).getParcelsToReturn(id);
    }

}
