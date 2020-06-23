package com.github.haseoo.courier.controllers.parcels;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.haseoo.courier.commandsdata.parcels.ParcelTypeCommandAddData;
import com.github.haseoo.courier.commandsdata.parcels.ParcelTypeCommandEditData;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelTypeNotFound;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeAddOperationData;
import com.github.haseoo.courier.servicedata.parcels.ParcelTypeEditOperationData;
import com.github.haseoo.courier.services.ports.ParcelTypeService;
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

import java.math.BigDecimal;
import java.util.Arrays;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelTypeData;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin1", roles = "ADMIN")
@Tag(INTEGRATION_TEST)
class ParcelTypeControllerTest {
    @MockBean
    private ParcelTypeService parcelTypeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_200_and_all_types() throws Exception {
        //given
        when(parcelTypeService.getList(anyBoolean())).thenReturn(Arrays.asList(getParcelTypeData()));
        //when & then
        mockMvc.perform(get("/api/parcelType"))
                .andExpect(status().isOk());
        verify(parcelTypeService).getList(false);
    }

    @Test
    void should_return_200_and_only_offer() throws Exception {
        //given
        when(parcelTypeService.getList(anyBoolean())).thenReturn(Arrays.asList(getParcelTypeData()));
        //when & then
        mockMvc.perform(get("/api/parcelType/offer"))
                .andExpect(status().isOk());
        verify(parcelTypeService).getList(true);
    }

    @Test
    void should_return_200_and_offer_with_given_id() throws Exception {
        //given
        final long id = 1L;
        when(parcelTypeService.getById(anyLong())).thenReturn(getParcelTypeData());
        //when & then
        mockMvc.perform(get("/api/parcelType/" + id))
                .andExpect(status().isOk());
        verify(parcelTypeService).getById(id);
    }

    @Test
    void should_return_400_when_type_not_exist() throws Exception {
        //given
        final long id = 1L;
        when(parcelTypeService.getById(anyLong())).thenThrow(new ParcelTypeNotFound(id));
        //when & then
        mockMvc.perform(get("/api/parcelType/" + id))
                .andExpect(status().isNotFound());
        verify(parcelTypeService).getById(id);
    }

    @Test
    void should_add_type() throws Exception{
        //given
        ParcelTypeCommandAddData in = new ParcelTypeCommandAddData("Test", "test", BigDecimal.TEN);
        when(parcelTypeService.add(any())).thenReturn(getParcelTypeData());
        ArgumentCaptor<ParcelTypeAddOperationData> argument = ArgumentCaptor.forClass(ParcelTypeAddOperationData.class);
        //when & then
        mockMvc.perform(put("/api/parcelType").contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        verify(parcelTypeService).add(argument.capture());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(in.getName());
        Assertions.assertThat(argument.getValue().getDescription()).isEqualTo(in.getDescription());
        Assertions.assertThat(argument.getValue().getPrice()).isEqualTo(in.getPrice());
        Assertions.assertThat(argument.getValue().getActive()).isTrue();
    }

    @Test
    void should_edit_type() throws Exception{
        //given
        final long id = 1L;
        ParcelTypeCommandEditData in = new ParcelTypeCommandEditData("Test", "test", BigDecimal.TEN, false);
        when(parcelTypeService.edit(anyLong(), any())).thenReturn(getParcelTypeData());
        ArgumentCaptor<ParcelTypeEditOperationData> argument = ArgumentCaptor.forClass(ParcelTypeEditOperationData.class);
        //when & then
        mockMvc.perform(post("/api/parcelType/" + id).contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        verify(parcelTypeService).edit(eq(id), argument.capture());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(in.getName());
        Assertions.assertThat(argument.getValue().getDescription()).isEqualTo(in.getDescription());
        Assertions.assertThat(argument.getValue().getPrice()).isEqualTo(in.getPrice());
        Assertions.assertThat(argument.getValue().getActive()).isFalse();
    }
}